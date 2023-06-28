package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

import static ru.netology.entity.Country.RUSSIA;

public class LocalizationServiceImplTest {

    //Написать тесты для проверки возвращаемого текста (класс LocalizationServiceImpl)
    //Проверить работу метода public String locale(Country country)

    @ParameterizedTest
    @EnumSource(Country.class)
    void test_get_text_byLocale(Country country) {
        //arrange
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        //act
        String result= localizationService.locale(country);

        //assert
        if (country.equals(RUSSIA)) {
            Assertions.assertEquals("Добро пожаловать", result);
        } else Assertions.assertEquals("Welcome", result);
    }

}
