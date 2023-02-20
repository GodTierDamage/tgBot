package gdt.projects.tgbot.service.commandsHandler;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FinanceSaveIncome implements FinanceSave{

    private final IncomeRepository incomeRepository;
    private final BotCommandsEnum ADD_INCOME = BotCommandsEnum.ADD_INCOME;

    @Override
    public String save(String price, Long chatId) {
        Income income = generateIncome(price, chatId);
        incomeRepository.save(income);
        return String.format("Доход в размере %s был успешно добавлен.", price);
    }

    private Income generateIncome(String price, Long chatId) {
        Income income = new Income();
        income.setChatId(chatId);
        income.setIncome(new BigDecimal(price));
        return income;
    }

    @Override
    public BotCommandsEnum getType() {
        return ADD_INCOME;
    }
}
