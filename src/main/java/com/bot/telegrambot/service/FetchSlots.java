package com.bot.telegrambot.service;

import com.bot.telegrambot.controller.APIController;
import com.bot.telegrambot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FetchSlots {

    @Autowired
     private APICall call;
    @Autowired
    private Environment environment;

    public List<OutputDetails> getAvailableSlot(String pincode) throws ResponseStatusException {
        List<OutputDetails> outputDetails = new ArrayList<>();
        AllCenters centers = call.getCentersFromInput(pincode);
        for (Center center : centers.getCenters()) {
            for (Session session : center.getSessions()) {
                int minAge = Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min")));
                int maxAge = Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.max")));
                if(session.getAvailableCapacity() > 0 && session.getMinAgeLimit() >= minAge && session.getMinAgeLimit() < maxAge )
                {
                    OutputDetails details = new OutputDetails();
                    details.setName(center.getName());
                    details.setAddress(center.getAddress());
                    details.setBlockName(center.getBlockName());
                    details.setDistrictName(center.getDistrictName());
                    details.setStateName(center.getStateName());
                    details.setPincode(center.getPincode().toString());
                    details.setFeeType(center.getFeeType());
                    details.setSlots(session.getSlots());
                    details.setDate(session.getDate());
                    details.setMinAge(session.getMinAgeLimit());
                    details.setVaccine(session.getVaccine());
                    outputDetails.add(details);
                }
            }
        }
        return outputDetails;
    }
}
