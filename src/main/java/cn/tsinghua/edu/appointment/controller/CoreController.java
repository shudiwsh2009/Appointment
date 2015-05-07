package cn.tsinghua.edu.appointment.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.util.DateUtil;

@Controller
@RequestMapping("/")
public class CoreController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage(HttpSession session, HttpServletRequest request) {
		return "main";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage(HttpSession session, HttpServletRequest request) {
		return "login";
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String adminPage(HttpSession session, HttpServletRequest request) {
		UserType userType = (UserType) session.getAttribute("userType");
		if (userType == UserType.ADMIN) {
			return "admin";
		}
		return "login";
	}

	@RequestMapping(value = "teacher", method = RequestMethod.GET)
	public String teacherPage(HttpSession session, HttpServletRequest request) {
		UserType userType = (UserType) session.getAttribute("userType");
		if (userType == UserType.TEACHER) {
			return "teacher";
		}
		return "login";
	}

	@RequestMapping(value = "init", method = RequestMethod.GET)
	public void init(HttpSession session, HttpServletRequest request)
			throws ParseException {
		Appointment app1 = new Appointment(
				DateUtil.convertDate("2015-05-10 08:00"),
				DateUtil.convertDate("2015-05-10 09:00"), "李老师");
		Appointment app2 = new Appointment(
				DateUtil.convertDate("2015-05-10 09:00"),
				DateUtil.convertDate("2015-05-10 10:00"), "李老师");
		Appointment app3 = new Appointment(
				DateUtil.convertDate("2015-05-10 13:00"),
				DateUtil.convertDate("2015-05-10 14:00"), "李老师");
		Appointment app4 = new Appointment(
				DateUtil.convertDate("2015-05-10 14:00"),
				DateUtil.convertDate("2015-05-10 15:00"), "李老师");
		MongoAccess mongo = new MongoAccess();
		mongo.saveApp(app1);
		mongo.saveApp(app2);
		mongo.saveApp(app3);
		mongo.saveApp(app4);
	}

}
