package be.kuleuven.timetoclimb.route;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.timetoclimb.Home;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRouteCreateBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ImageMapParam;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;
import be.kuleuven.timetoclimb.subActivity.imageResolver;

@SuppressWarnings("deprecation")
public class RouteCreateActivity extends AppCompatActivity implements imageResolver{

    private ActivityRouteCreateBinding binding;
    private EditText routIDEditText, climbingHallEditText,gradeEditText,descriptionEditText;

    private Spinner climbinghallSpinner,gradeSpinner;

    private Button btnUpdate, btnTakePic;
    private ImageView imageView;
    private Uri imageUri;
    private Bitmap selectedImageBM;
    private String encodedImage;
    private String uploadImgUrl = "addImageRoute";
    private static final int TAKE_PICTURE_REQUEST = 45;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private User user = null;
    private SpinnerAdapter spinnerAdapter;
    private static final String RouteCreateActivity_TAG = RouteCreateActivity.class.getSimpleName();


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
        this.btnTakePic = binding.btnTakePic;
        /*
        spinner section
        //this.climbinghallSpinner = binding.climbinghallSpinner;
         */



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        this.user = (User) getIntent().getSerializableExtra("User");

        /*
        //spinner section
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), "getAllHalls", "hall_name");
        climbinghallSpinner.setAdapter(spinnerAdapter.getAdapter());

        //configuring spinner listner for its menu list
        climbinghallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(parent.getItemAtPosition(pos)==null){
                    climbinghallSpinner.requestFocus();
                    climbinghallSpinner.setPrompt("choose a hall");
                    return;
                }
                String hallName = parent.getItemAtPosition(pos).toString();
                Toast.makeText(RouteCreateActivity.this,hallName,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(RouteCreateActivity_TAG, "onNothingSelected");
            }
        }); */

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

        btnTakePic.setOnClickListener(new View.OnClickListener(){
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(RouteCreateActivity.this,"no embedded camera",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //image Uri requires nonNull
                if (selectedImageBM == null){
                    imageView.requestFocus();
                    Toast.makeText(RouteCreateActivity.this,"please choose a photo",Toast.LENGTH_SHORT).show();
                    return;
                }
                // EditText requireNonEmpty
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
                        ErrorFields.add(edit); //add empty Edittext only in this ArrayList

                        for (int i = 0; i < ErrorFields.size(); i++) {
                            EditText currentField = ErrorFields.get(i);
                            currentField.setError("this field required");
                            currentField.setHintTextColor(Color.RED);
                            currentField.requestFocus();
                            Toast.makeText(RouteCreateActivity.this,"please fill in",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                //sout for debug
                System.out.println("bitmap recycled?"+ selectedImageBM.isRecycled());
                //upload to DB
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
                            params.put("description",descriptionEditText.getText().toString().trim());
                            //params.put("description",descriptionEditText.getText().toString().trim().replaceAll("\\s","+"));
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
        /*
        spinner section
        //configuring spinner adapter for its menu list & apply the adapter to the spinner
        climbinghallSpinner.setAdapter(spinnerAdapter.getAdapter());
         */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && data != null) {
                    Bundle extras = data.getExtras();
                    this.imageUri = data.getData();
                    this.selectedImageBM = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(this.selectedImageBM);

                    return;
                }
                break;

            case TAKE_PICTURE_REQUEST:
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
}