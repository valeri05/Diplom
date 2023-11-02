package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
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
    @DisplayName("Покупка по кредитной карте,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWithAValidCard() {
        val dashboardPage = new DashboardPage();
        val creditPage = dashboardPage.creditGate();
        creditPage.putData(DataHelper.getValidCard());
        creditPage.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findCreditRequestStatus());
    }
    @Test
    @DisplayName("Покупка по заблокированной кредитной карте,операция отклонена банком,в БД появилась запись со статусом DECLINED")
    void shouldDeniedcreditWithDeclinedCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getDeclinedCard());
        credit.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLHelper.findCreditRequestStatus());

    }
    @Test
    @DisplayName("Покупка по кредитной карте Владельцем чье имя с апострофом,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWereNameWithApostrophe() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameWithApostrophe());
        credit.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findCreditRequestStatus());
    }
    @Test
    @DisplayName("Покупка по кредитной карте Владельцем с именем через дефис,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldSuccessfulPurchaseWereHyphenatedName() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getHyphenatedName());
        credit.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findCreditRequestStatus());
    }
    // negative scenarios
    // card number
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой заполнен пробелами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой заполнен нулями. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardNull());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой заполнен буквами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой заполнен символами. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberFieldWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой не заполнен. Появилось сообщение под полем 'Номер карты': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutNumberField() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutNumberCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, номер которой заполнен не полностью. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNumberCardWithOneLessCharacter() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardWithOneLessCharacter());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, длина номера которой на одну цифру больше. Появилось сообщение под полем 'Номер карты': «Неверный формат», " +
            "в БД запись отсутствует")
    void purchaseUsingANotExistedCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNotExistedCard());
        credit.waitNotificationFailedVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // month
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен буквами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен нулями. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNullMonthCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен пробелами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен символами. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой не заполнен. Появилось сообщение под полем 'Месяц': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutMonthCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен не полностью. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле месяца заполнено длинной в три знака. Появилось сообщение под полем 'Месяц': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheMonthWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardToThreeValue());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, месяц которой заполнен не логичным значением. Появилось сообщение под полем 'Месяц': «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNotExistedMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNotExistedMonthCard());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, с истекшим сроком действия по месяцу. Появилось сообщение под полем 'Месяц': «Истёк срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheExpiredMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getExpiredMonthCard());
        credit.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // year
    @Test
    @DisplayName("Покупка по кредитной карте, год которой заполнен нулями. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNullYearCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, год которой заполнен буквами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, год которой заполнен пробелами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, год которой заполнен символами. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, год которой не заполнен. Появилось сообщение под полем 'Год': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutYear() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutYearCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, год которой заполнен не полностью. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле года заполнено длинной в три знака. Появилось сообщение под полем 'Год': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheYearWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardToThreeValue());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, с истекшим сроком действия по году. Появилось сообщение под полем 'Год': «Истёк срок действия карты», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheExpiredYear() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getExpiredYearCard());
        credit.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // card owner
    @Test
    @DisplayName("Покупка по кредитной карте, имя владельца указано на русском языке. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheRusNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getRusNameOwnerCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, имя владельца указано цифрами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardNumericalValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardNumericalValues());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле владельца заполнено пробелами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле владельца заполнено символами. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле владельца не заполнено. Появилось сообщение под полем 'Владелец': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutNameOwnerCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле владельца заполнено буквенными данными длинной в один знак. Появилось сообщение под полем 'Владелец': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardToOneLetter() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardToOneLetter());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле владельца заполнено буквенными данными длинной в 35 знаков. Появилось сообщение под полем 'Владелец': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheNameOwnerCardToThirtyFiveCharacters() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardToThirtyFiveCharacters());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // CVC
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC заполнено пробелами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInSpaces());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC заполнено буквами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC заполнено символами. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC заполнено числовыми данными длинной в один знак. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardToOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC заполнено числовыми данными длинной в четыре знака. Появилось сообщение под полем 'CVC': «Неверный формат», " +
            "в БД запись отсутствует")
    void invalidFillingOfTheCvcCardToForeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardToForeValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    @DisplayName("Покупка по кредитной карте, поле CVC не заполнено. Появилось сообщение под полем 'CVC': «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void invalidWithoutCvcCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutCVCCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
}
