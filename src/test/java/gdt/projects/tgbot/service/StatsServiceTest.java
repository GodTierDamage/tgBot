package gdt.projects.tgbot.service;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:init-data.sql")
class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Test
    public void testGetCountOfIncomesGreaterThan() {
        int result = statsService.getCountOfIncomesThatGreaterThan(new BigDecimal("1000.00"));
        assertEquals(2, result);
    }

    @Test
    public void testGetIncomesGreaterThen() {
        List<Income> incomes = statsService.getIncomesGreaterThen(500L);
        assertNotNull(incomes);
        assertFalse(incomes.isEmpty());

        int expectedIncomesSize = 4;

        assertEquals(expectedIncomesSize, incomes.size());
    }

    @Test
    public void testGetSpendsGreaterThan() {
        List<Spend> spends = statsService.getSpendsGreaterThen(1000L);
        assertNotNull(spends);
        assertFalse(spends.isEmpty());

        int expectedSpendsSize = 2;
        assertEquals(expectedSpendsSize, spends.size());
    }

}