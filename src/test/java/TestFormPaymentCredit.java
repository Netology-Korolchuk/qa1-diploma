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

public class TestFormPaymentCredit {

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
    @DisplayName("Оплата по активной карте, покупка в кредит, валидные данные")
    void shouldPayByApprovedCardInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormActiveCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неактивной карте, покупка в кредит, валидные данные")
    void shouldNoPayByDeclinedCardInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInactiveCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, покупка в кредит, валидные данные")
    void shouldNoPayByUnknownCardInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormUnknownCardData();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты, покупка в кредит")
    void shouldNoPayInvalidCardNumberFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidCardNumberField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца, покупка в кредит")
    void shouldNoPayInvalidMonthFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidMonthField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года, покупка в кредит")
    void shouldNoPayInvalidYearFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidYearField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец, покупка в кредит")
    void shouldNoPayInvalidCardOwnerFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidCardOwnerField();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV, покупка в кредит")
    void shouldNoPayInvalidCVVFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidCVVField();
        pageObject.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером карты, покупка в кредит")
    void shouldNoPayEmptyCardNumberFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormEmptyCardNumberField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером месяца, покупка в кредит")
    void shouldNoPayEmptyMonthFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormInvalidMonthField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером года, покупка в кредит")
    void shouldNoPayEmptyYearFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormEmptyYearField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем владелец, покупка в кредит")
    void shouldNoPayEmptyCardOwnerFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormEmptyCardOwnerField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем CVV, покупка в кредит")
    void shouldNoPayEmptyCVVFieldInCredit() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormEmptyCVVField();
        pageObject.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    //проверка записи в БД
    @Test
    @DisplayName("Оплата по активной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldPayByApprovedCardInCreditStatusInDB() throws SQLException {
        pageObject.buyOnCredit();
        pageObject.fillFormActiveCardData();
        pageObject.pushСontinueButton();
        pageObject.checkCreditStatus(Status.APPROVED);
    }

}




