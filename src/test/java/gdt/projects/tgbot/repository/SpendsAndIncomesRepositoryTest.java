package gdt.projects.tgbot.repository;

import gdt.projects.tgbot.entity.Income;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:init-data.sql")
class SpendsAndIncomesRepositoryTest {

    @Autowired
    IncomeRepository incomeRepository;

    @Test
    public void getIncomesListFromDb() {

    }

    @Test
    public void getIncomes() {
        Optional<Income> income = incomeRepository.findById(4L);
        assertTrue(income.isPresent());
        assertEquals(new BigDecimal("900.00"), income.get().getIncome());
    }
}