package be.kuleuven.timetoclimb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        public ViewHolder(final View view) {
            super(view);
            // findbyid views
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_date, parent, false);    // CHANGE VIEW_DATE TO WANTED LAYOUT XML
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
