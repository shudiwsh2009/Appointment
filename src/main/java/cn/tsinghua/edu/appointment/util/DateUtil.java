package cn.tsinghua.edu.appointment.util;

import cn.tsinghua.edu.appointment.exception.FormatException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    public static final String ZONE_ID = "Asia/Shanghai";
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    public static String getYYMMDD(LocalDateTime d) throws FormatException {
        try {
            return d.format(DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.CHINA));
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static String getHHmm(LocalDateTime d) throws FormatException {
        try {
            return d.format(DateTimeFormatter.ofPattern("HH:mm", Locale.CHINA));
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static String convertDate(LocalDateTime d) throws FormatException {
        try {
            return d.format(DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.CHINA));
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static LocalDateTime convertDate(String s) throws FormatException {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.CHINA));
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static LocalDateTime getLocalNow() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }
}
