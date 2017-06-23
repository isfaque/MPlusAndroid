package apps.lnsel.com.messengerplus.views.SignupScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import apps.lnsel.com.messengerplus.R;
import apps.lnsel.com.messengerplus.presenters.SignupPresenter;
import apps.lnsel.com.messengerplus.utils.ActivityUtil;
import apps.lnsel.com.messengerplus.utils.SharedManagerUtil;
import apps.lnsel.com.messengerplus.utils.UrlUtil;

/**
 * Created by apps2 on 6/14/2017.
 */
public class SignupActivity extends AppCompatActivity implements SignupActivityView {

    private SignupPresenter presenter;
    private ProgressDialog progress;

    SharedManagerUtil session;

    private EditText et_username, et_password, et_passwordc;

    private TextInputLayout til_username, til_password, til_passwordc;

    Button btn_login, btn_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new SignupPresenter(this);

        btn_login = (Button) findViewById(R.id.activity_signup_btn_login);
        btn_signup = (Button) findViewById(R.id.activity_signup_btn_signup);

        til_username = (TextInputLayout) findViewById(R.id.activity_signup_til_username);
        til_password = (TextInputLayout) findViewById(R.id.activity_signup_til_password);
        til_passwordc = (TextInputLayout) findViewById(R.id.activity_signup_til_passwordc);

        et_username = (EditText) findViewById(R.id.activity_signup_et_username);
        et_password = (EditText) findViewById(R.id.activity_signup_et_password);
        et_passwordc = (EditText) findViewById(R.id.activity_signup_et_passwordc);

        et_username.addTextChangedListener(new MyTextWatcher(et_username));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));
        et_passwordc.addTextChangedListener(new MyTextWatcher(et_passwordc));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                if (!validatePasswordConfirmation()) {
                    return;
                }

                submitSignup();
            }
        });
    }

    public void submitSignup(){
        String usrUserName = et_username.getText().toString().trim();
        String usrPassword = et_password.getText().toString().trim();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);

            presenter.signupService(UrlUtil.SIGNUP_URL, usrUserName, usrPassword);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void startLoginActivity() {
        new ActivityUtil(this).startLoginActivity();
    }

    public void startMainActivity(){
        new ActivityUtil(this).startMainActivity();
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        startLoginActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //********** Text Watcher for Validation *******************//
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.activity_signup_et_username:
                    validateUsername();
                    break;
                case R.id.activity_signup_et_password:
                    validatePassword();
                    break;
                case R.id.activity_signup_et_passwordc:
                    validatePasswordConfirmation();
                    break;
            }
        }
    }



    private boolean validateUsername() {
        if (et_username.getText().toString().trim().isEmpty()) {
            til_username.setError("Please enter username");
            requestFocus(et_username);
            return false;
        } else {
            til_username.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            til_password.setError("Please enter password");
            requestFocus(et_password);
            return false;
        } else {
            til_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePasswordConfirmation() {
        if (et_passwordc.getText().toString().trim().isEmpty()) {
            til_passwordc.setError("Please retype password");
            requestFocus(et_passwordc);
            return false;
        }
        else {

            if (et_password.getText().toString().trim().equals(et_passwordc.getText().toString().trim())) {

                til_passwordc.setErrorEnabled(false);

            } else {
                til_passwordc.setError("Password does not match");
                return false;
            }
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
