package com.accenture.russiaatc.irentservice10.SNAPSHOT.telegram.command;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.TransportStatus;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import lombok.SneakyThrows;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.XhtmlElementType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class SearchVehicleCommand extends MyBotCommand {
    private final VehicleService vehicleService;

    public SearchVehicleCommand(VehicleService vehicleService) {
        super("/search", "Поиск свободного транспорта");
        this.vehicleService = vehicleService;
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings){
        List<TransportDto> transports = vehicleService.findByTransportStatusAndStatus(TransportStatus.FREE,
                Status.WORKING);
        if (transports.isEmpty()){
            sendAnswer(absSender, chat.getId(),
                    "Свободного транспорта пока что нет. Попробуйте позже", ParseMode.HTML);
        } else {
            StringBuilder sb = new StringBuilder();
            transports.forEach(transport -> sb.append(vehicleToString(transport)).append("\n"));
            sb.append("Для аренды транспорта воспользуйте командой /starttrip id(рег. номер)");
            sendAnswer(absSender, chat.getId(), sb.toString(), ParseMode.HTML);
        }
    }


    private String vehicleToString(TransportDto transportDto) {
        if (transportDto.getType() == Type.BICYCLE) {
            return "\uD83D\uDEB4\u200D♂️Рег. номер: " + transportDto.getNumber() + "\t" + ", тип ТС: " + transportDto.getType() + "\t" +
                    ", текущая парковка: " + transportDto.getCurrentParking().getName();
        } else {
            return "\uD83D\uDEF4 Рег. номер: " + transportDto.getNumber() + "\t" + ", тип ТС: " + transportDto.getType() + "\t" +
                    ", текущая парковка: " + transportDto.getCurrentParking().getName();
        }
    }


}
