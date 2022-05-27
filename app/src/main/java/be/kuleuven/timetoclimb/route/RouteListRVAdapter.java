package be.kuleuven.timetoclimb.route;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.toolsInterface.imageResolver;

public class RouteListRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements imageResolver {

    //declare parameters for constructor to store in
    private Context context;
    private List<Route> routeList;
    private RecyclerView routeListRecyclerView;
    private User user;
    private final View.OnClickListener onClickListener = new RouteOnClickListener();


    //constructor of adapter
    public RouteListRVAdapter(Context context,List<Route> routeList,RecyclerView routeListRecyclerView, User user){
        this.context = context;
        this.routeListRecyclerView = routeListRecyclerView;
        this.routeList = routeList;
        this.user = user;
    }
    //define view holder
    //define what to inflate for the master viewHolder's inflater on the parent root layout, which is a view group, for each position
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentVG, int viewPosition) {

        //simplified LayoutInflater inflate = LayoutInflater.from(context) to LayoutInflater.from(context).inflate(layoutID,rootView,attachtoRoot)
        if (viewPosition == 0) {
            View view = LayoutInflater.from(parentVG.getContext()).inflate(R.layout.activity_adno_view, parentVG, false);
            return new RouteListAdHolder(view);
        } else {
            View view = LayoutInflater.from(parentVG.getContext()).inflate(R.layout.activity_routeno_view,parentVG, false);
            return new RouteListRowHolder(view);
        }
    }

    //implements the method of RecyclerView.Adapter<RecyclerView.ViewHolder>
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof RouteListAdHolder){
            //wait till there's an ad that wishes to buy this banner hahahahah
            //then we will do something for them
            //Toast.makeText(context,"unformtunately no ads yet",Toast.LENGTH_SHORT).show();
        }
        else if (viewHolder instanceof RouteListRowHolder){
            //get the route given from the index of the routeList populated from DB resposne
            Route route = routeList.get(position);
            ((RouteListRowHolder) viewHolder).tvHallName.setText(""+"Hall ID: " + route.getHallName());
            ((RouteListRowHolder) viewHolder).tvRouteNo.setText("Route number: "+ route.getRouteNO());
            ((RouteListRowHolder) viewHolder).tvDescription.setText(""+route.getDescription());
            ((RouteListRowHolder) viewHolder).rtGrade.setRating(route.getGrade());
            if (route.getRoutePicture() == null){
                Bitmap defaultBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.route);
                ((RouteListRowHolder) viewHolder).ivRoutePicture.setImageBitmap(defaultBitmap);
            }
            else{ ((RouteListRowHolder) viewHolder).ivRoutePicture.setImageBitmap(StringToBitmap(route.getRoutePicture()));}
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            System.out.println("getItemViewType position: " + 1);
            return 1;}
        System.out.println("getItemViewType position: " + position % 3);
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return routeList.size() + (routeList.size()/3);
    }

    //inner classes
    //single item view holders  & instantiation of each layout item
    public class RouteListAdHolder extends RecyclerView.ViewHolder {

        public RouteListAdHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class RouteListRowHolder extends RecyclerView.ViewHolder {

        TextView tvHallName, tvRouteNo, tvDescription;
        RatingBar rtGrade;
        ImageView ivRoutePicture;
        public RouteListRowHolder(@NonNull View itemView) {
            super(itemView);
            tvHallName = itemView.findViewById(R.id.tvHallName);
            tvRouteNo = itemView.findViewById(R.id.tvRouteNo);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            rtGrade = itemView.findViewById(R.id.rtGrade);
            ivRoutePicture = itemView.findViewById(R.id.ivRoutePicture);
            itemView.setOnClickListener(onClickListener);
        }
    }

    private class RouteOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View viewItem) {
            // viewItem being the view contained in the viewHolder
            // pass the view in to parentRecyclerView.getChildLayoutPosition(View childview) to identify viewItem's index
            int itemPosition = routeListRecyclerView.getChildLayoutPosition(viewItem);
            //implement method to allow transitions to routeDetail View
            String hallName = routeList.get(itemPosition).getHallName();
            String routeNo = String.valueOf(routeList.get(itemPosition).getRouteNO());

            //to RouteDetailActivity
            Intent intent = new Intent(context,RouteDetailViewActivity.class);
            intent.putExtra("User",user);
            intent.putExtra("Route",routeList.get(itemPosition));
            context.startActivity(intent);

            Toast.makeText(context, "hallName: "+ hallName + " routeNo: "+ routeNo, Toast.LENGTH_SHORT).show();
        }
    }

}