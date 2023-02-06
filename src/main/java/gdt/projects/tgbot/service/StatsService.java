package gdt.projects.tgbot.service;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import gdt.projects.tgbot.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository repository;

    public int getCountOfIncomesThatGraterThan(BigDecimal amount) {
        return repository.getCountOfIncomesThatGraterThan(amount);
    }

    public List<Income> getIncomesGreaterThen(Long amount) {
        return repository.getIncomesGreaterThen(amount);
    }

    public List<Spend> getSpendsGreaterThen(Long amount) {
        return repository.getSpendsGreaterThen(amount);
    }
}
