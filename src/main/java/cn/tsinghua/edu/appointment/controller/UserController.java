package cn.tsinghua.edu.appointment.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.BasicException;
import cn.tsinghua.edu.appointment.repository.UserRepository;

@Controller
@RequestMapping("user")
public class UserController {
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(@RequestParam("username") String _username,
			@RequestParam("password") String _password,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session, ModelMap model) throws IOException {
		UserRepository ur = new UserRepository();
		try {
			User user = ur.login(_username, _password);
			session.setAttribute("userId", user.getId());
			session.setAttribute("username", _username);
			session.setAttribute("userType", user.getUserType());
			if(user.getUserType() == UserType.ADMIN) {
				response.sendRedirect("/appointment/admin");
			} else if(user.getUserType() == UserType.TEACHER) {
				response.sendRedirect("/appointment/teacher");
			} else {
				response.sendRedirect("/appointment");
			}
		} catch (BasicException e) {
			model.addAttribute("message", e.getInfo());
			response.sendRedirect("/appointment/login");
		}
	}
	
}
