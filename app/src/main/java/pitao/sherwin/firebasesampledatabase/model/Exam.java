package pitao.sherwin.firebasesampledatabase.model;

public class Exam {
    private String name;
    private String classId;

    public Exam(String name, String classId) {
        this.name = name;
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
