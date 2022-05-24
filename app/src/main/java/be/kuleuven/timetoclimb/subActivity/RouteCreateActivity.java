package be.kuleuven.timetoclimb.subActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import be.kuleuven.timetoclimb.Home;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRouteCreateBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ImageMapParam;

public class RouteCreateActivity extends AppCompatActivity implements imageResolver{

    private ActivityRouteCreateBinding binding;
    private EditText routIDEditText, climbingHallEditText,gradeEditText,descriptionEditText;
    private Button btnUpdate;
    private ImageView imageView;
    private Uri imageUri;
    private Bitmap selectedImageBM;
    private String encodedImage;
    private String uploadImgUrl = "addImageRoute";
    private int TAKE_PICTURE_REQUEST = 45;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRouteCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.routIDEditText = binding.routIDEditText;
        this.climbingHallEditText = binding.climbingHallEditText;
        this.gradeEditText = binding.gradeEditText;
        this.descriptionEditText = binding.descriptionEditText;
        this.imageView = binding.imageView;
        this.btnUpdate = binding.btnUpdate;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        this.user = (User) getIntent().getSerializableExtra("User");

        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, TAKE_PICTURE_REQUEST);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (selectedImageBM == null){
                    imageView.requestFocus();
                    Toast.makeText(RouteCreateActivity.this,"please choose a photo",Toast.LENGTH_LONG).show();
                    return;
                }
                requireNonEmpty();
                System.out.println("bitmap recycled?"+ selectedImageBM.isRecycled());
                DBConnector dbConnector = new DBConnector(getApplicationContext());
                try {
                    dbConnector.imageUploadRequest(uploadImgUrl, user.getUsername().trim(), selectedImageBM, new ImageMapParam() {
                        @Override
                        public void setParam(Map<String, String> params, String encodedImage) {
                            params.put("hallid", Objects.requireNonNull(climbingHallEditText.getText().toString().trim(),"please enter hallID"));
                            params.put("routno", Objects.requireNonNull(routIDEditText.getText().toString().trim(),"please enter routID"));
                            params.put("grade",Objects.requireNonNull(gradeEditText.getText().toString().trim(),"please enter grade"));
                            params.put("routepic",Objects.requireNonNull(encodedImage,"please select an image"));
                            params.put("username", user.getUsername());
                            //params.put("details",descriptionEditText.getText().toString().toLowerCase().trim().replaceAll(" ","+"));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intentToMain = new Intent(RouteCreateActivity.this, Home.class);
                intentToMain.putExtra("User",user);
                startActivity(intentToMain);
            }
        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        System.out.println("username from user: "+ user.getUsername() + " password from user: " + user.getPassword()+ " profileImage from user: " + user.getProfileImage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 45:
                if (resultCode == RESULT_OK && data != null) {
                    try {

                        this.imageUri = data.getData();
                        this.selectedImageBM = uriToBitmap(imageUri);
                    } catch (IOException e) {
                        Log.d("RouteImage",e.toString());
                    }
                    //Setting image to ImageView
                    imageView.setImageBitmap(this.selectedImageBM);

                    return;
                }
                break;
        }
    }

    public void requireNonEmpty(){
        EditText[] allFields = {
                routIDEditText,
                climbingHallEditText,
                gradeEditText,
                descriptionEditText
        };
        List< EditText > ErrorFields = new ArrayList< EditText >();
        for (EditText edit: allFields) {
            if (TextUtils.isEmpty(edit.getText())) {

                // EditText was empty
                ErrorFields.add(edit); //add empty Edittext only in this ArayList

                for (int i = 0; i < ErrorFields.size(); i++) {
                    EditText currentField = ErrorFields.get(i);
                    currentField.setError("this field required");
                    currentField.setHintTextColor(Color.RED);
                    currentField.requestFocus();
                }
            }
        }
    }
}