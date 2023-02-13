package gdt.projects.tgbot.service.CommandsHandler;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.charset.StandardCharsets;

@Component
public class AddSpendsHandler implements BotServiceCommandsHandler{

    protected AddSpendsHandler() {}

    private static final BotCommandsEnum COMMAND_TYPE = BotCommandsEnum.ADD_SPEND;

    @Override
    public BotCommandsEnum getType() {
        return COMMAND_TYPE;
    }

    @Override
    public void processCommand(SendMessage response) {
        String message = "Добавьте сумму ваших расходов.";
        response.setText(new String(message.getBytes(), StandardCharsets.UTF_8));
    }
}
