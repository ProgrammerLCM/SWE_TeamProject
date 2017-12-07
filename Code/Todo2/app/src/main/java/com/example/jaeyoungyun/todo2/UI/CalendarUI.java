package com.example.jaeyoungyun.todo2.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

public class CalendarUI extends AppCompatActivity {

    CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_ui);

        getSupportActionBar().setTitle("Calendar");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔


        cal = (CalendarView)findViewById(R.id.calendar_ui_calendar);

        CalendarVuewListener listener = new CalendarVuewListener();
        cal.setOnDateChangeListener(listener);
    }

    class CalendarVuewListener implements CalendarView.OnDateChangeListener{

        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){

            Intent intent = new Intent(getApplicationContext(), DayTodoListUI.class);
            intent.putExtra("year", Integer.toString(year));
            intent.putExtra("month", Integer.toString(month));
            intent.putExtra("day", Integer.toString(dayOfMonth));
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
            Toast.makeText(CalendarUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
