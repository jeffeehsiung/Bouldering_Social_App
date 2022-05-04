package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnCalendar;
    private TextView lblIcon;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //content create :
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        lblIcon = (TextView) findViewById(R.id.lblIcon);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    public void onBtnCalendar_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void onBtnLogin_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intent = new Intent(caller.getContext(), LoginActivity.class);
        startActivity(intent);
    }

}