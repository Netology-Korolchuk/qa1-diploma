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

public class TestFormPayment {

    DataHelper dataHelper = new DataHelper();

    @Test
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные")
    void shouldPayByApprovedCard() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
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
    @DisplayName("Оплата по неактивной карте, обычная покупка, валидные данные")
    void shouldNoPayByDeclinedCard() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
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
    @DisplayName("Оплата по неизвестной карте, обычная покупка, валидные данные")
    void shouldNoPayByUnknownCard() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.unknownCardData();
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
    @DisplayName("Оплата по карте c невалидным номером карты, обычная покупка")
    void shouldNoPayInvalidCardNumberField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cardNumber.setValue("333 333 333");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
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
    @DisplayName("Оплата по карте c невалидным номером месяца, обычная покупка")
    void shouldNoPayInvalidMonthField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.month.setValue("13");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
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
    @DisplayName("Оплата по карте c невалидным номером года, обычная покупка")
    void shouldNoPayInvalidYearField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.setValue("18");
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
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
    @DisplayName("Оплата по карте c невалидным полем владелец, обычная покупка")
    void shouldNoPayInvalidCardOwnerField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cardOwner.setValue("Фыва с oldgem 123456");
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
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
    @DisplayName("Оплата по карте c невалидным полем CVV, обычная покупка")
    void shouldNoPayInvalidCVVField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.cvcOrCvvNumber.setValue("123aa");
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
    @DisplayName("Оплата по карте c пустым номером карты, обычная покупка")
    void shouldNoPayEmptyCardNumberField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
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
    @DisplayName("Оплата по карте c пустым номером месяца, обычная покупка")
    void shouldNoPayEmptyMonthField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
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
    @DisplayName("Оплата по карте c пустым номером года, обычная покупка")
    void shouldNoPayEmptyYearField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
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
    @DisplayName("Оплата по карте c пустым полем владелец, обычная покупка")
    void shouldNoPayEmptyCardOwnerField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
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
    @DisplayName("Оплата по карте c пустым полем CVV, обычная покупка")
    void shouldNoPayEmptyCVVField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
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

//тесты записи В БД - СДЕЛАТЬ REST API тесты

    @Test
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные - тест записи в БД")
    void shouldPayByApprovedCardSaveDb() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        dataHelper.paymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Оплата по активной карте, покупка в кредит, валидные данные - тест записи в БД")
    void shouldPayByApprovedCardInCreditSaveDb() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        dataHelper.creditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, обычная покупка, валидные данные - тест записи в БД")
    void shouldNoPayByUnknownCardSaveDb() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.unknownCardData();
        dataHelper.pushСontinueButton();
        dataHelper.paymentStatus(Status.DECLINED);
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, покупка в кредит, валидные данные - тест записи в БД")
    void shouldNoPayByUnknownCardInCreditSaveDb() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.unknownCardData();
        dataHelper.pushСontinueButton();
        dataHelper.paymentStatus(Status.DECLINED);
    }

 /*
    @Test
    @DisplayName("Rest API")
    void shouldRest() {
        String request = "{\n" +
                "           \"number\":\"4444 4444 4444 4443\",\n" +
                "           \"year\":\"22\",\n" +
                "           \"month\":\"08\",\n" +
                "           \"holder\":\"Petrov Ivan\",\n" +
                "           \"cvc\":\"999\"}";

            given().
            header("Content-Type", "application/json").
            body(request).
            when().
            post("http://localhost:8080/api/v1/credit").
            then().
            statusCode(500).
            body("message", equalTo("400 Bad Request"));
    }
*/


}

