package gdt.projects.tgbot.service;

import gdt.projects.tgbot.dto.GetCursOnDateXml;
import gdt.projects.tgbot.dto.GetCursOnDateXmlResponse;
import gdt.projects.tgbot.dto.ValuteCursOnDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CentralRussianBankService extends WebServiceTemplate {

    @Value("${cbr.api.url}")
    private String cbrApiUrl;

    public List<ValuteCursOnDate> getCurrenciesFromCbr() throws DatatypeConfigurationException {
        final GetCursOnDateXml getCursOnDateXML = new GetCursOnDateXml();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        getCursOnDateXML.setOnDate(xmlGregCal);
        GetCursOnDateXmlResponse response = (GetCursOnDateXmlResponse) marshalSendAndReceive(cbrApiUrl, getCursOnDateXML);

        if(response == null) {
            throw new DatatypeConfigurationException("Could not get response from CBR");
        }

        final List<ValuteCursOnDate> courses = response.getResult().getValuteData();
        courses.forEach(course -> course.setName(course.getName().trim()));
        return courses;
    }
}
