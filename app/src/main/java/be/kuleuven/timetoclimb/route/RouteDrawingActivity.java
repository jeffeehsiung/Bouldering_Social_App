package be.kuleuven.timetoclimb.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRouteDrawingBinding;
import be.kuleuven.timetoclimb.subActivity.imageResolver;

public class RouteDrawingActivity extends AppCompatActivity implements imageResolver {

    private ActivityRouteDrawingBinding binding;
    private RelativeLayout parentRelative;
    private FrameLayout imageViewFrameLayout;
    private Button btnsaveimage;
    private ImageView backgroundImageView, imageResult;
    private Uri uriSource;
    private User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        binding = ActivityRouteDrawingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.parentRelative = binding.parentRelative;
        this.imageViewFrameLayout = binding.imageViewFrameLayout;
        this.imageResult = binding.imageResult;
        this.btnsaveimage = binding.btnsaveimage;
        this.backgroundImageView = binding.backgroundImageView;
        this.user = (User) getIntent().getSerializableExtra("User");

        /*
        Specifies how a view is positioned within a RelativeLayout.
        The relative layout containing the view uses the value of these layout parameters to determine where to position the view on the screen.
        If the view is not contained within a relative layout, these attributes are ignored
        we are using constructor: LayoutParams(int w, int h)
         */

        //temporarily set backgroundImageView
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cave);
        backgroundImageView.setImageBitmap(bitmap);

        imageViewFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Integer x = (int) event.getX(); // get x-Coordinate
                    Integer y = (int) event.getY();  // get y-Coordinate
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // Assuming you use a RelativeLayout
                    ImageView imageView = new ImageView(getApplicationContext());
                    layoutParams.setMargins(x, y, 0, 0);// set margins
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageDrawable(getResizedDrawble(getApplicationContext(),R.drawable.placeholder,100,100)); // set the image from drawable
                    imageView.setOnTouchListener(MoveOnTouchListener());
                    imageViewArrayList.add(imageView);
                    System.out.println("size of imageViewArrayList: "+ imageViewArrayList.size());
                    imageViewFrameLayout.addView(imageView); // add a View programmatically to the ViewGroup
                }
                return true;
            }
        });

        btnsaveimage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                ProcessingBitmap(backgroundImageView,imageViewArrayList);
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
    private Bitmap ProcessingBitmap(ImageView backgroundImageView, ArrayList<ImageView> imarrayList){

        //create a new blank bitma background and attach a brand new canvas to it
        //ARGB_8888 meanings 4 bytes
        Bitmap bitmapBackgroud = Bitmap.createBitmap(backgroundImageView.getWidth(),backgroundImageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapBackgroud);
        System.out.println(String.format("bitmapBackgroud created: height is %d, width is %d",(int)bitmapBackgroud.getHeight(),(int)bitmapBackgroud.getWidth()));
        //set the paint for line between points on the canvas
        Paint linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(5);

        //draw the orginal fetched image bitmap into the canvas from the start point [0,0]
        canvas.drawBitmap(imageViewToBitmap(backgroundImageView),0,0,null);
        System.out.println("imageViewToBitmap(backgroundImageView) succeeded");

        imarrayList.forEach(ivCurr -> {
            System.out.println("ivCurr toString? : " + ivCurr.toString());
            // draw everything else you wnat into the
            canvas.drawBitmap(imageViewToBitmap(ivCurr),ivCurr.getX(),ivCurr.getY(),null);

            //take care of the point order, if the index > 0, we draw a line from index n-1 to n
            if(imarrayList.indexOf(ivCurr) > 0){
                System.out.println("ivCurr index > 0");
                //access last iv
                int lastivIndex = imarrayList.indexOf(ivCurr)-1;

                //store previous iv x,y and current iv x,y
                ImageView ivPrevious = imarrayList.get(lastivIndex);
                float startX = ivPrevious.getX() + ivPrevious.getWidth()/2;
                float startY = ivPrevious.getY() + ivPrevious.getHeight();
                float endX = ivCurr.getX() + ivCurr.getWidth()/2;
                float endY = ivCurr.getY() + ivCurr.getHeight();

                //draw a line segment with specified start and stop x,y coordindates using specified paint
                canvas.drawLine(startX,startY,endX,endY,linePaint);
            }
        });

        System.out.println("hope that this imageResult wont be null?");
        imageResult.setImageBitmap(bitmapBackgroud);
        return bitmapBackgroud;
    }
}