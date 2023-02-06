package gdt.projects.tgbot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWhenAskAboutAllCurrencies() throws Exception {
        mockMvc.perform(post("/getCurrencies"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testWhenAskAllIncomesWithFilterGreaterThen() throws Exception {
        mockMvc.perform(get("/getIncomes?amount=100"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testWhenAskAllSpendsWithFilterGreaterThen() throws Exception {
        mockMvc.perform(get("/getSpends?amount=100"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}