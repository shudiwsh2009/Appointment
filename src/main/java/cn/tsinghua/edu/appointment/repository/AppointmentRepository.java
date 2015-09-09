package cn.tsinghua.edu.appointment.repository;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.*;
import cn.tsinghua.edu.appointment.exception.*;
import cn.tsinghua.edu.appointment.sms.SMS;
import cn.tsinghua.edu.appointment.util.DateUtil;
import cn.tsinghua.edu.appointment.util.ExcelUtil;
import cn.tsinghua.edu.appointment.util.FormatUtil;
import cn.tsinghua.edu.appointment.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private MongoAccess mongo = new MongoAccess();

    /**
     * 查看前后一周内的所有咨询
     */
    public List<Appointment> getAppointmentsBetween() throws BasicException {
        LocalDateTime now = DateUtil.getLocalNow();
        LocalDateTime from = now.minusDays(7L);
        LocalDateTime to = now.plusDays(7L);
        return mongo.getAppsBetweenDates(from, to);
    }

    /**
     * 学生预约咨询
     */
    public Appointment makeAppointment(String appId, String name,
                                       String gender, String studentId, String school, String hometown,
                                       String mobile, String email, String experience, String problem)
            throws EmptyFieldException, NoExistException, ActionRejectException, FormatException {
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
        } else if (!FormatUtil.isStudentId(studentId)) {
            throw new FormatException("学号不正确");
        } else if (!FormatUtil.isMobile(mobile)) {
            throw new FormatException("手机号不正确");
        } else if (!FormatUtil.isEmail(email)) {
            throw new FormatException("邮箱不正确");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("咨询已下架");
        }
        if (app.getStartTime().isBefore(DateUtil.getLocalNow())) {
            throw new ActionRejectException("该咨询已过期");
        }
        if (app.getStatus() != Status.AVAILABLE) {
            throw new ActionRejectException("该咨询已被预约");
        }
        StudentInfo studentInfo = new StudentInfo(name, gender, studentId,
                school, hometown, mobile, email, experience, problem);
        app.setStudentInfo(studentInfo);
        app.setStatus(Status.APPOINTED);
        mongo.saveApp(app);

        //double check
        Appointment checkApp = mongo.getAppById(appId);
        if (app.getStudentInfo().getMobile().equals(mobile)
                && app.getStatus() == Status.APPOINTED) {
            SMS.sendSuccessSMS(checkApp);
        }
        return app;
    }

    /**
     * 学生验证学号
     */
    public Appointment studentCheck(String appId, String studentId)
            throws ActionRejectException, EmptyFieldException, NoExistException, FormatException {
        if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("预约已下架");
        } else if (studentId == null || studentId.equals("")) {
            throw new ActionRejectException("学号不正确");
        } else if (!FormatUtil.isStudentId(studentId)) {
            throw new FormatException("学号不正确");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("预约已下架");
        }
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
            throw new ActionRejectException("咨询未开始，不能反馈");
        }
        if (app.getStatus() == Status.AVAILABLE) {
            throw new ActionRejectException("咨询未被预约");
        }
        if (app.getStudentInfo() != null
                && app.getStudentInfo().getStudentId().equals(studentId)) {
            return app;
        } else {
            throw new ActionRejectException("预约学生不匹配");
        }
    }

    /**
     * 学生反馈
     */
    public Appointment studentFeedback(String appId, String name,
                                       String problem, String choices, String score, String feedback)
            throws EmptyFieldException, NoExistException, ActionRejectException {
        if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("预约已下架");
        } else if (name == null || name.equals("")) {
            throw new EmptyFieldException("姓名为空");
        } else if (problem == null || problem.equals("")) {
            throw new EmptyFieldException("咨询问题为空");
        } else if (choices == null || choices.equals("")) {
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
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
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
     * 咨询师添加咨询
     */
    public Appointment addAppointmentByTeacher(String startTime, String endTime,
                                               String teacher, String teacherMobile,
                                               UserType userType, String username) throws EmptyFieldException,
            FormatException, ActionRejectException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (startTime == null || startTime.equals("")) {
            throw new EmptyFieldException("开始时间为空");
        } else if (endTime == null || endTime.equals("")) {
            throw new EmptyFieldException("结束时间为空");
        } else if (teacher == null || teacher.equals("")) {
            throw new EmptyFieldException("咨询师姓名为空");
        } else if (teacherMobile == null || teacherMobile.equals("")) {
            throw new EmptyFieldException("咨询师手机号为空");
        } else if (!FormatUtil.isMobile(teacherMobile)) {
            throw new FormatException("咨询师手机号不正确");
        }
        LocalDateTime start = DateUtil.convertDate(startTime);
        LocalDateTime end = DateUtil.convertDate(endTime);
        if (start.isAfter(end)) {
            throw new FormatException("开始时间不能晚于结束时间");
        }
        Appointment newApp = new Appointment(start, end, teacher, username, teacherMobile);
        mongo.saveApp(newApp);
        return newApp;
    }

    /**
     * 咨询师编辑咨询
     */
    public Appointment editAppointmentByTeacher(String appId, String startTime, String endTime,
                                                String teacher, String teacherMobile,
                                                UserType userType, String username)
            throws EmptyFieldException, FormatException, ActionRejectException,
            NoExistException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("咨询已下架");
        } else if (startTime == null || startTime.equals("")) {
            throw new EmptyFieldException("开始时间为空");
        } else if (endTime == null || endTime.equals("")) {
            throw new EmptyFieldException("结束时间为空");
        } else if (teacher == null || teacher.equals("")) {
            throw new EmptyFieldException("咨询师姓名为空");
        } else if (teacherMobile == null || teacherMobile.equals("")) {
            throw new EmptyFieldException("咨询师手机号为空");
        } else if (!FormatUtil.isMobile(teacherMobile)) {
            throw new FormatException("咨询师手机号不正确");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("咨询已下架");
        }
        if (app.getStatus() == Status.APPOINTED) {
            throw new ActionRejectException("不能编辑已预约的咨询");
        }
        LocalDateTime start = DateUtil.convertDate(startTime);
        LocalDateTime end = DateUtil.convertDate(endTime);
        if (start.isAfter(end)) {
            throw new FormatException("开始时间不能晚于结束时间");
        }
        if (end.isBefore(DateUtil.getLocalNow())) {
            throw new ActionRejectException("不能编辑已过期咨询");
        }
        app.setStartTime(start);
        app.setEndTime(end);
        app.setTeacher(teacher);
        app.setTeacherMobile(teacherMobile);
        mongo.saveApp(app);
        return app;
    }

    /**
     * 咨询师删除咨询
     */
    public void removeAppointmentByTeacher(String[] appIds, UserType userType, String username)
            throws EmptyFieldException, ActionRejectException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (appIds == null) {
            throw new EmptyFieldException("咨询参数为空");
        }
        for (String appId : appIds) {
            Appointment app = mongo.getAppById(appId);
            if (app != null && app.getTeacherUsername().equals(username)) {
                mongo.removeApp(appId);
            }
        }
    }

    /**
     * 咨询师取消预约
     */
    public void cancelAppointmentByTeacher(String[] appIds, UserType userType, String username)
            throws EmptyFieldException, ActionRejectException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (appIds == null) {
            throw new EmptyFieldException("咨询参数为空");
        }
        for (String appId : appIds) {
            Appointment app = mongo.getAppById(appId);
            if (app == null || !app.getTeacherUsername().equals(username)) {
                continue;
            }
            if (app.getStatus() == Status.APPOINTED
                    && app.getStartTime().isAfter(DateUtil.getLocalNow())) {
                app.setStatus(Status.AVAILABLE);
                app.setStudentInfo(new StudentInfo());
                app.setStudentFeedback(new StudentFeedback());
                app.setTeacherFeedback(new TeacherFeedback());
                mongo.saveApp(app);
            }
        }
    }


    /**
     * 咨询师验证学号
     */
    public Appointment teacherCheck(String appId, UserType userType, String username) throws ActionRejectException,
            EmptyFieldException, NoExistException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (username == null || username.equals("")) {
            throw new ActionRejectException("权限不足");
        } else if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("预约已下架");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("预约已下架");
        }
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
            throw new ActionRejectException("咨询未开始，不能反馈");
        }
        if (app.getStatus() == Status.AVAILABLE) {
            throw new ActionRejectException("咨询未被预约");
        }
        if (!app.getTeacherUsername().equals(username)) {
            throw new ActionRejectException("预约不匹配");
        }
        if (app.getStudentInfo() != null) {
            return app;
        } else {
            throw new ActionRejectException("预约学生不匹配");
        }
    }

    /**
     * 咨询师反馈
     */
    public Appointment teacherFeedback(String appId, String teacherName,
                                       String teacherId, String studentName, String problem,
                                       String solution, String adviceToCenter,
                                       UserType userType, String username)
            throws EmptyFieldException, NoExistException, ActionRejectException {
        if (userType == null || userType != UserType.TEACHER) {
            throw new ActionRejectException("权限不足");
        } else if (username == null || username.equals("")) {
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
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
            throw new ActionRejectException("咨询未开始，不能反馈");
        }
        if (app.getStatus() == Status.AVAILABLE) {
            throw new ActionRejectException("咨询未被预约");
        }
        if (!username.equals(teacherId) || !username.equals(app.getTeacherUsername())) {
            throw new ActionRejectException("预约不匹配");
        }
        TeacherFeedback teacherFeedback = new TeacherFeedback(teacherName,
                teacherId, studentName, problem, solution, adviceToCenter);
        app.setTeacherFeedback(teacherFeedback);
        mongo.saveApp(app);
        return app;
    }

    /**
     * 管理员拉取反馈
     */
    public Appointment adminCheck(String appId, UserType userType)
            throws ActionRejectException, EmptyFieldException, NoExistException {
        if (userType == null || userType != UserType.ADMIN) {
            throw new ActionRejectException("权限不足");
        } else if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("预约已下架");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("预约已下架");
        }
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
            throw new ActionRejectException("咨询未开始，不能反馈");
        }
        if (app.getStatus() == Status.AVAILABLE) {
            throw new ActionRejectException("咨询未被预约");
        }
        if (app.getStudentInfo() != null) {
            return app;
        } else {
            throw new ActionRejectException("预约学生不匹配");
        }
    }

    /**
     * 管理员反馈
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
        if (app.getStartTime().isAfter(DateUtil.getLocalNow())) {
            throw new ActionRejectException("咨询未开始，不能反馈");
        }
        if (app.getStatus() == Status.AVAILABLE) {
            throw new ActionRejectException("咨询未被预约");
        }
        if (!teacherId.equals(app.getTeacherUsername())) {
            throw new ActionRejectException("预约不匹配");
        }
        TeacherFeedback teacherFeedback = new TeacherFeedback(teacherName,
                teacherId, studentName, problem, solution, adviceToCenter);
        app.setTeacherFeedback(teacherFeedback);
        mongo.saveApp(app);
        return app;
    }

    /**
     * 管理员增加咨询
     */
    public Appointment addAppointment(String startTime, String endTime,
                                      String teacher, String teacherUsername, String teacherMobile,
                                      UserType userType) throws EmptyFieldException,
            FormatException, ActionRejectException {
        if (userType == null || userType != UserType.ADMIN) {
            throw new ActionRejectException("权限不足");
        } else if (startTime == null || startTime.equals("")) {
            throw new EmptyFieldException("开始时间为空");
        } else if (endTime == null || endTime.equals("")) {
            throw new EmptyFieldException("结束时间为空");
        } else if (teacher == null || teacher.equals("")) {
            throw new EmptyFieldException("咨询师姓名为空");
        } else if (teacherUsername == null || teacherUsername.equals("")) {
            throw new EmptyFieldException("咨询师工号为空");
        } else if (teacherMobile == null || teacherMobile.equals("")) {
            throw new EmptyFieldException("咨询师手机号为空");
        } else if (!FormatUtil.isMobile(teacherMobile)) {
            throw new FormatException("咨询师手机号不正确");
        }
        LocalDateTime start = DateUtil.convertDate(startTime);
        LocalDateTime end = DateUtil.convertDate(endTime);
        if (start.isAfter(end)) {
            throw new FormatException("开始时间不能晚于结束时间");
        }
        if (!mongo.existUserByUsername(teacherUsername)) {
            mongo.addUser(teacherUsername, UserRepository.TEACHER_DEFAULT_PASSWORD, UserType.TEACHER);
        }
        Appointment newApp = new Appointment(start, end, teacher, teacherUsername, teacherMobile);
        mongo.saveApp(newApp);
        return newApp;
    }

    /**
     * 管理员编辑咨询
     */
    public Appointment editAppointment(String appId, String startTime, String endTime,
                                       String teacher, String teacherUsername, String teacherMobile,
                                       UserType userType)
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
        } else if (teacherUsername == null || teacherUsername.equals("")) {
            throw new EmptyFieldException("咨询师工号为空");
        } else if (teacherMobile == null || teacherMobile.equals("")) {
            throw new EmptyFieldException("咨询师手机号为空");
        } else if (!FormatUtil.isMobile(teacherMobile)) {
            throw new FormatException("咨询师手机号不正确");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("咨询已下架");
        }
        if (app.getStatus() == Status.APPOINTED) {
            throw new ActionRejectException("不能编辑已预约的咨询");
        }
        LocalDateTime start = DateUtil.convertDate(startTime);
        LocalDateTime end = DateUtil.convertDate(endTime);
        if (start.isAfter(end)) {
            throw new FormatException("开始时间不能晚于结束时间");
        }
        if (end.isBefore(DateUtil.getLocalNow())) {
            throw new ActionRejectException("不能编辑已过期咨询");
        }
        if (!mongo.existUserByUsername(teacherUsername)) {
            mongo.addUser(teacherUsername, UserRepository.TEACHER_DEFAULT_PASSWORD, UserType.TEACHER);
        }
        app.setStartTime(start);
        app.setEndTime(end);
        app.setTeacher(teacher);
        app.setTeacherUsername(teacherUsername);
        app.setTeacherMobile(teacherMobile);
        mongo.saveApp(app);
        return app;
    }

    /**
     * 管理员删除咨询
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
                    && app.getStartTime().isAfter(DateUtil.getLocalNow())) {
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
     */
    public Appointment viewAppointment(String appId, UserType userType, String username)
            throws ActionRejectException, EmptyFieldException, NoExistException {
        if (userType == null || (userType != UserType.ADMIN && userType != UserType.TEACHER)) {
            throw new ActionRejectException("权限不足");
        } else if (username == null || username.equals("")) {
            throw new ActionRejectException("权限不足");
        } else if (appId == null || appId.equals("")) {
            throw new EmptyFieldException("预约已下架");
        }
        Appointment app = mongo.getAppById(appId);
        if (app == null) {
            throw new NoExistException("预约已下架");
        } else if (app.getStatus() == Status.AVAILABLE) {
            throw new NoExistException("咨询暂未被预约");
        } else if (userType == UserType.TEACHER && !username.equals(app.getTeacherUsername())) {
            throw new ActionRejectException("预约不匹配");
        }
        return app;
    }

    /**
     * 管理员导出咨询
     */
    public String exportAppointment(String[] appIds, UserType userType)
            throws BasicException {
        if (userType == null || userType != UserType.ADMIN) {
            throw new ActionRejectException("权限不足");
        } else if (appIds == null) {
            throw new EmptyFieldException("咨询参数为空");
        }
        List<Appointment> appointments = new ArrayList<>();
        for (String appId : appIds) {
            Appointment app = mongo.getAppById(appId);
            if (app == null) {
                continue;
            }
            appointments.add(app);
        }
        String filename = "export_" + TimeUtil.getCurrentYMD()
                + ExcelUtil.EXCEL_SUFFIX;
        if (appointments.isEmpty()) {
            return "";
        } else {
            ExcelUtil.exportToExcel(appointments, filename);
            return ExcelUtil.EXPORT_PREFIX + filename;
        }
    }

}
