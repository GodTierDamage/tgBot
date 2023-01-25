package gdt.projects.tgbot.service;

import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.entity.ActiveChat;
import gdt.projects.tgbot.repository.ActiveChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final CentralRussianBankService cbr;
    @Lazy
    private final ActiveChatRepository repository;
    private final FinanceService financeService;

    private Map<Long, List<String>> previousCommands = new ConcurrentHashMap<>();

    private static final String CURRENT_RATES = "/currentrates";
    private static final String ADD_INCOME = "/addincome";
    private static final String ADD_SPEND = "/addspend";


    @Value("${bot.api.key}")
    private String apiKey;

    @Value("${bot.name}")
    private String name;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));
            if(CURRENT_RATES.equalsIgnoreCase(message.getText())) {
                for(ValuteCursOnDate valuteCursOnDate : cbr.getCurrenciesFromCbr()) {
                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") +
                    valuteCursOnDate.getName() + "-" + valuteCursOnDate.getCourse() + "\n");
                }
            } else if(ADD_INCOME.equalsIgnoreCase(message.getText())) {
                response.setText("Отправьте пожалуйста сумму полученного дохода.");
            } else if(ADD_SPEND.equalsIgnoreCase(message.getText())) {
                response.setText("Отправьте пожалуйста сумму расходов.");
            } else {
                response.setText(financeService.addFinanceOperation(getPreviousCommand(message.getChatId()), message.getText(), message.getChatId()));
            }
            putPreviousCommand(message.getChatId(), message.getText());
            execute(response);
            if(repository.findActiveChatByChatId(chatId).isEmpty()) {
                ActiveChat activeChat = new ActiveChat();
                activeChat.setChatId(chatId);
                repository.save(activeChat);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", name, apiKey);
    }

    public void sendNotificationToAllActiveChats(String message, Set<Long> chatIds) {
        for(Long id : chatIds) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(id));
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void putPreviousCommand(Long chatId, String command) {
        previousCommands.computeIfAbsent(chatId, value -> new ArrayList<String>()).add(command);
    }

    private String getPreviousCommand(Long chatId) {
        return previousCommands.get(chatId)
                .get(previousCommands.get(chatId).size() - 1);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return apiKey;
    }
}
