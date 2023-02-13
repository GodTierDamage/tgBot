package gdt.projects.tgbot.service.CommandsHandler;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotServiceCommandsHandler {

    void processCommand(SendMessage response);

    BotCommandsEnum getType();
}
