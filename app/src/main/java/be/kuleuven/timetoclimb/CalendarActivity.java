package be.kuleuven.timetoclimb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import android.app.Activity;

public class CalendarActivity extends Activity{
    private CalendarView calendar;
    private String selectedDate;        // Format: DD/MM/YYYY
    private Button btnViewDate;
    private Button btnCreateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.calendar_view);

        //initializes the calendarview
        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);



        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                selectedDate = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
            }
        });
    }

    public void onBtnViewDate_Clicked(View caller) {
        // Intent to start ViewDate and pass selected date from calendar
        Intent intentViewDate = new Intent(this, ViewDate.class);
        intentViewDate.putExtra("SelectedDate", selectedDate);
        startActivity(intentViewDate);
    }

    public void onBtnCreateEvent_Clicked(View caller) {
        // Intent to start CreateEvent and pass selected date from calendar
        Intent intentCreateEvent = new Intent(this, EventCreator.class);
        intentCreateEvent.putExtra("SelectedDate", selectedDate);
        startActivity(intentCreateEvent);
    }
}
