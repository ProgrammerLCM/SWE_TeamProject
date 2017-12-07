package com.example.jaeyoungyun.todo2.UI;

import android.app.Activity;
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
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Subject;
import com.example.jaeyoungyun.todo2.Function.ScheduleManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

import java.util.ArrayList;

public class ScheduleUI extends AppCompatActivity {
    ScheduleManager scheduleManager;
    ArrayList<Subject> subjectList;
    ArrayList<Integer> timeList;
    Subject subject;
    Activity act;
    GridView gridView;
    Subject[] subjectArr = new Subject[45];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_ui);
        getSupportActionBar().setTitle("SchedileUI");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔
        scheduleManager = ScheduleManager.getInstance();
        act = this;
        gridView = (GridView) findViewById(R.id.schedule_gridview );
        gridView.setAdapter(new gridAdapter());

        for(int i = 0; i < subjectArr.length; i++)
            subjectArr[i] = null;

        subjectList = (ArrayList) scheduleManager.getList();

        for (int i = 0; i < subjectList.size(); i++) {
            subject = subjectList.get(i);
            timeList = (ArrayList) subject.timeToNumber();
            for(int j = 0; j < timeList.size(); j++){
                subjectArr[j] = subject;
            }
        }
    }


    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public gridAdapter() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return subjectArr.length;    //그리드뷰에 출력할 목록 수
        }

        @Override
        public Object getItem(int position) {
            return subjectArr[position].getSubject();    //아이템을 호출할 때 사용하는 메소드
        }

        @Override
        public long getItemId(int position) {
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.schedule_gridview, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            textView.setText(subjectArr[position].getSubject());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scheduleManager.setCurrentSubject(subjectArr[position]);
                    Intent intent = new Intent(getApplicationContext(), SubjectTodoListUI.class);
                    startActivity(intent);
                }

            });

            return convertView;
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
            Toast.makeText(ScheduleUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}