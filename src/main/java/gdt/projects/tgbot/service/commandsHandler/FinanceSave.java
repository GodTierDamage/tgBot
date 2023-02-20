package gdt.projects.tgbot.service.commandsHandler;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceSave{

    String save(String price, Long chatId);

    BotCommandsEnum getType();
}
