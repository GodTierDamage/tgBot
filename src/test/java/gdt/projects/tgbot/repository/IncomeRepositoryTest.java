package gdt.projects.tgbot.repository;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:init-data.sql")
class IncomeRepositoryTest {

    @Autowired
    IncomeRepository incomeRepository;

    @Test
    public void testFindIncomeById() {
        Optional<Income> income = incomeRepository.findById(4L);
        assertTrue(income.isPresent());
        assertEquals(new BigDecimal("900.00"), income.get().getIncome());
    }

    @Test
    public void testFindAllIncomes() {
        List<Income> incomeList = incomeRepository.findAll();
        assertFalse(incomeList.isEmpty());

        int expectedSpendListSize = 5;
        assertEquals(expectedSpendListSize, incomeList.size());
    }

    @Test
    public void testSave() {
        for(int i = 0; i < 10; i++) {
            incomeRepository.save(new Income());
        }
        List<Income> incomeList = incomeRepository.findAll();
        assertEquals(15, incomeList.size());
    }
}

