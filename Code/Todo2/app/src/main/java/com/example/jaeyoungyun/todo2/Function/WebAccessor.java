package com.example.jaeyoungyun.todo2.Function;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 한번에 접속하고 나면 재사용 불가하므로 다시 인스턴스생성해야함
 * 데이터를 통째로 주기때문에 파싱필요
 */

public class WebAccessor extends AsyncTask<String, Integer, String>{

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected String doInBackground(String... urls){
        StringBuilder resultText = new StringBuilder();
        try{
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while(true){
                String s_line = br.readLine();
                if( s_line == null )
                    break;
                resultText.append(s_line);
                }
            br.close();

            conn.disconnect();

        } catch(Exception e){
            e.printStackTrace();
        }
        return resultText.toString();
    }
}