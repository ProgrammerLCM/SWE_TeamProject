package com.example.jaeyoungyun.todo2.Data;

/**
 * Mysql과의 연동을 위해 접속해야할 웹주소들을 가지고 있는 클래스
 */

public class WebAddress {
    private String s_basicURL;
    private String s_logInURL;
    private String s_signUpURL;
    private String s_todoCallURL;
    private String s_addTodoURL;
    private String s_adjustTodoURL;
    private String s_addLectureURL;
    private String s_lectureCallURL;
    private String s_adjustLectureURL;
    private String s_logOutURL;
    private String s_leaveURL;

    public WebAddress(){
        s_basicURL = "http://52.79.153.162/yjy/software/";
        s_logInURL = s_basicURL + "login.php";
        s_signUpURL = s_basicURL + "signup.php";
        s_todoCallURL = s_basicURL + "todocall.php";
        s_addTodoURL = s_basicURL + "todoinsert.php";
        s_adjustTodoURL = s_basicURL + "todoAD.php";
        s_addLectureURL = s_basicURL + "lectureinsert.php";
        s_lectureCallURL = s_basicURL + "lecturecall.php";
        s_adjustLectureURL = s_basicURL + "lectureAD.php";
        s_logOutURL = s_basicURL + "logout.php";
        s_leaveURL = s_basicURL + "quit.php";
    }

    public String getLogInURL(){
        return s_logInURL;
    }

    public String getSignUpURL(){
        return s_signUpURL;
    }

    public String getTodoCallURL(){
        return s_todoCallURL;
    }

    public String getAddTodoURL(){
        return s_addTodoURL;
    }

    public String getAdjustTodoURL(){
        return s_adjustTodoURL;
    }

    public String getAddLectureURL(){
        return s_addLectureURL;
    }

    public String getLectureCallURL(){
        return s_lectureCallURL;
    }

    public String getAdjustLectureURL(){
        return s_adjustLectureURL;
    }

    public String getLogOutURL(){
        return s_logOutURL;
    }

    public String getLeaveURL(){
        return s_leaveURL;
    }
}
