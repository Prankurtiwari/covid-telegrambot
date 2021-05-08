package com.bot.telegrambot.service;

import com.bot.telegrambot.TelegramBotApplication;
import com.bot.telegrambot.controller.APIController;
import com.bot.telegrambot.model.OutputDetails;
import com.bot.telegrambot.model.UserInput;
import com.bot.telegrambot.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Scheduler {

    @Autowired
    APIController caller;
    @Autowired
    UserRepo user;


//    @Scheduled(cron = "0 9-17 * * * *")
//    public void fetchSlotSchedule()
//    {
//        Map<Long, UserInput> users = user.getUserList();
//        for (Map.Entry<Long,UserInput> entry : users.entrySet()) {
//            List<OutputDetails> centerList = caller.getCenters(entry.getValue());
////            appointVix.sendMsg(1000764100L,"Hello World");
//            for (OutputDetails center: centerList) {
////                    appointVix.sendMsg("Hello world");
//
//            }
//        }
//    }

}
