package com.example.ibrahim.chatddemo.dataproviders;


import android.content.Context;
import android.os.AsyncTask;


import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessageDao;
import com.example.ibrahim.chatddemo.dataproviders.greendao.DaoMaster;
import com.example.ibrahim.chatddemo.dataproviders.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * By katepratik on 30/08/16.
 */

// GreenDAO Database

public class DB {
    private DaoSession deoSession;
    private Context context;
    private String DB_NAME = "chat_demo";
    private Database db;

    public DB(Context context) {
        this.context = context;
        init();
    }

    private DaoSession init() {
        if (deoSession == null || db == null) {
            deleteOldTrackerDB();
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME); //The baetter here is the name of our database.
            db = helper.getWritableDb();
            deoSession = new DaoMaster(db).newSession();
        }
        return deoSession;
    }

    /*public void migrateOldTracker(){
        // Run this at splashscreen

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String oldDBName = "tracker";
                // old tracker DB now migrated to baetter so delete old tables
                if (context.getDatabasePath(oldDBName).exists()) {
                    Database trackerDB = new DaoMaster.DevOpenHelper(context, oldDBName).getWritableDb();
                    DaoSession oldTrackerDb = new DaoMaster(trackerDB).newSession();
                    if (oldTrackerDb.getTrackerDao().count() > 0) {
                        // Get old tracker db entries since it has data (rows) > 0
                        List<Tracker> entities = oldTrackerDb.getTrackerDao().queryBuilder().orderAsc(TrackerDao.Properties.Id).list();
                        // insert into new tracker DB
                        getTrackerDB().updateInTx(entities);
                        // Drop tracker table
                        DaoMaster.dropAllTables(trackerDB, true);
                        context.deleteDatabase(oldDBName);
                    }
                }
            }
        });

    }*/

    public Database getDB() {
        init();
        return db;
    }

    public void createTables() {
        init();
        DaoMaster.createAllTables(db, true);
    }

    private void deleteOldTrackerDB() {
        final String oldDBName = "tracker";
        // old tracker DB now migrated to baetter so delete old tables
        if (context.getDatabasePath(oldDBName).exists()) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // Delete Database
                    context.deleteDatabase(oldDBName);
                }
            });
        }
    }

    /*    private DaoSession initConversationList() {
        if (daoConversationListSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME); //The baetter here is the name of our database.
            Database db = helper.getWritableDb();
            daoConversationListSession = new DaoMaster(db).newSession();
        }
        return daoConversationListSession;
    }*/

    /*    private DaoSession initChatMessages() {
        if (daoChatSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME); //The baetter here is the name of our database.
            Database db = helper.getWritableDb();
            daoChatSession = new DaoMaster(db).newSession();
        }
        return daoChatSession;
    }*/


    public ChatMessageDao getChatMessageDB() {
        return init().getChatMessageDao();
    }

    public void removeChatData() {
        init();
        db.execSQL("DROP TABLE IF EXISTS 'CHAT_MESSAGE'");
    }

    public void removeTrackerData() {
        init();
        db.execSQL("DROP TABLE IF EXISTS 'TRACKER'");
    }
}
