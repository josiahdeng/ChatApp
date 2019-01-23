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

import com.example.deng.chatapp.adapter.MsgAdapter;
import com.example.deng.chatapp.entity.Msg;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        initMsgs();
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
//                    map.put("text",  )
                    String url = "http://openapi.tuling123.com/openapi/api/v2";
                    JSONObject jsonObject = acceptJson(content);
                    HttpParams httpParams = new HttpParams();
                    httpParams.putJsonParams(jsonObject.toString());
                    RxVolley.jsonPost(url, httpParams, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            System.out.println(t);
                            pasingJson(t);
                        }
                    });
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("你好", Msg.TYEP_RECEIVED);
        mMsgList.add(msg1);
        Msg msg2 = new Msg("你是？", Msg.TYEP_SENT);
        mMsgList.add(msg2);
        Msg msg3 = new Msg("我是李志威啊！智障。", Msg.TYEP_RECEIVED);
        mMsgList.add(msg3);
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

    private JSONObject acceptJson(String chatContent) {
        JSONObject jsonObjects = null;
        try {
            jsonObjects = new JSONObject();
            jsonObjects.put("reqType", 0);
            JSONObject inputText = new JSONObject();
            JSONObject text = new JSONObject();
            text.put("text", chatContent);
            inputText.put("inputText", text);
            jsonObjects.put("perception", inputText);
            JSONObject userInfo = new JSONObject();
            userInfo.put("apiKey", "84e7b6a1f7ad4355af49cfc74c5fb52b");
            userInfo.put("userId", "deng123");
            jsonObjects.put("userInfo", userInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
