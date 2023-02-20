package gdt.projects.tgbot.service.commandsHandler;

import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.service.CentralRussianBankService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.xml.datatype.DatatypeConfigurationException;

@Component
@RequiredArgsConstructor
public class CurrentRatesHandler implements BotServiceCommandsHandler{

    private final CentralRussianBankService cbr;

    private static final BotCommandsEnum COMMAND_TYPE = BotCommandsEnum.CURRENT_RATES;

    @Override
    public BotCommandsEnum getType() {
        return COMMAND_TYPE;
    }

    @Override
    public void processCommand(SendMessage response) {
        try {
            for(ValuteCursOnDate valuteCursOnDate : cbr.getCurrenciesFromCbr()) {
                response.setText(StringUtils.defaultIfBlank(response.getText(), "") +
                        valuteCursOnDate.getName() + "-" + valuteCursOnDate.getCourse() + "\n");
            }
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
