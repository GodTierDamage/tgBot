package gdt.projects.tgbot.service.CommandsHandler;

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
public class CommandsHandlerFactory {

    private static Map<BotCommandsEnum, BotServiceCommandsHandler> commandsMap;

    @Autowired
    private CommandsHandlerFactory(List<BotServiceCommandsHandler> commands) {
        commandsMap = commands
                .stream()
                .collect(Collectors.toUnmodifiableMap(BotServiceCommandsHandler::getType, Function.identity()));
    }

    public static BotServiceCommandsHandler getCommandsHandler(BotCommandsEnum type) {
        return Optional
                .ofNullable(commandsMap.get(type))
                .orElseThrow(() -> new CommandNotFoundException("Команда не найдена"));
    }
}
