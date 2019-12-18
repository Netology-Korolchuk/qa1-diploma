import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageObject {

    private static String url = System.getProperty("db.url");
    private static String appURL = System.getProperty("app.url");
    private static String appPORT = System.getProperty("app.port");
    private static String userDB = System.getProperty("userDB");
    private static String password = System.getProperty("password");

    List<SelenideElement> input = $$(".input__control");
    SelenideElement cardNumber = input.get(0);
    SelenideElement month = input.get(1);
    SelenideElement year = input.get(2);
    SelenideElement cardOwner = input.get(3);
    SelenideElement cvcOrCvvNumber = input.get(4);

    public void buyForYourMoney() {
        open(appURL +":"+appPORT);
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void buyOnCredit(){
        open(appURL +":"+appPORT);
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
    }

    public void fillFormActiveCardData(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInactiveCardData(){
        cardNumber.setValue("4444444444444442");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Petr Ivanov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormUnknownCardData(){
        cardNumber.setValue("4444444444444443");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Anon Unknown");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInvalidCardNumberField(){
        cardNumber.setValue("3333 3333");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Anon Unknown");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInvalidMonthField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("13");
        year.setValue("22");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInvalidYearField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("18");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInvalidCardOwnerField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Фыва с oldgem 123456");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormInvalidCVVField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("123AS");
    }

    public void fillFormEmptyCardNumberField(){
        cardNumber.setValue("");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Anon Unknown");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormEmptyMonthField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("");
        year.setValue("22");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormEmptyYearField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormEmptyCardOwnerField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("");
        cvcOrCvvNumber.setValue("999");
    }

    public void fillFormEmptyCVVField(){
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Ivan Petrov");
        cvcOrCvvNumber.setValue("");
    }

    public void pushСontinueButton(){
        $$(".button__content").find(exactText("Продолжить")).click();
    }

    public void checkPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        //val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass");
        val conn = DriverManager.getConnection(url, userDB, password);
        val paymentDataSQL = "SELECT * FROM payment_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
        assertEquals(status, payment.status);
    }

    public void checkCreditStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(url, userDB, password);
        val creditDataSQL = "SELECT * FROM credit_request_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val credit = runner.query(conn, creditDataSQL, new BeanHandler<>(Payment.class));
        assertEquals(status, credit.status);
    }
}
