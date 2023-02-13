package gdt.projects.tgbot.service;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import gdt.projects.tgbot.repository.IncomeRepository;
import gdt.projects.tgbot.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private static final String ADD_INCOME = "/addincome";
    private final IncomeRepository incomeRepository;
    private final SpendRepository spendRepository;

    public String addFinanceOperation(String operationType, String price, Long chatId) {
        StringBuilder message = new StringBuilder();
        if(ADD_INCOME.equalsIgnoreCase(operationType)) {
            Income income = new Income();
            income.setChatId(chatId);
            income.setIncome(new BigDecimal(price));
            message.append("Доход в размере ").append(price).append(" был успешно добавлен.");
        } else {
            Spend spend = new Spend();
            spend.setChat_id(chatId);
            spend.setSpend(new BigDecimal(price));
            message.append("Расход в размере ").append(price).append(" был успешно добавлен.");
        }
        return new String(message.toString().getBytes(), StandardCharsets.UTF_8);
    }
}
