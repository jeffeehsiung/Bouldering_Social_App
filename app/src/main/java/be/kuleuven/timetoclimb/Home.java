package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private Button btnCalendar;
    private TextView lblIcon;
    private Button btnLogin;
    private String username;
    private String password;
    private User user;
    public final static String MainActivity_TAG = Home.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //content create :
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUser();
        btnCalendar = findViewById(R.id.btnCalendar);
        lblIcon = findViewById(R.id.lblIcon);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void onBtnCalendar_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intentCalendar = new Intent(this, CalendarActivity.class);
        intentCalendar.putExtra("User", user);
        startActivity(intentCalendar);
    }

    public void onBtnLogin_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }


    public void createUser() {
        Bundle extras = getIntent().getExtras();
        this.username = extras.getString("username");
        this.password = extras.getString("password");
        this.user = new User(username, password);
        Log.d(MainActivity_TAG,"username: "+ username + " password: " + password);
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

}