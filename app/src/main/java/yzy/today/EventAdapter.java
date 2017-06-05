package yzy.today;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yzy.today.Dao.Event;
import yzy.today.Net.HttpCallbackListener;
import yzy.today.Net.HttpUtil;

/**
 * Created by yzy on 2016/9/18.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private int resourceId;




    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event=getItem(position);
        final Handler handler;
        final ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder.titleView=(TextView)convertView.findViewById(R.id.event_title);
            viewHolder.yearView=(TextView)convertView.findViewById(R.id.event_year);
            viewHolder.monthView=(TextView)convertView.findViewById(R.id.event_month);
            viewHolder.dayView=(TextView)convertView.findViewById(R.id.event_day);


            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.picView=(ImageView)convertView.findViewById(R.id.event_pic);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                viewHolder.picView.setImageBitmap((Bitmap) msg.obj);
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
        },event.getPic());


        viewHolder.titleView.setText(event.getTitle());
        viewHolder.yearView.setText(event.getYear());
        viewHolder.monthView.setText(event.getMonth());
        viewHolder.dayView.setText(event.getDay());



        return convertView;
    }


    class ViewHolder{

        public TextView titleView;
        public TextView yearView;
        public TextView monthView;
        public TextView dayView;
        public ImageView picView;
    }

}
