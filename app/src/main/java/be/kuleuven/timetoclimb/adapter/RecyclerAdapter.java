package be.kuleuven.timetoclimb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.Climbinghall;
import be.kuleuven.timetoclimb.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Climbinghall> climbinghalls;

    public RecyclerAdapter(ArrayList<Climbinghall> climbinghalls) {
        this.climbinghalls = climbinghalls;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Add View elements
        private TextView lblImage;
        private TextView lblHallName;
        private TextView lblHallAdress;
        public ViewHolder(final View view) {
            super(view);
            lblImage = view.findViewById(R.id.lblImage);
            lblHallAdress = view.findViewById(R.id.lblHallAdress);
            lblHallName = view.findViewById(R.id.lblHallName);
            // findbyid views
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
    }

    @Override
    public int getItemCount() {
        return climbinghalls.size();
    }
}
