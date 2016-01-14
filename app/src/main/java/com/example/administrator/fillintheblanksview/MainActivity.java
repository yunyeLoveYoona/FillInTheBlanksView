package com.example.administrator.fillintheblanksview;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.fillintheblanksview.view.FillInTheBlanksView;


public class MainActivity extends ActionBarActivity {
    FillInTheBlanksView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (FillInTheBlanksView) findViewById(R.id.view);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setText("春眠不觉晓，(处处闻啼鸟)。处处闻处处闻啼鸟" +
                        "(夜来风雨声)处处闻啼鸟，(处处闻)花落知多少(处)处处闻啼鸟处处闻啼鸟处处闻啼鸟" +
                        "(处处闻啼鸟)处处闻啼鸟处处闻啼鸟" +
                        "(处处闻啼鸟处处闻啼鸟)处处闻啼鸟处处闻啼鸟？鸟处处闻啼鸟鸟处处闻啼鸟鸟处处闻啼鸟" +
                        "(鸟处处闻啼鸟)鸟处处闻啼鸟", 20, getResources().getColor(R.color.black));
            }
        }, 5000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}
