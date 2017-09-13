package com.example.ibrahim.chatddemo.utils;

import android.content.Context;

import com.example.ibrahim.chatddemo.dataproviders.DB;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessage;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessageDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

/**
 * Created by katepratik on 11/7/17.
 */

public class ChatHelper {
    private static final String USER_ME = "USER_ME";
    public static final String USER_BOT = "USER_BOT";
    private Context context;
    private DB db;

    public ChatHelper(Context context){
        this.context = context;
    }



    private void initDB(){
        // Make sure db
        if (db == null)
            db = new DB(context);
    }





    /** public void clearAllChatMessages(){
        // Delete the entries
        DeleteQuery<ChatMessage> deleteQuery = chatMessagesListQuery().buildDelete();
        DebugUtils.log("CHAT [Chat Messages Delete] : " + deleteQuery);
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }*/



    public ChatMessage saveNewSendingMessageToDB(String content){
        return saveChatMessageToDB(null, content);
    }

    public ChatMessage saveChatMessageToDB( String sender,  String content){

        long time = System.currentTimeMillis();
        if (sender==null)
            sender = USER_ME;
        ChatMessage newChatMsg = new ChatMessage();
        newChatMsg.setChatId(String.valueOf(time));
        newChatMsg.setSender(sender);
        newChatMsg.setContent(content);
        newChatMsg.setCreatedAt(time);
        initDB();
        // save to chat message Table
        db.getChatMessageDB().insertOrReplace(newChatMsg);
        return newChatMsg;
    }
    public ArrayList<ChatMessage> readChatMessageListFromDB() {
        // Make sure db
        initDB();
        DebugUtils.log("CHAT [Chat Size] : " + db.getChatMessageDB().count());
        QueryBuilder<ChatMessage> query = db.getChatMessageDB().queryBuilder().orderDesc(ChatMessageDao.Properties.CreatedAt);
        return new ArrayList<>(query.list());
    }

    /*public void deleteChat(String groupId){
        initDB();

        // Delete from Chat Message Table
        DeleteQuery<ChatMessage> deleteChatMessageQuery =  db.getChatMessageDB().queryBuilder()
                .where(ChatMessageDao.Properties.GroupId.eq(groupId)).buildDelete();
        deleteChatMessageQuery.executeDeleteWithoutDetachingEntities();
    }*/

}
