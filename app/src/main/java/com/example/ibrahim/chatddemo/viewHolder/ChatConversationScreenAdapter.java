package com.example.ibrahim.chatddemo.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.chatddemo.R;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessage;
import com.example.ibrahim.chatddemo.gui.FontTextView;
import com.example.ibrahim.chatddemo.utils.TimeUtils;

import java.util.ArrayList;

/**
 * By katepratik on 01/07/16.
 */
public class ChatConversationScreenAdapter extends
        RecyclerView.Adapter<ChatConversationScreenAdapter.ViewHolder> {

    private static final int CHAT_USER_ME = 0;
    private static final int CHAT_USER_OTHER = 1;
    private final ArrayList<ChatMessage> mtList;

    public ChatConversationScreenAdapter(ArrayList<ChatMessage> list) {
        mtList = list;
    }
    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mtList.get(position);
        if (chatMessage != null) {
            return chatMessage.getSender().equals("USER_ME")?CHAT_USER_ME:CHAT_USER_OTHER;
        }
        else
            return CHAT_USER_ME;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a layout
        View itemView;
        switch (viewType){
            case CHAT_USER_ME:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_chat_me, parent, false);
                break;
            case CHAT_USER_OTHER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_chat_other, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_chat_other, parent, false);
                break;
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ChatMessage chatMessage = mtList.get(position);
        if (chatMessage!=null) {
            holder.commentText.setText(chatMessage.getContent());
            holder.time.setText(TimeUtils.getDateTimePretty(chatMessage.getCreatedAt()));

            holder.usernameTV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mtList.size();
    }



    // initializes some private fields to be used by RecyclerView.
    static class ViewHolder extends RecyclerView.ViewHolder {

        final FontTextView commentText;
        final FontTextView usernameTV;
        @NonNull
        final FontTextView time;

        ViewHolder(@NonNull View view) {
            super(view);
            commentText = view.findViewById(R.id.comment_text);
            usernameTV = view.findViewById(R.id.user_name);
            time = view.findViewById(R.id.comment_time);
        }
    }

}
