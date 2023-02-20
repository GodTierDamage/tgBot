package gdt.projects.tgbot.service;

import com.google.common.base.Charsets;
import gdt.projects.tgbot.enums.BotCommandsEnum;
import gdt.projects.tgbot.repository.IncomeRepository;
import gdt.projects.tgbot.repository.SpendRepository;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FinanceServiceTest {

    @InjectMocks
    private FinanceService financeService;

    @Mock
    private SpendRepository spendRepository;

    @Mock
    private IncomeRepository incomeRepository;

    private Long time;

    @BeforeEach
    public void beforeEach() {
        time = System.currentTimeMillis();
        System.out.println("Start:");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("End: " + (System.currentTimeMillis() - time));
    }

    @Test
    @DisplayName("ADD_INCOME_test")
    public void addIncomeTest() {
        String price = "200";
        String expected = "ƒоход в размере " + price + " был успешно добавлен.";
        String message = financeService.addFinanceOperation(BotCommandsEnum.ADD_INCOME, price, 300L);


        assertEquals(expected, message);
    }

    @Test
    @DisplayName("ADD_SPEND_test")
    public void addSpendTest() {
        String price = "200";
        String expected = "–асход в размере " + price + " был успешно добавлен.";
        String spendMessage = financeService.addFinanceOperation(BotCommandsEnum.ADD_SPEND, price, 300L);

        assertEquals(expected, spendMessage);
    }
}