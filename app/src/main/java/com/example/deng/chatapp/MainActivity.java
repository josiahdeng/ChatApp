package com.example.deng.chatapp;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.example.deng.chatapp.adapter.MsgAdapter;
import com.example.deng.chatapp.entity.InputText;
import com.example.deng.chatapp.entity.Msg;
import com.example.deng.chatapp.entity.Preception;
import com.example.deng.chatapp.entity.SendMessage;
import com.example.deng.chatapp.entity.UserInfo;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Msg> mMsgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        initMsgs();
        msgRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(mMsgList);
        msgRecyclerView.setAdapter(adapter);
        inputText = (EditText) findViewById(R.id.chat_text);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYEP_SENT);
                    mMsgList.add(msg);
                    adapter.notifyItemInserted(mMsgList.size() - 1);
                    msgRecyclerView.scrollToPosition(mMsgList.size() - 1);
                    String url = "http://openapi.tuling123.com/openapi/api/v2";
                    String jsonObject = acceptJson(content);
                    HttpParams httpParams = new HttpParams();
                    httpParams.putJsonParams(jsonObject);
                    RxVolley.jsonPost(url, httpParams, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            pasingJson(t);
                        }
                    });
                    inputText.setText("");
                }
            }
        });
    }

    private void pasingJson(String message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject jsonObject1 = results.getJSONObject(0);
            JSONObject values = jsonObject1.getJSONObject("values");
            String text = values.getString("text");
            Msg msg = new Msg(text, Msg.TYEP_RECEIVED);
            mMsgList.add(msg);
            adapter.notifyItemInserted(mMsgList.size() - 1);
            msgRecyclerView.scrollToPosition(mMsgList.size() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String acceptJson(String chatContent) {
        SendMessage message = new SendMessage();
        InputText text = new InputText();
        text.setText(chatContent);
        Preception preception = new Preception();
        preception.setInputText(text);
        message.setReqType(1);
        message.setPreception(preception);
        UserInfo info = new UserInfo();
        info.setApiKey("84e7b6a1f7ad4355af49cfc74c5fb52b");
        info.setUserId("user123");
        message.setUserInfo(info);
        String chat = JSON.toJSONString(message);
        return chat;
    }
}
