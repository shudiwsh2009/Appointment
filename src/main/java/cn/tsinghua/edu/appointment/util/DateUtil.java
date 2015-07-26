package cn.tsinghua.edu.appointment.util;

import cn.tsinghua.edu.appointment.exception.FormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String convertDate(Date d) throws FormatException {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return df.format(d);
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static Date convertDate(String s) throws FormatException {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return df.parse(s);
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }

    public static String getYYMMDD(Date d) throws FormatException {
        try {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            return df.format(d);
        } catch (Exception e) {
            throw new FormatException("日期格式错误");
        }
    }
}
