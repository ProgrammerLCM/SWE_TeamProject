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
import android.widget.Toast;

import com.example.jaeyoungyun.todo2.Data.Subject;
import com.example.jaeyoungyun.todo2.Function.ScheduleManager;
import com.example.jaeyoungyun.todo2.Function.WebAccessor;
import com.example.jaeyoungyun.todo2.Function.WebAddressMaker;
import com.example.jaeyoungyun.todo2.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddSubjectUI extends AppCompatActivity {
    private Button addSubject_Btn;
    private Button cancelAddSubject_Btn;
    private EditText subject_input;
    private EditText professor_input;
    private EditText semester_input;
    private EditText book_input;
    private EditText room_input;
    private WebAddressMaker webAddressMaker;
    private CheckBox[] checkBoxes;
    String s_days[] = {"월", "화", "수", "목", "금"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_ui);

        addSubject_Btn = (Button)findViewById(R.id.add_subject_check_btn);
        cancelAddSubject_Btn = (Button)findViewById(R.id.add_subject_cancel_btn);
        subject_input = (EditText)findViewById(R.id.add_subject_name_input);
        professor_input = (EditText)findViewById(R.id.add_subject_professor_input);
        semester_input = (EditText)findViewById(R.id.add_subject_semester_input);
        book_input = (EditText)findViewById(R.id.add_subject_textbook_input);
        room_input = (EditText)findViewById(R.id.add_subject_classroom_input);
        checkBoxes = new CheckBox[45];
        checkBoxes[0] = (CheckBox)findViewById(R.id.add_subject_checkBox101);
        checkBoxes[1] = (CheckBox)findViewById(R.id.add_subject_checkBox102);
        checkBoxes[2] = (CheckBox)findViewById(R.id.add_subject_checkBox103);
        checkBoxes[3] = (CheckBox)findViewById(R.id.add_subject_checkBox104);
        checkBoxes[4] = (CheckBox)findViewById(R.id.add_subject_checkBox105);
        checkBoxes[5] = (CheckBox)findViewById(R.id.add_subject_checkBox201);
        checkBoxes[6] = (CheckBox)findViewById(R.id.add_subject_checkBox202);
        checkBoxes[7] = (CheckBox)findViewById(R.id.add_subject_checkBox203);
        checkBoxes[8] = (CheckBox)findViewById(R.id.add_subject_checkBox204);
        checkBoxes[9] = (CheckBox)findViewById(R.id.add_subject_checkBox205);
        checkBoxes[10] = (CheckBox)findViewById(R.id.add_subject_checkBox301);
        checkBoxes[11] = (CheckBox)findViewById(R.id.add_subject_checkBox302);
        checkBoxes[12] = (CheckBox)findViewById(R.id.add_subject_checkBox303);
        checkBoxes[13] = (CheckBox)findViewById(R.id.add_subject_checkBox304);
        checkBoxes[14] = (CheckBox)findViewById(R.id.add_subject_checkBox305);
        checkBoxes[15] = (CheckBox)findViewById(R.id.add_subject_checkBox401);
        checkBoxes[16] = (CheckBox)findViewById(R.id.add_subject_checkBox402);
        checkBoxes[17] = (CheckBox)findViewById(R.id.add_subject_checkBox403);
        checkBoxes[18] = (CheckBox)findViewById(R.id.add_subject_checkBox404);
        checkBoxes[19] = (CheckBox)findViewById(R.id.add_subject_checkBox405);
        checkBoxes[20] = (CheckBox)findViewById(R.id.add_subject_checkBox501);
        checkBoxes[21] = (CheckBox)findViewById(R.id.add_subject_checkBox502);
        checkBoxes[22] = (CheckBox)findViewById(R.id.add_subject_checkBox503);
        checkBoxes[23] = (CheckBox)findViewById(R.id.add_subject_checkBox504);
        checkBoxes[24] = (CheckBox)findViewById(R.id.add_subject_checkBox505);
        checkBoxes[25] = (CheckBox)findViewById(R.id.add_subject_checkBox601);
        checkBoxes[26] = (CheckBox)findViewById(R.id.add_subject_checkBox602);
        checkBoxes[27] = (CheckBox)findViewById(R.id.add_subject_checkBox603);
        checkBoxes[28] = (CheckBox)findViewById(R.id.add_subject_checkBox604);
        checkBoxes[29] = (CheckBox)findViewById(R.id.add_subject_checkBox605);
        checkBoxes[30] = (CheckBox)findViewById(R.id.add_subject_checkBox701);
        checkBoxes[31] = (CheckBox)findViewById(R.id.add_subject_checkBox702);
        checkBoxes[32] = (CheckBox)findViewById(R.id.add_subject_checkBox703);
        checkBoxes[33] = (CheckBox)findViewById(R.id.add_subject_checkBox704);
        checkBoxes[34] = (CheckBox)findViewById(R.id.add_subject_checkBox705);
        checkBoxes[35] = (CheckBox)findViewById(R.id.add_subject_checkBox801);
        checkBoxes[36] = (CheckBox)findViewById(R.id.add_subject_checkBox802);
        checkBoxes[37] = (CheckBox)findViewById(R.id.add_subject_checkBox803);
        checkBoxes[38] = (CheckBox)findViewById(R.id.add_subject_checkBox804);
        checkBoxes[39] = (CheckBox)findViewById(R.id.add_subject_checkBox805);
        checkBoxes[40] = (CheckBox)findViewById(R.id.add_subject_checkBox901);
        checkBoxes[41] = (CheckBox)findViewById(R.id.add_subject_checkBox902);
        checkBoxes[42] = (CheckBox)findViewById(R.id.add_subject_checkBox903);
        checkBoxes[43] = (CheckBox)findViewById(R.id.add_subject_checkBox904);
        checkBoxes[44] = (CheckBox)findViewById(R.id.add_subject_checkBox905);

        checkBoxesinitiate();

        webAddressMaker = WebAddressMaker.getInstance();
        getSupportActionBar().setTitle("ACTIONBAR");                                //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));  //액션바 색깔

        addSubject_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptAddSubject();
            }
        });

        cancelAddSubject_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptCancel();
            }
        });
    }


    private void checkBoxesinitiate(){
        ArrayList<Subject> subjectList = (ArrayList)ScheduleManager.getInstance().getList();
        ArrayList<Integer> subjectTime;
        for(Subject subject : subjectList){
            subjectTime = (ArrayList)subject.timeToNumber();
            for(int time : subjectTime){
                checkBoxes[time - 1].setClickable(false);
            }
        }
    }


    private void attemptAddSubject(){
        String s_subject = subject_input.getText().toString();
        String s_professor = professor_input.getText().toString();
        String s_semester = semester_input.getText().toString();
        String s_time = null;
        for(int i = 0; i < checkBoxes.length; i++){
            if(checkBoxes[i].isChecked()){
                String s_tmpTime = Integer.toString( (i / 5) + 1 );
                String s_tmpDay = s_days[i % 5];
                if( s_time == null )
                    s_time = s_tmpDay + s_tmpTime;
                else
                    s_time += "/" + s_tmpDay + s_tmpTime;
            }
        }
        String s_book = book_input.getText().toString();
        String s_room = room_input.getText().toString();
        String s_year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        Subject subject = new Subject(s_subject, s_professor, s_time, s_semester, s_room, s_book, s_year);
        ScheduleManager.getInstance().addToList(subject);
        new AddSubjectTask().execute(webAddressMaker.getAddLectureURL(subject));
    }


    private void attemptCancel(){
        Intent intent = new Intent(getApplicationContext(), ScheduleUI.class);
    }


    class AddSubjectTask extends WebAccessor {
        protected void onProgresssUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(AddSubjectUI.this, "완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ScheduleUI.class);
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
            Toast.makeText(AddSubjectUI.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LogInUI.class);
            startActivity(intent);
        }
    }
}
