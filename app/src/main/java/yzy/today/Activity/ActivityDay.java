package yzy.today.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import yzy.today.Dao.DaoManage;
import yzy.today.Dao.Event;
import yzy.today.EventAdapter;
import yzy.today.Net.HttpCallbackListener;
import yzy.today.Net.HttpUtil;
import yzy.today.R;

public class ActivityDay extends AppCompatActivity {

    private String month = "10";
    private String day = "10";
    ListView listView;
    EventAdapter eventAdapter;



    static DaoManage daoManage;

    final  static String[] items={"收藏","删除"};


    private List<Event> eventList = new ArrayList<Event>();

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"已关注",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent=getIntent();
        month=intent.getStringExtra("month");
        day=intent.getStringExtra("day");

        daoManage=DaoManage.getDaoManage(ActivityDay.this);

        setContentView(R.layout.activity_day);
        initEvent();
        eventAdapter = new EventAdapter(ActivityDay.this, R.layout.event_item, eventList);
        listView = (ListView) findViewById(R.id.day_list);
        listView.setAdapter(eventAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event=eventList.get(position);
                Intent intent1=new Intent(ActivityDay.this,ActivityEvent.class);
                intent1.putExtra("title",event.getTitle());
                intent1.putExtra("pic",event.getPic());
                intent1.putExtra("des",event.getDes());
                startActivity(intent1);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder listDialog=new AlertDialog.Builder(ActivityDay.this);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("onClick: ","ssssssssssssssssssssssssssssss");
                     switch (items[which]){
                         case "收藏":
                             Event event=eventList.get(position);
                             Log.d("onClick: ",event.toString());
                             daoManage.insertEvent(event);
                             Toast.makeText(ActivityDay.this,"收藏成功",Toast.LENGTH_LONG).show();
                             break;
                         case "删除":
                             break;
                     }
                    }
                });

                listDialog.show();

                return true;
            }
        });



    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            eventList= (List<Event>) msg.obj;
            eventAdapter = new EventAdapter(ActivityDay.this, R.layout.event_item, eventList);
            listView = (ListView) findViewById(R.id.day_list);
            listView.setAdapter(eventAdapter);
        }
    };

    private void initEvent() {
//        eventList.add(new Event("10","rdy","yy","eyer","ertre","ertr","ert","yyy"));
        HttpUtil.sendHttpRequest(new HttpCallbackListener<String>() {
            @Override
            public void onFinish(String response) {
                Log.e("onFinish: ", response+"");
                Gson gson = new Gson();
                data d = gson.fromJson(response, data.class);
                eventList = d.getResult();
                Message m = new Message();
                m.obj = d.getResult();
                handler.sendMessage(m);

            }

            @Override
            public void onError(Exception e) {
                Log.d("onError: ", e.toString());
            }
        }, month, day);
    }

    class data {
        String error_code;
        String reason;
        List<Event> result;

        public List<Event> getResult() {
            return result;
        }

        public String getError_code() {
            return error_code;
        }

        public String getReason() {
            return reason;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setResults(List<Event> result) {
            this.result = result;
        }
    }
}
