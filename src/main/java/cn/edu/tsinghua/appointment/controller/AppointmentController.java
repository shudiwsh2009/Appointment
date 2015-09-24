package cn.edu.tsinghua.appointment.controller;

import cn.edu.tsinghua.appointment.domain.Appointment;
import cn.edu.tsinghua.appointment.repository.UserRepository;
import cn.edu.tsinghua.appointment.domain.Status;
import cn.edu.tsinghua.appointment.domain.User;
import cn.edu.tsinghua.appointment.domain.UserType;
import cn.edu.tsinghua.appointment.exception.ActionRejectException;
import cn.edu.tsinghua.appointment.exception.BasicException;
import cn.edu.tsinghua.appointment.exception.NoExistException;
import cn.edu.tsinghua.appointment.repository.AppointmentRepository;
import cn.edu.tsinghua.appointment.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("appointment")
public class AppointmentController {

    /**
     * 查看前后一周内的所有咨询
     */
    @RequestMapping(value = "viewAppointments", method = RequestMethod.GET)
    public void viewAppointments(HttpSession session,
                                 HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        UserType userType = (UserType) session.getAttribute("userType");
        String username = (String) session.getAttribute("username");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<Appointment> appList = ar.getAppointmentsBetween();
            for (Appointment app : appList) {
                if (userType == UserType.TEACHER && !username.equals(app.getTeacherUsername())) {
                    continue;
                }
                if (app.getStatus() == Status.AVAILABLE
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("appId", app.getId());
                object.put("startTime",
                        DateUtil.convertDateTime(app.getStartTime()));
                object.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
                object.put("teacher", app.getTeacher());
                if (userType == UserType.ADMIN) {
                    object.put("teacherId", app.getTeacherUsername());
                    object.put("teacherMobile", app.getTeacherMobile());
                } else if (userType == UserType.TEACHER) {
                    object.put("teacherMobile", app.getTeacherMobile());
                }
                if (app.getStatus() == Status.AVAILABLE) {
                    object.put("status", Status.AVAILABLE.toString());
                } else if (app.getStatus() == Status.APPOINTED
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    object.put("status", Status.FEEDBACK.toString());
                } else {
                    object.put("status", Status.APPOINTED.toString());
                }
                array.put(object);
            }
            result.put("array", array);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 学生查看前后一周内的所有咨询
     */
    @RequestMapping(value = "student/viewAppointments", method = RequestMethod.GET)
    public void studnetViewAppointments(HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<Appointment> appList = ar.getAppointmentsForStudent();
            for (Appointment app : appList) {
                if (app.getStatus() == Status.AVAILABLE
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("appId", app.getId());
                object.put("startTime",
                        DateUtil.convertDateTime(app.getStartTime()));
                object.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
                object.put("teacher", app.getTeacher());
                if (app.getStatus() == Status.AVAILABLE) {
                    object.put("status", Status.AVAILABLE.toString());
                } else if (app.getStatus() == Status.APPOINTED
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    object.put("status", Status.FEEDBACK.toString());
                } else {
                    object.put("status", Status.APPOINTED.toString());
                }
                array.put(object);
            }
            result.put("array", array);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师查看负一周以后的所有咨询
     */
    @RequestMapping(value = "teacher/viewAppointments", method = RequestMethod.GET)
    public void teacherViewAppointments(HttpSession session,
                                        HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        UserRepository ur = new UserRepository();
        UserType userType = (UserType) session.getAttribute("userType");
        String username = (String) session.getAttribute("username");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (userType != UserType.TEACHER) {
                throw new ActionRejectException("权限不足");
            }
            User teacherUser = ur.getUserByUsername(username);
            if (teacherUser == null) {
                throw new NoExistException("咨询师账户失效");
            }
            result.put("teacher", teacherUser.getFullname());
            result.put("teacherMobile", teacherUser.getMobile());
            List<Appointment> appList = ar.getAppointmentsForTeacher();
            for (Appointment app : appList) {
                if (!username.equals(app.getTeacherUsername())) {
                    continue;
                }
                if (app.getStatus() == Status.AVAILABLE
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("appId", app.getId());
                object.put("startTime",
                        DateUtil.convertDateTime(app.getStartTime()));
                object.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
                object.put("teacher", app.getTeacher());
                object.put("teacherMobile", app.getTeacherMobile());
                if (app.getStatus() == Status.AVAILABLE) {
                    object.put("status", Status.AVAILABLE.toString());
                } else if (app.getStatus() == Status.APPOINTED
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    object.put("status", Status.FEEDBACK.toString());
                } else {
                    object.put("status", Status.APPOINTED.toString());
                }
                array.put(object);
            }
            result.put("array", array);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员查看负一周以后的所有咨询
     */
    @RequestMapping(value = "admin/viewAppointments", method = RequestMethod.GET)
    public void adminViewAppointments(HttpSession session,
                                      HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        UserType userType = (UserType) session.getAttribute("userType");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (userType != UserType.ADMIN) {
                throw new ActionRejectException("权限不足");
            }
            List<Appointment> appList = ar.getAppointmentsForTeacher();
            for (Appointment app : appList) {
                if (app.getStatus() == Status.AVAILABLE
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("appId", app.getId());
                object.put("startTime",
                        DateUtil.convertDateTime(app.getStartTime()));
                object.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
                object.put("teacher", app.getTeacher());
                object.put("teacherId", app.getTeacherUsername());
                object.put("teacherMobile", app.getTeacherMobile());
                if (app.getStatus() == Status.AVAILABLE) {
                    object.put("status", Status.AVAILABLE.toString());
                } else if (app.getStatus() == Status.APPOINTED
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    object.put("status", Status.FEEDBACK.toString());
                } else {
                    object.put("status", Status.APPOINTED.toString());
                }
                array.put(object);
            }
            result.put("array", array);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员查看指定日期30天以内的所有咨询
     */
    @RequestMapping(value = "admin/queryAppointments", method = RequestMethod.POST)
    public void adminQueryAppointments(@RequestParam("fromTime") String _fromTime,
                                       HttpSession session,
                                       HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        UserType userType = (UserType) session.getAttribute("userType");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (userType != UserType.ADMIN) {
                throw new ActionRejectException("权限不足");
            }
            List<Appointment> appList = ar.getAppointmentsForAdmin(_fromTime);
            for (Appointment app : appList) {
                if (app.getStatus() == Status.AVAILABLE
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("appId", app.getId());
                object.put("startTime",
                        DateUtil.convertDateTime(app.getStartTime()));
                object.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
                object.put("teacher", app.getTeacher());
                object.put("teacherId", app.getTeacherUsername());
                object.put("teacherMobile", app.getTeacherMobile());
                if (app.getStatus() == Status.AVAILABLE) {
                    object.put("status", Status.AVAILABLE.toString());
                } else if (app.getStatus() == Status.APPOINTED
                        && app.getStartTime().isBefore(DateUtil.getLocalNow())) {
                    object.put("status", Status.FEEDBACK.toString());
                } else {
                    object.put("status", Status.APPOINTED.toString());
                }
                array.put(object);
            }
            result.put("array", array);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 学生预约咨询
     */
    @RequestMapping(value = "makeAppointment", method = RequestMethod.POST)
    public void makeAppointment(@RequestParam("appId") String _appId,
                                @RequestParam("name") String _name,
                                @RequestParam("gender") String _gender,
                                @RequestParam("studentId") String _studentId,
                                @RequestParam("school") String _school,
                                @RequestParam("hometown") String _hometown,
                                @RequestParam("mobile") String _mobile,
                                @RequestParam("email") String _email,
                                @RequestParam("experience") String _experience,
                                @RequestParam("problem") String _problem,
                                HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.makeAppointment(_appId, _name, _gender,
                    _studentId, _school, _hometown, _mobile, _email,
                    _experience, _problem);
            result.put("startTime", DateUtil.convertDateTime(app.getStartTime()));
            result.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
            result.put("teacher", app.getTeacher());
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 学生验证学号
     */
    @RequestMapping(value = "studentCheck", method = RequestMethod.POST)
    public void studentCheck(@RequestParam("appId") String _appId,
                             @RequestParam("studentId") String _studentId,
                             HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.studentCheck(_appId, _studentId);
            result.put("name", app.getStudentFeedback().getName());
            result.put("problem", app.getStudentFeedback().getProblem());
            result.put("choices", app.getStudentFeedback().getChoices());
            result.put("score", app.getStudentFeedback().getScore());
            result.put("feedback", app.getStudentFeedback().getFeedback());
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 学生反馈
     */
    @RequestMapping(value = "studentFeedback", method = RequestMethod.POST)
    public void studentFeedback(@RequestParam("appId") String _appId,
                                @RequestParam("name") String _name,
                                @RequestParam("problem") String _problem,
                                @RequestParam("choices") String _choices,
                                @RequestParam("score") String _score,
                                @RequestParam("feedback") String _feedback,
                                HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.studentFeedback(_appId, _name, _problem, _choices, _score,
                    _feedback);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师增加咨询
     */
    @RequestMapping(value = "teacher/addAppointment", method = RequestMethod.POST)
    public void addAppointmentByTeacher(@RequestParam("startTime") String _startTime,
                                        @RequestParam("endTime") String _endTime,
                                        @RequestParam("teacher") String _teacher,
                                        @RequestParam(value = "teacherMobile", defaultValue = "15210561025") String _teacherMobile,
                                        HttpSession session,
                                        HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment newApp = ar.addAppointmentByTeacher(_startTime, _endTime,
                    _teacher, _teacherMobile, (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("state", "SUCCESS");
            result.put("appId", newApp.getId());
            result.put("startTime", DateUtil.convertDateTime(newApp.getStartTime()));
            result.put("endTime", DateUtil.convertDateTime(newApp.getEndTime()));
            result.put("teacher", newApp.getTeacher());
            result.put("teacherMobile", newApp.getTeacherMobile());
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师编辑咨询
     */
    @RequestMapping(value = "teacher/editAppointment", method = RequestMethod.POST)
    public void editAppointmentByTeacher(@RequestParam("appId") String _appId,
                                         @RequestParam("startTime") String _startTime,
                                         @RequestParam("endTime") String _endTime,
                                         @RequestParam("teacher") String _teacher,
                                         @RequestParam("teacherMobile") String _teacherMobile,
                                         HttpSession session,
                                         HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.editAppointmentByTeacher(_appId, _startTime, _endTime,
                    _teacher, _teacherMobile,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("state", "SUCCESS");
            result.put("appId", app.getId());
            result.put("startTime", DateUtil.convertDateTime(app.getStartTime()));
            result.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
            result.put("teacher", app.getTeacher());
            result.put("teacherMobile", app.getTeacherMobile());
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师删除咨询
     */
    @RequestMapping(value = "teacher/removeAppointment", method = RequestMethod.POST)
    public void removeAppointmentByTeacher(@RequestParam("appIds[]") String[] _appIds,
                                           HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.removeAppointmentByTeacher(_appIds,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师取消预约
     */
    @RequestMapping(value = "teacher/cancelAppointment", method = RequestMethod.POST)
    public void cancelAppointmentByTeacher(@RequestParam("appIds[]") String[] _appIds,
                                           HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.cancelAppointmentByTeacher(_appIds,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师验证学号
     */
    @RequestMapping(value = "teacherCheck", method = RequestMethod.POST)
    public void teacherCheck(@RequestParam("appId") String _appId,
                             HttpSession session,
                             HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.teacherCheck(_appId,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("teacherName", app.getTeacherFeedback().getTeacherName());
            result.put("teacherId", app.getTeacherFeedback().getTeacherId());
            result.put("studentName", app.getTeacherFeedback().getStudentName());
            result.put("problem", app.getTeacherFeedback().getProblem());
            result.put("solution", app.getTeacherFeedback().getSolution());
            result.put("advice", app.getTeacherFeedback().getAdviceToCenter());
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 咨询师反馈
     */
    @RequestMapping(value = "teacherFeedback", method = RequestMethod.POST)
    public void teacherFeedback(@RequestParam("appId") String _appId,
                                @RequestParam("teacherName") String _teacherName,
                                @RequestParam("teacherId") String _teacherId,
                                @RequestParam("studentName") String _studentName,
                                @RequestParam("problem") String _problem,
                                @RequestParam("solution") String _solution,
                                @RequestParam("advice") String _advice, HttpSession session,
                                HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.teacherFeedback(_appId, _teacherName, _teacherId, _studentName,
                    _problem, _solution, _advice,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员拉取反馈
     */
    @RequestMapping(value = "adminCheck", method = RequestMethod.POST)
    public void adminCheck(@RequestParam("appId") String _appId,
                           HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.adminCheck(_appId,
                    (UserType) session.getAttribute("userType"));
            result.put("teacherName", app.getTeacherFeedback().getTeacherName());
            result.put("teacherId", app.getTeacherFeedback().getTeacherId());
            result.put("studentName", app.getTeacherFeedback().getStudentName());
            result.put("problem", app.getTeacherFeedback().getProblem());
            result.put("solution", app.getTeacherFeedback().getSolution());
            result.put("advice", app.getTeacherFeedback().getAdviceToCenter());
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员反馈
     */
    @RequestMapping(value = "adminFeedBack", method = RequestMethod.POST)
    public void adminFeedBack(@RequestParam("appId") String _appId,
                              @RequestParam("teacherName") String _teacherName,
                              @RequestParam("teacherId") String _teacherId,
                              @RequestParam("studentName") String _studentName,
                              @RequestParam("problem") String _problem,
                              @RequestParam("solution") String _solution,
                              @RequestParam("advice") String _advice, HttpSession session,
                              HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.adminFeedback(_appId, _teacherName, _teacherId, _studentName,
                    _problem, _solution, _advice,
                    (UserType) session.getAttribute("userType"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员增加咨询
     */
    @RequestMapping(value = "addAppointment", method = RequestMethod.POST)
    public void addAppointment(@RequestParam("startTime") String _startTime,
                               @RequestParam("endTime") String _endTime,
                               @RequestParam("teacher") String _teacher,
                               @RequestParam(value = "teacherId", defaultValue = "#TEACHER#") String _teacherUsername,
                               @RequestParam(value = "teacherMobile", defaultValue = "15210561025") String _teacherMobile,
                               HttpSession session,
                               HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment newApp = ar.addAppointment(_startTime, _endTime,
                    _teacher, _teacherUsername, _teacherMobile, (UserType) session.getAttribute("userType"));
            result.put("state", "SUCCESS");
            result.put("appId", newApp.getId());
            result.put("startTime", DateUtil.convertDateTime(newApp.getStartTime()));
            result.put("endTime", DateUtil.convertDateTime(newApp.getEndTime()));
            result.put("teacher", newApp.getTeacher());
            result.put("teacherId", newApp.getTeacherUsername());
            result.put("teacherMobile", newApp.getTeacherMobile());
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员编辑咨询
     */
    @RequestMapping(value = "editAppointment", method = RequestMethod.POST)
    public void editAppointment(@RequestParam("appId") String _appId,
                                @RequestParam("startTime") String _startTime,
                                @RequestParam("endTime") String _endTime,
                                @RequestParam("teacher") String _teacher,
                                @RequestParam("teacherId") String _teacherUsername,
                                @RequestParam("teacherMobile") String _teacherMobile,
                                HttpSession session,
                                HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.editAppointment(_appId, _startTime, _endTime,
                    _teacher, _teacherUsername, _teacherMobile,
                    (UserType) session.getAttribute("userType"));
            result.put("state", "SUCCESS");
            result.put("appId", app.getId());
            result.put("startTime", DateUtil.convertDateTime(app.getStartTime()));
            result.put("endTime", DateUtil.convertDateTime(app.getEndTime()));
            result.put("teacher", app.getTeacher());
            result.put("teacherId", app.getTeacherUsername());
            result.put("teacherMobile", app.getTeacherMobile());
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员删除咨询
     */
    @RequestMapping(value = "removeAppointment", method = RequestMethod.POST)
    public void removeAppointment(@RequestParam("appIds[]") String[] _appIds,
                                  HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.removeAppointment(_appIds,
                    (UserType) session.getAttribute("userType"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员取消预约
     */
    @RequestMapping(value = "cancelAppointment", method = RequestMethod.POST)
    public void cancelAppointment(@RequestParam("appIds[]") String[] _appIds,
                                  HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            ar.cancelAppointment(_appIds,
                    (UserType) session.getAttribute("userType"));
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员/咨询师查看学生信息
     */
    @RequestMapping(value = "viewAppointment", method = RequestMethod.POST)
    public void viewAppointment(@RequestParam("appId") String _appId,
                                HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            Appointment app = ar.viewAppointment(_appId,
                    (UserType) session.getAttribute("userType"),
                    (String) session.getAttribute("username"));
            result.put("name", app.getStudentInfo().getName());
            result.put("gender", app.getStudentInfo().getGender());
            result.put("studentId", app.getStudentInfo().getStudentId());
            result.put("school", app.getStudentInfo().getSchool());
            result.put("hometown", app.getStudentInfo().getHometown());
            result.put("mobile", app.getStudentInfo().getMobile());
            result.put("email", app.getStudentInfo().getEmail());
            result.put("experience", app.getStudentInfo().getExperience());
            result.put("problem", app.getStudentInfo().getProblem());
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 管理员导出预约
     */
    @RequestMapping(value = "exportAppointment", method = RequestMethod.POST)
    public void exportAppointment(@RequestParam("appIds[]") String[] _appIds,
                                  HttpSession session, HttpServletResponse response) {
        AppointmentRepository ar = new AppointmentRepository();
        JSONObject result = new JSONObject();
        try {
            String zipUrl = ar.exportAppointment(_appIds,
                    (UserType) session.getAttribute("userType"));
            result.put("url", zipUrl);
            result.put("state", "SUCCESS");
        } catch (BasicException e) {
            result.put("state", "FAILED");
            result.put("message", e.getInfo());
        }

        // send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
