package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.CardHelper;
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
       val payment = dashboardPage.paymentGate();
       payment.putCard(CardHelper.getAPPROVEDCard());
       payment.putDate(DataHelper.generateMonth(0),
               DataHelper.generateYear(0),
               DataHelper.generateCardOwner(""),
               DataHelper.generateCVC(""));
       payment.waitNotificationSuccessVisible();
       assertEquals("APPROVED", SQLHelper.findPaymentStatus());
    }
}
