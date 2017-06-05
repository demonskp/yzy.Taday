package yzy.today.Net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yzy on 2016/9/19.
 */
public class HttpUtil {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="5deddfdf2c847e616d9b0efe690cbfca";



    public static void sendHttpRequest(final HttpCallbackListener<String> listener, final String month, final String day){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String result =null;
                String url ="http://api.juheapi.com/japi/toh";//请求接口地址
                Map params = new HashMap();//请求参数
                params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
                params.put("v","1.0");//版本，当前：1.0
                params.put("month",month);//月份，如：10
                params.put("day",day);//日，如：1

                try {
                    result =net(url, params, "GET");
                    Log.d("run: ",result);
                    listener.onFinish(result);
                } catch (Exception e) {
                    listener.onError(e);
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void sendImageBack(final HttpCallbackListener<Bitmap> listener, final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap=null;
                try {
                    bitmap =getBitmap(url);
                    listener.onFinish(bitmap);
                } catch (Exception e) {
                    listener.onError(e);
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public static Bitmap getBitmap(String u){
        Bitmap bitmap = null;
        HttpURLConnection conn=null;
        try {
            URL url=new URL(u);
            try {
                conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

