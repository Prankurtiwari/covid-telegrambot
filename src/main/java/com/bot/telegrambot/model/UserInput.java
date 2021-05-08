package com.bot.telegrambot.model;

import org.springframework.stereotype.Component;

public class UserInput {

    private Long chatId;
    private String Pincode;

    public UserInput() {
    }


    public UserInput(Long chatId, String pincode) {
        this.chatId = chatId;
        Pincode = pincode;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    @Override
    public String toString() {
        return "UserInput{" +
                ", Pincode='" + Pincode + '\'' +
                '}';
    }
}
