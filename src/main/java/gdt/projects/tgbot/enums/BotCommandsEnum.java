package gdt.projects.tgbot.enums;

import gdt.projects.tgbot.service.CentralRussianBankService;
import gdt.projects.tgbot.service.CommandsHandler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public enum BotCommandsEnum {


    CURRENT_RATES("/currentrates"),
    ADD_INCOME("/addincome"),
    ADD_SPEND("/addspend");

    private String command;

    BotCommandsEnum(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}

