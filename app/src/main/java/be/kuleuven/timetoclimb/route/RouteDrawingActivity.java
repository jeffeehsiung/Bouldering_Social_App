package be.kuleuven.timetoclimb.route;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRouteDrawingBinding;
import be.kuleuven.timetoclimb.toolsInterface.CommonBitmap;
import be.kuleuven.timetoclimb.toolsInterface.imageResolver;

public class RouteDrawingActivity extends AppCompatActivity implements imageResolver {

    private ActivityRouteDrawingBinding binding;
    private RelativeLayout parentRelative;
    private RelativeLayout imageViewRelatve;
    private Button btnshowimage, btnsaveimage;
    private ImageView backgroundImageView, imageResult;
    private Bitmap resizedBM;
    private User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        binding = ActivityRouteDrawingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.parentRelative = binding.parentRelative;
        this.imageViewRelatve = binding.imageViewRelatve;
        this.imageResult = binding.imageResult;
        this.btnsaveimage = binding.btnsaveimage;
        this.btnshowimage = binding.btnshowimage;
        this.backgroundImageView = binding.backgroundImageView;
        this.user = (User) getIntent().getSerializableExtra("User");

        /*
        Specifies how a view is positioned within a RelativeLayout.
        The relative layout containing the view uses the value of these layout parameters to determine where to position the view on the screen.
        If the view is not contained within a relative layout, these attributes are ignored
        we are using constructor: LayoutParams(int w, int h)
         */

        resizedBM = CommonBitmap.bitmap;
        backgroundImageView.setImageBitmap(resizedBM);

        imageViewRelatve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Integer x = (int) event.getX(); // get x-Coordinate
                    Integer y = (int) event.getY();  // get y-Coordinate
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // Assuming you use a RelativeLayout
                    ImageView imageView = new ImageView(getApplicationContext());
                    layoutParams.setMargins(x, y, 0, 0);// set margins
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageDrawable(getResizedDrawble(getApplicationContext(),R.drawable.placeholder,70,70)); // set the image from drawable
                    imageView.setOnTouchListener(MoveOnTouchListener());
                    imageViewArrayList.add(imageView);
                    System.out.println("size of imageViewArrayList: "+ imageViewArrayList.size());
                    imageViewRelatve.addView(imageView); // add a View programmatically to the ViewGroup
                }
                return true;
            }
        });

        btnshowimage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                System.out.println("hope that this imageResult wont be null?");
                CommonBitmap.bitmap = ProcessingBitmap(backgroundImageView, resizedBM,imageViewArrayList);
                imageResult.setImageBitmap(CommonBitmap.bitmap);
            }
        });

        btnsaveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RouteDrawingActivity.this, RouteCreateActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

    }

    private View.OnTouchListener MoveOnTouchListener() {
        return new View.OnTouchListener() {
            int xDelta, yDelta;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                parentRelative.invalidate();
                return true;
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap ProcessingBitmap(ImageView imageResult, Bitmap resizedBM, ArrayList<ImageView> imarrayList){

        //create a new blank bitma background and attach a brand new canvas to it
        //ARGB_8888 meanings 4 bytes
        Bitmap bitmapBackgroud = Bitmap.createBitmap(resizedBM.getWidth(),resizedBM.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapBackgroud);
        System.out.println(String.format("bitmapBackgroud created: height is %d, width is %d",bitmapBackgroud.getHeight(),bitmapBackgroud.getWidth()));
        //set the paint for line between points on the canvas
        Paint linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(5);

        //draw the orginal fetched image bitmap into the canvas from the start point [0,0]
        canvas.drawBitmap(resizedBM,0,0,null);
        System.out.println("resizedBM succeeded");


        imarrayList.forEach(ivCurr -> {
            System.out.println("ivCurr toString? : " + ivCurr.toString());
            // draw everything else you want into the
            canvas.drawBitmap(imageViewToBitmap(ivCurr),ivCurr.getX(),ivCurr.getY(),null);

            //take care of the point order, if the index > 0, we draw a line from index n-1 to n
            if(imarrayList.indexOf(ivCurr) > 0){
                System.out.println("ivCurr index > 0");
                //access last iv
                int lastivIndex = imarrayList.indexOf(ivCurr)-1;

                //store previous iv x,y and current iv x,y

                ImageView ivPrevious = imarrayList.get(lastivIndex);
                float startX = (ivPrevious.getX() + ivPrevious.getWidth()/2);
                float startY = (ivPrevious.getY() + ivPrevious.getHeight());
                float endX = (ivCurr.getX() + ivCurr.getWidth()/2);
                float endY = (ivCurr.getY() + ivCurr.getHeight());

                //draw a line segment with specified start and stop x,y coordindates using specified paint
                canvas.drawLine(startX,startY,endX,endY,linePaint);
            }
        });
        return bitmapBackgroud;
    }
}