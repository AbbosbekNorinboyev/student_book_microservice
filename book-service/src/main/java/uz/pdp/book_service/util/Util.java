package uz.pdp.book_service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String localDateTimeFormatter(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

        return localDateTime
                .atZone(ZoneId.of("UTC+5"))
                .toLocalDateTime()
                .format(formatter);
    }
}
