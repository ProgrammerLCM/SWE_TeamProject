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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Function.TodoManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

import org.w3c.dom.Text;

public class TodoUI extends AppCompatActivity {
    CheckBox importance_checkBox;
    TextView name_text;
    TextView startDate_text;
    TextView dueDate_text;
    TextView doneDate_text;
    TextView content_text;
    Button modify_btn;
    Button delete_btn;
    private TodoManager todoManager;
    private Todo currentTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_ui);

        importance_checkBox = (CheckBox)findViewById(R.id.todo_importance_checkbox);
        name_text = (TextView)findViewById(R.id.todo_name_text);
        startDate_text = (TextView)findViewById(R.id.todo_startdate_text);
        dueDate_text = (TextView)findViewById(R.id.todo_duedate_text);
        doneDate_text = (TextView)findViewById(R.id.todo_donedate_text);
        content_text = (TextView)findViewById(R.id.todo_content_text);
        modify_btn = (Button)findViewById(R.id.todo_modify_btn);
        delete_btn = (Button)findViewById(R.id.todo_delete_btn);
        todoManager = TodoManager.getInstance();
        currentTodo = todoManager.getCurrentTodo();

        importance_checkBox.setChecked(currentTodo.isImportant());
        importance_checkBox.setClickable(false);
        name_text.setText(currentTodo.getTitle());
        startDate_text.setText(currentTodo.getStartDate());
        dueDate_text.setText(currentTodo.getDueDate());
        doneDate_text.setText(currentTodo.getDoneDate());
        content_text.setText(currentTodo.getContent());

        modify_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ModifyTodoUI.class);
                startActivity(intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptDelete();
            }
        });

        getSupportActionBar().setTitle("ACTIONBAR");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔
    }


    private void attemptDelete(){
        todoManager.removeFromList(currentTodo);
        new RemoveTodoTask().execute(WebAddressMaker.getInstance().getDeleteTodoURL(currentTodo));
    }


    class RemoveTodoTask extends WebAccessor {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(TodoUI.this, "삭제 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TodoListUI.class);
            startActivity(intent);
        }
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
            Toast.makeText(TodoUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
