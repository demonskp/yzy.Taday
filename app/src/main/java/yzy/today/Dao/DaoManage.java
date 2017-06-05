package yzy.today.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by yzy on 2017/3/29.
 */

public class DaoManage {
    private static DaoManage daoManage;
    private static Context appContext;
    private static DaoSession mDaoSession;
    private static EventDao eventDao;
    private static DaoMaster daoMaster;
    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase db;

    private DaoManage(){}

    public static DaoManage getDaoManage(Context context){
        if (daoManage==null){
            daoManage=new DaoManage();
            if (appContext==null){
                appContext=context.getApplicationContext();
            }
            daoManage.mHelper=new DaoMaster.DevOpenHelper(appContext,"event_db",null);
            daoManage.db=daoManage.mHelper.getWritableDatabase();
            daoManage.daoMaster=new DaoMaster(daoManage.db);
            daoManage.mDaoSession=daoManage.daoMaster.newSession();
            daoManage.eventDao=daoManage.mDaoSession.getEventDao();
        }

        return daoManage;
    }

    /**
     * 插入事件
     *
     */

    public void insertEvent(Event event){
        Log.d("insertEvent: ",event.toString());
        eventDao.insertOrReplace(event);
    }

    /**
     * 搜索事件
     */

    public List<Event> showAll(){
        List<Event> list=eventDao.loadAll();
        return list;
    }

}
