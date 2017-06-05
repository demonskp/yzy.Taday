package yzy.today.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import yzy.today.Net.HttpCallbackListener;
import yzy.today.Net.HttpUtil;
import yzy.today.R;

public class ActivityEvent extends AppCompatActivity {

    private TextView titleView;
    private ImageView picView;
    private TextView desView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        final Intent intent1=getIntent();
        String title=intent1.getStringExtra("title");
        String des=intent1.getStringExtra("des");
        String pic=intent1.getStringExtra("pic");

        titleView=(TextView)findViewById(R.id.z_title);
        picView=(ImageView)findViewById(R.id.z_pic);
        desView=(TextView)findViewById(R.id.z_des);

        titleView.setText(title);
        desView.setText(des);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                picView.setImageBitmap((Bitmap) msg.obj);
            }
        };

        HttpUtil.sendImageBack(new HttpCallbackListener<Bitmap>() {
            @Override
            public void onFinish(Bitmap response) {
                Message mm=new Message();
                mm.what=1;
                mm.obj=response;
                handler.sendMessage(mm);
            }

            @Override
            public void onError(Exception e) {

            }
        },pic);




    }
}
