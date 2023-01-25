package gdt.projects.tgbot.controllers;

import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CentralRussianBankService cbr;

    @PostMapping("/getCurrencies")
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return cbr.getCurrenciesFromCbr();
    }
}
