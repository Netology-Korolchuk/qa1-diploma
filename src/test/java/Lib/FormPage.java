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

public class FormPage {

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

    public void checkMessageSuccess() {
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 15000);
    }

    public void checkMessageError() {
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 15000);
    }

    public void checkMessageWrongFormat() {
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    public void checkMessageWrongDate() {
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void checkMessageOverDate() {
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void checkMessageRequiredField() {
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void setCardNumber(String cNumber) {
        cardNumber.setValue(cNumber);
    }

    public void setCardMonth(String cMonth) {
        month.setValue(cMonth);
    }

    public void setCardYear(String cYear) {
        year.setValue(cYear);
    }

    public void setCardOwner(String cOwner) {
        cardOwner.setValue(cOwner);
    }

    public void setCardCVV(String cCvv) {
        cvcOrCvvNumber.setValue(cCvv);
    }


    public void pushСontinueButton(){
        $$(".button__content").find(exactText("Продолжить")).click();
    }

    public void checkPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        //val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass");
        val conn = DriverManager.getConnection(url, userDB, password);
        val paymentDataSQL = "SELECT * FROM payment_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(PaymentModel.class));
        assertEquals(status, payment.status);
    }

    public void checkCreditStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(url, userDB, password);
        val creditDataSQL = "SELECT * FROM credit_request_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val credit = runner.query(conn, creditDataSQL, new BeanHandler<>(CreditModel.class));
        assertEquals(status, credit.status);
    }
}
