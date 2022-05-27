package be.kuleuven.timetoclimb.route;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.timetoclimb.CommentItem;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.toolsInterface.imageResolver;

public class RouteDetailRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements imageResolver {

    private Context context;
    private List<CommentItem> commentItemList;
    private RecyclerView commentsRecyclerView;

    public RouteDetailRVAdapter (Context context, List<CommentItem> commentItemList, RecyclerView commentsRecyclerView){
        this.context = context;
        this.commentsRecyclerView = commentsRecyclerView;
        this.commentItemList = commentItemList;
    }

    //define view holder
    //define what to inflate for the master viewHolder's inflater on the parent root layout, which is a view group, for each position
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentVG, int viewPosition) {

        //simplified LayoutInflater inflate = LayoutInflater.from(context) to LayoutInflater.from(context).inflate(layoutID,rootView,attachtoRoot)
        View view = LayoutInflater.from(parentVG.getContext()).inflate(R.layout.comment_list_item,parentVG,false);
        return new CommentItemListRowHolder(view);

    }

    //implements the method of RecyclerView.Adapter<RecyclerView.ViewHolder>
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        //get the route given from the index of the routeList populated from DB resposne
        CommentItem commentItem = commentItemList.get(position);
        ((CommentItemListRowHolder)viewHolder).tvComment.setText(""+"Comment: " + commentItem.getDescription());
        ((CommentItemListRowHolder)viewHolder).tvDate.setText("Date: "+ commentItem.getCreated_datetime());
        if (commentItem.getPicture()==null){
            Bitmap defaultBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.user);
            ((CommentItemListRowHolder)viewHolder).ivAvatar.setImageBitmap(defaultBitmap);
        }
        else{ ((CommentItemListRowHolder)viewHolder).ivAvatar.setImageBitmap(StringToBitmap(commentItem.getPicture()));}

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    //inner classes
    //single item view holders  & instantiation of each layout item
    public class CommentItemListRowHolder extends RecyclerView.ViewHolder {

        TextView tvComment,tvDate;
        ImageView ivAvatar;

        public CommentItemListRowHolder(@NonNull View itemView) {
            super(itemView);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
        }
    }
}
