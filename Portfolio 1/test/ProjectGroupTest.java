import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectGroupTest {

    // setup
    BachelorProgramme humtek = new BachelorProgramme("HUMTEK", 110, 70);
    Student student = new Student("Emma", humtek);
    Project project = new Project("Basic Project", 15, ProgrammeCategory.HUMTEK);

    @Test
    void groupMustHaveAtLeastOneMember() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectGroup(null, "Reddit Study", "Alina", project);
        });
    }

    @Test
    void supervisorCannotBeEmpty() {
        student.signUp(project);
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectGroup(student, "Reddit Study", "", project);
        });
    }

    @Test
    void titleCannotBeEmpty() {
        student.signUp(project);
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectGroup(student, "", "Alina", project);
        });
    }

    @Test
    void memberMustBeSignedUpForProject() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectGroup(student, "Reddit Study", "Alina", project);
        });
    }
}