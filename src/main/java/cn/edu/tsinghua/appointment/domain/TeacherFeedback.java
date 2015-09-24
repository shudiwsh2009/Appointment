package cn.edu.tsinghua.appointment.domain;

public class TeacherFeedback {

    protected String teacherName = "";
    protected String teacherId = "";
    protected String studentName = "";
    protected String problem = "";
    protected String solution = "";
    protected String adviceToCenter = "";

    public TeacherFeedback() {

    }

    public TeacherFeedback(String teacherName, String teacherId,
                           String studentName, String problem,
                           String solution, String adviceToCenter) {
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.studentName = studentName;
        this.problem = problem;
        this.solution = solution;
        this.adviceToCenter = adviceToCenter;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getStudentName() {
        return studentName;
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

    public String getAdviceToCenter() {
        return adviceToCenter;
    }

}
