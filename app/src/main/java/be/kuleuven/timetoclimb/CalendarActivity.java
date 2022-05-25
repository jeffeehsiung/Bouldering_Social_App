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
    private User user;
    private CalendarView calendar;
    private String selectedDate;        // Format: DD/MM/YYYY
    private Button btnViewDate;
    private Button btnCreateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) getIntent().getSerializableExtra("User");
        //sets the main layout of the activity
        setContentView(R.layout.calendar_view);

        //initializes the calendarview
        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // don't show week nr.
        calendar.setShowWeekNumber(false);

        // set first day of week to monday
        calendar.setFirstDayOfWeek(2);

        // what happens on selected date change here
        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                String selectedMonth = "";
                String selectedDay = "";
                if(month < 10) {
                    selectedMonth = "0" + Integer.toString(month + 1);
                } else {
                    selectedMonth = Integer.toString(month+1);
                }
                if(day < 10) {
                    selectedDay = "0" + Integer.toString(day);
                } else {
                    selectedDay = Integer.toString(day);
                }
                Toast.makeText(getApplicationContext(), selectedDay + "/" + selectedMonth + "/" + year, Toast.LENGTH_LONG).show();
                selectedDate = selectedDay + "/" + selectedMonth + "/" + Integer.toString(year);
            }
        });
    }

    public void onBtnViewDate_Clicked(View caller) {
        // Intent to start ViewDate and pass selected date from calendar
        Intent intentViewDate = new Intent(this, ViewDate.class);
        intentViewDate.putExtra("SelectedDate", selectedDate);
        intentViewDate.putExtra("User", user);
        startActivity(intentViewDate);
    }

    public void onBtnCreateEvent_Clicked(View caller) {
        // Intent to start CreateEvent and pass selected date from calendar
        Intent intentCreateEvent = new Intent(this, EventCreator.class);
        intentCreateEvent.putExtra("SelectedDate", selectedDate);
        intentCreateEvent.putExtra("User", user);
        startActivity(intentCreateEvent);
    }
}
