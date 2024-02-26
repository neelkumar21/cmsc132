package onlineTest;

import java.io.Serializable;

public class TrueFalseQ extends Questions implements Serializable{
	private static final long serialVersionUID = 1L;
	boolean answer;
	double points;

	public TrueFalseQ(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points);
		this.answer = answer;
		this.points = points;
	}

	public boolean getAnswer() {
		return answer;
	}

	public double getPoints() {
		return points;
	}

	public String toString() {
		String r = "";
		if (answer) {
			r = "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: True";
			return r;
		}
		r = "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: False";
		return r;
	}
}
