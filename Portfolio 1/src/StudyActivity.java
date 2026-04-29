// abstract activity represents a study activity
//all activity gets a name, ects points and a programme category
abstract class StudyActivity {

    protected String name; // name of activity
    protected int ects; // ects
    protected ProgrammeCategory category; // programme category

 //constructer
    public StudyActivity(String name, int ects,
                         ProgrammeCategory category) {
        this.name = name;
        this.ects = ects;
        this.category = category;
    }

    // returns ects points
    public int getEcts() {
        return ects;
    }

    // returns programme category
    public ProgrammeCategory getCategory() {
        return category;
    }
}
