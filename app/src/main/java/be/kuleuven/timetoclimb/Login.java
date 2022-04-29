package be.kuleuven.timetoclimb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends Activity {

    private Button btnLoginNow = findViewById(R.id.btnLoginNow);
    private EditText txtUsername = findViewById(R.id.txtUsername);
    private EditText txtPassword = findViewById(R.id.txtPassword);
    private TextView txtvMessage = findViewById(R.id.txtvMessage);

    private String userDBserver = "getUserComplete";
    private String userkey = "username";
    private String passwordvalue = "password";
    private boolean loginSucceed = false;
    private boolean userExist = false;
    private boolean pwdCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.fragment_login);

        //initializes the calendarview
        try {
            initializeCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeCalendar() throws IOException {

        checkLogin();
    }
    /* data base connection and comparison */
    public boolean compareUserDB (String username, String password, String serverName) throws IOException {
        //database connection
        DBConnector dbConnector = new DBConnector();
        JSONArray dbJSON = dbConnector.JSONRequest(serverName);
        //iterate and compare to JSONarray
        try {
            for (int i = 0; i < dbJSON.length(); i++) {
                JSONObject curObject = dbJSON.getJSONObject(i);
                if (username.equals(curObject.getString(userkey))) {
                    userExist = true; //userName incorrect, userExist remains false
                    if (password.equals(curObject.getString(passwordvalue))) {
                        pwdCorrect = true; //password incorrect, pwdCorrect remains false
                        loginSucceed = true;
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("userDataBase", e.getMessage());
        }
        return loginSucceed;
    }

    public void checkLogin() throws IOException {
        compareUserDB("jeffee","hsiung",userDBserver); //user exist, pwd correct
        if(loginSucceed){
            txtvMessage.setText("Login successful");
        }
        /*else if(txtUsername.getText() && txtPassword.getText()) { //no input
            txtvMessage.setText("Please enter your data.");
        }*/
        else if(userExist && !pwdCorrect){ //user exist, password incorrect
            txtvMessage.setText("yoyoyoyoyoyoy");
        }
        else{
            txtvMessage.setText("No user found");//no user data in database
        }
    }




}
