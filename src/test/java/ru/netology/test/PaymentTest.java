package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        dashboardPage = open("http://localhost:8080", DashboardPage.class);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.clearTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

// positive scenarios

    @Test
    @DisplayName("Покупка по карте,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWithAValidCard() {
       val dashboardPage = new DashboardPage();
        val paymentPage = dashboardPage.paymentGate();
        paymentPage.putData(DataHelper.getValidCard());
        paymentPage.waitNotificationSuccessVisible();
       assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }
    @Test
    @DisplayName("Покупка по заблокированной карте,операция отклонена банком,в БД появилась запись со статусом DECLINED")
    void shouldDeniedPaymentWithDeclinedCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLHelper.findPaymentStatus());
    }
    @Test
    @DisplayName("Покупка по карте Владельцем чье имя с апострофом,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWereNameWithApostrophe() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameWithApostrophe());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }
    @Test
    @DisplayName("Покупка по карте Владельцем с именем через дефис,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWereHyphenatedName() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getHyphenatedName());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }

    // negative scenarios
    // card number

    @Test
    @DisplayName("Покупка по карте, номер которой заполнен пробелами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, номер которой заполнен нулями. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardNull());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, номер которой заполнен буквами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, номер которой заполнен символами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, номер которой не заполнен. Появилось сообщение под полем 'Номер карты': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutNumberField() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutNumberCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, номер которой заполнен не полностью. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberCardWithOneLessCharacter() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardWithOneLessCharacter());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, длина номера которой на одну цифру больше. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void purchaseUsingANotExistedCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNotExistedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }

    // month

    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен буквами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен нулями. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNullMonthCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен пробелами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен символами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой не заполнен. Появилось сообщение под полем 'Месяц': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutMonthCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен не полностью. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле месяца заполнено длинной в три знака. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardToThreeValue());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, месяц которой заполнен не логичным значением. Появилось сообщение под полем 'Месяц': «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNotExistedMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNotExistedMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, с истекшим сроком действия по месяцу. Появилось сообщение под полем 'Месяц': «Истёк срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheExpiredMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getExpiredMonthCard());
        payment.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }

    // year

    @Test
    @DisplayName("Покупка по карте, год которой заполнен нулями. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNullYearCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, год которой заполнен буквами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, год которой заполнен пробелами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, год которой заполнен символами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, год которой не заполнен. Появилось сообщение под полем 'Год': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutYear() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutYearCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, год которой заполнен не полностью. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле года заполнено длинной в три знака. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardToThreeValue());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, с истекшим сроком действия по году. Появилось сообщение под полем 'Год': «Истёк срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheExpiredYear() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getExpiredYearCard());
        payment.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }

    // card owner

    @Test
    @DisplayName("Покупка по карте, имя владельца указано на русском языке. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheRusNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getRusNameOwnerCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, имя владельца указано цифрами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardNumericalValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardNumericalValues());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле владельца заполнено пробелами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле владельца заполнено символами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле владельца не заполнено. Появилось сообщение под полем 'Владелец': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutNameOwnerCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле владельца заполнено буквенными данными длинной в один знак. Появилось сообщение под полем 'Владелец': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardToOneLetter() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardToOneLetter());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле владельца заполнено буквенными данными длинной в 35 знаков. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardToThirtyFiveCharacters() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardToThirtyFiveCharacters());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }

    // CVC

    @Test
    @DisplayName("Покупка по карте, поле CVC заполнено пробелами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInSpaces());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле CVC заполнено буквами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле CVC заполнено символами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле CVC заполнено числовыми данными длинной в один знак. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardToOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле CVC заполнено числовыми данными длинной в четыре знака. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardToForeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardToForeValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по карте, поле CVC не заполнено. Появилось сообщение под полем 'CVC': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutCvcCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutCVCCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
}