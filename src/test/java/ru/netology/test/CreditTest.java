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
        val creditPage = dashboardPage.creditGate();
        creditPage.putData(DataHelper.getValidCard());
        creditPage.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findCreditRequestStatus());
    }
    @Test
    void shouldDeniedcreditWithDeclinedCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getDeclinedCard());
        credit.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLHelper.findCreditRequestStatus());

    }
    @Test
    void shouldSuccessfulPurchaseWereNameWithApostrophe() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameWithApostrophe());
        credit.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLHelper.findCreditRequestStatus());
    }
    @Test
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
    void invalidFillingOfTheNumberFieldWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardNull());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberFieldWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutNumberField() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutNumberCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNumberCardWithOneLessCharacter() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNumberCardWithOneLessCharacter());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void purchaseUsingANotExistedCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNotExistedCard());
        credit.waitNotificationFailedVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // month
    @Test
    void invalidFillingOfTheMonthWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNullMonthCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutMonthCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheMonthWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getMonthCardToThreeValue());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNotExistedMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNotExistedMonthCard());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheExpiredMonth() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getExpiredMonthCard());
        credit.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // year
    @Test
    void invalidFillingOfTheYearWithZeros() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNullYearCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutYear() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutYearCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheYearWithThreeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getYearCardToThreeValue());
        credit.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheExpiredYear() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getExpiredYearCard());
        credit.waitNotificationExpiredErrorVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // card owner
    @Test
    void invalidFillingOfTheRusNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getRusNameOwnerCard());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardNumericalValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardNumericalValues());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardInSpaces());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutNameOwnerCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutNameOwnerCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardToOneLetter() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardToOneLetter());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheNameOwnerCardToThirtyFiveCharacters() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getNameOwnerCardToThirtyFiveCharacters());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    // CVC
    @Test
    void invalidFillingOfTheCvcCardInSpaces() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInSpaces());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardWithLiteralValues() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInText());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardInSymbols() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardInSymbols());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardToOneValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardToOneValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidFillingOfTheCvcCardToForeValue() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getCVCCardToForeValue());
        credit.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
    @Test
    void invalidWithoutCvcCard() {
        val dashboardPage = new DashboardPage();
        val credit = dashboardPage.creditGate();
        credit.putData(DataHelper.getWithoutCVCCard());
        credit.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLHelper.findCountOrderEntity());
    }
}
