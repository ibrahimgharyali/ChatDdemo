package com.example.ibrahim.chatddemo.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.ibrahim.chatddemo.R;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessage;
import com.example.ibrahim.chatddemo.utils.ChatHelper;
import com.example.ibrahim.chatddemo.viewHolder.ChatConversationScreenAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    private EditText addComments;
    private RecyclerView chatRecyclerView;
    private ArrayList<ChatMessage> chatModelsList;
    private ChatConversationScreenAdapter userChatAdapter;
    private ImageButton  sendButton;
    private ProgressBar progressBar;
    private ChatHelper chatHelper;
    private RelativeLayout typingTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setBackgroundDrawableResource(R.drawable.chat_background);

        // initialize chat helper
        initChatHelper();
        addComments = (EditText) findViewById(R.id.add_comment);
        sendButton = (ImageButton) findViewById(R.id.send_comment);
        typingTV = (RelativeLayout) findViewById(R.id.typing_text);
        chatRecyclerView = (RecyclerView) findViewById(R.id.comments_list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        chatRecyclerView.setLayoutManager(mLayoutManager);
        chatRecyclerView.setNestedScrollingEnabled(false);

        chatModelsList = new ArrayList<>();
//        YoYo.with(Techniques.RollIn).duration(800).repeat(6).playOn(typingTV);

        final View parent = (View) sendButton.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                sendButton.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , sendButton));
            }
        });
        getData(true);

        // Add comments
        enableSend();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }




    private void getData(final boolean firstTime) {
        if (!firstTime)
            progressBar.setVisibility(View.VISIBLE);
        // now get chat list from chat message table via local db
        getPreviousMessages();
    }

    private void getPreviousMessages() {
        //Fetching data from DB
        chatModelsList = chatHelper.readChatMessageListFromDB();
        //Creating and setting Adapter
        userChatAdapter = new ChatConversationScreenAdapter(chatModelsList);
        chatRecyclerView.setAdapter(userChatAdapter);
    }


    private void initChatHelper(){
        if (chatHelper == null) {
            chatHelper = new ChatHelper(this);
        }
    }
    
    private void enableSend() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String enteredText = addComments.getText().toString().trim();
                if (enteredText.length() > 0){
                    addComments.setText("");
                    // save msg in local DB
                    ChatMessage chatMessage = chatHelper.saveNewSendingMessageToDB(enteredText);
                    showTyping(true);
                    addToAdapterAndShowInList(chatMessage);
                    //now show loading for 2 seconds and show a automated message
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showTyping(false);
                            sendReplyToUser();
                        }
                    }, 2000);
                }
            }

        });
    }

    private void addToAdapterAndShowInList(ChatMessage chatMessage) {
        if (chatMessage!=null){
            chatModelsList.add(0, chatMessage);
        }
        userChatAdapter.notifyItemInserted(0);
        scrollToBottom();
    }

    /** Create an automated message, save it in db */
    private void sendReplyToUser() {
        ChatMessage chatMessage = chatHelper.saveChatMessageToDB(ChatHelper.USER_BOT, System.currentTimeMillis()+"");
        addToAdapterAndShowInList(chatMessage);
    }

    private void showTyping(boolean b) {
        typingTV.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private void scrollToBottom() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int scrollTo = ((View) chatRecyclerView.getParent().getParent()).getBottom() + chatRecyclerView.getBottom();
                chatRecyclerView.smoothScrollBy(0, scrollTo);
            }
        }, 500);
    }

    /*private void disableSend() {
        sendButton.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.comment_off), PorterDuff.Mode.SRC_ATOP);
        sendButton.setOnClickListener(null);
    }*/




}
