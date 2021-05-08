package com.bot.telegrambot.repo;

import com.bot.telegrambot.model.UserInput;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepo {

    private Map<Long, Set<String>> userToPinList = new HashMap<>();

    private Map<String, Set<UserInput>> pinToUserList = new HashMap<>();

    public Map<String, Set<UserInput>> getPinToUserList() {
        return pinToUserList;
    }

    public Set<String> getUserToPinList(Long chatId) {
        return userToPinList.get(chatId);
    }

    public void addUser(UserInput input) {
        addUserToPinToUserList(input);
        addUserToUserToPinList(input);
    }

    public boolean containUser(Long chatId)
    {
        return userToPinList.containsKey(chatId);
    }

    public void removeUser(Long chatId) {
        removeUserFromUserToPinList(chatId);
    }

    private void addUserToUserToPinList(UserInput input)
    {
        Set<String> pinCodeList;
        if (userToPinList.containsKey(input.getChatId())) {
            pinCodeList = userToPinList.get(input.getChatId());
            pinCodeList.add(input.getPincode());
        } else {
            pinCodeList = new HashSet<>();
            pinCodeList.add(input.getPincode());
            userToPinList.put(input.getChatId(), pinCodeList);
        }

    }

    private void addUserToPinToUserList(UserInput input) {
        Set<UserInput> userList;
        if (pinToUserList.containsKey(input.getPincode())) {
            userList = pinToUserList.get(input.getPincode());
            userList.add(input);
        } else {
            userList = new HashSet<>();
            userList.add(input);
            pinToUserList.put(input.getPincode(), userList);
        }
    }

    private void removeUserFromUserToPinList(Long chatId)
    {
        Set<String> pinList =userToPinList.remove(chatId);
        removeUserFromPinToUserList(chatId,pinList);
    }



    private void removeUserFromPinToUserList(Long chatId, Set<String> pinCodeList) {
        for (String pinCode:pinCodeList) {
            pinToUserList.get(pinCode).remove(new UserInput(chatId,pinCode));
        }
    }


}
