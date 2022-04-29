package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {
    private Button btnCalendar;
    private TextView lblIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        lblIcon = (TextView) findViewById(R.id.lblIcon);
    }

    public void onBtnCalendar_Clicked(View caller) {
        // Create intent to start the calendar
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void btnLogin_Clicked(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}