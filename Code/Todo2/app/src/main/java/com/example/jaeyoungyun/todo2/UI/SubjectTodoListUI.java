package com.example.jaeyoungyun.todo2.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Data.TodoDoneDateComparator;
import com.example.jaeyoungyun.todo2.Data.TodoDueDateComparator;
import com.example.jaeyoungyun.todo2.Data.TodoIsDoneComparator;
import com.example.jaeyoungyun.todo2.Data.TodoIsImportantComparator;
import com.example.jaeyoungyun.todo2.Data.TodoStartDateComparator;
import com.example.jaeyoungyun.todo2.Function.ScheduleManager;
import com.example.jaeyoungyun.todo2.Function.TodoManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

import java.util.ArrayList;
import java.util.Collections;

public class SubjectTodoListUI extends AppCompatActivity {
    private TodoManager todoManager;
    private ListView listView;
    private ArrayList<Todo> todoList;
    private itemAdapter itemAdapter;
    private Spinner orderSpinner;
    private Button addTodo_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_todo_list_ui);
        getSupportActionBar().setTitle("ACTIONBAR");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔
        todoManager = TodoManager.getInstance();
        todoList = new ArrayList<>();
        ArrayList<Todo> allTodoList = (ArrayList)todoManager.getList();
        addTodo_btn = (Button)findViewById(R.id.todo_listview_footer_addtodo_btn);
        String s_subject = ScheduleManager.getInstance().getCurrentSubject().getSubject();
        for(Todo todo : allTodoList){
            if( todo.getLecture().equals(s_subject) ){
                todoList.add(todo);
            }
        }

        orderSpinner = (Spinner)findViewById(R.id.todolist_order);

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String s_orderCriteria = (String)parent.getItemAtPosition(position);
                switch (s_orderCriteria) {
                    case "시작날짜":
                        Collections.sort(todoList, new TodoStartDateComparator());
                        break;
                    case "마감기한":
                        Collections.sort(todoList, new TodoDueDateComparator());
                        break;
                    case "실제 마감일":
                        Collections.sort(todoList, new TodoDoneDateComparator());
                        break;
                    case "완료여부":
                        Collections.sort(todoList, new TodoIsDoneComparator());
                        break;
                    case "중요도":
                        Collections.sort(todoList, new TodoIsImportantComparator());
                }
            }

            public void onNothingSelected(AdapterView<?> parent){}
        });

        listView = (ListView)findViewById(R.id.subject_todolist_listview);
        View footerView = getLayoutInflater().inflate(R.layout.todo_listview_footer, null);
        addTodo_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddTodoUI.class);
                startActivity(intent);
            }
        });
        listView.addFooterView(footerView);
        itemAdapter = new itemAdapter(getApplicationContext(), R.layout.todo_list_item, todoList);
        listView.setAdapter(itemAdapter);

    }


    class itemAdapter extends ArrayAdapter<Todo> {
        private ArrayList<Todo> todoList;
        private Context context;

        public itemAdapter(Context context, int resource, ArrayList<Todo> todoList){
            super(context, resource, todoList);
            this.context = context;
            this.todoList = todoList;
        }

        public View getView(final int position, View convertView, ViewGroup parent){
            View v = convertView;
            if( v == null ){
                LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.todo_list_item, null);
            }
            if( !todoList.isEmpty() ) {
                Todo todo = todoList.get(position);
                if (todo != null) {
                    CheckBox importanceBox = (CheckBox) v.findViewById(R.id.todo_list_item_importance);
                    TextView titleView = (TextView) v.findViewById(R.id.todo_list_item_todo_title);
                    TextView dueDateView = (TextView) v.findViewById(R.id.todo_list_item_todo_dueDate);

                    importanceBox.setChecked(todo.isImportant());
                    titleView.setText(todo.getTitle());
                    dueDateView.setText(todo.getDueDate());

                    titleView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            todoManager.setCurrentTodo(todoList.get(position));
                            Intent intent = new Intent(getApplicationContext(), TodoUI.class);
                            startActivity(intent);
                        }
                    });
                }
            }
            return v;
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
            Toast.makeText(SubjectTodoListUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
