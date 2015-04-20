package cn.tsinghua.edu.appointment.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appointment")
public class Appointment {

	@Id
	private String id;
	@Indexed
	protected Date startTime = new Date();
	protected Date endTime = new Date();
	protected Status status = Status.AVAILABLE;
	protected String teacher = "";
	protected StudentInfo studentInfo = new StudentInfo();
	protected StudentFeedback studentFeedback = new StudentFeedback();
	protected TeacherFeedback teacherFeedback = new TeacherFeedback();
	
	public Appointment(Date s, Date e, String t) {
		startTime = s;
		endTime = e;
		teacher = t;
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
