package be.kuleuven.timetoclimb.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;

import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.adapter.ViewPagerAdapter;

public class LoginTabFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    EditText email, password;
    Button btnLogin;
    TextView message;

    float opacityf;
    String databaseUrl = "getUserComplete";

    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        email.setTranslationX(800);
        password.setTranslationX(800);
        message.setTranslationX(800);
        btnLogin.setTranslationX(800);

        email.setAlpha(opacityf);
        password.setAlpha(opacityf);
        message.setAlpha(opacityf);
        btnLogin.setAlpha(opacityf);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        message.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        btnLogin.setOnClickListener(view -> {
            String strUser = email.getText().toString().trim();
            String strPass = password.getText().toString().trim();

            Toast.makeText(getContext(),
                    "Email: "+strUser+"Password: "+ strPass,
                    Toast.LENGTH_LONG).show();

            if(TextUtils.isEmpty(strUser)){
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(strPass)){
                password.setError("Password is required");
                return;
            }
            if(strPass.length() < 6){
                password.setError("Password length Must be larger than 6  Characters");
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

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        ((TextView) view.findViewById(android.R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
    }
}
