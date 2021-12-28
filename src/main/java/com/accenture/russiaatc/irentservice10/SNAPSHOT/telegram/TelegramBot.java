package com.accenture.russiaatc.irentservice10.SNAPSHOT.telegram;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.ParkingService;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.TripService;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.telegram.command.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final TelegramUserService telegramUserService;
    private final VehicleService vehicleService;
    private final TripService tripService;
    private final ParkingService parkingService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public TelegramBot(TelegramUserService telegramUserService, VehicleService vehicleService, TripService tripService, ParkingService parkingService) {
        this.telegramUserService = telegramUserService;
        this.vehicleService = vehicleService;
        this.tripService = tripService;
        this.parkingService = parkingService;
        this.register(new UserInfoCommand(telegramUserService));
        this.register(new StartCommand(telegramUserService));
        this.register(new SearchVehicleCommand(vehicleService));
        this.register(new ParkingCommand(parkingService));
        this.register(new StartTripCommand(tripService, telegramUserService, vehicleService));
        this.register(new FinishTripCommand(tripService, telegramUserService, vehicleService, parkingService));
        this.register(new HelpCommand());
    }



    @Override
    public void processNonCommandUpdate(Update update) {
        String command = update.getMessage().getText().substring(1);
        String chatId = update.getMessage().getChatId().toString();
        String text = "Некорректная команда " + command + ". Чтобы узнать доступные команды, наберите /help";

        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(text);

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

//    @Override
//    protected void processInvalidCommandUpdate(Update update) {
//
//    }

    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


}