package cn.tsinghua.edu.appointment.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.tsinghua.edu.appointment.exception.FormatException;

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

	public static String exportDate(Date d) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd HHmm");
		return df.format(d);
	}
}
