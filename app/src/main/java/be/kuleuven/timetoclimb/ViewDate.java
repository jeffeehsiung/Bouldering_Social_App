package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewDate extends AppCompatActivity {

    private User user;
    private TextView lblDate;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_date);
        lblDate = (TextView) findViewById(R.id.lblDate);

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        selectedDate = extras.get("SelectedDate").toString();
        user = (User) getIntent().getSerializableExtra("User");
    }
}