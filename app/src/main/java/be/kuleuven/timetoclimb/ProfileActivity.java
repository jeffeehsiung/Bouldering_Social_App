package be.kuleuven.timetoclimb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import be.kuleuven.timetoclimb.databinding.ActivityProfileBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private TextView username, bio;
    private ImageView profileImage;
    private Uri selectedImage;
    private String pfiByteBase64Encoding; //flag 0
    private String encodedImage;
    private Button btnUpdate;
    private String databaseUrl = "setProfileImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.username = binding.txtProfileName;
        this.bio = binding.txtBioField;
        this.profileImage = binding.imgProfile;
        this.btnUpdate = binding.btnUpdate;

        Bundle extras = getIntent().getExtras();
        this.username.setText(extras.getString("username"));

        username.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditNameActivity.class);
                intent.putExtra("userName", username.getText().toString());
                //startActivity(intent);
                startActivityForResult(intent,0);
            }

        });
        bio.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BioEditActivity.class);
                intent.putExtra("bio", bio.getText().toString());
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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBConnector dbConnector = new DBConnector(getApplicationContext());
                dbConnector.JSONRequest(
                        databaseUrl + "/"+ pfiByteBase64Encoding+ "/" +username.getText().toString().trim(), new ServerCallback() {
                            @Override
                            public void onSuccess(JSONArray jsonArrayResponse) {
                                //system message
                                Toast.makeText(getApplicationContext(),
                                        "username: "+username.getText().toString() + " "+ "profileImage64Encoded: "+ pfiByteBase64Encoding,
                                        Toast.LENGTH_LONG).show();
                                //new intent start
                                //create a Bundle object
                                Bundle extras = new Bundle();
                                //Adding key value pairs to this bundle
                                extras.putString("username",username.getText().toString().trim());
                                extras.putString("profileImage",selectedImage.toString());

                                Intent intentToMain = new Intent(ProfileActivity.this, MainActivity.class);
                                intentToMain.putExtras(extras);
                                startActivity(intentToMain);
                            }
                        });
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
                if (resultCode == RESULT_OK && data != null){
                    binding.imgProfile.setImageURI(data.getData());
                    selectedImage = data.getData();
                    //data.getData().toString().getBytes();//bytesArray
                    /*//another method
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);
                    encodedImage = encodeImage(selectedImageBM);

                    setSelectImage(encodedImage);
                    //end*/
                    pfiByteBase64Encoding = Base64.encodeToString(data.getData().toString().getBytes(),Base64.DEFAULT);
                    Log.d("path of image to base64 encoded string: ", pfiByteBase64Encoding + "base64ECBM: "+ encodedImage);
                    return;
                    //still needs to be implemented
                }
                break;
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public void setSelectImage(String encodedImage) {
        byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImage.setImageBitmap(decodedImage);
    }
}