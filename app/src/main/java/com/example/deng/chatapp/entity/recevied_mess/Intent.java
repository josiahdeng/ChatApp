package com.example.deng.chatapp.entity.recevied_mess;

public class Intent {
    private int code;
    private String intentName;
    private String actionName;
    private Parameters parameters;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
