package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Collections;
import java.util.Map;


public class MessageSenderTest {

    //    Написать тесты для проверки языка отправляемого сообщения (класс MessageSender) с использованием Mockito
    //    Поверить, что MessageSenderImpl всегда отправляет только русский текст, если ip относится к российскому сегменту адресов.
    //    Поверить, что MessageSenderImpl всегда отправляет только английский текст, если ip относится к американскому сегменту адресов.

    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1", "172.0.32.11", "96.44.183.149"})
    void test_send_msg_mockito(String ipAddress) {
        //arrange
        GeoService geoService = Mockito.mock(GeoService.class);
        Location locRus = new Location(null, Country.RUSSIA, null, 0);
        Location locUSA = new Location(null, Country.USA, null, 0);

        Mockito.when(geoService.byIp(ipAddress)).thenReturn(ipAddress.startsWith("172.") ? locRus : locUSA);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        //act
        Map<String, String> headers = Collections.singletonMap(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);
        String result = messageSender.send(headers);

        //assert
        if (ipAddress.startsWith("172.")) {
            Assertions.assertEquals("Добро пожаловать", result);
        } else {
            Assertions.assertEquals("Welcome", result);
        }
    }
}
