package gdt.projects.tgbot.service.CommandsHandler;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.charset.StandardCharsets;

@Component
public class AddIncomesHandler implements BotServiceCommandsHandler {

    protected AddIncomesHandler(){}

    private static final BotCommandsEnum COMMAND_TYPE = BotCommandsEnum.ADD_INCOME;

    @Override
    public BotCommandsEnum getType() {
        return COMMAND_TYPE;
    }

    @Override
    public void processCommand(SendMessage response) {
        String message = "Отправьте пожалуйста сумму полученного дохода.";
        response.setText(new String(message.getBytes(), StandardCharsets.UTF_8));
    }
}
