package gdt.projects.tgbot.service;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.service.commandsHandler.FinanceCommandsFactory;
import gdt.projects.tgbot.service.commandsHandler.FinanceSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FinanceService {

    public String addFinanceOperation(BotCommandsEnum command, String price, Long chatId) {
        FinanceSave saveService = getFinanceSaveService(command);
        String response = saveService.save(price, chatId);
        return new String(response.getBytes(), StandardCharsets.UTF_8);
    }

    private FinanceSave getFinanceSaveService(BotCommandsEnum type) {
        return FinanceCommandsFactory.getFinanceSaveService(type);
    }
}
