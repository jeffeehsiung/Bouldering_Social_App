package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ViewDate extends AppCompatActivity {
    private TextView lblDate;
    private String selectedDate;

    private String userDBserver = "getUserComplete";
    private String userkey = "username";
    private String passwordvalue = "password";
    private boolean loginSucceed = false;
    private boolean userExist = false;
    private boolean pwdCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBCommunicator dbCommunicator = new DBCommunicator(getApplicationContext());
        DBConnector dbConnector = new DBConnector(getApplicationContext());

        setContentView(R.layout.view_date);
        lblDate = (TextView) findViewById(R.id.lblDate);

        // Get selected date from intent
        Bundle extras = getIntent().getExtras();
        selectedDate = extras.get("SelectedDate").toString();

        lblDate.setText(selectedDate);
        lblDate.setText(dbCommunicator.getUserNames());
    }
}