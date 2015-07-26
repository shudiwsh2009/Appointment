package cn.tsinghua.edu.appointment.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {

    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isStudentId(String sId) {
        Pattern p = Pattern.compile("^\\d{9}$");
        Matcher m = p.matcher(sId);
        return m.matches();
    }
}
