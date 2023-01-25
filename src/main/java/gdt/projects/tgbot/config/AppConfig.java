package gdt.projects.tgbot.config;

import gdt.projects.tgbot.dto.GetCursOnDateXml;
import gdt.projects.tgbot.dto.GetCursOnDateXmlResponse;
import gdt.projects.tgbot.dto.GetCursOnDateXmlResult;
import gdt.projects.tgbot.dto.ValuteCursOnDate;
import gdt.projects.tgbot.service.*;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;


import javax.xml.soap.SOAPConstants;
import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    @Bean
    public CentralRussianBankService cbr() throws SOAPException {
        CentralRussianBankService cbr = new CentralRussianBankService();
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
        cbr.setMessageFactory(newSoapMessageFactory);

        jaxb2Marshaller.setClassesToBeBound(
                GetCursOnDateXml.class,
                GetCursOnDateXmlResponse.class,
                GetCursOnDateXmlResult.class,
                ValuteCursOnDate.class
        );
        cbr.setMarshaller(jaxb2Marshaller);
        cbr.setUnmarshaller(jaxb2Marshaller);
        return cbr;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.name());
        filter.setForceEncoding(true);
        return filter;
    }
}
