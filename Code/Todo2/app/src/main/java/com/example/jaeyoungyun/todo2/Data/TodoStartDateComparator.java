package com.example.jaeyoungyun.todo2.Data;

import java.util.Comparator;

public class TodoStartDateComparator implements Comparator<Todo> {
    public int compare(Todo todo1, Todo todo2){
        String s_startDate1 = todo1.getStartDate();
        String s_startDate2 = todo2.getStartDate();

        return s_startDate1.compareTo(s_startDate2);
    }
}

