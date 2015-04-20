package cn.tsinghua.edu.appointment.domain;

public class TeacherFeedback {

	protected String teacherName = "";
	protected String teacherId = "";
	protected String studentName = "";
	protected String problem = "";
	protected String solution = "";
	protected String adviceToCenter = "";

	public TeacherFeedback() {

	}

	public TeacherFeedback(String tn, String ti, String st, String p,
			String so, String ad) {
		teacherName = tn;
		teacherId = ti;
		studentName = st;
		problem = p;
		solution = so;
		adviceToCenter = ad;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getAdviceToCenter() {
		return adviceToCenter;
	}

	public void setAdviceToCenter(String adviceToCenter) {
		this.adviceToCenter = adviceToCenter;
	}

}
