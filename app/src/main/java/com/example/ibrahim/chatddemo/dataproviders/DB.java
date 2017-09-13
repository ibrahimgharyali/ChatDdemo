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
    private static final String DB_NAME = "chat_demo";
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


    public ChatMessageDao getChatMessageDB() {
        return init().getChatMessageDao();
    }

}
