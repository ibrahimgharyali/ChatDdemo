package com.example.ibrahim.chatddemo.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ibrahim.chatddemo.dataproviders.DB;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessage;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessageDao;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katepratik on 11/7/17.
 */

public class ChatHelper {

    public final String LOCAL_GROUP_ID_PREFIX = "LOCAL_GROUP_";
    private final String LOCAL_CHAT_ID_PREFIX = "LOCAL_CHAT_";
    private final String USER_ME = "USER_ME";
    private final String USER_BOT = "USER_BOT";
    private final String USER_NAME = "Ibrahim";
    private Context context;
    private DB db;
    private String groupIdFromLocalDB;

    public ChatHelper(Context context){
        this.context = context;
    }



    private void initDB(){
        // Make sure db
        if (db == null)
            db = new DB(context);
    }

    private QueryBuilder<ChatMessage> chatMessagesListQuery(){
        initDB();
        return db.getChatMessageDB().queryBuilder().orderDesc(ChatMessageDao.Properties.CreatedAt);
    }




    public void clearAllChatMessages(){
        // Delete the entries
        DeleteQuery<ChatMessage> deleteQuery = chatMessagesListQuery().buildDelete();
        DebugUtils.log("CHAT [Chat Messages Delete] : " + deleteQuery);
        deleteQuery.executeDeleteWithoutDetachingEntities();

    }



    public ChatMessage saveNewSendingMessageToDB(int chat_type, String content){
        return saveChatMessageToDB(null, chat_type,content);
    }

    public ChatMessage saveChatMessageToDB(ChatMessage object,boolean read){
        ChatMessage toUser = new ChatMessage();
        if (object.getUsername() != null &&  object.getSender() != null) {
            toUser.setUsername(object.getUsername());
        }
        return saveChatMessageToDB(object.getSender(), object.getContent_type(),object.getContent());
    }


    private ChatMessage saveChatMessageToDB( String sender, int contentType, String content){

        long time = System.currentTimeMillis();
        if (sender==null)
            sender = USER_ME;

        ChatMessage newChatMsg = new ChatMessage();
        newChatMsg.setChatId(String.valueOf(time));
        newChatMsg.setSender(sender);
        newChatMsg.setUsername(USER_NAME);
        newChatMsg.setContent(content);
        newChatMsg.setContent_type(contentType);
        newChatMsg.setCreatedAt(time);

        initDB();
        // save to chat message list

        db.getChatMessageDB().insertOrReplace(newChatMsg);

        return newChatMsg;
    }






/*    public ConversationList updateNewChatMessageOffline(String localChatId, NewChatMessageResponse response){
        if (response!=null) {
            initDB();
            // check if group id already exist
            ConversationList conversationWithSameGroupID = db.getConversationListDB().queryBuilder().where(ConversationListDao.Properties.GroupId.eq(response.getGroupId())).unique();
            ConversationList conversationWithLocalID = db.getConversationListDB().queryBuilder().where(ConversationListDao.Properties.ChatId.eq(localChatId)).unique();
            if (conversationWithSameGroupID!=null){
                // this group conversation already exits in DB so just replace the content
                conversationWithSameGroupID.setChatId(response.getChatId());
                conversationWithSameGroupID.setGroupId(response.getGroupId());
                conversationWithSameGroupID.setCreatedAt(response.getCreatedAt());
                conversationWithSameGroupID.setChat_type(response.getChat_type());
                db.getConversationListDB().update(conversationWithSameGroupID);
                db.getConversationListDB().delete(conversationWithLocalID);
                return conversationWithSameGroupID;
            } else {
                // This is new group ID
                conversationWithLocalID.setChatId(response.getChatId());
                conversationWithLocalID.setGroupId(response.getGroupId());
                conversationWithLocalID.setCreatedAt(response.getCreatedAt());
                conversationWithLocalID.setContent_type(response.getChat_type());
                db.getConversationListDB().update(conversationWithLocalID);
                return conversationWithLocalID;
            }
        } else {
            return  null;
        }
    }*/





    /*public void deleteChat(String groupId){
        initDB();

        // Delete from Chat Message Table
        DeleteQuery<ChatMessage> deleteChatMessageQuery =  db.getChatMessageDB().queryBuilder()
                .where(ChatMessageDao.Properties.GroupId.eq(groupId)).buildDelete();
        deleteChatMessageQuery.executeDeleteWithoutDetachingEntities();
    }*/





    public void setGroupIdFromLocalDB(String groupIdFromLocalDB) {
        this.groupIdFromLocalDB = groupIdFromLocalDB;
    }
}
