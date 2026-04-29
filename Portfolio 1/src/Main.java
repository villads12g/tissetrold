//Alina, Emma & Villads
// Software Development
// 2. marts 2026

import java.util.*; // import utilities

public class Main {

    public static void main(String[] args) {

        // programme with ects points
        BachelorProgramme humtek =
                new BachelorProgramme("HUMTEK", 110, 70);

        // students
        Student s1 = new Student("Emma", humtek);
        Student s2 = new Student("Villads", humtek);

        // basic courses
        StudyActivity basicCourse1 =
                new Course("Interactive Design", 10, ProgrammeCategory.HUMTEK);

        // create project
        StudyActivity basicProject =
                new Project("Basic Project", 15, ProgrammeCategory.HUMTEK);

        // more basic courses
        StudyActivity basicCourse2 =
                new Course("Mathematics", 30, ProgrammeCategory.HUMTEK);

        StudyActivity basicCourse3 =
                new Course("Software Development", 30, ProgrammeCategory.HUMTEK);

        StudyActivity basicCourse4 =
                new Course("Essential Computing", 25, ProgrammeCategory.HUMTEK);

        // subject modules
        StudyActivity subject1 =
                new Course("Artificial Intelligence", 35, ProgrammeCategory.SUBJECT_MODULE);

        StudyActivity subject2 =
                new Course("Human & Technology", 35, ProgrammeCategory.SUBJECT_MODULE);

        // student 1 sign up
        s1.signUp(basicCourse1);
        s1.signUp(basicProject);
        s1.signUp(basicCourse2);
        s1.signUp(basicCourse3);
        s1.signUp(basicCourse4);
        s1.signUp(subject1);
        s1.signUp(subject2);

        // student 2 sign up
        s2.signUp(basicProject);

        // project group creation
        ProjectGroup group =
                new ProjectGroup(
                        s1,
                        "Reddit User Behaviour",
                        "Alina Basit",
                        (Project) basicProject
                );

        // add member
        group.addMember(s2);

        // check completion
        System.out.println("Programme completed? "
                + s1.hasCompletedProgramme());
        // print student
        System.out.println(s1);
    }
}

