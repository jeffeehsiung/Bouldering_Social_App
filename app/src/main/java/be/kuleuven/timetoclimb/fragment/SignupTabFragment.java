package be.kuleuven.timetoclimb.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class SignupTabFragment extends Fragment {

    EditText signupEmail, signupPassword,mobileNum,confirmPassword;
    Button btnSignup;
    String databaseUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        signupEmail = view.findViewById(R.id.email);
        mobileNum= view.findViewById(R.id.mobileNum);
        signupPassword = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnSignup = view.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(e -> {
            String strUser = signupEmail.getText().toString().trim();
            String strPass = confirmPassword.getText().toString().trim();

            Toast.makeText(getContext(),
                    "Email: "+strUser+"Password: "+ strPass,
                    Toast.LENGTH_LONG).show();

            if(TextUtils.isEmpty(strUser)){
                signupEmail.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(strPass)){
                confirmPassword.setError("Password is required");
                return;
            }
            if(strPass.length() < 6){
                confirmPassword.setError("Password length Must be larger than 6  Characters");
                return;
            }

            //connect to database
            DBConnector dbConnector = new DBConnector(this.getContext());
            dbConnector.JSONRequest(databaseUrl, new ServerCallback() {
                @Override
                public void onSuccess(JSONArray jsonArrayResponse) {
                    System.out.println(jsonArrayResponse.length());
                }
            });

        });
    }
}
