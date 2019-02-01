package com.example.deng.chatapp.entity.send_mess;

public class Preception {
    private InputText inputText;

    private InputImage inputImage;

    private SelfInfo selfInfo;

    public InputText getInputText() {
        return inputText;
    }

    public void setInputText(InputText inputText) {
        this.inputText = inputText;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public void setInputImage(InputImage inputImage) {
        this.inputImage = inputImage;
    }

    public SelfInfo getSelfInfo() {
        return selfInfo;
    }

    public void setSelfInfo(SelfInfo selfInfo) {
        this.selfInfo = selfInfo;
    }
}
