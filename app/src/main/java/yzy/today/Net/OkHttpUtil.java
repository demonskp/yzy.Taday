package yzy.today.Net;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yzy on 2017/5/19.
 */

public class OkHttpUtil {
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
                    Log.d("OK:run:--------- ",result);
                    listener.onFinish(result);
                } catch (Exception e) {
                    listener.onError(e);
                    e.printStackTrace();
                }

            }
        }).start();
    }



    private static String net(String strUrl, Map params,String method) throws Exception{

        if(method==null || method.equals("GET")){
            strUrl = strUrl+"?"+urlencode(params);
        }


        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(strUrl).build();
        Response response=okHttpClient.newCall(request).execute();
        if (response!=null){
            return request.body().toString();
        }else {
            throw new IOException();
        }
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
