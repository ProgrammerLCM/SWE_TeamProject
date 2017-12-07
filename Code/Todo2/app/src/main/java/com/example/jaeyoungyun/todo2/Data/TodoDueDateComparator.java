package com.example.jaeyoungyun.todo2.Data;

import java.util.Comparator;

public class TodoDueDateComparator implements Comparator<Todo> {
    public int compare(Todo todo1, Todo todo2){
        String s_dueDate1 = todo1.getDueDate();
        String s_dueDate2 = todo2.getDueDate();

        return s_dueDate1.compareTo(s_dueDate2);
    }
}
