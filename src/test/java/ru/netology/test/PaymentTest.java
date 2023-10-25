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

    @BeforeEach
    public void openPage() {
        String url = System.getProperty("sut.url");
        open(url);
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
    void shouldSuccessfulPurchaseWithAValidCard() {
       val dashboardPage = new DashboardPage();
       val paymentPage = dashboardPage.paymentGate();
       paymentPage.putData(DataHelper.getValidCard());
       paymentPage.waitNotificationSuccessVisible();
       assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }
    @Test
    void shouldDeniedPaymentWithDeclinedCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLHelper.findPaymentStatus());

    }
    @Test
    void shouldSuccessfulPurchaseWereNameWithApostrophe() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameWithApostrophe());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }
    @Test
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
    void invalidFillingOfTheNumberFieldWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardNull());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutNumberField() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutNumberCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberCardWithOneLessCharacter() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNumberCardWithOneLessCharacter());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void purchaseUsingANotExistedCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNotExistedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // month
    @Test
    void invalidFillingOfTheMonthWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNullMonthCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutMonthCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getMonthCardToThreeValue());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNotExistedMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNotExistedMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheExpiredMonth() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getExpiredMonthCard());
        payment.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // year
    @Test
    void invalidFillingOfTheYearWithZeros() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNullYearCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutYear() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutYearCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getYearCardToThreeValue());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheExpiredYear() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getExpiredYearCard());
        payment.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // card owner
    @Test
    void invalidFillingOfTheRusNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getRusNameOwnerCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardNumericalValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardNumericalValues());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardInSpaces());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutNameOwnerCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardToOneLetter() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardToOneLetter());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardToThirtyFiveCharacters() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getNameOwnerCardToThirtyFiveCharacters());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // CVC
    @Test
    void invalidFillingOfTheCvcCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInSpaces());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInText());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardInSymbols());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardToOneValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardToOneValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardToForeValue() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getCVCCardToForeValue());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutCvcCard() {
        val dashboardPage = new DashboardPage();
        val payment = dashboardPage.paymentGate();
        payment.putData(DataHelper.getWithoutCVCCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
}
