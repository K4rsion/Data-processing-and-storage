package ru.nsu.gurin.Task5.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static int getDayOfWeekByDate(String dateTimeString) {
        String dateString = dateTimeString.substring(0, 10); // Получаем первые 10 символов

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date.getDayOfWeek().getValue() == 7 ? 0 : date.getDayOfWeek().getValue();
    }

    public static String getTimeByDate(String dateTimeString) {
        return dateTimeString.substring(11, 16);
    }

    public static OffsetDateTime extractTime(String dateTimeString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"));

        return offsetDateTime;
    }
}
