package yzy.today.Net;

/**
 * Created by yzy on 2016/9/19.
 */
public interface HttpCallbackListener <T>{
    void onFinish(T response);
    void onError(Exception e);
}
