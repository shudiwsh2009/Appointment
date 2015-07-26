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
