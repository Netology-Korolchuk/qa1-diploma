import org.openqa.selenium.Keys;
import java.sql.SQLException;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFormPayment {

    PageObject pageObject = new PageObject();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные")
    void shouldPayByApprovedCard() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormActiveCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неактивной карте, обычная покупка, валидные данные")
    void shouldNoPayByDeclinedCard() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInactiveCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, обычная покупка, валидные данные")
    void shouldNoPayByUnknownCard() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormUnknownCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }
//
    @Test
    @DisplayName("Оплата по карте c невалидным номером карты, обычная покупка")
    void shouldNoPayInvalidCardNumberField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInvalidCardNumberField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца, обычная покупка")
    void shouldNoPayInvalidMonthField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInvalidMonthField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года, обычная покупка")
    void shouldNoPayInvalidYearField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInvalidYearField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец, обычная покупка")
    void shouldNoPayInvalidCardOwnerField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInvalidCardOwnerField();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV, обычная покупка")
    void shouldNoPayInvalidCVVField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormInvalidCVVField();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером карты, обычная покупка")
    void shouldNoPayEmptyCardNumberField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormEmptyCardNumberField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером месяца, обычная покупка")
    void shouldNoPayEmptyMonthField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormEmptyMonthField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером года, обычная покупка")
    void shouldNoPayEmptyYearField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormEmptyYearField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем владелец, обычная покупка")
    void shouldNoPayEmptyCardOwnerField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormEmptyCardOwnerField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем CVV, обычная покупка")
    void shouldNoPayEmptyCVVField() throws SQLException {
        pageObject.buyForYourMoney();
        pageObject.fillFormEmptyCVVField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

//проверка записи в БД
@Test
@DisplayName("Оплата по активной карте, обычная покупка, валидные данные, проверка записи в БД")
void shouldPayByApprovedCardStatusInDB() throws SQLException {
    pageObject.buyForYourMoney();
    pageObject.fillFormActiveCardData();
    pageObject.pushСontinueButton();
    pageObject.checkPaymentStatus(Status.APPROVED);
}

}




