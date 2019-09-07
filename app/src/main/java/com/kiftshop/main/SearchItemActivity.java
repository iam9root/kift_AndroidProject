package com.kiftshop.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchItemActivity extends AppCompatActivity {

    Activity act = this;
    private EditText edit_search;
    private TextView mTextMessage;
    private ImageButton searchBtn;
    private GridView search_gridview;
    private String word;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(item.isChecked()) { //이미 선택된 버튼 클릭시 아무동작 안함.
                        return true;
                    }
                    else {
                        //mTextMessage.setText(R.string.title_home);
                        mTextMessage.setText("Home");
                        item.setChecked(true);
                        return true;
                    }
                case R.id.navigation_mylist:
                    if(item.isChecked()) {
                        return true;
                    }
                    else {
                        mTextMessage.setText("MyList");
                        item.setChecked(true);
                        return true;
                    }
                case R.id.navigation_kiftmap:
                    if(item.isChecked()) {
                        return true;
                    }
                    else {
                        mTextMessage.setText("K-iftMap");
                        item.setChecked(true);
                        return true;
                    }
                case R.id.navigation_setting:
                    if(item.isChecked()) {
                        return true;
                    }
                    else {
                        mTextMessage.setText("Setting");
                        item.setChecked(true);
                        return true;
                    }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchitem);

        edit_search = findViewById(R.id.edit_search);
        searchBtn = findViewById(R.id.searchBtn);
        search_gridview = findViewById(R.id.search_gridview);

        //editText 엔터키 동작
        edit_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                word = edit_search.getText().toString();
                search_gridview.setAdapter(new SearchItemActivity.gridAdapter(word));
                search_gridview.setVisibility(View.VISIBLE);
                return true;
            }
        });

        //이미지버튼 클릭시
        searchBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                word = edit_search.getText().toString();
                Toast.makeText(act, word, Toast.LENGTH_SHORT).show();
                search_gridview.setAdapter(new SearchItemActivity.gridAdapter(word));
                search_gridview.setVisibility(View.VISIBLE);
            }
        });
    }

    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;
        String word = "";
        int size=0;
        JSONArray array = new JSONArray();
        JSONObject object;
        ImageView imageView = null;
        ImageView imageView2 = null;
        TextView textView = null;
        TextView textView2 = null;

        public gridAdapter(String word){
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.word = word;
        }

        public final int getCount(){
            //DB연결
            String result="";
            System.out.println("1");
            GetitemsearchActivity task = new GetitemsearchActivity();
            System.out.println("2");
            try{
                System.out.println("word="+word);
                result = task.execute(word).get();
            } catch(Exception e){
                e.printStackTrace();
            }

            try{
                array = new JSONArray(result);
            }catch (Exception e) {
                e.printStackTrace();
            }

            size=array.length();
            return size;
        }

        public final Object getItem(int position){
            //return apps.get(position);
            return null;
        }

        public final long getItemId(int position){
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent){

            if(convertView == null){
                convertView = inflater.inflate(R.layout.grid_item, parent, false);
            }

                for(int i=0; i<size; i++){
                    try{
                        //griditem
                        convertView = inflater.inflate(R.layout.grid_item, parent, false);
                        //final ResolveInfo info = apps.get(position);

                        imageView = (ImageView) convertView.findViewById(R.id.imageView);
                        imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
                        textView = (TextView) convertView.findViewById(R.id.textView);
                        textView2 = (TextView) convertView.findViewById(R.id.textView2);

                        imageView = (ImageView) convertView.findViewById(R.id.imageView);
                        textView = (TextView) convertView.findViewById(R.id.textView);
                        textView2 = (TextView) convertView.findViewById(R.id.textView2);

                        object = array.getJSONObject(Integer.valueOf(position));
                        Glide.with(getApplicationContext())
                                .load(object.getString("thumbnailpath"))
                                .placeholder(R.drawable.everywhere)
                                .error(R.drawable.gangnam)
                                .into(imageView);
                        imageView2.setImageResource(R.drawable.heart1);
                        textView.setText(object.getString("itemname"));
                        textView2.setText(object.getString("price"));

                        imageView.setOnClickListener(new Button.OnClickListener(){
                            public void onClick(View v){
                                try{
                                    object = array.getJSONObject(Integer.valueOf(position));
                                    Toast.makeText(act,object.getString("itemid"),Toast.LENGTH_SHORT).show();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });


                        imageView2.setOnClickListener(new Button.OnClickListener(){
                            public void onClick(View v){
                                try{
                                    object = array.getJSONObject(Integer.valueOf(position));
                                    Toast.makeText(act,object.getString("price"),Toast.LENGTH_SHORT).show();
                                    imageView2.setImageResource(R.drawable.heart2);
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            return convertView;
        }
    }
}
