package be.kuleuven.timetoclimb.profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class EditNameActivity extends AppCompatActivity {

    private Button btnupdate;
    private EditText txtprofileName;
    private ImageView back;
    private String oldProfileName;
    private String readUsers = "getAllUsernames";
    private String databaseUrl= "changeUsername";
    public final static String EditNameActivity_TAG = EditNameActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        btnupdate = findViewById(R.id.btnUpdate);
        txtprofileName = findViewById(R.id.txtProfileName);
        back = findViewById(R.id.Back);

        //get original profile name from profile
        oldProfileName = getIntent().getStringExtra("userName");
        txtprofileName.setText(oldProfileName);

        btnupdate.setOnClickListener(new View.OnClickListener() {
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
        //connect to DB to change
        DBConnector dbConnector = new DBConnector(getApplicationContext());
        dbConnector.JSONRequest(readUsers, new ServerCallback() {
            String userkey = "username";

            boolean userExist = false;

            //define what to do with the jsonArray response once connection succeeded
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {
                System.out.println(jsonArrayResponse.length());
                //map to a copy of new list for streaming
                List<JSONObject> data = IntStream.range(0,jsonArrayResponse.length()).mapToObj(i-> {
                    try {
                        return jsonArrayResponse.getJSONObject(i);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                //
                if(data.parallelStream().anyMatch(jsonObject -> {
                    try {
                        return jsonObject.getString(userkey).equals(txtprofileName.getText().toString().trim());
                        //new username already exist
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return false;
                })){ return; }
                else{
                    dbConnector.JSONRequest(
                            databaseUrl + String.format("/%s/%s", txtprofileName.getText().toString().trim(), oldProfileName), new ServerCallback() {
                                @Override
                                public void onSuccess(JSONArray jsonArrayResponse) {
                                    //system message
                                    Toast.makeText(getApplicationContext(),
                                            "Updated Username: "+txtprofileName.getText(),
                                            Toast.LENGTH_LONG).show();
                                    //new intent start
                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                    intent.putExtra("profileName", txtprofileName.getText().toString().trim());
                                    setResult(RESULT_OK, intent);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
        finish();
    }
}