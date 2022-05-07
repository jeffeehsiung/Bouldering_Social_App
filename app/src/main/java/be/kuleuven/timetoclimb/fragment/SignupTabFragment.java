package be.kuleuven.timetoclimb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import be.kuleuven.timetoclimb.Home;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class SignupTabFragment extends Fragment {

    private EditText signupUserName, signupPassword,mobileNum,confirmPassword;
    private Button btnSignup;
    private String databaseUrl;
    private static final String SignupFragment_TAG = SignupTabFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        signupUserName = view.findViewById(R.id.username);
        //mobileNum= view.findViewById(R.id.mobileNum);
        signupPassword = view.findViewById(R.id.signupPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnSignup = view.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(e -> {
            String strUser = signupUserName.getText().toString().trim();
            String strPass = signupPassword.getText().toString().trim().replaceAll("\\s","+");
            String strconfirmPass = confirmPassword.getText().toString().trim().replaceAll("\\s","+");

            Toast.makeText(getContext(),
                    "Username: "+strUser+"Password: "+ strPass,
                    Toast.LENGTH_LONG).show();
            //check edit text inputs
            if(TextUtils.isEmpty(strUser)){
                signupUserName.setError("Username is required");
                return;
            }
            if(TextUtils.isEmpty(strPass)){
                signupPassword.setError("Password is required");
                return;
            }
            if(strPass.length() < 6){
                signupPassword.setError("Password length Must be larger than 6  Characters");
                return;
            }
            if(TextUtils.isEmpty(strconfirmPass)){
                confirmPassword.setError("Password is required");
                return;
            }
            if(!strPass.equals(strconfirmPass)){
                confirmPassword.setError("Passwords need to match");
                return;
            }
            //clear edit text
            signupUserName.getText().clear();
            signupPassword.getText().clear();
            confirmPassword.getText().clear();
            //set url
            databaseUrl = String.format("addUserProfile/%s/%s/%s", strUser, strPass, "note");
            Log.d(SignupFragment_TAG,"url "+ databaseUrl);

            //connect to database
            DBConnector dbConnector = new DBConnector(this.getContext());
            dbConnector.JSONRequest(databaseUrl, new ServerCallback() {
                @Override
                public void onSuccess(JSONArray jsonArrayResponse) {
                    //create a Bundle object
                    Bundle extras = new Bundle();
                    //Adding key value pairs to this bundle
                    extras.putString("username",strUser);
                    extras.putString("password",strPass);

                    Intent intentSignupSucceed= new Intent(getContext(), Home.class);
                    intentSignupSucceed.putExtras(extras);
                    startActivity(intentSignupSucceed);
                }
            });

        });
    }
}
