package onlineTest;

import java.io.Serializable;
import java.util.HashMap;

public class Students implements Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	HashMap<Integer, Double> score = new HashMap<Integer, Double>();
    HashMap<String, HashMap<Integer, Boolean>> answers = new HashMap<String, HashMap<Integer, Boolean>>();


	public Students(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void addExamScore(int examId, double grade) {
		score.put(examId, grade);
	}
	
	public double getScore(int id) {
		return (score.get(id));
	}
	
//	public String getGradingReport(String studentName, int exam, HashMap s) {
//		String r = "";
//		double score = 0.0;
//		for(int i = 0; i < s.get(studentName).get(exam).size()) {
//			
//		}
//		return r;
//	}
}
