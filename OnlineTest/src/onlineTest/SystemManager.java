package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class SystemManager implements Manager, Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<Integer, String> exams = new HashMap<Integer, String>();
	HashMap<Integer, ArrayList<Questions>> questions = new HashMap<Integer, ArrayList<Questions>>();
	HashMap<String, HashMap<Integer, ArrayList<Double>>> scores = new HashMap<String, HashMap<Integer, ArrayList<Double>>>();
	ArrayList<Students> students = new ArrayList<>();
	HashMap<Double, String> cutoffs = new HashMap<Double, String>();

	public boolean addExam(int examId, String title) {
		if (exams.containsKey(examId)) {
			return false;
		} else {
			exams.put(examId, title);
			questions.put(examId, new ArrayList<Questions>());
			return true;
		}
	}

	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
	    if (exams.containsKey(examId)) {
	        for (Questions q : questions.get(examId)) {
	            if (q.getQuestionNumber() == questionNumber) {
	                questions.get(examId).remove(q);
	            }
	        }
	        questions.get(examId).add(new TrueFalseQ(questionNumber, text, points, answer));
	    }
	}

	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
	    if (exams.containsKey(examId)) {
	        for (Questions q : questions.get(examId)) {
	            if (q.getQuestionNumber() == questionNumber) {
	                questions.get(examId).remove(q);
	            }
	        }
	        questions.get(examId).add(new Mcq(questionNumber, text, points, answer));
	    }
	}

	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
	    if (exams.containsKey(examId)) {
	        for (Questions q : questions.get(examId)) {
	            if (q.getQuestionNumber() == questionNumber) {
	                questions.get(examId).remove(q);
	            }
	        }
	        questions.get(examId).add(new Fbq(questionNumber, text, points, answer));
	    }
	}

