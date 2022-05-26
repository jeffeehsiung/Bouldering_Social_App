package be.kuleuven.timetoclimb.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.Event;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;

public class RecyclerAdapterAttendees extends RecyclerView.Adapter<RecyclerAdapterAttendees.AttendeeViewHolder> {
    private ArrayList<User> attendees;
    private final OnItemClickListener listener;

    @NonNull
    @Override
    public AttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(User attendee);
    }

    public RecyclerAdapterAttendees(ArrayList<User> attendees, OnItemClickListener listener) {
        this.attendees = attendees;
        this.listener = listener;
    }

    public class AttendeeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAttendee;
        private TextView lblAttendee;

        public AttendeeViewHolder(final View view) {
            super(view);
            imgAttendee = view.findViewById(R.id.imgAttendee);
            lblAttendee = view.findViewById(R.id.lblAttendee);
        }

        public void bind(final User attendee, final RecyclerAdapterAttendees.OnItemClickListener listener) {
            attendeeView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(event);
                }
            });
        }
    }

}
