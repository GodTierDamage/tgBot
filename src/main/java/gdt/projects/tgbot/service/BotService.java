package gdt.projects.tgbot.service;

import gdt.projects.tgbot.entity.ActiveChat;
import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.exception.CommandNotFoundException;
import gdt.projects.tgbot.repository.ActiveChatRepository;
import gdt.projects.tgbot.service.CommandsHandler.BotServiceCommandsHandler;
import gdt.projects.tgbot.service.CommandsHandler.CommandsHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static gdt.projects.tgbot.enums.BotCommandsEnum.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    @Lazy
    private final ActiveChatRepository repository;
    private final FinanceService financeService;

    private Map<Long, List<String>> previousCommands = new ConcurrentHashMap<>();

    private static final List<BotCommandsEnum> commands = Arrays.asList(
            ADD_INCOME,
            CURRENT_RATES,
            ADD_SPEND
    );

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
            try {
                BotServiceCommandsHandler commandsHandler = findCommand(message);
                commandsHandler.processCommand(response);
            } catch (CommandNotFoundException e) {
                e.printStackTrace();
            }
            putPreviousCommand(message.getChatId(), message.getText());
            execute(response);
            checkAndSaveActiveChatInRepo(chatId);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BotServiceCommandsHandler findCommand(Message message) {
        BotCommandsEnum commandType = getCommandType(message);
        return CommandsHandlerFactory.getCommandsHandler(commandType);
    }

    private BotCommandsEnum getCommandType(Message message)throws CommandNotFoundException {
        BotCommandsEnum command = commands.stream()
                .filter(curCommand -> curCommand.toString().equalsIgnoreCase(message.getText()))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException("Команда не найдена"));
        return command;
    }

    private void checkAndSaveActiveChatInRepo(Long chatId) {
        if(repository.findActiveChatByChatId(chatId).isEmpty()) {
            ActiveChat activeChat = new ActiveChat();
            activeChat.setChatId(chatId);
            repository.save(activeChat);
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
        previousCommands.computeIfAbsent(chatId, value -> new ArrayList<>()).add(command);
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
