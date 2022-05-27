package be.kuleuven.timetoclimb.route;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Comment;

import java.util.List;

import be.kuleuven.timetoclimb.CommentItem;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;

public class RouteDetailRVAdapter {

    private Context context;
    private List<CommentItem> commentItemList;
    private RecyclerView commentsRecyclerView;
    private User user;
    private Route route;

    public RouteDetailRVAdapter (Context context, List<CommentItem> commentItemList, RecyclerView commentsRecyclerView, User user, Route route){
        this.context = context;
        this.commentsRecyclerView = commentsRecyclerView;
        this.commentItemList = commentItemList;
        this.route = route;
        this.user = user;
    }


    //define view holder
    //define what to inflate for the master viewHolder's inflater on the parent root layout, which is a view group, for each position
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentVG, int viewPosition) {

        //simplified LayoutInflater inflate = LayoutInflater.from(context) to LayoutInflater.from(context).inflate(layoutID,rootView,attachtoRoot)
        View view = LayoutInflater.from(parentVG.getContext()).inflate(R.layout.comment_list_item,parentVG, false);
        return new CommentItemListRowHolder(view);

    }

    //implements the method of RecyclerView.Adapter<RecyclerView.ViewHolder>
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        //get the route given from the index of the routeList populated from DB resposne
        CommentItem commentItem = commentItemList.get(position);
        viewHolder.tvHallName.setText(""+"Hall ID: " + route.getHallName());
        viewHolder.tvRouteNo.setText("Route number: "+ route.getRouteNO());
        viewHolder.tvDescription.setText(""+route.getDescription());
        viewHolder.rtGrade.setRating(route.getGrade());
        if (route.getRoutePicture()==null){
            Bitmap defaultBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.route);
            viewHolder.ivRoutePicture.setImageBitmap(defaultBitmap);
        }
        else{ viewHolder.ivRoutePicture.setImageBitmap(StringToBitmap(route.getRoutePicture()));}

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;}
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    //inner classes
    //single item view holders  & instantiation of each layout item
    public class CommentItemListRowHolder extends RecyclerView.ViewHolder {

        TextView tvHallName, tvRouteNo, tvDescription;
        RatingBar rtGrade;
        ImageView ivRoutePicture;

        public CommentItemListRowHolder(@NonNull View itemView) {
            super(itemView);
            tvHallName = itemView.findViewById(R.id.tvHallName);
            tvRouteNo = itemView.findViewById(R.id.tvRouteNo);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            rtGrade = itemView.findViewById(R.id.rtGrade);
            ivRoutePicture = itemView.findViewById(R.id.ivRoutePicture);
        }
    }
}

