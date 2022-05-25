package be.kuleuven.timetoclimb.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRouteDrawingBinding;
import be.kuleuven.timetoclimb.subActivity.imageResolver;

public class RouteDrawingActivity extends AppCompatActivity implements imageResolver {

    private ActivityRouteDrawingBinding binding;
    private RelativeLayout relativeLayout;
    private User user = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRouteDrawingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.relativeLayout = binding.parentRelative;
        this.user = (User) getIntent().getSerializableExtra("User");

        /*
        Specifies how a view is positioned within a RelativeLayout.
        The relative layout containing the view uses the value of these layout parameters to determine where to position the view on the screen.
        If the view is not contained within a relative layout, these attributes are ignored
        we are using constructor: LayoutParams(int w, int h)
         */

        relativeLayout.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Integer x = (int) event.getRawX(); // get x-Coordinate
                Integer y = (int) event.getRawY();  // get y-Coordinate
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // Assuming you use a RelativeLayout
                ImageView imageView = new ImageView(getApplicationContext());
                layoutParams.setMargins(x, y, 0, 0);// set margins
                imageView.setLayoutParams(layoutParams);
                imageView.setImageDrawable(getResizedDrawble(getApplicationContext(),R.drawable.placeholder,100,100)); // set the image from drawable
                binding.getRoot().addView(imageView); // add a View programmatically to the ViewGroup
            }
            return true;
        });
    }
}