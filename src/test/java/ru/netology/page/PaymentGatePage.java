package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import ru.netology.data.CardHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class PaymentGatePage {

    private final SelenideElement header3 = $("h3.heading");
    private final SelenideElement cardNumber = $("input.input__control[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("input.input__control[placeholder='08']");
    private final SelenideElement year = $("input.input__control[placeholder='22']");
    private final SelenideElement cardowner = $(byText("Владелец")).parent().$("[class='input__control']");
    private final SelenideElement cvc = $("input.input__control[placeholder='999']");
    private final SelenideElement successfulPurchase = $$("[class='notification__content']").find(text("Операция одобрена Банком."));
    private final SelenideElement failedPurchase = $$("[class='notification__content']").find(text("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private final SelenideElement invalidCardExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardDateExpired = $(byText("Истёк срок действия карты"));
    private final SelenideElement fieldRequired = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public PaymentGatePage() {
        header3.shouldBe(visible);
    }

    public void putData(CardHelper card) {
        cardNumber.setValue(card.getCard());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        cardowner.setValue(card.getName());
        cvc.setValue(card.getCode());
        continueButton.click();
    }

    public void waitNotificationSuccessVisible() { successfulPurchase.shouldBe(Condition.visible, Duration.ofSeconds(15)); }
    public void waitNotificationFailedVisible() {
        failedPurchase.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
    public void waitNotificationValidityErrorVisible() {
        invalidCardExpirationDate.shouldBe(Condition.visible);
    }
    public void waitNotificationExpiredErrorVisible() {
        cardDateExpired.shouldBe(Condition.visible);
    }
    public void waitNotificationRequiredFieldVisible() {
        fieldRequired.shouldBe(Condition.visible);
    }
    public void waitNotificationFullWrongFormatVisible() {
        wrongFormatError.shouldBe(visible);
    }
}