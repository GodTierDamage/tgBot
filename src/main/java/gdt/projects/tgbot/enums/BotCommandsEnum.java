package gdt.projects.tgbot.enums;

import lombok.RequiredArgsConstructor;

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

