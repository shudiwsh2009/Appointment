package cn.tsinghua.edu.appointment.controller;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.FormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class CoreController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage() {
        return "entry";
    }

    @RequestMapping(value = "entry", method = RequestMethod.GET)
    public String entryPage() {
        return "entry";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "student", method = RequestMethod.GET)
    public String studentPage() {
        return "main";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String adminPage(HttpSession session) {
        UserType userType = (UserType) session.getAttribute("userType");
        if (userType == UserType.ADMIN) {
            return "admin";
        }
        return "login";
    }

    @RequestMapping(value = "teacher", method = RequestMethod.GET)
    public String teacherPage(HttpSession session) {
        UserType userType = (UserType) session.getAttribute("userType");
        if (userType == UserType.TEACHER) {
            return "teacher";
        }
        return "login";
    }

}
