package com.example.jaeyoungyun.todo2.Data;


public class Todo implements Comparable<Todo>{
    private String s_title;
    private String s_content;
    private String s_startDate;
    private String s_dueDate;
    private String s_doneDate;
    private String s_lecture;
    private boolean isDone;
    private boolean isImportant;
    private boolean isHide;
    private static int i_num = 0;

    public Todo(){
        s_title = null;
        s_content = null;
        s_startDate = null;
        s_dueDate = null;
        s_doneDate = null;
        s_lecture = null;
        isDone = false;
        isImportant = false;
        isHide = false;
        i_num += 1;
    }

    public Todo(String s_title, String s_content, String s_startDate, String s_dueDate, boolean isImportant){
        super();
        this.s_title = s_title;
        this.s_content = s_content;
        this.s_startDate = s_startDate;
        this.s_dueDate = s_dueDate;
        this.isImportant = isImportant;
    }
    public Todo(String s_title, String s_content, String s_startDate,
                String s_dueDate, String s_doneDate, String s_lecture, boolean isDone, boolean isImportant, boolean isHide){
        super();
        this.s_title = s_title;
        this.s_content = s_content;
        this.s_startDate = s_startDate;
        this.s_dueDate = s_dueDate;
        this.s_doneDate = s_doneDate;
        this.s_lecture = s_lecture;
        this.isDone = isDone;
        this.isImportant = isImportant;
        this.isHide = isHide;
    }

    public String getTitle(){
        return s_title;
    }

    public String getContent(){
        return s_content;
    }

    public String getStartDate(){
        return s_startDate;
    }

    public String getDueDate(){
        return s_dueDate;
    }

    public String getDoneDate(){
        return s_doneDate;
    }

    public String getLecture(){
        return s_lecture;
    }

    public int getNum(){
        return i_num;
    }

    public boolean isDone(){
        return isDone;
    }

    public boolean isImportant(){
        return isImportant;
    }

    public boolean isHide(){
        return isHide;
    }

    public void setTitle(String s_title){
        this.s_title = s_title;
    }

    public void setContent(String s_content){
        this.s_content = s_content;
    }

    public void setStartDate(String s_startDate){
        this.s_startDate = s_startDate;
    }

    public void setDueDate(String s_dueDate){
        this.s_dueDate = s_dueDate;
    }

    public void setDoneDate(String s_doneDate){
        this.s_doneDate = s_doneDate;
    }

    public void setLecture(String s_lecture){
        this.s_lecture = s_lecture;
    }

    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }

    public void setIsImportant(boolean isImportant){
        this.isImportant = isImportant;
    }

    public void setIsHide(boolean isHide){
        this.isHide = isHide;
    }

    public int compareTo(Todo compareTodo){
        return this.getTitle().compareTo(compareTodo.getTitle());
    }
}
