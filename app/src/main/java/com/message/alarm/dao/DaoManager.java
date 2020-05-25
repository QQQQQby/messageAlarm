package com.message.alarm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.message.alarm.bean.DaoMaster;
import com.message.alarm.bean.DaoSession;

public class DaoManager {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    public DaoSession mDaoSession;

    private static DaoManager mDaoManager;
    /**
     * 上下文
     */
    private static  Context context;
    /**
     * 使用单例模式获得操作数据库的对象
     */
    public static DaoManager getInstance() {
        if (mDaoManager == null) {
            synchronized (DaoManager.class) {
                if (mDaoManager == null) {
                    mDaoManager = new DaoManager();
                }
            }
        }
        return mDaoManager;
    }


    public static void initContext(Context mContext){
        context = mContext;
    }
    /**
     * 初始化
     */
    public DaoManager() {
        mHelper = new DaoMaster.DevOpenHelper(context,"message.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"message.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"message.db",null);

        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param personInfor
     */
//    public void insertOrReplace(PersonInfor personInfor){
//        personInforDao.insertOrReplace(personInfor);
//    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param personInfor
     */
//    public long insert(PersonInfor personInfor){
//        return  personInforDao.insert(personInfor);
//    }

    /**
     * 更新数据
     * @param personInfor
     */
//    public void update(PersonInfor personInfor){
//        PersonInfor mOldPersonInfor = personInforDao.queryBuilder().where(PersonInforDao.Properties.Id.eq(personInfor.getId())).build().unique();//拿到之前的记录
//        if(mOldPersonInfor !=null){
//            mOldPersonInfor.setName("张三");
//            personInforDao.update(mOldPersonInfor);
//        }
//    }
    /**
     * 按条件查询数据
     */
//    public List<PersonInfor> searchByWhere(String wherecluse){
//        List<PersonInfor>personInfors = (List<PersonInfor>) personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(wherecluse)).build().unique();
//        return personInfors;
//    }
    /**
     * 查询所有数据
     */
//    public List<PersonInfor> searchAll(){
//        List<PersonInfor>personInfors=personInforDao.queryBuilder().list();
//        return personInfors;
//    }
    /**
     * 删除数据
     */
//    public void delete(String wherecluse){
//        personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
//    }
}
