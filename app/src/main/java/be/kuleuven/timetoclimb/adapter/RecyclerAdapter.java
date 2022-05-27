package be.kuleuven.timetoclimb.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.Climbinghall;
import be.kuleuven.timetoclimb.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Climbinghall> climbinghalls;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Climbinghall item);
    }

    public RecyclerAdapter(ArrayList<Climbinghall> climbinghalls, OnItemClickListener listener) {
        this.climbinghalls = climbinghalls;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Add View elements
        private ImageView imgClimbinghall;
        private TextView lblHallName;
        private TextView lblHallAdress;


        public ViewHolder(final View view) {
            super(view);
            imgClimbinghall = view.findViewById(R.id.imgClimbingHall);
            lblHallAdress = view.findViewById(R.id.lblHallAdress);
            lblHallName = view.findViewById(R.id.lblHallName);
        }

        public void bind(final Climbinghall climbinghall, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(climbinghall);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_climbinghall, parent, false);    // CHANGE VIEW_DATE TO WANTED LAYOUT XML
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String hallName = climbinghalls.get(position).getHallName();
        String hallAdress = climbinghalls.get(position).getAddress();
        holder.lblHallName.setText(hallName);
        holder.lblHallAdress.setText(hallAdress);

        // Set image
        String b64String = climbinghalls.get(position).getImage();
        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        holder.imgClimbinghall.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/3, bitmap.getHeight()/3, false));
        holder.bind(climbinghalls.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return climbinghalls.size();
    }
}
