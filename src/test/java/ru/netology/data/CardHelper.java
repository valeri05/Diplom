package ru.netology.data;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
    @AllArgsConstructor
    public class CardHelper {

    private String card;
    private String month;
    private String year;
    private String name;
    private String code;

    public static String getAPPROVEDCard() {
        String APPROVED = "4444 4444 4444 4441";
        return APPROVED;
    }
    public static String getDECLINEDCard() {
        String DECLINED = "4444 4444 4444 4442";
        return DECLINED;
    }
}

