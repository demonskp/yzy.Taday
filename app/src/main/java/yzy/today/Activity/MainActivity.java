package yzy.today.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;

import yzy.today.Net.HttpCallbackListener;
import yzy.today.Net.OkHttpUtil;
import yzy.today.R;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMoth;
    private EditText editTextDay;
    private Button button;
    private Button button1;
    private String moth;
    private String day;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        moth="error";
        day="error";

        OkHttpUtil.sendHttpRequest(new HttpCallbackListener<String>() {
            @Override
            public void onFinish(String response) {
                Log.d("---------------", "onFinish: ");
            }

            @Override
            public void onError(Exception e) {

            }
        },"1","1");

        editTextMoth=(EditText) findViewById(R.id.main_moth);
        editTextDay=(EditText)findViewById(R.id.main_date);
        button=(Button)findViewById(R.id.main_ensure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moth=editTextMoth.getText().toString();
                day=editTextDay.getText().toString();
//                Log.e( "onClick: ",moth+" "+day);

                Intent intent=new Intent(MainActivity.this,ActivityDay.class);
                intent.putExtra("month",moth);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });

        button1=(Button)findViewById(R.id.main_today);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int d = c.get(Calendar.DAY_OF_MONTH);
                int m = c.get(Calendar.MONTH);
                m++;
                moth=Integer.toString(m);
                day=Integer.toString(d);
//                Log.d("onClickklk: ",moth);
//                Log.d("onClick:",day);
                Intent intent=new Intent(MainActivity.this,ActivityDay.class);
                intent.putExtra("month",moth);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });
    }
}
