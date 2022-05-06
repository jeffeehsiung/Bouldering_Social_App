package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnCalendar;
    private TextView lblIcon;
    private Button btnLogin;
    private String username;
    private String password;
    public final static String MainActivity_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //content create :
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendar = findViewById(R.id.btnCalendar);
        lblIcon = findViewById(R.id.lblIcon);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void onBtnCalendar_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intentCalendar = new Intent(this, CalendarActivity.class);
        startActivity(intentCalendar);
    }

    public void onBtnLogin_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }


    public void setUserAndPassword() {
        Bundle extras = getIntent().getExtras();
        this.username = extras.getString("username");
        this.password = extras.getString("password");
        Log.d(MainActivity_TAG,"username: "+ username + " password: " + password);
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

}