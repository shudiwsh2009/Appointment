package cn.tsinghua.edu.appointment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "appointment")
public class Appointment {

    @Id
    private String id;
    @Indexed
    protected Date startTime = new Date();
    protected Date endTime = new Date();
    protected Status status = Status.AVAILABLE;
    protected String teacher = "";
    @Indexed
    protected String teacherUsername = "";
    protected String teacherMobile = "";
    protected StudentInfo studentInfo = new StudentInfo();
    protected StudentFeedback studentFeedback = new StudentFeedback();
    protected TeacherFeedback teacherFeedback = new TeacherFeedback();

    public Appointment() {

    }

    public Appointment(Date startTime, Date endTime, String teacher,
                       String teacherUsername, String teacherMobile) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.teacherUsername = teacherUsername;
        this.teacherMobile = teacherMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public StudentFeedback getStudentFeedback() {
        return studentFeedback;
    }

    public void setStudentFeedback(StudentFeedback studentFeedback) {
        this.studentFeedback = studentFeedback;
    }

    public TeacherFeedback getTeacherFeedback() {
        return teacherFeedback;
    }

    public void setTeacherFeedback(TeacherFeedback teacherFeedback) {
        this.teacherFeedback = teacherFeedback;
    }
}
