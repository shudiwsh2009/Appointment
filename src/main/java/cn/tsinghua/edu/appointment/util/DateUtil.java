package cn.tsinghua.edu.appointment.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    public static final String ZONE_ID = "Asia/Shanghai";
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    public static String getYYMMDD(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.CHINA));
    }

    public static String getHHmm(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofPattern("HH:mm", Locale.CHINA));
    }

    public static String convertDate(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.CHINA));
    }

    public static LocalDateTime convertDate(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.CHINA));
    }

    public static LocalDateTime getLocalNow() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }
}
