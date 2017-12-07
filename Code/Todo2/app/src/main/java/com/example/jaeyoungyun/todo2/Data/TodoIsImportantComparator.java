package com.example.jaeyoungyun.todo2.Data;

import java.util.Comparator;

public class TodoIsImportantComparator implements Comparator<Todo> {
    public int compare(Todo todo1, Todo todo2){
        int num1 = todo1.isImportant() ? 1 : 0;
        int num2 = todo2.isImportant() ? 1 : 0;

        return num1 - num2;
    }
}
