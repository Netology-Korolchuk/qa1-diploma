import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.sql.SQLException;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.*;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;


public class TestFormPaymentCredit {

    DataHelper dataHelper = new DataHelper();


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
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неактивной карте, покупка в кредит, валидные данные")
    void shouldNoPayByDeclinedCardInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, покупка в кредит, валидные данные")
    void shouldNoPayByUnknownCardInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.unknownCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты, покупка в кредит")
    void shouldNoPayInvalidCardNumberFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cardNumber.setValue("333 333 333 bbbb");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца, покупка в кредит")
    void shouldNoPayInvalidMonthFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.month.setValue("13");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года, покупка в кредит")
    void shouldNoPayInvalidYearFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.setValue("18");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец, покупка в кредит")
    void shouldNoPayInvalidCardOwnerFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cardOwner.setValue("Фыва с oldgem 123456");
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV, покупка в кредит")
    void shouldNoPayInvalidCVVFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cvcOrCvvNumber.setValue("123aa");
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером карты, покупка в кредит")
    void shouldNoPayEmptyCardNumberFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером месяца, покупка в кредит")
    void shouldNoPayEmptyMonthFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером года, покупка в кредит")
    void shouldNoPayEmptyYearFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем владелец, покупка в кредит")
    void shouldNoPayEmptyCardOwnerFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем CVV, покупка в кредит")
    void shouldNoPayEmptyCVVFieldInCredit() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

}




