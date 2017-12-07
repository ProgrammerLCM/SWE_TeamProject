package com.example.jaeyoungyun.todo2.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.R;

public class SignUpUI extends AppCompatActivity {

    private EditText name_input;
    private EditText email_input;
    private EditText password_input;
    private Button cancle_Btn;
    private Button SignUp_Btn;
    private WebAddressMaker webAddressMaker;
    private String s_url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_ui);
        webAddressMaker = WebAddressMaker.getInstance();
        name_input = (EditText) findViewById(R.id.signup_name_input);
        email_input = (EditText) findViewById(R.id.signup_email_input);
        password_input = (EditText) findViewById(R.id.signup_pwd_input);
        cancle_Btn = (Button) findViewById(R.id.signup_cancel_btn);
        cancle_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
            }
        });


        SignUp_Btn = (Button) findViewById(R.id.signup_regist_btn);
        SignUp_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });


    }

    void attemptSignUp(){
        String s_name = name_input.getText().toString();
        String s_email = email_input.getText().toString();
        String s_password = password_input.getText().toString();

        s_url = webAddressMaker.getSignUpURL(s_name, s_email, s_password);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(s_name)) {
            name_input.setError(getString(R.string.error_field_required));
            focusView = name_input;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(s_password) && !isPasswordValid(s_password)) {
            password_input.setError(getString(R.string.error_invalid_password));
            focusView = password_input;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(s_email)) {
            email_input.setError(getString(R.string.error_field_required));
            focusView = email_input;
            cancel = true;
        } else if (!isEmailValid(s_email)) {
            email_input.setError(getString(R.string.error_invalid_email));
            focusView = email_input;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            webAddressMaker = WebAddressMaker.getInstance();
            s_url = webAddressMaker.getSignUpURL(s_name, s_email, s_password);
           // Toast.makeText(SignUpUI.this, s_url, Toast.LENGTH_SHORT).show();
            new SignUpTask().execute(s_url);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    class SignUpTask extends WebAccessor {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if( result.equals("success") ){
                Toast.makeText(SignUpUI.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LogInUI.class);
                startActivity(intent);
            }
            else if( result.equals("idexist") ){
                Toast.makeText(SignUpUI.this, "이미 있는 아이디입니다", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
