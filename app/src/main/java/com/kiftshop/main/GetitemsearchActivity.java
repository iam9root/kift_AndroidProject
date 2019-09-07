package com.kiftshop.main;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetitemsearchActivity extends AsyncTask<String, Void, String> {

    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            String urls = "http://172.30.1.30:8081/join/Getitemsearch.jsp";
            System.out.println("3");
            // 접속할 서버 주소
            URL url = new URL(urls);
            System.out.println("4");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            System.out.println("5");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            System.out.println("6");

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "word=" + strings[0];
            osw.write(sendMsg);
            osw.flush();
            System.out.println("7");

            //jsp와 통신 성공 시 수행
            System.out.println(conn.getResponseCode());
            System.out.println(conn.HTTP_OK);
            if (conn.getResponseCode() == conn.HTTP_OK) {
                System.out.println("8-0");
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                System.out.println("8");

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                System.out.println("9");
                System.out.println("buffer value = " + buffer.toString());
                receiveMsg = buffer.toString();

            } else {
                // 통신 실패
                System.out.println("통신실패");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        System.out.println("10");
        System.out.println("return value :" + receiveMsg);
        return receiveMsg;
    }
}
