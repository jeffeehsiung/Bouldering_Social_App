package be.kuleuven.timetoclimb.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.route.RouteListRowHolder;

public class RouteListRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_routeno_view, parent, false);
        return new RouteListRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
