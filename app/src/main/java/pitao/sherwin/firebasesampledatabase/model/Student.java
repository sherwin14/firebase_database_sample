package pitao.sherwin.firebasesampledatabase.model;

import java.util.List;

public class Student {

    private String examId;
    private String name;
    private String studentId;
    private int score;
    private List<Integer> correctItems;
    private List<Integer> failedItems;

    public Student(String name, String studentId, int score, List<Integer> correctItems, List<Integer> failedItems, String examId) {
        this.name = name;
        this.studentId = studentId;
        this.score = score;
        this.correctItems = correctItems;
        this.failedItems = failedItems;
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Integer> getCorrectItems() {
        return correctItems;
    }

    public void setCorrectItems(List<Integer> correctItems) {
        this.correctItems = correctItems;
    }

    public List<Integer> getFailedItems() {
        return failedItems;
    }

    public void setFailedItems(List<Integer> failedItems) {
        this.failedItems = failedItems;
    }
}
