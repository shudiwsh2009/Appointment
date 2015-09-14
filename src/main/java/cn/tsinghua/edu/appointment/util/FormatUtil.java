package cn.tsinghua.edu.appointment.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {

    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isStudentId(String sId) {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(sId);
        return m.matches();
    }
}
