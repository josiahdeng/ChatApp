package com.example.deng.chatapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Msg> mMsgList= new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
       initMsgs();
        msgRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(mMsgList);
        msgRecyclerView.setAdapter(adapter);
        inputText = (EditText) findViewById(R.id.chat_text);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYEP_SENT);
                    mMsgList.add(msg);
                    adapter.notifyItemInserted(mMsgList.size() -1);
                    msgRecyclerView.scrollToPosition(mMsgList.size() -1);
                    String url = "http://openapi.tuling123.com/openapi/api?" +
                            "key=" + "84e7b6a1f7ad4355af49cfc74c5fb52b" + "&info=" + content;
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Log.i("chat:", t);
                            pasingJson(t);
                        }
                    });
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs(){
        Msg msg1 = new Msg("你好", Msg.TYEP_RECEIVED);
        mMsgList.add(msg1);
        Msg msg2 = new Msg("你是？", Msg.TYEP_SENT);
        mMsgList.add(msg2);
        Msg msg3 = new Msg("我是李志威啊！智障。", Msg.TYEP_RECEIVED);
        mMsgList.add(msg3);
    }

    private void pasingJson(String message){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            String text = jsonObject.getString("text");
            Msg msg = new Msg(text, Msg.TYEP_RECEIVED);
            mMsgList.add(msg);
            adapter.notifyItemInserted(mMsgList.size() -1);
            msgRecyclerView.scrollToPosition(mMsgList.size() -1);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
