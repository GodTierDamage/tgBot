package gdt.projects.tgbot.service.commandsHandler;

import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.exception.CommandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FinanceCommandsFactory {

    private static Map<BotCommandsEnum, FinanceSave> financeSaveMap;

    @Autowired
    private FinanceCommandsFactory(List<FinanceSave> financeSaveList) {
        financeSaveMap = financeSaveList.stream()
                .collect(Collectors.toUnmodifiableMap(FinanceSave::getType, Function.identity()));
    }

    public static FinanceSave getFinanceSaveService(BotCommandsEnum type) {
        return Optional
                .ofNullable(financeSaveMap.get(type))
                .orElseThrow(() -> new CommandNotFoundException("Команда не найдена"));
    }

}
