package com.bot.telegrambot.service;

import com.bot.telegrambot.controller.APIController;
import com.bot.telegrambot.model.OutputDetails;
import com.bot.telegrambot.model.UserInput;
import com.bot.telegrambot.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;
import java.util.regex.Pattern;

public class AppointVix extends TelegramLongPollingBot {

    @Autowired
    APIController caller;
    @Autowired
    UserRepo user;
    @Autowired
    Environment environment;


    @Scheduled(cron = "0 0 7-21 * * *")
    private void fetchSlotSchedule() {
        Map<String, Set<UserInput>> users = user.getPinToUserList();
        for (Map.Entry<String, Set<UserInput>> entry : users.entrySet()) {
            List<OutputDetails> centerList = caller.getCenters(entry.getKey());
            if (centerList.size() > 0)
            {
                for (UserInput user : entry.getValue()) {
                    for (OutputDetails center : centerList) {
                        sendMsg(user.getChatId(), "Center Name : " + center.getName() +
                                " \n Center Address : " + center.getAddress() +
                                " \n Block : " + center.getBlockName() +
                                " \n District : " + center.getDistrictName() +
                                " \n State : " + center.getStateName() +
                                "\n PinCode :  " + center.getPincode() +
                                "\n Fee Type : " + center.getFeeType() +
                                "\n Date : " + center.getDate() +
                                "\n Vaccine : " + center.getVaccine() +
                                "\n Minimum Age : " + center.getMinAge() +
                                "\n Slots : " + center.getSlots().toString());
                    }
                }
            }
        }
    }

    //--TODO  use AppointVix class object to access sendMessage method to send detail
    // TODO ask about deployment
    // TODO ask about more optimize approach consider that 100 call per 5 min
    private boolean pinValidate(String pinString) {
        return Pattern.matches(Objects.requireNonNull(environment.getProperty("pin.regexp")), pinString);
    }

    public synchronized void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText()
                    .toLowerCase(Locale.ROOT).trim()
                    .replaceAll(" ", "");

            if (message.equals("/start")) {
                sendMsg(chatId, "Hi, " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName()
                        + ". Please Enter your 6 digit PinCode/ZipCode.");
            } else if (message.equals("/help")) {
                sendMsg(chatId, "Enter PinCode/ZipCode to get notification for the availability of slots in that area. " +
                        "In case of any problem type /exit followed by /start.\n\n"+
                        "If you want you can raise the issue at : https://github.com/Prankurtiwari/covid-telegrambot");
            } else if (pinValidate(message) ) {
                UserInput input = new UserInput(chatId, message);
                user.addUser(input);
                List<OutputDetails> outputDetailsList = caller.getCenters(message);
                if (outputDetailsList.size() > 0) {
                    for (OutputDetails center : outputDetailsList
                    ) {
                        sendMsg(chatId, "Center Name : " + center.getName() +
                                " \n Center Address : " + center.getAddress() +
                                " \n Block : " + center.getBlockName() +
                                " \n District : " + center.getDistrictName() +
                                " \n State : " + center.getStateName() +
                                "\n PinCode :  " + center.getPincode() +
                                "\n Fee Type : " + center.getFeeType() +
                                "\n Date : " + center.getDate() +
                                "\n Vaccine : " + center.getVaccine() +
                                "\n Minimum Age : " + center.getMinAge() +
                                "\n Slots : " + center.getSlots().toString());
                    }
                } else {
                    sendMsg(chatId, "Right now NO slots are available. " +
                            "We will Notify you as soon as there is availability of slots.");
                }
            } else if (message.equals("/monitor")) {
                if(user.containUser(chatId))
                    sendMsg(chatId, "You are registered to these pinCodes : " + user.getUserToPinList(chatId) +
                            "\nYou can add multiple pinCodes one by one.");
                else
                    sendMsg(chatId,  "You have not been registered to our system. Enter a valid Indian PinCode/ZipCode" +
                            " to register for the service.");
            } else if (message.equals("/exit")) {
                if(user.containUser(chatId))
                {
                    user.removeUser(chatId);
                    sendMsg(chatId, "Thank you for using the service.Now you will not get any notification from our side.");
                }
                else
                {
                    sendMsg(chatId,  "You have not been registered to our system. Enter a valid Indian PinCode/ZipCode" +
                            " to register for the service.");
                }
            } else {
                sendMsg(chatId, "You have not entered the valid Indian pinCode. Please enter 6 digit valid Indian PinCode/ZipCode to use the service.");
            }
        } catch (NullPointerException exception) {
            sendMsg(update.getMessage().getChatId(), "Something is INCORRECT. " +
                    "Please enter 6 digit valid Indian PinCode/ZipCode to use the service");
        }
    }

    @Override
    public String getBotUsername() {
        return "VixineBot";
    }

    @Override
    public String getBotToken() {
        return "1755819453:AAGdzOmUQ_PqQqimOKa7g4K5EcMTtuacvPs";
    }
}
