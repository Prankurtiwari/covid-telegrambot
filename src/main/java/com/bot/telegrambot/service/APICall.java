package com.bot.telegrambot.service;

import com.bot.telegrambot.model.AllCenters;
import com.bot.telegrambot.model.UserInput;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class APICall {

    private String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(new Date());
    }
    // TODO synchronized hona chaiye yeh nahi as all request are get but on the same time blind write bhi ho sakta hai.
    private synchronized AllCenters getCenters(String pincode)
    {
        WebClient client = WebClient.create("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin");
        try {
            return client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("pincode", pincode)
                            .queryParam("date", getCurrentDate())
                            .build())
                    .header("Accept-Language", "en_US").header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")
                    .retrieve()
                    .bodyToMono(AllCenters.class)
                    .block();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad Request",exception);
        }
    }

    public AllCenters getCentersFromInput(String pincode) {
        return getCenters(pincode);
    }


}
