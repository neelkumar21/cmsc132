package onlineTest;

import java.io.Serializable;

public class Mcq extends Questions implements Serializable{
	private static final long serialVersionUID = 1L;
	String[] answer;
	double points;

	public Mcq(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points);
		this.answer = answer;
		this.points = points;
	}

	public String[] getAnswer() {
		return answer;
	}

	public double getPoints() {
		return points;
	}

	public String toString() {
		String r = "Correct Answer: [";
		for(int i = 0; i< answer.length; i++) {
			r+= answer[i];
			if(!(answer.length -1 == i)) {
				r+= ", ";
			}
		}
		r += "]";
		return "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + r;
	}
}
