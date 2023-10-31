package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }
    private static final Faker faker = new Faker(new Locale("en"));

  //  public static String generatePlusOneMonth(int shift) {
    // return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    // }
    public static String generateMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }
   // public static String generateMinusOneMonth(int shift) {
     //   return LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM");
    // }
   // public static String generatePlusOneYear(int shift) {
     //   return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
   // }
    public static String generateYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
    }
  //  public static String generateMinusOneYear(int shift) {
  //      return LocalDate.now().minusYears(shift).format(DateTimeFormatter.ofPattern("yy");
  // }
    public static String generateCardOwner(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    public static String generateCVC(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.numerify("###");
    }
}
