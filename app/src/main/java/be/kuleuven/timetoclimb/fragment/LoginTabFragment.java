package be.kuleuven.timetoclimb.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.timetoclimb.Home;
import be.kuleuven.timetoclimb.ProfileActivity;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class LoginTabFragment extends Fragment {

    private EditText username, password;
    private Button btnLogin;
    private TextView message;
    private static final String LoginFragment_TAG = LoginTabFragment.class.getSimpleName();

    private String databaseUrl = "getUserComplete";

    public LoginTabFragment() {}

    //on create view is automatically called
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        //inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        return root;
    }
    //once layout is inflated, view should also be created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        //instantiate elements that shall be accessed
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btnLogin);
        message= view.findViewById(R.id.message);

        //configure btn with onClick method
        btnLogin.setOnClickListener(e -> {
            String strUser = username.getText().toString().trim();
            String strPass = password.getText().toString().trim().replaceAll("\\s","+");

            Toast.makeText(getContext(),
                    "Username: " + strUser + "Password: " + strPass,
                    Toast.LENGTH_LONG).show();

            //validate input null and length
            if (TextUtils.isEmpty(strUser)) {
                username.setError("Username is required");
                return;
            }
            if (TextUtils.isEmpty(strPass)) {
                password.setError("Password is required");
                return;
            }
            if (strPass.length() < 6) {
                password.setError("Password length Must be larger than 6  Characters");
                return;
            }

            //connect to database
            DBConnector dbConnector = new DBConnector(this.getContext());
            dbConnector.JSONRequest(databaseUrl, new ServerCallback() {
                final String userkey = "username";
                final String passwordvalue = "password";
                final String profile_picture = "profile_picture";
                String profileImage = null;

                boolean userExist = false;
                boolean loginSucceed = false;
                boolean pwdCorrect = false;

                /*dedine what to do with the jsonArray response once connection succeeded*/
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onSuccess(JSONArray jsonArrayResponse) {
                    System.out.println(jsonArrayResponse.length());
                    List<JSONObject> data = IntStream.range(0,jsonArrayResponse.length()).mapToObj(i-> {
                        try {
                            return jsonArrayResponse.getJSONObject(i);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        return null;
                    }).collect(Collectors.toList());
                    data.forEach(object -> {
                        try {
                            if (object.getString(userkey).equals(strUser)) {
                                userExist = true; //userName incorrect, userExist remains false
                                if (object.getString(passwordvalue).equals(strPass)) {
                                    pwdCorrect = true; //password incorrect, pwdCorrect remains false
                                    loginSucceed = true;
                                    if(!object.getString(profile_picture).isEmpty()){
                                    profileImage = object.getString(profile_picture);}

                                }
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    });
                    //check if login succeed or else sends appropriate error
                    if (loginSucceed) {
                        //clear edit text
                        username.getText().clear();
                        password.getText().clear();
                        message.setText("Login successful");
                    } else if (userExist && !pwdCorrect) { //user exist, password incorrect
                        //clear edit text
                        password.getText().clear();
                        message.setText("Incorrect password");
                    } else {//clear edit text
                        username.getText().clear();
                        password.getText().clear();
                        message.setText("No user found");//no user data in database
                    }
                    //create a Bundle object
                    Bundle extras = new Bundle();
                    //Adding key value pairs to this bundle
                    extras.putString("username",strUser);
                    extras.putString("password",strPass);
                    extras.putString("profileImage",profileImage);

                    Intent intentLoginSucceed= new Intent(getContext(), Home.class);
                    intentLoginSucceed.putExtras(extras);
                    startActivity(intentLoginSucceed);
                }
            });
        });
    }
}
