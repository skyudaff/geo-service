package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;


public class GeoServiceImplTest {

    //Написать тесты для проверки определения локации по ip (класс GeoServiceImpl)
    //Проверить работу метода public Location byIp(String ip)
    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1", "172.0.32.11", "96.44.183.149", "172.1.1.1", "96.2.2.2"})
    void test_get_location_byIp(String ip) {
        //arrange
        GeoServiceImpl geoService = new GeoServiceImpl();

        //act
        Location location = geoService.byIp(ip);

        //assert
        if (GeoServiceImpl.LOCALHOST.equals(ip)) {
            Assertions.assertNull(location.getCity());
            Assertions.assertNull(location.getCountry());
            Assertions.assertNull(location.getStreet());
            Assertions.assertEquals(0, location.getBuiling());
        } else if (GeoServiceImpl.MOSCOW_IP.equals(ip)) {
            Assertions.assertEquals("Moscow", location.getCity());
            Assertions.assertEquals(Country.RUSSIA, location.getCountry());
            Assertions.assertEquals("Lenina", location.getStreet());
            Assertions.assertEquals(15, location.getBuiling());
        } else if (GeoServiceImpl.NEW_YORK_IP.equals(ip)) {
            Assertions.assertEquals("New York", location.getCity());
            Assertions.assertEquals(Country.USA, location.getCountry());
            Assertions.assertEquals(" 10th Avenue", location.getStreet());
            Assertions.assertEquals(32, location.getBuiling());
        } else if (ip.startsWith("172.")) {
            Assertions.assertEquals("Moscow", location.getCity());
            Assertions.assertEquals(Country.RUSSIA, location.getCountry());
            Assertions.assertNull(location.getStreet());
            Assertions.assertEquals(0, location.getBuiling());
        } else if (ip.startsWith("96.")) {
            Assertions.assertEquals("New York", location.getCity());
            Assertions.assertEquals(Country.USA, location.getCountry());
            Assertions.assertNull(location.getStreet());
            Assertions.assertEquals(0, location.getBuiling());
        }
        Assertions.assertNotNull(location);
    }


    //проверить, что выбрасывается исключение (прошлое занятие)
    @Test
    void test_get_location_byCoordinate_throwsException() {
        //arrange
        GeoServiceImpl geoService = new GeoServiceImpl();
        double latitude = 0.0, longitude = 0.1;

        //assert                                           //act
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(latitude, longitude));
    }

}
