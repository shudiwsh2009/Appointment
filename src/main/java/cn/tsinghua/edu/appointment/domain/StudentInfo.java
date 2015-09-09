package cn.tsinghua.edu.appointment.domain;

public class StudentInfo {

    protected String name = ""; // 姓名
    protected String gender = ""; // 性别
    protected String studentId = ""; // 学号
    protected String school = ""; // 院系
    protected String hometown = ""; // 生源地
    protected String mobile = ""; // 手机
    protected String email = ""; // E-mail
    protected String experience = ""; // 以前是否有过咨询
    protected String problem = ""; // 咨询问题

    public StudentInfo() {

    }

    public StudentInfo(String name, String gender, String studentId,
                       String school, String hometown, String mobile,
                       String email, String experience, String problem) {
        this.name = name;
        this.gender = gender;
        this.studentId = studentId;
        this.school = school;
        this.hometown = hometown;
        this.mobile = mobile;
        this.email = email;
        this.experience = experience;
        this.problem = problem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSchool() {
        return school;
    }

    public String getHometown() {
        return hometown;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getExperience() {
        return experience;
    }

}
