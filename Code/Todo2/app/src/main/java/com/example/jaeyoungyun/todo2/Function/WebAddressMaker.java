package com.example.jaeyoungyun.todo2.Function;

import com.example.jaeyoungyun.todo2.Data.Subject;
import com.example.jaeyoungyun.todo2.Data.Todo;
import com.example.jaeyoungyun.todo2.Data.WebAddress;


public class WebAddressMaker {
    private static WebAddressMaker instance;

    private WebAddress webAddress;
    private String s_name;
    private String s_email;

    public static WebAddressMaker getInstance(){
        if( instance == null ){
            instance = new WebAddressMaker();
        }

        return instance;
    }

    private WebAddressMaker(){
        webAddress = new WebAddress();
    }

    public String getName(){
        return s_name;
    }

    public String getEmail(){
        return s_email;
    }

    public void setName(String s_name){
        this.s_name = s_name;
    }

    public void setEmail(String s_email){
        this.s_email = s_email;
    }

    public String getSignInURL(String s_email, String s_password){
        String s_url = webAddress.getLogInURL() + "?email=" + s_email + "&pwd=" + s_password;
        return s_url;
    }

    public String getSignOutURL(){
        String s_url = webAddress.getLogOutURL();
        return s_url;
    }

    public String getSignUpURL(String s_name, String s_email, String s_password){
        String s_url = webAddress.getSignUpURL() + "?name=" + s_name + "&pwd=" + s_password + "&email=" + s_email;
        return s_url;
    }

    public String getLeaveURL(){
        String s_url = webAddress.getLeaveURL();
        return s_url;
    }

    public String getTodoCallURL(){
        String s_url = webAddress.getTodoCallURL();
        return s_url;
    }

    public String getAddTodoURL(Todo todo){
        String s_url = webAddress.getAddTodoURL() + "?tdnum=" + todo.getNum() + "&tdcontent=" + todo.getContent()
                + "&tdfinish=" + todo.isDone() + "&tdfinishtime=" + todo.getDoneDate()
                + "&tdimportant=" + todo.isImportant() + "&tdname=" + todo.getTitle()
                + "&tddeadline=" + todo.getDueDate() + "&tdstart=" + todo.getStartDate()
                + "&thide=" + todo.isHide() + "&lecname=" + todo.getLecture();
        return s_url;
    }

    public String getDeleteTodoURL(Todo todo){
        String s_url = webAddress.getAdjustTodoURL() + "?Aord=D" + "&tdnum=" + todo.getNum();
        return s_url;
    }

    public String getAdjustTodoURL(Todo todo){
        String s_url = webAddress.getAdjustTodoURL() + "?Aord=A" + "&tdnum=" + todo.getNum()
                + "&tdcontent=" + todo.getContent()
                + "&tdfinish=" + todo.isDone() + "&tdfinishtime=" + todo.getDoneDate()
                + "&tdimportant=" + todo.isImportant() + "&tdname=" + todo.getTitle()
                + "&tddeadline=" + todo.getDueDate() + "&tdstart=" + todo.getStartDate()
                + "&thide=" + todo.isHide() + "&lecname=" + todo.getLecture();
        return s_url;
    }

    public String getAddLectureURL(Subject subject){
        String s_url = webAddress.getAddLectureURL() + "?lecnum=" + subject.getNum()
                + "&lecname=" + subject.getSubject() + "&lecpfname=" + subject.getProfessor()
                + "&lecroom=" + subject.getRoom() + "&lectextbook=" + subject.getBook()
                + "&lecsemester=" + subject.getSemester() + "&lectime=" + subject.getTime()
                + "&lecyear=" + subject.getYear();
        return s_url;
    }

    public String getDeleteLectureURL(Subject subject){
        String s_url = webAddress.getAdjustLectureURL() + "?AorD=D" + "&lecnum=" + subject.getNum()
                + "&lecname=" + subject.getSubject() + "&lecpfname=" + subject.getProfessor()
                + "&lecroom=" + subject.getRoom() + "&lectextbook=" + subject.getBook()
                + "&lecsemester=" + subject.getSemester() + "&lectime=" + subject.getTime();
        return s_url;
    }

    public String getLectureCallURL(){
        String s_url = webAddress.getLectureCallURL();
        return s_url;
    }

    public String getAdjustLectureURL(Subject subject){
        String s_url = webAddress.getAdjustLectureURL() + "?AorD=A" + "&lecnum=" + subject.getNum()
                + "&lecname=" + subject.getSubject() + "&lecpfname=" + subject.getProfessor()
                + "&lecroom=" + subject.getRoom() + "&lectextbook=" + subject.getBook()
                + "&lecsemester=" + subject.getSemester() + "&lectime=" + subject.getTime();
        return s_url;
    }
}