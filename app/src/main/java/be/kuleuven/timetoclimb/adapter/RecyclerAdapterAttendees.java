package be.kuleuven.timetoclimb.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import be.kuleuven.timetoclimb.Event;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;

public class RecyclerAdapterAttendees extends RecyclerView.Adapter<RecyclerAdapterAttendees.AttendeeViewHolder> {
    private ArrayList<User> attendees;

    @NonNull
    @Override
    public RecyclerAdapterAttendees.AttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendee_list_item, parent, false);    // CHANGE VIEW_DATE TO WANTED LAYOUT XML
        return new RecyclerAdapterAttendees.AttendeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeeViewHolder holder, int position) {
        String attendee = attendees.get(position).getUsername();
        holder.lblAttendee.setText(attendee);
        // Image profilePic = attendees.get(position).getProfileImage(); -> STILL NEEDS TO BE IMPLEMENTED
        // holder.imgAttendee.setImageBitmap(profilePic);
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public interface OnItemClickListener {
        void onItemClick(User attendee);
    }

    public RecyclerAdapterAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }

    public class AttendeeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAttendee;
        private TextView lblAttendee;

        public AttendeeViewHolder(final View view) {
            super(view);
            imgAttendee = view.findViewById(R.id.imgAttendee);
            lblAttendee = view.findViewById(R.id.lblAttendee);
        }
    }

}
