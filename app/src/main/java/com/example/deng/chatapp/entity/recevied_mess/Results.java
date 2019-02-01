package com.example.deng.chatapp.entity.recevied_mess;

public class Results {

    private int groupType;
    private String resultType;
    private Values values;

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setValues(Values values) {
        this.values = values;
    }

    public Values getValues() {
        return values;
    }
}
