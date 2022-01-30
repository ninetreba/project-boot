package com.accenture.russiaatc.irentservice10.SNAPSHOT.telegram.command;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.telegram.TelegramVehicleService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;


@Component
public class SearchVehicleCommand extends MyBotCommand {
    private final VehicleService vehicleService;
    private final TelegramVehicleService telegramVehicleService;


    public SearchVehicleCommand(VehicleService vehicleService, TelegramVehicleService telegramVehicleService) {
        super("/search", "Поиск свободного транспорта");
        this.vehicleService = vehicleService;
        this.telegramVehicleService = telegramVehicleService;
    }


    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings){

        List<Transport> transports = telegramVehicleService.getVehiclesTelegram(chat.getId());

//        List<Transport> transports = vehicleService.findByTransportStatusAndStatus(TransportStatus.FREE,
//                Status.WORKING);
        if (transports.isEmpty()){
            sendAnswer(absSender, chat.getId(),
                    "Свободного транспорта пока что нет. Попробуйте позже", false);
        } else {
            StringBuilder sb = new StringBuilder();
            transports.forEach(transport -> sb.append(vehicleToString(transport)).append("\n"));
            sb.append("Для аренды транспорта воспользуйте командой /starttrip id(рег. номер)");
            sendAnswer(absSender, chat.getId(), sb.toString(), false);
        }
    }

//    @Override
//    public void mySend(Update update){
//        Update update = new Update();
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        //  replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//
//        KeyboardButton keyboardButton = new KeyboardButton();
//        keyboardButton.setText("share location");
//        keyboardButton.setRequestLocation(true);
//
//
//        KeyboardRow keyboardRow = new KeyboardRow();
//        keyboardRow.add(keyboardButton);
//
//        keyboardRowList.add(keyboardRow);
//
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//
//        SendMessage sendMessage = new SendMessage();
//        String chatId = update.getMessage().getChatId().toString();
//
//        sendMessage.setChatId(chatId);
//        sendMessage.setText("whaat??");
//        sendMessage.setParseMode(ParseMode.HTML);
//
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }



    private String vehicleToString(Transport transport) {
        String emoji = (transport.getType() == Type.BICYCLE) ? "\uD83D\uDEB4\u200D♂" : "\uD83D\uDEF4";

        if (transport.getType() == Type.BICYCLE) {
            return "\uD83D\uDEB4\u200D♂️Рег. номер: " + transport.getNumber() + "\t" + ", тип ТС: " + transport.getType() + "\t" +
                    ", текущая парковка: " + transport.getCurrentParking().getName();
        } else {
            return "\uD83D\uDEF4 Рег. номер: " + transport.getNumber() + "\t" + ", тип ТС: " + transport.getType() + "\t" +
                    ", текущая парковка: " + transport.getCurrentParking().getName();
        }
    }



}
