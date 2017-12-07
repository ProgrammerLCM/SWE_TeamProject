package com.example.jaeyoungyun.todo2.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Subject {
    private String s_subject;
    private String s_professor;
    private String s_time;
    private String s_semester;
    private String s_room;
    private String s_book;
    private String s_year;
    private static int i_num = 0;

    public Subject(){
        s_subject = null;
        s_professor = null;
        s_time = null;
        s_semester = null;
        s_room = null;
        s_book = null;
        s_year = null;
        i_num += 1;
    }

    public Subject(String s_subject, String s_professor, String s_time, String s_semester, String s_room,
                   String s_book, String s_year){
        super();
        this.s_subject = s_subject;
        this.s_professor = s_professor;
        this.s_time = s_time;
        this.s_semester = s_semester;
        this.s_room = s_room;
        this.s_book = s_book;
        this.s_year = s_year;

    }


    public String getSubject(){
        return s_subject;
    }

    public String getProfessor(){
        return s_professor;
    }

    public String getTime(){
        return s_time;
    }

    public String getSemester(){
        return s_semester;
    }

    public String getRoom(){
        return s_room;
    }

    public String getBook(){
        return s_book;
    }

    public String getYear(){
        return s_year;
    }

    public String getNum(){
        return Integer.toString(i_num);
    }

    public void setSubject(String s_subject){
        this.s_subject = s_subject;
    }

    public void setProfessor(String s_professor){
        this.s_professor = s_professor;
    }

    public void setTime(String s_time){
        this.s_time = s_time;
    }

    public void setSemester(String s_semester){
        this.s_semester = s_semester;
    }

    public void setRoom(String s_room){
        this.s_room = s_room;
    }

    public void setBook(String s_book){
        this.s_book = s_book;
    }

    public void setYear(String s_year){
        this.s_year = s_year;
    }

    public List timeToNumber(){
        ArrayList<Integer> result = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(s_time, "/");
        while(st.hasMoreElements()){
            int i_dayPoint = 0;
            String s_token = st.nextToken();
            char day = s_token.charAt(0);
            switch (day){
                case '월':
                    i_dayPoint = 1;
                    break;
                case '화':
                    i_dayPoint = 2;
                    break;
                case '수':
                    i_dayPoint = 3;
                    break;
                case '목':
                    i_dayPoint = 4;
                    break;
                case '금':
                    i_dayPoint = 5;
            }

            i_dayPoint += ( s_token.charAt(1) - 1 ) * 5;
            result.add(i_dayPoint);
        }
        return result;
    }
}
