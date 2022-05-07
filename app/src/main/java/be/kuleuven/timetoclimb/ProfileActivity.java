package be.kuleuven.timetoclimb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import be.kuleuven.timetoclimb.databinding.ActivityProfileBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private RequestQueue requestQueue;
    private TextView username, bio;
    private ImageView profileImage;
    private Uri selectedImage;
    private Bitmap selectedImageBM;
    //private String pfiByteBase64Encoding; //flag 0
    private String encodedImage;
    private Button btnUpdate;
    private String databaseUrl = "setProfileImage";
    private static final String POST_URL = "https://studev.groept.be/api/a21pt411/insertImage";
    private String retrieveImgUrl = "getProfileImage";
    ;
    private int PICK_IMAGE_REQUEST = 111;
    private ProgressDialog progressDialog;

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
        //retrieveProfileImage(this.username.getText().toString().trim());


        username.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditNameActivity.class);
                intent.putExtra("userName", username.getText().toString());
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }

        });
        bio.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BioEditActivity.class);
                intent.putExtra("bio", bio.getText().toString());
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //not finished yet
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    this.username.setText(data.getStringExtra("profileName"));
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    this.bio.setText(data.getStringExtra("bio"));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK && data != null) {
                    //binding.imgProfile.setImageURI(data.getData());
                    selectedImage = data.getData();
                    imageOnActivityResult(data);
                    System.out.println("imageOnActivityResult succeeded");
                    return;
                    //still needs to be implemented
                }
                break;
        }
    }

    public void onBtnPostClicked(View caller){
        //encodedImage = encodeImage(selectedImage);
        //imageStringToServer(encodedImage);
        //Start an animating progress widget
        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBM.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POST_URL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Post request failed", Toast.LENGTH_LONG).show();
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", imageString);
                return params;
            }
        };

        requestQueue.add(submitRequest);
        //new intent start
        //create a Bundle object
        Bundle extras = new Bundle();
        //Adding key value pairs to this bundle
        extras.putString("username", username.getText().toString().trim());
        extras.putString("profileImage", encodedImage);

        Intent intentToMain = new Intent(ProfileActivity.this, Home.class);
        intentToMain.putExtras(extras);
        startActivity(intentToMain);
    }

    private String encodeImage(Uri uri) throws IOException {
        System.out.println("******"+"\n"+"original selectedImageBM: "+ this.selectedImageBM.toString());
        this.selectedImageBM = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
        System.out.println("******"+"\n"+"new selectedImageBM: "+ this.selectedImageBM.toString());
        //encode image to base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);*/
        //compress bitmap into PNG and output to byte array with quality 100%
        selectedImageBM.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytesArray = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytesArray, Base64.DEFAULT);

        return encodedImage;
    }

    public void setSelectImage(String encodedImage) {
        byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImage.setImageBitmap(decodedImage);
    }

    public void decodeImage(String encodedImage) {
        byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImage.setImageBitmap(decodedImage);
    }


    public void imageOnActivityResult(Intent data) {
        try {
            //getting image from gallery
            this.selectedImageBM = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            //Rescale the bitmap to 400px wide (avoid storing large images!)
            this.selectedImageBM = getResizedBitmap(selectedImageBM, 400);

            //Setting image to ImageView
            profileImage.setImageBitmap(this.selectedImageBM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Submits a new image to the database
     */
    public void imageStringToServer(String encodedImage) throws IOException {
        /*//Start an animating progress widget
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();*/

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest submitRequest = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Post request failed", Toast.LENGTH_LONG).show();
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("profile_picture", encodedImage);
                return params;
            }
        };
        requestQueue.add(submitRequest);
    }

    /**
     * Retrieves the most recent image from the DB
     */
    public void retrieveProfileImage(String username) {
        //Standard Volley request. We don't need any parameters for this one
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, retrieveImgUrl + "/" + username, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Check if the DB actually contains an image
                            if (response.length() > 0) {
                                JSONObject o = response.getJSONObject(0);

                                //converting base64 string to image
                                String b64String = o.getString("profile_picture");
                                byte[] imageBytes = Base64.decode(b64String, Base64.DEFAULT);
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                                //Link the bitmap to the ImageView, so it's visible on screen
                                profileImage.setImageBitmap(bitmap2);

                                //Just a double-check to tell us the request has completed
                                Toast.makeText(getApplicationContext(), "Image retrieved from DB", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(retrieveImageRequest);
    }

    /**
     * Helper method to create a rescaled bitmap. You enter a desired width, and the height is scaled uniformly
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        // We create a matrix to transform the image
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}