package ru.netology.data;

public class DataHelper {

    public static CardHelper getValidCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getDeclinedCard() {
        return new CardHelper("4444 4444 4444 4442", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNameWithApostrophe() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina' Ivanova", "123");
    }
    public static CardHelper getHyphenatedName() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina- Ivanova", "123");
    }

    // card number

    public static CardHelper getNumberCardInSpaces() {

        return new CardHelper("                   ", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNumberCardNull() {

        return new CardHelper("0000 0000 0000 0000", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNumberCardInText() {
        return new CardHelper("РРРР РРРР РРРР РРРР", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNumberCardInSymbols() {
        return new CardHelper(".... .... .... ....", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getWithoutNumberCard() {

        return new CardHelper("", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNumberCardWithOneLessCharacter() {
        return new CardHelper("4444 4444 4444 444", "10", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNotExistedCard() {
        return new CardHelper("4444 4444 4444 4444", "10", "23", "Alina Ivanova", "123");
    }

    // month

    public static CardHelper getMonthCardText() {
        return new CardHelper("4444 4444 4444 4441", "aa", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNullMonthCard() {
        return new CardHelper("4444 4444 4444 4441", "00", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getMonthCardInSpaces() {
        return new CardHelper("4444 4444 4444 4441", "  ", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getMonthCardInSymbols() {
        return new CardHelper("4444 4444 4444 4441", "..", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getWithoutMonthCard() {
        return new CardHelper("4444 4444 4444 4441", "", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getMonthCardToOneValue() {
        return new CardHelper("4444 4444 4444 4441", "1", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getMonthCardToThreeValue() {
        return new CardHelper("4444 4444 4444 4441", "001", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getNotExistedMonthCard() {
        return new CardHelper("4444 4444 4444 4441", "13", "23", "Alina Ivanova", "123");
    }
    public static CardHelper getExpiredMonthCard() {
        return new CardHelper("4444 4444 4444 4441", "09", "23", "Alina Ivanova", "123");
    }

    // year

    public static CardHelper getNullYearCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "00", "Alina Ivanova", "123");
    }
    public static CardHelper getYearCardInText() {
        return new CardHelper("4444 4444 4444 4441", "10", "aa", "Alina Ivanova", "123");
    }
    public static CardHelper getYearCardInSpaces() {
        return new CardHelper("4444 4444 4444 4441", "10", "  ", "Alina Ivanova", "123");
    }
    public static CardHelper getYearCardInSymbols() {
        return new CardHelper("4444 4444 4444 4441", "10", "..", "Alina Ivanova", "123");
    }
    public static CardHelper getWithoutYearCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "", "Alina Ivanova", "123");
    }
    public static CardHelper getYearCardToOneValue() {
        return new CardHelper("4444 4444 4444 4441", "10", "1", "Alina Ivanova", "123");
    }
    public static CardHelper getYearCardToThreeValue() {
        return new CardHelper("4444 4444 4444 4441", "10", "001", "Alina Ivanova", "123");
    }
    public static CardHelper getExpiredYearCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "20", "Alina Ivanova", "123");
    }

    // card owner

    public static CardHelper getRusNameOwnerCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Алина Иванова", "123");
    }
    public static CardHelper getNameOwnerCardNumericalValues() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "00000", "123");
    }
    public static CardHelper getNameOwnerCardInSpaces() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "             ", "123");
    }
    public static CardHelper getNameOwnerCardInSymbols() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "******", "123");
    }
    public static CardHelper getWithoutNameOwnerCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "", "123");
    }
    public static CardHelper getNameOwnerCardToOneLetter() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "N", "123");
    }
    public static CardHelper getNameOwnerCardToThirtyFiveCharacters() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "123");
    }

    // CVC

    public static CardHelper getCVCCardInSpaces() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "   ");
    }
    public static CardHelper getCVCCardInText() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "aaa");
    }
    public static CardHelper getCVCCardInSymbols() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "...");
    }
    public static CardHelper getCVCCardToOneValue() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "1");
    }
    public static CardHelper getCVCCardToForeValue() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "1111");
    }
    public static CardHelper getWithoutCVCCard() {
        return new CardHelper("4444 4444 4444 4441", "10", "23", "Alina Ivanova", "");
    }
}
