package com.example.ibrahim.chatddemo.dataproviders.greendao;


import com.example.ibrahim.chatddemo.models.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by katepratik on 14/7/17.
 */

@Entity
public class ChatMessage extends BaseModel {

    @Id
    private String chatId;
    private String sender;
    private String content;
    private String username;
    private int content_type;
    private long createdAt;


    @Generated(hash = 1271746737)
    public ChatMessage(String chatId, String sender, String content,
            String username, int content_type, long createdAt) {
        this.chatId = chatId;
        this.sender = sender;
        this.content = content;
        this.username = username;
        this.content_type = content_type;
        this.createdAt = createdAt;
    }

    @Generated(hash = 2271208)
    public ChatMessage() {
    }

    
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


}
