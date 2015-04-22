package cn.tsinghua.edu.appointment.repository;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.domain.Status;
import cn.tsinghua.edu.appointment.domain.StudentFeedback;
import cn.tsinghua.edu.appointment.domain.StudentInfo;
import cn.tsinghua.edu.appointment.domain.TeacherFeedback;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.ActionRejectException;
import cn.tsinghua.edu.appointment.exception.BasicException;
import cn.tsinghua.edu.appointment.exception.EmptyFieldException;
import cn.tsinghua.edu.appointment.exception.FormatException;
import cn.tsinghua.edu.appointment.exception.NoExistException;
import cn.tsinghua.edu.appointment.util.DateUtil;

public class AppointmentRepository {

	private MongoAccess mongo = new MongoAccess();

	/**
	 * 查看前后一周内的所有咨询
	 * 
	 * @return
	 * @throws BasicException
	 */
	public List<Appointment> getAppointmentsBetween() throws BasicException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Calendar fromCal = (Calendar) cal.clone();
		fromCal.add(Calendar.DAY_OF_MONTH, -7);
		Calendar toCal = (Calendar) cal.clone();
		toCal.add(Calendar.DAY_OF_MONTH, 7);
		return mongo.getAppsBetweenDates(fromCal.getTime(), toCal.getTime());
	}

	/**
	 * 学生预约咨询
	 * 
	 * @param appId
	 * @param name
	 * @param gender
	 * @param studentId
	 * @param school
	 * @param hometown
	 * @param mobile
	 * @param email
	 * @param problem
	 * @return
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 * @throws ActionRejectException
	 */
	public Appointment makeAppointment(String appId, String name,
			String gender, String studentId, String school, String hometown,
			String mobile, String email, String experience, String problem)
			throws EmptyFieldException, NoExistException, ActionRejectException {
		if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("咨询已下架");
		} else if (name == null || name.equals("")) {
			throw new EmptyFieldException("姓名为空");
		} else if (gender == null || gender.equals("")) {
			throw new EmptyFieldException("性别为空");
		} else if (studentId == null || studentId.equals("")) {
			throw new EmptyFieldException("学号为空");
		} else if (school == null || school.equals("")) {
			throw new EmptyFieldException("院系为空");
		} else if (hometown == null || hometown.equals("")) {
			throw new EmptyFieldException("生源地为空");
		} else if (mobile == null || mobile.equals("")) {
			throw new EmptyFieldException("手机号为空");
		} else if (email == null || email.equals("")) {
			throw new EmptyFieldException("邮箱为空");
		} else if (experience == null || experience.equals("")) {
			throw new EmptyFieldException("咨询经历为空");
		} else if (problem == null || problem.equals("")) {
			throw new EmptyFieldException("咨询问题为空");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("咨询已下架");
		}
		if (app.getStartTime().compareTo(new Date()) < 0) {
			throw new ActionRejectException("该咨询已过期");
		}
		if (app.getStatus() != Status.AVAILABLE) {
			throw new ActionRejectException("该咨询已被占用");
		}
		StudentInfo studentInfo = new StudentInfo(name, gender, studentId,
				school, hometown, mobile, email, experience, problem);
		app.setStudentInfo(studentInfo);
		app.setStatus(Status.APPOINTED);
		mongo.saveApp(app);
		return app;
	}

	/**
	 * 学生验证学号
	 * 
	 * @param appId
	 * @param studentId
	 * @return
	 * @throws ActionRejectException
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 */
	public boolean studentCheck(String appId, String studentId)
			throws ActionRejectException, EmptyFieldException, NoExistException {
		if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		} else if (studentId == null || studentId.equals("")) {
			throw new ActionRejectException("预约学生不匹配");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		}
		if (app.getStartTime().compareTo(new Date()) > 0) {
			throw new ActionRejectException("咨询未开始，不能反馈");
		}
		if (app.getStatus() == Status.AVAILABLE) {
			throw new ActionRejectException("咨询未被预约");
		}
		if (app.getStudentInfo() != null
				&& app.getStudentInfo().getStudentId().equals(studentId)) {
			return true;
		} else {
			throw new ActionRejectException("预约学生不匹配");
		}
	}

	/**
	 * 学生反馈
	 * 
	 * @param appId
	 * @param studentId
	 * @param name
	 * @param problem
	 * @param choices
	 * @param score
	 * @param feedback
	 * @return
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 * @throws ActionRejectException
	 */
	public Appointment studentFeedback(String appId, String studentId,
			String name, String problem, String[] choices, String score,
			String feedback) throws EmptyFieldException, NoExistException,
			ActionRejectException {
		if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		} else if (studentId == null || studentId.equals("")) {
			throw new EmptyFieldException("预约学号不匹配");
		} else if (name == null || name.equals("")) {
			throw new EmptyFieldException("姓名为空");
		} else if (problem == null || problem.equals("")) {
			throw new EmptyFieldException("咨询问题为空");
		} else if (choices == null || choices.length == 0) {
			throw new EmptyFieldException("选项为空");
		} else if (score == null || score.equals("")) {
			throw new EmptyFieldException("总评为空");
		} else if (feedback == null || feedback.equals("")) {
			throw new EmptyFieldException("反馈为空");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		}
		if (app.getStartTime().compareTo(new Date()) > 0) {
			throw new ActionRejectException("咨询未开始，不能反馈");
		}
		if (app.getStatus() == Status.AVAILABLE) {
			throw new ActionRejectException("咨询未被预约");
		}
		StudentFeedback studentFeedback = new StudentFeedback(name, problem,
				choices, score, feedback);
		app.setStudentFeedback(studentFeedback);
		mongo.saveApp(app);
		return app;
	}

	/**
	 * 咨询师验证学号
	 * 
	 * @param appId
	 * @param studentId
	 * @param userType
	 * @return
	 * @throws ActionRejectException
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 */
	public boolean teacherCheck(String appId, String studentId,
			UserType userType) throws ActionRejectException,
			EmptyFieldException, NoExistException {
		if (userType == null || userType != UserType.TEACHER) {
			throw new ActionRejectException("权限不足");
		} else if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		} else if (studentId == null || studentId.equals("")) {
			throw new ActionRejectException("预约学生不匹配");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		}
		if (app.getStartTime().compareTo(new Date()) > 0) {
			throw new ActionRejectException("咨询未开始，不能反馈");
		}
		if (app.getStatus() == Status.AVAILABLE) {
			throw new ActionRejectException("咨询未被预约");
		}
		if (app.getStudentInfo() != null
				&& app.getStudentInfo().getStudentId().equals(studentId)) {
			return true;
		} else {
			throw new ActionRejectException("预约学生不匹配");
		}
	}

	/**
	 * 咨询师反馈
	 * 
	 * @param appId
	 * @param studentId
	 * @param teacherName
	 * @param teacherId
	 * @param studentName
	 * @param problem
	 * @param solution
	 * @param adviceToCenter
	 * @return
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 * @throws ActionRejectException
	 */
	public Appointment teacherFeedback(String appId, String teacherName,
			String teacherId, String studentName, String problem,
			String solution, String adviceToCenter, UserType userType)
			throws EmptyFieldException, NoExistException, ActionRejectException {
		if (userType == null || userType != UserType.TEACHER) {
			throw new ActionRejectException("权限不足");
		} else if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		} else if (teacherName == null || teacherName.equals("")) {
			throw new EmptyFieldException("咨询师姓名为空");
		} else if (teacherId == null || teacherId.equals("")) {
			throw new EmptyFieldException("咨询师工作证号为空");
		} else if (studentName == null || studentName.equals("")) {
			throw new EmptyFieldException("学生姓名为空");
		} else if (problem == null || problem.equals("")) {
			throw new EmptyFieldException("咨询问题为空");
		} else if (solution == null || solution.equals("")) {
			throw new EmptyFieldException("解决方法为空");
		} else if (adviceToCenter == null || adviceToCenter.equals("")) {
			throw new EmptyFieldException("工作建议为空");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		}
		if (app.getStartTime().compareTo(new Date()) > 0) {
			throw new ActionRejectException("咨询未开始，不能反馈");
		}
		if (app.getStatus() == Status.AVAILABLE) {
			throw new ActionRejectException("咨询未被预约");
		}
		TeacherFeedback teacherFeedback = new TeacherFeedback(teacherName,
				teacherId, studentName, problem, solution, adviceToCenter);
		app.setTeacherFeedback(teacherFeedback);
		mongo.saveApp(app);
		return app;
	}

	/**
	 * 管理员反馈
	 * 
	 * @param appId
	 * @param teacherName
	 * @param teacherId
	 * @param studentName
	 * @param problem
	 * @param solution
	 * @param adviceToCenter
	 * @param userType
	 * @return
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 * @throws ActionRejectException
	 */
	public Appointment adminFeedback(String appId, String teacherName,
			String teacherId, String studentName, String problem,
			String solution, String adviceToCenter, UserType userType)
			throws EmptyFieldException, NoExistException, ActionRejectException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		} else if (teacherName == null || teacherName.equals("")) {
			throw new EmptyFieldException("咨询师姓名为空");
		} else if (teacherId == null || teacherId.equals("")) {
			throw new EmptyFieldException("咨询师工作证号为空");
		} else if (studentName == null || studentName.equals("")) {
			throw new EmptyFieldException("学生姓名为空");
		} else if (problem == null || problem.equals("")) {
			throw new EmptyFieldException("咨询问题为空");
		} else if (solution == null || solution.equals("")) {
			throw new EmptyFieldException("解决方法为空");
		} else if (adviceToCenter == null || adviceToCenter.equals("")) {
			throw new EmptyFieldException("工作建议为空");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		}
		if (app.getStartTime().compareTo(new Date()) > 0) {
			throw new ActionRejectException("咨询未开始，不能反馈");
		}
		if (app.getStatus() == Status.AVAILABLE) {
			throw new ActionRejectException("咨询未被预约");
		}
		TeacherFeedback teacherFeedback = new TeacherFeedback(teacherName,
				teacherId, studentName, problem, solution, adviceToCenter);
		app.setTeacherFeedback(teacherFeedback);
		mongo.saveApp(app);
		return app;
	}

	/**
	 * 管理员增加咨询
	 * 
	 * @param startTime
	 * @param endTime
	 * @param teacher
	 * @param userType
	 * @return
	 * @throws EmptyFieldException
	 * @throws FormatException
	 * @throws ActionRejectException
	 */
	public String[] addAppointment(String startTime, String endTime,
			String teacher, UserType userType) throws EmptyFieldException,
			FormatException, ActionRejectException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (startTime == null || startTime.equals("")) {
			throw new EmptyFieldException("开始时间为空");
		} else if (endTime == null || endTime.equals("")) {
			throw new EmptyFieldException("结束时间为空");
		} else if (teacher == null || teacher.equals("")) {
			throw new EmptyFieldException("咨询师姓名为空");
		}
		try {
			Date start = DateUtil.convertDate(startTime);
			Date end = DateUtil.convertDate(endTime);
			if (start.compareTo(end) >= 1) {
				throw new FormatException("开始时间不能晚于结束时间");
			}
			Appointment newApp = new Appointment(start, end, teacher);
			mongo.saveApp(newApp);
			return new String[] { newApp.getId(), startTime, endTime, teacher };
		} catch (ParseException e) {
			throw new FormatException("日期格式有误");
		}
	}

	/**
	 * 管理员编辑咨询
	 * 
	 * @param appId
	 * @param startTime
	 * @param endTime
	 * @param teacher
	 * @param userType
	 * @return
	 * @throws EmptyFieldException
	 * @throws FormatException
	 * @throws ActionRejectException
	 * @throws NoExistException
	 */
	public String[] editAppointment(String appId, String startTime,
			String endTime, String teacher, UserType userType)
			throws EmptyFieldException, FormatException, ActionRejectException,
			NoExistException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("咨询已下架");
		} else if (startTime == null || startTime.equals("")) {
			throw new EmptyFieldException("开始时间为空");
		} else if (endTime == null || endTime.equals("")) {
			throw new EmptyFieldException("结束时间为空");
		} else if (teacher == null || teacher.equals("")) {
			throw new EmptyFieldException("咨询师姓名为空");
		}
		try {
			Appointment app = mongo.getAppById(appId);
			if (app == null) {
				throw new NoExistException("咨询已下架");
			}
			Date start = DateUtil.convertDate(startTime);
			Date end = DateUtil.convertDate(endTime);
			if (start.compareTo(end) >= 1) {
				throw new FormatException("开始时间不能晚于结束时间");
			}
			app.setStartTime(start);
			app.setEndTime(end);
			app.setTeacher(teacher);
			mongo.saveApp(app);
			return new String[] { app.getId(), startTime, endTime, teacher };
		} catch (ParseException e) {
			throw new FormatException("日期格式有误");
		}
	}

	/**
	 * 管理员删除咨询
	 * 
	 * @param appIds
	 * @param userType
	 * @throws EmptyFieldException
	 * @throws ActionRejectException
	 */
	public void removeAppointment(String[] appIds, UserType userType)
			throws EmptyFieldException, ActionRejectException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appIds == null) {
			throw new EmptyFieldException("咨询参数为空");
		}
		for (String appId : appIds) {
			mongo.removeApp(appId);
		}
	}

	/**
	 * 管理员取消预约
	 * 
	 * @param appIds
	 * @param userType
	 * @throws EmptyFieldException
	 * @throws ActionRejectException
	 */
	public void cancelAppointment(String[] appIds, UserType userType)
			throws EmptyFieldException, ActionRejectException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appIds == null) {
			throw new EmptyFieldException("咨询参数为空");
		}
		for (String appId : appIds) {
			Appointment app = mongo.getAppById(appId);
			if (app == null) {
				continue;
			}
			if (app.getStatus() == Status.APPOINTED
					&& app.getStartTime().compareTo(new Date()) > 0) {
				app.setStatus(Status.AVAILABLE);
				app.setStudentInfo(new StudentInfo());
				app.setStudentFeedback(new StudentFeedback());
				app.setTeacherFeedback(new TeacherFeedback());
				mongo.saveApp(app);
			}
		}
	}

	/**
	 * 管理员查看学生信息
	 * 
	 * @param appId
	 * @param userType
	 * @return
	 * @throws ActionRejectException
	 * @throws EmptyFieldException
	 * @throws NoExistException
	 */
	public Appointment viewAppointment(String appId, UserType userType)
			throws ActionRejectException, EmptyFieldException, NoExistException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appId == null || appId.equals("")) {
			throw new EmptyFieldException("预约已下架");
		}
		Appointment app = mongo.getAppById(appId);
		if (app == null) {
			throw new NoExistException("预约已下架");
		} else if (app.getStatus() == Status.AVAILABLE) {
			throw new NoExistException("咨询暂未被预约");
		}
		return app;
	}

	public String exportAppointments(String appIds, UserType userType)
			throws ActionRejectException, EmptyFieldException {
		if (userType == null || userType != UserType.ADMIN) {
			throw new ActionRejectException("权限不足");
		} else if (appIds == null) {
			throw new EmptyFieldException("咨询参数为空");
		}
		return "";
	}

}
