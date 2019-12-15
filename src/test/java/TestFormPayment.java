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


public class TestFormPayment {

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
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные")
    void shouldPayByApprovedCard() throws SQLException {
        dataHelper.buyingForYourMoney();
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
    @DisplayName("Оплата по неизвестной карте, обычная покупка, валидные данные")
    void shouldNoPayByUnknownCard() throws SQLException {
        dataHelper.buyingForYourMoney();
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
    @DisplayName("Оплата по карте c пустым номером карты, обычная покупка")
    void shouldNoPayEmptyCardNumberField() throws SQLException {
        dataHelper.buyingForYourMoney();
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
    @DisplayName("Оплата по карте c пустым номером года, обычная покупка")
    void shouldNoPayEmptyYearField() throws SQLException {
        dataHelper.buyingForYourMoney();
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
    @DisplayName("Оплата по карте c пустым полем CVV, обычная покупка")
    void shouldNoPayEmptyCVVField() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

}




