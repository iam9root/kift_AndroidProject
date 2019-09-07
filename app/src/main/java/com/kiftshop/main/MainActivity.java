package com.kiftshop.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Activity act = this;
    GridView gridView1, gridView2, gridView3, gridView4, gridView5, gridView6, gridView7;
    private PackageManager pm;
    private TextView mTextMessage;
    private EditText edit_btn;

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
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.name);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        edit_btn = findViewById(R.id.edit_btn);

        edit_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainActivity.this, SearchItemActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //tabhost 부분
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;
        ts1.setIndicator("Best") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        ts2.setIndicator("Tradition") ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.content3) ;
        ts3.setIndicator("Cosmetics") ;
        tabHost1.addTab(ts3) ;

        // 네 번째 Tab. (탭 표시 텍스트:"TAB 4"), (페이지 뷰:"content4")
        TabHost.TabSpec ts4 = tabHost1.newTabSpec("Tab Spec 4") ;
        ts4.setContent(R.id.content4) ;
        ts4.setIndicator("Foods") ;
        tabHost1.addTab(ts4) ;

        TabHost.TabSpec ts5 = tabHost1.newTabSpec("Tab Spec 5") ;
        ts5.setContent(R.id.content5) ;
        ts5.setIndicator("Prop") ;
        tabHost1.addTab(ts5) ;

        TabHost.TabSpec ts6 = tabHost1.newTabSpec("Tab Spec 6") ;
        ts6.setContent(R.id.content6) ;
        ts6.setIndicator("Health") ;
        tabHost1.addTab(ts6) ;

        TabHost.TabSpec ts7 = tabHost1.newTabSpec("Tab Spec 7") ;
        ts7.setContent(R.id.content7) ;
        ts7.setIndicator("Experience") ;
        tabHost1.addTab(ts7) ;

        pm = getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //그리드뷰
        gridView1 = (GridView)findViewById(R.id.gridview1);
        gridView1.setAdapter(new MainActivity.gridAdapter("b"));
        gridView2 = (GridView)findViewById(R.id.gridview2);
        gridView2.setAdapter(new MainActivity.gridAdapter("t"));
        gridView3 = (GridView)findViewById(R.id.gridview3);
        gridView3.setAdapter(new MainActivity.gridAdapter("c"));
        gridView4 = (GridView)findViewById(R.id.gridview4);
        gridView4.setAdapter(new MainActivity.gridAdapter("f"));
        gridView5 = (GridView)findViewById(R.id.gridview5);
        gridView5.setAdapter(new MainActivity.gridAdapter("p"));
        gridView6 = (GridView)findViewById(R.id.gridview6);
        gridView6.setAdapter(new MainActivity.gridAdapter("H"));
        gridView7 = (GridView)findViewById(R.id.gridview7);
        gridView7.setAdapter(new MainActivity.gridAdapter("e"));
    }

    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;
        String classify = "";
        int size=0;
        JSONArray array = new JSONArray();
        JSONObject object;
        ImageView imageView = null;
        ImageView imageView2 = null;
        TextView textView = null;
        TextView textView2 = null;

        public gridAdapter(String classify){
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.classify = classify;
        }

        public final int getCount(){
            //return apps.size();
            //DB연결
            String result="";
            System.out.println("1");
            GetitemlistActivity task = new GetitemlistActivity();
            System.out.println("2");
            try{
                result = task.execute(classify).get();
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

            if(classify.equals("H")) { //Health 카테고리 클릭시
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
            }

            return convertView;
        }
    }
}
