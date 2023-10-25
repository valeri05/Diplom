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
    }

