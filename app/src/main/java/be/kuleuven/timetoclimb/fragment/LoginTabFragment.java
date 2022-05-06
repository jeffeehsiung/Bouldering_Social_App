package be.kuleuven.timetoclimb.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.timetoclimb.adapter.ViewPagerAdapter;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;
import be.kuleuven.timetoclimb.R;

public class LoginTabFragment extends Fragment {

    private EditText username, password;
    private Button btnLogin;
    private TextView message;
    private static final String LoginFragment_TAG = LoginTabFragment.class.getSimpleName();

    private String databaseUrl = "getUserComplete";

    public LoginTabFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        //inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btnLogin);
        message= view.findViewById(R.id.message);

        btnLogin.setOnClickListener(e -> {
            String strUser = username.getText().toString().trim();
            String strPass = password.getText().toString().trim().replaceAll("\\s","+");

            Toast.makeText(getContext(),
                    "Username: " + strUser + "Password: " + strPass,
                    Toast.LENGTH_LONG).show();

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
                String userkey = "username";
                String passwordvalue = "password";

                boolean userExist = false;
                boolean loginSucceed = false;
                boolean pwdCorrect = false;

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
                                }
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    });
                    //check login
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
                }
            });
        });
    }
}
