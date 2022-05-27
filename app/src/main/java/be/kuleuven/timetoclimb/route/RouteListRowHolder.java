package be.kuleuven.timetoclimb.route;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.kuleuven.timetoclimb.R;

public class RouteListRowHolder extends RecyclerView.ViewHolder {

    TextView tvHallName, tvRouteNo, tvDescription;
    RatingBar rtGrade;
    ImageView ivRoutePicture;
    public RouteListRowHolder(@NonNull View itemView) {
        super(itemView);
        System.out.println("RouteListRowHolder called");
        tvHallName = itemView.findViewById(R.id.tvHallName);
        tvRouteNo = itemView.findViewById(R.id.tvRouteNo);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        rtGrade = itemView.findViewById(R.id.rtGrade);
        ivRoutePicture = itemView.findViewById(R.id.ivRoutePicture);
    }
}