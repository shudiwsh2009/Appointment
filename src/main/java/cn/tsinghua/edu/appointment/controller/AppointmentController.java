package cn.tsinghua.edu.appointment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.domain.Status;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.BasicException;
import cn.tsinghua.edu.appointment.repository.AppointmentRepository;
import cn.tsinghua.edu.appointment.util.DateUtil;

@Controller
@RequestMapping("appointment")
public class AppointmentController {

	/**
	 * 查看前后一周内的所有咨询
	 * 
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "viewAppointments", method = RequestMethod.GET)
	public void viewAppointments(HttpSession session,
			HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			List<Appointment> appList = ar.getAppointmentsBetween();
			for (Appointment app : appList) {
				if (app.getStatus() == Status.AVAILABLE
						&& app.getStartTime().compareTo(new Date()) < 0) {
					continue;
				}
				JSONObject object = new JSONObject();
				object.put("appId", app.getId());
				object.put("startTime",
						DateUtil.convertDate(app.getStartTime()));
				object.put("endTime", DateUtil.convertDate(app.getEndTime()));
				object.put("teacher", app.getTeacher());
				if (app.getStatus() == Status.AVAILABLE) {
					object.put("status", Status.AVAILABLE.toString());
				} else if (app.getStatus() == Status.APPOINTED
						&& app.getStartTime().compareTo(new Date()) < 0) {
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
	 * 
	 * @param _appId
	 * @param _name
	 * @param _gender
	 * @param _studentId
	 * @param _school
	 * @param _hometown
	 * @param _mobile
	 * @param _email
	 * @param _problem
	 * @param session
	 * @param response
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
			@RequestParam("problem") String _problem, HttpSession session,
			HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		try {
			Appointment app = ar.makeAppointment(_appId, _name, _gender,
					_studentId, _school, _hometown, _mobile, _email,
					_experience, _problem);
			result.put("startTime", DateUtil.convertDate(app.getStartTime()));
			result.put("endTime", DateUtil.convertDate(app.getEndTime()));
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
	 * 
	 * @param _appId
	 * @param _studentId
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "studentCheck", method = RequestMethod.POST)
	public void studentCheck(@RequestParam("appId") String _appId,
			@RequestParam("studentId") String _studentId, HttpSession session,
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
	 * 
	 * @param _appId
	 * @param _studentId
	 * @param _name
	 * @param _problem
	 * @param _choices
	 * @param _score
	 * @param _feedback
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "studentFeedback", method = RequestMethod.POST)
	public void studentFeedback(@RequestParam("appId") String _appId,
			@RequestParam("name") String _name,
			@RequestParam("problem") String _problem,
			@RequestParam("choices") String _choices,
			@RequestParam("score") String _score,
			@RequestParam("feedback") String _feedback, HttpSession session,
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
	 * 咨询师验证学号
	 * 
	 * @param _appId
	 * @param _studentId
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "teacherCheck", method = RequestMethod.POST)
	public void teacherCheck(@RequestParam("appId") String _appId,
			@RequestParam("studentId") String _studentId, HttpSession session,
			HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		try {
			Appointment app = ar.teacherCheck(_appId, _studentId,
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
	 * 咨询师反馈
	 * 
	 * @param _appId
	 * @param _studentId
	 * @param _teacherName
	 * @param _teacherId
	 * @param _studentName
	 * @param _problem
	 * @param _solution
	 * @param _advice
	 * @param session
	 * @param response
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
	
	@RequestMapping(value = "adminCheck", method = RequestMethod.POST)
	public void adminCheck(@RequestParam("appId") String _appId,
			HttpSession session,
			HttpServletResponse response) {
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
	 * 
	 * @param _appId
	 * @param _teacherName
	 * @param _teacherId
	 * @param _studentName
	 * @param _problem
	 * @param _solution
	 * @param _advice
	 * @param session
	 * @param response
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
	 * 
	 * @param _startTime
	 * @param _endTime
	 * @param _teacher
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "addAppointment", method = RequestMethod.POST)
	public void addAppointment(@RequestParam("startTime") String _startTime,
			@RequestParam("endTime") String _endTime,
			@RequestParam("teacher") String _teacher, HttpSession session,
			HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		try {
			String[] addResult = ar.addAppointment(_startTime, _endTime,
					_teacher, (UserType) session.getAttribute("userType"));
			result.put("state", "SUCCESS");
			result.put("appId", addResult[0]);
			result.put("startTime", addResult[1]);
			result.put("endTime", addResult[2]);
			result.put("teacher", addResult[3]);
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
	 * 
	 * @param _appId
	 * @param _startTime
	 * @param _endTime
	 * @param _teacher
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "editAppointment", method = RequestMethod.POST)
	public void editAppointment(@RequestParam("appId") String _appId,
			@RequestParam("startTime") String _startTime,
			@RequestParam("endTime") String _endTime,
			@RequestParam("teacher") String _teacher, HttpSession session,
			HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		try {
			String[] editResult = ar.editAppointment(_appId, _startTime,
					_endTime, _teacher,
					(UserType) session.getAttribute("userType"));
			result.put("state", "SUCCESS");
			result.put("appId", editResult[0]);
			result.put("startTime", editResult[1]);
			result.put("endTime", editResult[2]);
			result.put("teacher", editResult[3]);
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
	 * 
	 * @param _appIds
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "removeAppointment", method = RequestMethod.POST)
	public void removeAppointment(@RequestParam("appIds") String[] _appIds,
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
	 * 
	 * @param _appIds
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "cancelAppointment", method = RequestMethod.POST)
	public void cancelAppointment(@RequestParam("appIds") String[] _appIds,
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
	 * 管理员查看学生信息
	 * 
	 * @param _appId
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "viewAppointment", method = RequestMethod.POST)
	public void viewAppointment(@RequestParam("appId") String _appId,
			HttpSession session, HttpServletResponse response) {
		AppointmentRepository ar = new AppointmentRepository();
		JSONObject result = new JSONObject();
		try {
			Appointment app = ar.viewAppointment(_appId,
					(UserType) session.getAttribute("userType"));
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

}
