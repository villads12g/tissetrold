import java.util.*;

// bachelor programme, requires ects points
class BachelorProgramme {

    private String name; // name of programme
    private int requiredBasicEcts; // required basic ects
    private int requiredSubjectEcts; // required subject ects

    public BachelorProgramme(String name, int requiredBasicEcts, int requiredSubjectEcts) {
        this.name = name;
        this.requiredBasicEcts = requiredBasicEcts;
        this.requiredSubjectEcts = requiredSubjectEcts;
    }

    // check if student completed programme, loops through activities and sums ects point
    //checks if student is part of a group for each project
    public boolean isCompletedBy(Student student) {

        int basicECTS = 0; // basic sum
        int subjectECTS = 0; // subject sum

        // loops activities
        for (StudyActivity a : student.getActivities()) {
            // adds ects points to the right category
            if (a.getCategory() == ProgrammeCategory.SUBJECT_MODULE) {
                subjectECTS += a.getEcts();
            } else {
                basicECTS += a.getEcts();
            }

            // checks if student has a project group
            if (a instanceof Project) {
                if (!student.hasProjectGroupFor((Project) a)) {
                    return false;
                }
            }
        }

        // returns true if requirements are met
        return basicECTS >= requiredBasicEcts
                && subjectECTS >= requiredSubjectEcts;
    }
}