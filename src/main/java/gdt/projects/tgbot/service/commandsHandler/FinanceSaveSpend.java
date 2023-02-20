package gdt.projects.tgbot.service.commandsHandler;

import gdt.projects.tgbot.entity.Spend;
import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FinanceSaveSpend implements FinanceSave{

    private final SpendRepository spendRepository;
    private final BotCommandsEnum ADD_SPEND = BotCommandsEnum.ADD_SPEND;

    @Override
    public String save(String price, Long chatId) {
        Spend spend = generateSpend(price, chatId);
        spendRepository.save(spend);
        return String.format("Расход в размере %s был успешно добавлен.", price);
    }

    private Spend generateSpend(String price, Long chatId) {
        Spend spend = new Spend();
        spend.setChat_id(chatId);
        spend.setSpend(new BigDecimal(price));
        return spend;
    }

    @Override
    public BotCommandsEnum getType() {
        return ADD_SPEND;
    }
}
