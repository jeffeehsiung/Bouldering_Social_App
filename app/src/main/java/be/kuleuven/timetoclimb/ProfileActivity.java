package be.kuleuven.timetoclimb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import be.kuleuven.timetoclimb.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private TextView username, bio;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.username = binding.txtProfileName;
        this.bio = binding.txtBioField;
        this.profileImage = binding.imgProfile;

        Bundle extras = getIntent().getExtras();
        this.username.setText(extras.getString("username"));

        username.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                String name_ = username.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, EditNameActivity.class);
                intent.putExtra("userName", name_);
                //startActivity(intent);
                startActivityForResult(intent,0);
            }

        });
        bio.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                String bio_ = bio.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, BioEditActivity.class);
                intent.putExtra("bio", bio_);
                //startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //not finished yet
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == RESULT_OK){
                    this.username.setText(data.getStringExtra("profileName"));
                }
                break;
            case 1:
                if (resultCode == RESULT_OK){
                    this.bio.setText(data.getStringExtra("bio"));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    return;
                    //still needs to be implemented
                }
                break;
        }
    }
}