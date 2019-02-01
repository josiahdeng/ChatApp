package com.example.deng.chatapp.entity;

import com.example.deng.chatapp.entity.recevied_mess.Intent;
import com.example.deng.chatapp.entity.recevied_mess.Results;

import java.util.List;

public class ReceivedMessage {

    private Intent intent;
    private List<Results> results;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }
}
