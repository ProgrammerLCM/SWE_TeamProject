package com.example.jaeyoungyun.todo2.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Data.TodoDueDateComparator;
import com.example.jaeyoungyun.todo2.Data.TodoIsDoneComparator;
import com.example.jaeyoungyun.todo2.Data.TodoIsImportantComparator;
import com.example.jaeyoungyun.todo2.Data.TodoStartDateComparator;
import com.example.jaeyoungyun.todo2.Data.TodoDoneDateComparator;



public class TodoManager implements Manager {
    private static TodoManager instance;
    private ArrayList<Todo> todoList;
    private Todo currentTodo;

    private TodoManager() {
        todoList = new ArrayList<>();
        currentTodo = null;
    }

    public void setCurrentTodo(Todo todo){
        currentTodo = todo;
    }

    public Todo getCurrentTodo(){
        return currentTodo;
    }

    public static TodoManager getInstance() {
        if( instance == null ) {
            instance = new TodoManager();
        }

        return instance;
    }

    public void addToList(Object todo){
        todoList.add((Todo)todo);
    }

    public void removeFromList(Object todo){
        todoList.remove((Todo)todo);
    }

    public List getList(){
        return todoList;
    }

    public void sortByBase(String s_base){
        switch(s_base) {
            case "title":
                Collections.sort(todoList);
                break;
            case "doneDate":
                Collections.sort(todoList, new TodoDoneDateComparator());
                break;
            case "dueDate":
                Collections.sort(todoList, new TodoDueDateComparator());
                break;
            case "isDone":
                Collections.sort(todoList, new TodoIsDoneComparator());
                break;
            case "isImportant":
                Collections.sort(todoList, new TodoIsImportantComparator());
                break;
            case "startDate":
                Collections.sort(todoList, new TodoStartDateComparator());
        }
    }
}