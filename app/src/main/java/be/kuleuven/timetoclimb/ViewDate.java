package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewDate extends AppCompatActivity {
    private TextView lblDate;
    private String selectedDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBCommunicator dbCommunicator = new DBCommunicator(getApplicationContext());

        setContentView(R.layout.view_date);
        lblDate = (TextView) findViewById(R.id.lblDate);

        // Get selected date from intent
        Bundle extras = getIntent().getExtras();
        selectedDate = extras.get("SelectedDate").toString();

        //lblDate.setText(selectedDate);
        //dbCommunicator.request("https://studev.groept.be/api/a21pt411/getAllUsernames", "username", lblDate);
        dbCommunicator.requestJSONTest();

    }
}