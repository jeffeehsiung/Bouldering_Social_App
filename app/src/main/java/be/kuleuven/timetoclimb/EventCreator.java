package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.timepicker.TimeFormat;
import com.google.type.DateTime;

import org.w3c.dom.Text;

import java.util.Calendar;

public class EventCreator extends AppCompatActivity {

    private User user;
    private Button btnEditStartTime;
    private Button btnEditEndTime;
    private TextView lblStartTime;
    private TextView lblEndTime;
    private TextView lblSelectedDate;
    private TextView txtTitle;
    private TextView txtDescription;
    private String selectedDate;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String date;
    private String organiser;
    private int climbingHallID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_view);

        btnEditEndTime = (Button) findViewById(R.id.btnEditEndTime);
        btnEditStartTime = (Button) findViewById(R.id.btnEditStartTime);
        lblStartTime = (TextView) findViewById(R.id.lblStartTime);
        lblEndTime = (TextView) findViewById(R.id.lblEndTime);
        lblSelectedDate =(TextView) findViewById(R.id.lblSelectedDate);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        // get extras
        Bundle extras = getIntent().getExtras();
        selectedDate = extras.getString("SelectedDate");
        user = (User) getIntent().getSerializableExtra("User");
        lblSelectedDate.setText(selectedDate);
        date = selectedDate.substring(0, 2) + "-" + selectedDate.substring(3, 5) + "-" + selectedDate.substring(6, 10);

        // add values for db
        title = "";
        description = "";
        organiser = user.getUsername();
        climbingHallID = 1; // Set to default for now, will implement later

    }

    public void onBtnEditStartTime_Click(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setLbl(lblStartTime);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onBtnEditEndTime_Click(View V) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setLbl(lblEndTime);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onBtnAddEvent_Click(View v) {
        // Define values for database
        startTime = date + " " + lblStartTime.getText().toString();
        endTime = date + " " + lblEndTime.getText().toString();
        title = txtTitle.getText().toString();
        description = txtDescription.getText().toString();
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private TextView lbl;

        public void setLbl(TextView lbl) {this.lbl = lbl;}

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
            // Do something with the time chosen by the user
            String hour = "";
            String minute = "";

            if(hourOfDay < 10) {
                hour = "0" + Integer.toString(hourOfDay);
            } else {
                hour = Integer.toString(hourOfDay);
            }
            if(minuteOfDay < 10) {
                minute = "0" + Integer.toString(minuteOfDay);
            } else {
                minute = Integer.toString(minuteOfDay);
            }
            lbl.setText(hour + ":" + minute + ":00");
        }
    }
}
