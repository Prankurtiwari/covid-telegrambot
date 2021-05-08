package com.bot.telegrambot.controller;

import com.bot.telegrambot.model.OutputDetails;
import com.bot.telegrambot.model.UserInput;
import com.bot.telegrambot.service.FetchSlots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class APIController {

    @Autowired
    FetchSlots slots;


    public List<OutputDetails> getCenters(String pincode) throws ResponseStatusException {
        return slots.getAvailableSlot(pincode);
    }



}
