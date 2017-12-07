package com.example.jaeyoungyun.todo2.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Function.TodoManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

public class ModifyTodoUI extends AppCompatActivity {
    private Button modifyTodo_Btn;
    private Button cancelModifyTodo_Btn;
    private EditText name_input;
    private EditText content_input;
    private CheckBox importance_checkBox;
    private WebAddressMaker webAddressMaker;
    private Spinner startYear_spinner;
    private Spinner startMonth_spinner;
    private Spinner startDay_spinner;
    private Spinner dueYear_spinner;
    private Spinner dueMonth_spinner;
    private Spinner dueDay_spinner;
    private TodoManager todoManager;
    private Todo currentTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ACTIONBAR");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔
        modifyTodo_Btn = (Button)findViewById(R.id.modify_todo_check_btn);
        cancelModifyTodo_Btn = (Button)findViewById(R.id.modify_todo_cancel_btn);
        name_input = (EditText)findViewById(R.id.modify_todo_name_input);
        content_input = (EditText)findViewById(R.id.modify_todo_content_input);
        importance_checkBox = (CheckBox)findViewById(R.id.modify_todo_importance_checkbox);
        startYear_spinner = (Spinner)findViewById(R.id.modify_todo_startdate_year);
        startMonth_spinner = (Spinner)findViewById(R.id.modify_todo_startdate_month);
        startDay_spinner = (Spinner)findViewById(R.id.modify_todo_startdate_day);
        dueYear_spinner = (Spinner)findViewById(R.id.modify_todo_duedate_year);
        dueMonth_spinner = (Spinner)findViewById(R.id.modify_todo_duedate_month);
        dueDay_spinner = (Spinner)findViewById(R.id.modify_todo_duedate_day);
        webAddressMaker = WebAddressMaker.getInstance();
        todoManager = TodoManager.getInstance();
        currentTodo = todoManager.getCurrentTodo();

        name_input.setText(currentTodo.getTitle());
        content_input.setText(currentTodo.getContent());
        importance_checkBox.setChecked(currentTodo.isImportant());

        setContentView(R.layout.activity_modify_todo_ui);

        modifyTodo_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptModifyTodo();
            }
        });

        cancelModifyTodo_Btn.setOnClickListener((new View.OnClickListener(){
            public void onClick(View view){
                attemptCancel();
            }
        }));
    }


    private void attemptModifyTodo(){
        String s_name = name_input.getText().toString();
        String s_content = content_input.getText().toString();
        String s_startDate = startYear_spinner.getSelectedItem().toString() + startMonth_spinner.getSelectedItem().toString() + startDay_spinner.getSelectedItem().toString();
        String s_dueDate = dueYear_spinner.getSelectedItem().toString() + dueMonth_spinner.getSelectedItem().toString() + dueDay_spinner.getSelectedItem().toString();
        boolean importance = importance_checkBox.isChecked();

        Todo todo = new Todo(s_name, s_content, s_startDate, s_dueDate, importance);
        new ModifyTodoTask().execute(webAddressMaker.getAdjustTodoURL(todo));
    }


    class ModifyTodoTask extends WebAccessor {
        protected void onProgresssUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(ModifyTodoUI.this, "수정 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TodoListUI.class);
            startActivity(intent);
        }
    }


    private void attemptCancel(){
        Intent intent = new Intent(getApplicationContext(), TodoListUI.class);
        startActivity(intent);
    }


    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.menu_bar_calendar) {
            Intent intent = new Intent(getApplicationContext(), CalendarUI.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_bar_scheduler) {
            Intent intent = new Intent(getApplicationContext(), ScheduleUI.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_bar_todolist) {
            Intent intent = new Intent(getApplicationContext(), TodoListUI.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_bar_completedlist) {
            Intent intent = new Intent(getApplicationContext(), CompletedTodoListUI.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_bar_logout) {  //logout
            new LogOutTask().execute(WebAddressMaker.getInstance().getSignOutURL());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class LogOutTask extends WebAccessor {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ModifyTodoUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
