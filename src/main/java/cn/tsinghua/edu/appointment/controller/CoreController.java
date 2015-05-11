package cn.tsinghua.edu.appointment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.FormatException;

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
			throws FormatException {
		MongoAccess mongo = new MongoAccess();

		User admin = new User("1", "1");
		admin.setUserType(UserType.ADMIN);
		mongo.saveUser(admin);

		User teacher = new User("2", "2");
		teacher.setUserType(UserType.TEACHER);
		mongo.saveUser(teacher);
	}

}
