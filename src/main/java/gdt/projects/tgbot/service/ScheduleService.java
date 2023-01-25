package gdt.projects.tgbot.service;

import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.entity.ActiveChat;
import gdt.projects.tgbot.repository.ActiveChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleService {

    private final ActiveChatRepository repository;
    private final BotService bot;
    private final CentralRussianBankService cbr;
    private final List<ValuteCursOnDate> previousRates = new ArrayList<>();

    @Scheduled(cron = "0 0 0/3 ? * *")
    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = cbr.getCurrenciesFromCbr();
            Set<Long> activeChatIds = repository.findAll().stream().map(ActiveChat::getChatId).collect(Collectors.toSet());

            if(!previousRates.isEmpty()) {
                for(int index = 0; index < currentRates.size(); index++) {
                    if(currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10.0) {
                        bot.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " увеличился на 10 рублей", activeChatIds);
                    } else if(currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10.0) {
                        bot.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " уменьшился на 10 рублей", activeChatIds);
                    }
                }
            } else {
                previousRates.addAll(currentRates);
            }
        }catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
