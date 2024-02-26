package onlineTest;

import java.io.Serializable;

public class Questions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int questionNumber;
	String text;
	double points;

	public Questions(int questionNumber, String text, double points) {
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
	}

	public int getQuestionNumber() {
		return this.questionNumber;
	}
	
	public double getPoints() {
		return points;
	}

}
