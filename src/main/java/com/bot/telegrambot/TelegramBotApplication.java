package com.bot.telegrambot;

import com.bot.telegrambot.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {

    @Bean
    public void telegramRegister() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(appointVix());
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    @Bean
    public AppointVix appointVix()
    {
        return new AppointVix();
    }


    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}