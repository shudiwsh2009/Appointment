package cn.tsinghua.edu.appointment.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String convertDate(Date d) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(d);
	}
	
	public static Date convertDate(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.parse(s);
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.convertDate(new Date()));
	}
}
