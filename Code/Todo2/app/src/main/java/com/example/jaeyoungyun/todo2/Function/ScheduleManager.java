package com.example.jaeyoungyun.todo2.Function;


import android.content.Context;

import com.example.jaeyoungyun.todo2.Data.Subject;
import java.util.ArrayList;
import java.util.List;

public class ScheduleManager implements Manager {

    public static Context mc;

    private static ScheduleManager instance;
    private ArrayList<Subject> subjectList;
    private Subject currentSubject;

    private ScheduleManager() {
        subjectList = new ArrayList<>();
        currentSubject = null;
    }

    public void setCurrentSubject(Subject subject){
        currentSubject = subject;
    }

    public Subject getCurrentSubject(){
        return currentSubject;
    }

    public static ScheduleManager getInstance() {
        if( instance == null ) {
            instance = new ScheduleManager();
        }

        return instance;
    }

    public void addToList(Object subject){
        subjectList.add((Subject)subject);
    }

    public void removeFromList(Object subject){
        subjectList.remove((Subject)subject);
    }

    public List getList(){
        return subjectList;
    }
}
