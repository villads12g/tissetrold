import java.util.ArrayList;
import java.util.List;

// create project group
class ProjectGroup {

    private String title; // title of project group
    private String supervisor; // supervisor for project group
    private Project activity; // project
    private List<Student> members = new ArrayList<>(); // members list

    //constructor that creates a project group with creator of group as first member
    public ProjectGroup(Student creator,
                        String title,
                        String supervisor,
                        Project activity) {
        // supervisor cant be empty
        if (supervisor == null || supervisor.isEmpty()) {
            throw new IllegalArgumentException("Supervisor can't be empty");
        }
        //title cant be empty
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title can't be empty");
        }
        //must have at least one member
        if (creator == null) {
            throw new IllegalArgumentException("Group must have at least one member");
        }

        this.title = title;
        this.supervisor = supervisor;
        this.activity = activity;

        addMember(creator); // add creator
    }

    // add member to group
    public void addMember(Student student) {

        if (!members.contains(student)) {
            members.add(student);
            student.joinProjectGroup(this);
        }
    }

    // return project group works on
    public Project getActivity() {
        return activity;
    }
}