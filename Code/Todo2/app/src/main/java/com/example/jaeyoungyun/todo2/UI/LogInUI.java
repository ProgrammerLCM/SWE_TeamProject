package com.example.jaeyoungyun.todo2.UI;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Subject;
import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Function.ScheduleManager;
import com.example.jaeyoungyun.todo2.Function.TodoManager;
import com.example.jaeyoungyun.todo2.R;

import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;

import org.json.JSONArray;
import org.json.JSONObject;

public class LogInUI extends AppCompatActivity {

    WebAddressMaker webAddressMaker;

    // UI references.
    private EditText email_input;
    private EditText password_input;
    private Button logIn_Btn;
    private Button signUp_Btn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_ui);

        getSupportActionBar().setTitle("Log-In");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔

        email_input = (EditText) findViewById(R.id.login_id_input);
        password_input = (EditText) findViewById(R.id.login_pw_input);
        logIn_Btn = (Button) findViewById(R.id.login_login_btn);
        signUp_Btn = (Button) findViewById(R.id.login_signup_btn);

        logIn_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                attemptLogin();
            }
        });

        signUp_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                goToSignUp();
            }
        });
    }


    private void attemptLogin() {
        // Reset errors.
        email_input.setError(null);
        password_input.setError(null);

        // Store values at the time of the login attempt.
        String s_email = email_input.getText().toString();
        String s_password = password_input.getText().toString();
        String s_url;

        boolean cancel = false;
        View focusView = null;

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
            s_url = webAddressMaker.getSignInURL(s_email, s_password);
            new LogInTask().execute(s_url);
        }
    }

    private void goToSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpUI.class);
        startActivity(intent);
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
            }

    class LogInTask extends WebAccessor{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if( result.equals("success") ){
                Toast.makeText(LogInUI.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                new ScheduleLoadingTask().execute(webAddressMaker.getLectureCallURL());
                new TodoLoadingTask().execute(webAddressMaker.getTodoCallURL());
            }
            else{
                Toast.makeText(LogInUI.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ScheduleLoadingTask extends WebAccessor{
        protected void onProgresssUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            ScheduleManager scheduleManager = ScheduleManager.getInstance();
            try {
                JSONArray ja = new JSONArray(result);
                for(int i = 0;i < ja.length(); i++){
                    JSONObject json = ja.getJSONObject(i);
                    Subject subject = new Subject();
                    subject.setSubject(json.getString("lecname"));
                    subject.setProfessor(json.getString("lecpfname"));
                    subject.setRoom(json.getString("lecroom"));
                    subject.setBook(json.getString("lectextbook"));
                    subject.setTime(json.getString("lectime"));
                    subject.setSemester(json.getString("lecsemester"));
                    subject.setYear(json.getString("lecyear"));
                    scheduleManager.addToList(subject);
                }
            } catch (Exception e){}
        }
    }

    class TodoLoadingTask extends WebAccessor{
        protected void onProgresssUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            TodoManager todoManager = TodoManager.getInstance();
            try {
                JSONArray ja = new JSONArray(result);
                for(int i = 0;i < ja.length(); i++){
                    JSONObject json = ja.getJSONObject(i);
                    Todo todo = new Todo();

                    todo.setContent(json.getString("tdcontent"));
                    todo.setLecture(json.getString("lecname"));
                    todo.setDoneDate(json.getString("tdfinishtime"));
                    todo.setTitle(json.getString("tdname"));
                    todo.setDueDate(json.getString("tddeadline"));
                    todo.setStartDate(json.getString("tdstart"));
                    todo.setIsImportant(Integer.parseInt(json.getString("tdimportant")) > 0);
                    todo.setIsDone(Integer.parseInt(json.getString("tdfinish")) > 0);
                    todo.setIsHide(Integer.parseInt(json.getString("tdhide")) > 0);
                    todoManager.addToList(todo);
                }

                Intent intent = new Intent(getApplicationContext(), CalendarUI.class);
                startActivity(intent);
            } catch (Exception e){}
        }
    }
}
