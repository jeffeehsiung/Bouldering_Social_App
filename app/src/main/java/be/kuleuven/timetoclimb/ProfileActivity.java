package be.kuleuven.timetoclimb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
            @Override
            public void onClick(View v) {
                String name_ = username.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, EditNameActivity.class);
                intent.putExtra("userName", name_);
                startActivity(intent);
            }

        });
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio_ = bio.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, BioEditActivity.class);
                intent.putExtra("bio", bio_);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //not finished yet
            }
        });

    }

    public ActivityProfileBinding getBinding() {
        return binding;
    }

    public TextView getUsername() {
        return username;
    }

    public TextView getBio() {
        return bio;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }
}