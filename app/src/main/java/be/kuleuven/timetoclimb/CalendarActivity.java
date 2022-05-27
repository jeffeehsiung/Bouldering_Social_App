package be.kuleuven.timetoclimb;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import android.app.Activity;

import androidx.annotation.RequiresApi;

import com.google.type.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
                selectedDate = buildAppDate(year, month, day);
                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
    Helper methods to build and/or convert datetime from app to database format and vice versa
     */
    public String convertToAppDate(String DBDate) {
        return DBDate.substring(8,10) + "/" + DBDate.substring(5,7) + "/" + DBDate.substring(0,4);
    }

    public String convertToDBDate(String WidgetDate) {
        return WidgetDate.substring(6, 10) + "-" + WidgetDate.substring(3, 5) + "-" + WidgetDate.substring(0, 2) + " 00:00:00";
    }

    public String convertJavaToDBDateTime(LocalDateTime localDateTime) {
        String dateTime = localDateTime.toString();
        return dateTime.substring(0,10) + " " + dateTime.substring(11,19);
    }
    public String buildAppDate(int year, int month, int day) {
        String strMonth = "";
        String strDay = "";
        if(month < 10) {
            strMonth = "0" + Integer.toString(month + 1);
        } else {
            strMonth = Integer.toString(month+1);
        }
        if(day < 10) {
            strDay = "0" + Integer.toString(day);
        } else {
            strDay = Integer.toString(day);
        }
        return strDay + "/" + strMonth + "/" + Integer.toString(year);
    }

    /*
    Button actions
     */
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnBrowseEvents_Clicked(View caller) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        String currentDateTime = convertJavaToDBDateTime(nowDateTime);
        Intent intentBrowseEvents = new Intent(this, BrowseEvents.class);
        intentBrowseEvents.putExtra("User", user);
        intentBrowseEvents.putExtra("currentDateTime", currentDateTime);
        startActivity(intentBrowseEvents);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnAttendingEvents_Clicked(View caller) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        String currentDateTime = convertJavaToDBDateTime(nowDateTime);
        Intent intentAttendingEvents = new Intent(this, AttendingEvents.class);
        intentAttendingEvents.putExtra("User", user);
        intentAttendingEvents.putExtra("currentDateTime", currentDateTime);
        startActivity(intentAttendingEvents);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnOrganisedEvents_Clicked(View caller) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        String currentDateTime = convertJavaToDBDateTime(nowDateTime);
        Intent intOrganisedEvents = new Intent(this, OrganisedEvents.class);
        intOrganisedEvents.putExtra("User", user);
        intOrganisedEvents.putExtra("currentDateTime", currentDateTime);
        startActivity(intOrganisedEvents);
    }
}
