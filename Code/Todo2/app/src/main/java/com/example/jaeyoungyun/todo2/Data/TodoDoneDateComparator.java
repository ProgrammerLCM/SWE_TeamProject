package com.example.jaeyoungyun.todo2.Data;

import java.util.Comparator;

public class TodoDoneDateComparator implements Comparator<Todo> {
    public int compare(Todo todo1, Todo todo2){
        String s_doneDate1 = todo1.getDoneDate();
        String s_doneDate2 = todo2.getDoneDate();

        return s_doneDate1.compareTo(s_doneDate2);
    }
}

