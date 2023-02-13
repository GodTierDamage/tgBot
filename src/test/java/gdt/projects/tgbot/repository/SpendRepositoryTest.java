package gdt.projects.tgbot.repository;

import gdt.projects.tgbot.entity.Income;
import gdt.projects.tgbot.entity.Spend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class SpendRepositoryTest {

    @Autowired
    SpendRepository spendRepository;

    @Test
    public void testFindSpendById() {
        Optional<Spend> spend = spendRepository.findById(5L);
        assertTrue(spend.isPresent());
        assertEquals(new BigDecimal("3999.00"), spend.get().getSpend());
    }

    @Test
    public void testFindAllSpends() {
        List<Spend> spendList = spendRepository.findAll();
        assertFalse(spendList.isEmpty());

        int expectedSpendListSize = 5;
        assertEquals(expectedSpendListSize, spendList.size());
    }

}