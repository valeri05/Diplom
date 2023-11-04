package ru.netology.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }
    private static final Faker faker = new Faker(new Locale("en"));

    public static String generateCardOwner(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    public static String generateCVC(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.numerify("###");
    }
    public static String generateMonth(int addMonth, String pattern) {
        return LocalDate.now().plusMonths(addMonth).format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String generateYear(int addYear, String pattern) {
        return LocalDate.now().plusYears(addYear).format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String getAPPROVEDCard() {
        return "4444 4444 4444 4441";
    }
    public static String getDECLINEDCard() {
        return "4444 4444 4444 4442";
    }
    public static CardHelper getValidCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getDeclinedCard() {
        return new CardHelper(getDECLINEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNameWithApostrophe() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "Alina' Ivanova", generateCVC("en"));
    }
    public static CardHelper getHyphenatedName() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "Alina- Ivanova", generateCVC("en"));
    }

    // card number

    public static CardHelper getNumberCardInSpaces() {

        return new CardHelper("                   ", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNumberCardNull() {

        return new CardHelper("0000 0000 0000 0000", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNumberCardInText() {
        return new CardHelper("РРРР РРРР РРРР РРРР", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNumberCardInSymbols() {
        return new CardHelper(".... .... .... ....", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getWithoutNumberCard() {

        return new CardHelper("", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNumberCardWithOneLessCharacter() {
        return new CardHelper("4444 4444 4444 444", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNotExistedCard() {
        return new CardHelper("4444 4444 4444 4444", generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }

    // month

    public static CardHelper getMonthCardText() {
        return new CardHelper(getAPPROVEDCard(), "aa", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNullMonthCard() {
        return new CardHelper(getAPPROVEDCard(), "00", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getMonthCardInSpaces() {
        return new CardHelper(getAPPROVEDCard(), "  ", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getMonthCardInSymbols() {
        return new CardHelper(getAPPROVEDCard(), "..", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getWithoutMonthCard() {
        return new CardHelper(getAPPROVEDCard(), "", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getMonthCardToOneValue() {
        return new CardHelper(getAPPROVEDCard(), "1", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getMonthCardToThreeValue() {
        return new CardHelper(getAPPROVEDCard(), "111", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getNotExistedMonthCard() {
        return new CardHelper(getAPPROVEDCard(), "13", generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getExpiredMonthCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(-1,"MM"), generateYear(0,"yy"), generateCardOwner("en"), generateCVC("en"));
    }

    // year

    public static CardHelper getNullYearCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "00", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getYearCardInText() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "aa", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getYearCardInSpaces() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "  ", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getYearCardInSymbols() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "..", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getWithoutYearCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getYearCardToOneValue() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "1", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getYearCardToThreeValue() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), "111", generateCardOwner("en"), generateCVC("en"));
    }
    public static CardHelper getExpiredYearCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(-1,"yy"), generateCardOwner("en"), generateCVC("en"));
    }

    // card owner

    public static CardHelper getRusNameOwnerCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("ru"), generateCVC("en"));
    }
    public static CardHelper getNameOwnerCardNumericalValues() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "00000", generateCVC("en"));
    }
    public static CardHelper getNameOwnerCardInSpaces() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "             ", generateCVC("en"));
    }
    public static CardHelper getNameOwnerCardInSymbols() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "******", generateCVC("en"));
    }
    public static CardHelper getWithoutNameOwnerCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "", generateCVC("en"));
    }
    public static CardHelper getNameOwnerCardToOneLetter() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "N", generateCVC("en"));
    }
    public static CardHelper getNameOwnerCardToThirtyFiveCharacters() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), "Nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", generateCVC("en"));
    }

    // CVC

    public static CardHelper getCVCCardInSpaces() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "   ");
    }
    public static CardHelper getCVCCardInText() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "aaa");
    }
    public static CardHelper getCVCCardInSymbols() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "...");
    }
    public static CardHelper getCVCCardToOneValue() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "1");
    }
    public static CardHelper getCVCCardToForeValue() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "1111");
    }
    public static CardHelper getWithoutCVCCard() {
        return new CardHelper(getAPPROVEDCard(), generateMonth(0,"MM"), generateYear(0,"yy"), generateCardOwner("en"), "");
    }
}