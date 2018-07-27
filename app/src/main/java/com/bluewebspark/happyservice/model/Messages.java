package com.bluewebspark.happyservice.model;

/**
 * Created by Karan on 10/17/2016.
 */
public class Messages {

    /**
     * messageBY : 42
     * messageTo : 51
     * chat : 3464 shsh ahsh 2018-04-30 15:38:00
     * messageDate : 2018-04-30 12:04:05
     */

    private String messageBY;
    private String messageTo;
    private String chat;
    private String messageDate;

    public String getMessageBY() {
        return messageBY;
    }

    public void setMessageBY(String messageBY) {
        this.messageBY = messageBY;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}