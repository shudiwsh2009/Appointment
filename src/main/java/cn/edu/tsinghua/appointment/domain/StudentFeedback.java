package cn.edu.tsinghua.appointment.domain;

public class StudentFeedback {

    protected String name = "";
    protected String problem = "";
    protected String choices = "";
    protected String score = "";
    protected String feedback = "";

    public StudentFeedback() {

    }

    public StudentFeedback(String name, String problem, String choices,
                           String score, String feedback) {
        this.name = name;
        this.problem = problem;
        this.choices = choices;
        this.score = score;
        this.feedback = feedback;
    }

    public boolean isEmpty() {
        return name.equals("") || problem.equals("") || choices.equals("")
                || score.equals("") || feedback.equals("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getChoices() {
        return choices;
    }

    public String getScore() {
        return score;
    }

    public String getFeedback() {
        return feedback;
    }

}
