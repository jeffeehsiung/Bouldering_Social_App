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
import be.kuleuven.timetoclimb.Event;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.ViewDate;

public class RecyclerAdapterViewDate extends RecyclerView.Adapter<RecyclerAdapterViewDate.myViewHolder> {
   private ArrayList<Event> eventList;
   private ArrayList<Climbinghall> climbinghalls;
   private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

   public RecyclerAdapterViewDate(ArrayList<Event> eventList, ArrayList<Climbinghall> climbinghalls, OnItemClickListener listener) {
       this.eventList = eventList;
       this.climbinghalls = climbinghalls;
       this.listener = listener;
   }

   public class myViewHolder extends RecyclerView.ViewHolder {
       private ImageView imgClimbinghall;
       private TextView lblTitleEvent;
       private TextView lblClimbinghallEvent;

       public myViewHolder(final View view) {
           super(view);
           imgClimbinghall = view.findViewById(R.id.imgClimbinghall);
           lblTitleEvent = view.findViewById(R.id.lblTitleEvent);
           lblClimbinghallEvent = view.findViewById(R.id.lblClimbinghallEvent);
       }

       public void bind(final Event event, final RecyclerAdapterViewDate.OnItemClickListener listener) {
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override public void onClick(View v) {
                   listener.onItemClick(event);
               }
           });
       }
   }

    @NonNull
    @Override
    public RecyclerAdapterViewDate.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_events_in_date, parent, false);
       return new myViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterViewDate.myViewHolder holder, int position) {
        String titleEvent = eventList.get(position).getTitle();
        holder.lblClimbinghallEvent.setText(climbinghalls.get(position).getHallName());

        //set image
        String b64String = climbinghalls.get(position).getImage();
        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        holder.imgClimbinghall.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/3, bitmap.getHeight()/3, false));

        holder.lblTitleEvent.setText(titleEvent);
        holder.bind(eventList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
