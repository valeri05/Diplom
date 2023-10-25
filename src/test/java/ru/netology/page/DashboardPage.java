package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement header2 = $("h2.heading");

    public DashboardPage() {header2.shouldBe(visible); }

    private final SelenideElement paymentButton = $("button.button.button_size_m");

    private final SelenideElement creditButton = $("button.button.button_view_extra");

    public PaymentGatePage paymentGate() {
        paymentButton.click();
        return new PaymentGatePage();
    }

    public CreditGatePage creditGate() {
        creditButton.click();
        return new CreditGatePage();
    }
}
