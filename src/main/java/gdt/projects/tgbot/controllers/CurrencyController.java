package gdt.projects.tgbot.controllers;

import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import gdt.projects.tgbot.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CentralRussianBankService cbr;
    private final StatsService statsService;

    @PostMapping("/getCurrencies")
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return cbr.getCurrenciesFromCbr();
    }

    @GetMapping("/getStats")
    @ApiOperation(value = "Получение количества пополнений, превышающих определенную сумму")
    public int getStatsAboutIncomesThatGreaterThen(@RequestParam(value = "amount")BigDecimal amount) {
        return statsService.getCountOfIncomesThatGraterThan(amount);
    }

    @GetMapping("/getIncomes")
    @ApiOperation(value = "Получить все доходы, которые больше указанной суммы")
    public List<Income> getIncomesGreaterThen(@RequestParam(value = "amount")Long amount) {
        return statsService.getIncomesGreaterThen(amount);
    }

    @GetMapping("/getSpends")
    @ApiOperation("Получение всех расходов, которые больше указанной суммы")
    public List<Spend> getSpendsGreaterThen(@RequestParam(value = "amount") Long amount) {
        return statsService.getSpendsGreaterThen(amount);
    }


}
