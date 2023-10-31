package ru.netology.data;


import lombok.AllArgsConstructor;
import lombok.Data;

    @Data
    @AllArgsConstructor
    public class CardHelper {

    private String cardNumber;

    public static CardHelper getAPPROVEDCard() {
        return new CardHelper("4444 4444 4444 4441");
    }

    public static CardHelper getDECLINEDCard() {
        return new CardHelper("4444 4444 4444 4442");
    }
}

