package cn.tsinghua.edu.appointment.test;

import java.text.ParseException;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.util.DateUtil;

public class DataInit {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Appointment app1 = new Appointment(
				DateUtil.convertDate("2015-04-23 08:00"),
				DateUtil.convertDate("2015-04-23 09:00"), "李老师");
		Appointment app2 = new Appointment(
				DateUtil.convertDate("2015-04-23 09:00"),
				DateUtil.convertDate("2015-04-23 10:00"), "李老师");
		Appointment app3 = new Appointment(
				DateUtil.convertDate("2015-04-23 13:00"),
				DateUtil.convertDate("2015-04-23 14:00"), "李老师");
		Appointment app4 = new Appointment(
				DateUtil.convertDate("2015-04-23 14:00"),
				DateUtil.convertDate("2015-04-23 15:00"), "李老师");
		MongoAccess mongo = new MongoAccess();
		mongo.saveApp(app1);
		mongo.saveApp(app2);
		mongo.saveApp(app3);
		mongo.saveApp(app4);
	}

}
