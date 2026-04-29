// project class, also a study activity
//requires a project group

class Project extends StudyActivity {
//constructor
//passes values to StudyActivity
    public Project(String name, int ects,
                   ProgrammeCategory category) {
        super(name, ects, category);
    }
}

