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

import org.w3c.dom.Text;

import java.util.Calendar;

public class EventCreator extends AppCompatActivity {
    private User user;
    private Button btnEditStartTime;
    private Button btnEditEndTime;
    private TextView lblStartTime;
    private TextView lblEndTime;
    private TextView lblSelectedDate;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_view);

        btnEditEndTime = (Button) findViewById(R.id.btnEditEndTime);
        btnEditStartTime = (Button) findViewById(R.id.btnEditStartTime);
        lblStartTime = (TextView) findViewById(R.id.lblStartTime);
        lblEndTime = (TextView) findViewById(R.id.lblEndTime);
        lblSelectedDate =(TextView) findViewById(R.id.lblSelectedDate);

        Bundle extras = getIntent().getExtras();
        selectedDate = extras.getString("SelectedDate");
        user = (User) getIntent().getSerializableExtra("User");
        lblSelectedDate.setText(selectedDate);

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

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            lbl.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
        }
    }
}
