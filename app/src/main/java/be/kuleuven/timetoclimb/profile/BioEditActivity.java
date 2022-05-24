package be.kuleuven.timetoclimb.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import be.kuleuven.timetoclimb.profile.ProfileActivity;
import be.kuleuven.timetoclimb.R;

public class BioEditActivity extends AppCompatActivity {

    Button update;
    EditText bio;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_edit);

        update = findViewById(R.id.btnUpdate);
        bio = findViewById(R.id.txtEditBioField);
        back = findViewById(R.id.Back);

        //get original bio text from intent extra of profile activity
        String bio_ = getIntent().getStringExtra("bio");
        bio.setText(bio_);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenProfileActivity();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClickOpenProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        String bio = this.bio.getText().toString().trim();
        intent.putExtra("bio", bio);
        setResult(RESULT_OK, intent);
        finish();
    }
}