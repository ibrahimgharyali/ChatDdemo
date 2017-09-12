package com.example.ibrahim.chatddemo.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.example.ibrahim.chatddemo.R;
import com.example.ibrahim.chatddemo.dataproviders.greendao.ChatMessage;
import com.example.ibrahim.chatddemo.listeners.EndlessRecyclerViewScrollListener;
import com.example.ibrahim.chatddemo.models.BaseModel;
import com.example.ibrahim.chatddemo.utils.ChatHelper;
import com.example.ibrahim.chatddemo.utils.DebugUtils;
import com.example.ibrahim.chatddemo.utils.NetworkUtils;
import com.example.ibrahim.chatddemo.utils.ShowUtils;
import com.example.ibrahim.chatddemo.viewHolder.ChatConversationScreenAdapter;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    @NonNull
    public static final String CHAT_USER_KEY = "user_data";
    public static final int FROM_CONVERSATION_LIST_ADAPTER=1;
    public static final int FROM_CHAT_USER_SEARCH_ADAPTER =2;
    public static final int FROM_CHAT_GROUP_USER_SEARCH_ADAPTER =3;
    public String groupIdFromLocalDB;
    private EditText addComments;
    private RecyclerView chatRecyclerView;
    private ArrayList<ChatMessage> chatModelsList;
    private ChatConversationScreenAdapter userChatAdapter;
    private ImageButton  sendButton;
    private boolean getMore = false;
    private ProgressBar progressBar;
    //
    private BottomSheetBehavior mBottomSheetBehavior;
    @Nullable
    private BottomSheetDialog mBottomSheetDialog;
//    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private ChatHelper chatHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setBackgroundDrawableResource(R.drawable.chat_background);


        // initialize chat helper
        initChatHelper();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        }
        addComments = (EditText) findViewById(R.id.add_comment);
        sendButton = (ImageButton) findViewById(R.id.send_comment);
        chatRecyclerView = (RecyclerView) findViewById(R.id.comments_list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        chatRecyclerView.setLayoutManager(mLayoutManager);
        chatRecyclerView.setNestedScrollingEnabled(false);

        chatModelsList = new ArrayList<>();
        userChatAdapter = new ChatConversationScreenAdapter(chatModelsList,1, this);
        chatRecyclerView.setAdapter(userChatAdapter);

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
        /* List size will be one at first because latest message will be at */
//        getPreviousMessages();
    }


    private void initChatHelper(){
        if (chatHelper == null) {
            chatHelper = new ChatHelper(this);

        }
    }

    private void initChatHelperGroupId() {
        if (chatHelper == null)
            initChatHelper();
        if (groupIdFromLocalDB != null)
            chatHelper.setGroupIdFromLocalDB(groupIdFromLocalDB);
    }
    
    private void enableSend() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String enteredText = addComments.getText().toString().trim();
                if (enteredText.length() > 0){

                        addComments.setText("");
//                        hideKeyboard(ChatActivity.this);
                        ChatMessage chatMessage = null;

                        // save msg in local DB
                        chatMessage = chatHelper.saveNewSendingMessageToDB(1,  enteredText);

                        if (chatMessage!=null){
                            chatModelsList.add(0, chatMessage);

                        }
                        userChatAdapter.notifyItemInserted(0);
                        scrollToBottom();

                }
            }

        });
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



    private void disableSend() {
        sendButton.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.comment_off), PorterDuff.Mode.SRC_ATOP);
        sendButton.setOnClickListener(null);
    }

    // Long press Comment Options




    private void hideCommentsOptionMenu() {
        if (mBottomSheetBehavior != null && mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


}
