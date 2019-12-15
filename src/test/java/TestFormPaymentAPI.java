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


public class TestFormPaymentAPI {

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
    @DisplayName("API Оплата по активной карте, обычная покупка, валидные данные, проверка записи в БД")
    void shouldPayByApprovedCardApi() throws SQLException{
        String request = "{\n" +
                "           \"number\":\"4444 4444 4444 4441\",\n" +
                "           \"year\":\"22\",\n" +
                "           \"month\":\"08\",\n" +
                "           \"holder\":\"Petrov Ivan\",\n" +
                "           \"cvc\":\"999\"}";
        given().
                header("Content-Type", "application/json").
                body(request).
                when().
                post("http://localhost:8080/api/v1/pay").
                then().
                statusCode(200).
                body("status", equalTo("APPROVED"));

        dataHelper.paymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("API Оплата по неактивной карте, обычная покупка, валидные данные, проверка записи в БД")
    void shouldNoPayByDeclinedCardApi() throws SQLException{
        String request = "{\n" +
                "           \"number\":\"4444 4444 4444 4442\",\n" +
                "           \"year\":\"22\",\n" +
                "           \"month\":\"08\",\n" +
                "           \"holder\":\"Petrov Ivan\",\n" +
                "           \"cvc\":\"999\"}";
        given().
                header("Content-Type", "application/json").
                body(request).
                when().
                post("http://localhost:8080/api/v1/pay").
                then().
                statusCode(200).
                body("status", equalTo("DECLINED"));

        dataHelper.paymentStatus(Status.DECLINED);
    }

    @Test
    @DisplayName("API Оплата по неизвестной карте, обычная покупка, валидные данные, проверка записи в БД")
    void shouldNoPayByUnknownCardApi() throws SQLException{

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
                post("http://localhost:8080/api/v1/pay").
                then().
                statusCode(500).
                body("message", equalTo("400 Bad Request"));

        dataHelper.paymentStatus(Status.DECLINED);
    }

    @Test
    @DisplayName("API Оплата по активной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldPayByApprovedCardInCreditApi() throws SQLException{
        String request = "{\n" +
                "           \"number\":\"4444 4444 4444 4441\",\n" +
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
                statusCode(200).
                body("status", equalTo("APPROVED"));

        dataHelper.creditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("API Оплата по неактивной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldNoPayByDeclinedCardInCreditApi() throws SQLException{
        String request = "{\n" +
                "           \"number\":\"4444 4444 4444 4442\",\n" +
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
                statusCode(200).
                body("status", equalTo("DECLINED"));

        dataHelper.creditStatus(Status.DECLINED);
    }

    @Test
    @DisplayName("API Оплата по неизвестной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldNoPayByUnknownCardInCreditApi() throws SQLException{

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

        dataHelper.creditStatus(Status.DECLINED);
    }

}




