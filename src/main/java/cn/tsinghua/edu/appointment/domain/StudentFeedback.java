package cn.tsinghua.edu.appointment.domain;

public class StudentFeedback {

	protected String name = "";
	protected String problem = "";
	protected String choices = "";
	protected String score = "";
	protected String feedback = "";

	public StudentFeedback() {

	}

	public StudentFeedback(String n, String p, String c, String s, String f) {
		name = n;
		problem = p;
		choices = c;
		score = s;
		feedback = f;
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

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
