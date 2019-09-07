package com.kiftshop.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectAttractionsActivity extends AppCompatActivity {
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private TextView tx1, tx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectattractions);

        Intent intent = getIntent();

        //id, destination값 받아오기
        //final String id = intent.getStringExtra("id");
        //final String destination = intent.getStringExtra("destination");

        editSearch = (EditText) findViewById(R.id.editText);
        tx1 = (TextView) findViewById(R.id.tx1);
        //tx1.setText(id);
        tx2 = (TextView) findViewById(R.id.tx2);
        // tx2.setText(destination);

        // editSearch 클릭 시
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchListviewActivity.class);
                startActivity(intent);//액티비티 띄우기
            }
        });
    }
}
