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
import android.widget.BaseAdapter;
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
import com.example.jaeyoungyun.todo2.Function.TodoManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

import java.util.ArrayList;
import java.util.Collections;

public class TodoListUI extends AppCompatActivity {
    private TodoManager todoManager;
    private ListView listView;
    private ArrayList<Todo> todoList;
    private itemAdapter itemAdapter;
    private Spinner orderSpinner;
    private Button addTodo_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_ui);
        getSupportActionBar().setTitle("ACTIONBAR");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔
        todoManager = TodoManager.getInstance();
        todoList = (ArrayList)todoManager.getList();
        listView = (ListView)findViewById(R.id.todolist_listview);
        addTodo_btn = (Button)findViewById(R.id.todo_listview_footer_addtodo_btn);


        addTodo_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddTodoUI.class);
                startActivity(intent);
            }
        });

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
        itemAdapter = new itemAdapter(getApplicationContext(), R.layout.todo_list_item, todoList);
        View footerView = getLayoutInflater().inflate(R.layout.todo_listview_footer, null);
        listView.addFooterView(footerView);
        itemAdapter = new itemAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
                todoManager.setCurrentTodo(todoList.get(position));
                Intent intent = new Intent(getApplicationContext(), TodoUI.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(itemAdapter);
        for(Todo todo : todoList){
            itemAdapter.addItem(todo);
        }
    }

    class itemAdapter extends BaseAdapter {
        private ArrayList<Todo> todoList;
        private Context context;
        private int i_layout;
        private LayoutInflater inflater;


        public itemAdapter(){
            todoList = new ArrayList<>();
        }


        public itemAdapter(Context context, int resource, ArrayList<Todo> todoList){
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.i_layout = resource;
            this.todoList = todoList;
            this.context = context;
        }


        public int getCount(){
            return todoList.size();
        }


        public Todo getItem(int position){
            return todoList.get(position);
        }


        public long getItemId(int position){
            return position;
        }


        public void addItem(Todo todo){
            todoList.add(todo);
        }


        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = inflater.inflate(i_layout, null);
            }

            CheckBox importanceBox = (CheckBox) convertView.findViewById(R.id.todo_list_item_importance);
            TextView titleView = (TextView) convertView.findViewById(R.id.todo_list_item_todo_title);
            TextView dueDateView = (TextView) convertView.findViewById(R.id.todo_list_item_todo_dueDate);

            Todo todo = todoList.get(position);

            importanceBox.setChecked(todo.isImportant());
            titleView.setText(todo.getTitle());
            dueDateView.setText(todo.getDueDate());

            return convertView;


            /*
            View v = convertView;
            final int pos = position;
   ////         final Context context = parent.getContext();

            if( v == null ){
              //  LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
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
                    }
            }
            return v;*/
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
            Toast.makeText(TodoListUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