//	public boolean addStudent(String name) {
//		for(int i = 0; i < students.size(); i ++) {
//			if(students.get(i).getName().equals(name)) {
//				return false;
//			}
//		}
//		students.add(new Students(name));
//		return true;
//	}

	public boolean addStudent(String name) {
		try {
			scores.put(name, new HashMap<Integer, ArrayList<Double>>());
			students.add(new Students(name));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		if (!scores.containsKey(studentName)) {
			scores.put(studentName, new HashMap<Integer, ArrayList<Double>>());
		}
		if (!scores.get(studentName).containsKey(examId)) {
			scores.get(studentName).put(examId, new ArrayList<Double>());
		}
		for (Questions question : questions.get(examId)) {
			if (question.getQuestionNumber() == questionNumber && question instanceof TrueFalseQ) {
				TrueFalseQ trueFalseQ = (TrueFalseQ) question;
				if (answer == trueFalseQ.getAnswer()) {
					scores.get(studentName).get(examId).add(trueFalseQ.getPoints());
				} else {
					scores.get(studentName).get(examId).add(0.0);
				}
				return;
			}
		}
	}

	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if (!scores.containsKey(studentName) || !scores.get(studentName).containsKey(examId)) {
			scores.put(studentName, new HashMap<Integer, ArrayList<Double>>());
		}
		if (!scores.get(studentName).containsKey(examId)) {
			scores.get(studentName).put(examId, new ArrayList<Double>());
		}

		for (Questions question : questions.get(examId)) {
			if (question.getQuestionNumber() == questionNumber && question instanceof Mcq) {
				Mcq mcq = (Mcq) question;
				if (Arrays.equals(answer, mcq.getAnswer())) {
					scores.get(studentName).get(examId).add(mcq.getPoints());
				} else {
					scores.get(studentName).get(examId).add(0.0);
				}
				return;
			}
		}
	}

	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if (!scores.containsKey(studentName)) {
			scores.put(studentName, new HashMap<Integer, ArrayList<Double>>());
		}
		if (!scores.get(studentName).containsKey(examId)) {
			scores.get(studentName).put(examId, new ArrayList<Double>());
		}

		for (Questions question : questions.get(examId)) {
			if (question.getQuestionNumber() == questionNumber && question instanceof Fbq) {
				Fbq fbq = (Fbq) question;
				ArrayList<Double> pointsList = scores.get(studentName).get(examId);
				double totalPoints = 0.0;
				for (String s : answer) {
					for (String answerBlank : fbq.getAnswer()) {
						if (s.equals(answerBlank)) {
							totalPoints += fbq.getPoints() / fbq.getAnswer().length;
							break;
						}
					}
				}
				pointsList.add(totalPoints);
				return;
			}
		}
	}

	public String getKey(int examId) {
		String key = "";
		ArrayList<Questions> examQuestions = questions.get(examId);
		for (int i = 0; i < examQuestions.size(); i++) {
			key += examQuestions.get(i).toString() + "\n";
		}
		return key.toString();
	}

	public double getExamScore(String studentName, int examId) {
		ArrayList<Double> points = scores.get(studentName).get(examId);
		return getScoreHelper(points, points.size() - 1);
	}

	private double getScoreHelper(ArrayList<Double> points, int index) {
		if (index == 0) {
			return points.get(0);
		} else {
			double score = getScoreHelper(points, index - 1);
			double r = points.get(index) + score;
			return r;
		}
	}

	public String getGradingReport(String studentName, int examId) {
		ArrayList<Double> points = scores.get(studentName).get(examId);
		ArrayList<Questions> qs = questions.get(examId);
		String r = "";
		double total = 0;
		for (int i = 0; i < qs.size(); i++) {
			Questions q = qs.get(i);
			double point = points.get(i);
			r += "Question #" + q.getQuestionNumber() + " " + point;
			r += "  points out of " + q.getPoints();
			r += "\n";
			total += q.getPoints();
		}
		r += "Final Score: " + getExamScore(studentName, examId) + " out of " + total;
		return r;
	}

	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		int index = 0;
		for (double cutoff : cutoffs) {
			String letterGrade = letterGrades[index];
			index++;
			this.cutoffs.put(cutoff, letterGrade);
		}
	}

	public double getCourseNumericGrade(String studentName) {
		double points = 0.0;
		int numberOf = 0;
		double examScore;
		double maxPoints;

		for (int examId : scores.get(studentName).keySet()) {
			examScore = getExamScore(studentName, examId);
			maxPoints = 0.0;
			for (Questions question : questions.get(examId)) {
				maxPoints += question.getPoints();
			}
			points += examScore * 100 / maxPoints;
			numberOf++;
		}

		return points / numberOf;
	}

	public String getCourseLetterGrade(String studentName) {
		TreeMap<Double, String> sorted = new TreeMap<>(cutoffs);
		String grade = "";
		for (Double d : sorted.keySet()) {
			if (getCourseNumericGrade(studentName) >= d) {
				grade = sorted.get(d);
			}
		}
		return grade;
	}

	public String getCourseGrades() {
		String grades = "";
		ArrayList<String> studentList = new ArrayList<>(scores.keySet());
		Collections.sort(studentList);
		int studentNumber = 0;

		while (studentNumber < studentList.size()) {
			double number = getCourseNumericGrade(studentList.get(studentNumber));
			String letter = getCourseLetterGrade(studentList.get(studentNumber));

			grades += studentList.get(studentNumber) + " " + number + " " + letter + "\n";
			studentNumber++;
		}

		return grades;
	}

	public double getMaxScore(int examId) {
		double maxScore = 0;
		double currentScore = 0;
		for (String name : scores.keySet()) {
			currentScore = getExamScore(name, examId);
			if (currentScore > maxScore) {
				maxScore = currentScore;
			}
		}
		return maxScore;
	}

	public double getMinScore(int examId) {
		double minScore = 100;
		double currentScore = 0;
		for (String name : scores.keySet()) {
			currentScore = getExamScore(name, examId);
			if (currentScore < minScore) {
				minScore = currentScore;
			}
		}
		return minScore;
	}

	public double getAverageScore(int examId) {
		double tScore = 0.0;
		int students = 0;
		for (String student : scores.keySet()) {
			if (scores.get(student).containsKey(examId)) {
				ArrayList<Double> examScores = scores.get(student).get(examId);
				for (Double score : examScores) {
					tScore += score;
				}
				students++;
			}
		}
		return tScore / students;
	}

	public void saveManager(Manager manager, String fileName) {
		try {

			File file = new File(fileName);
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (Exception error) {
			System.err.print(error.getMessage());
		}

	}

	public Manager restoreManager(String fileName) {
		try {
			File file = new File(fileName);
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			Manager manager = (Manager) input.readObject();
			input.close();
			return manager;
		} catch (Exception error) {
			System.err.print(error.getMessage());
			return null;
		}
	}

}
