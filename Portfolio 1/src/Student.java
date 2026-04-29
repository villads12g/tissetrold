import java.util.ArrayList;
import java.util.List;

// student class, where student can sign up for courses and groups
class Student {

    private String name; // student name
    private BachelorProgramme programme; // programme
    private List<StudyActivity> activities = new ArrayList<>(); // signed up activities
    private List<ProjectGroup> projectGroups = new ArrayList<>(); // project groups

    public Student(String name, BachelorProgramme programme) {
        this.name = name;
        this.programme = programme;
    }

    // sign up activity
    public void signUp(StudyActivity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    // checks if programme is completed
    public boolean hasCompletedProgramme() {
        return programme.isCompletedBy(this);
    }

    // join project group
    // exception if student is not signed up for project
    //or already in a group

    public void joinProjectGroup(ProjectGroup group) {

        Project activity = group.getActivity(); // get the project
    // checks if signed up for a group
        if (!activities.contains(activity)) {
            throw new IllegalArgumentException(
                    "Student must be signed up for the project");
        }

        //check if already in a group
        for (ProjectGroup pg : projectGroups) {
            if (pg.getActivity().equals(activity)) {
                throw new IllegalArgumentException(
                        "Already in group for this project");
            }
        }

        projectGroups.add(group); // add group
    }

    // check if student has a project group
    public boolean hasProjectGroupFor(Project project) {
        for (ProjectGroup pg : projectGroups) {
            if (pg.getActivity().equals(project)) {
                return true;
            }
        }
        return false;
    }

    // return signed up activities
    public List<StudyActivity> getActivities() {
        return activities;
    }
    @Override
    public String toString() {
        return "Student: " + name;
    }
}