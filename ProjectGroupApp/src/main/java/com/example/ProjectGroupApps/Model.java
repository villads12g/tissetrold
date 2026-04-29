package com.example.ProjectGroupApps;

import java.util.ArrayList;

public class Model {
    private MyDB db;

    public Model() {
        this.db = new MyDB();
    }

    // Courses

    public ArrayList<String> getCourses() {
        return db.query("SELECT name FROM course", "name");
    }

    public void signUpStudent(String studentName, String courseName) {
        String sql = String.format(
                "INSERT INTO SignUp (StudentName, CourseName) VALUES ('%s', '%s')",
                studentName.replace("'", "''"),
                courseName.replace("'", "''")
        );
        db.cmd(sql);
    }

    public ArrayList<String> getStudentActivities(String studentName) {
        String sql = String.format(
                "SELECT CourseName FROM SignUp WHERE StudentName = '%s'",
                studentName.replace("'", "''")
        );
        return db.query(sql, "CourseName");
    }


    // Student count query

    public ArrayList<String> getCoursesWithStudentCount() {
        ArrayList<String> result = new ArrayList<>();

        String sql = """
                SELECT course.name, course.ects, COUNT(SignUp.StudentName) as count
                FROM course
                LEFT JOIN SignUp ON course.name = SignUp.CourseName
                GROUP BY course.name, course.ects
                """;

        ArrayList<String> names = db.query(sql, "name");
        ArrayList<String> counts = db.query(sql, "count");

        for (int i = 0; i < names.size(); i++) {
            result.add(names.get(i) + " - Students: " + counts.get(i));
        }

        return result;
    }


    // project group ( now stored in db after app closes)

    public void createProjectGroup(String title, String supervisor) {
        String sql = String.format(
                "INSERT INTO ProjectGroup (title, supervisor) VALUES ('%s', '%s')",
                title.replace("'", "''"),
                supervisor.replace("'", "''")
        );
        db.cmd(sql);
    }

    public ArrayList<String> getAllGroupTitles() {
        return db.query("SELECT title FROM ProjectGroup", "title");
    }

    public String getGroupSupervisor(String title) {
        String sql = String.format(
                "SELECT supervisor FROM ProjectGroup WHERE title = '%s'",
                title.replace("'", "''")
        );

        ArrayList<String> res = db.query(sql, "supervisor");
        return res.isEmpty() ? null : res.get(0);
    }

    public boolean groupExists(String title) {
        String sql = String.format(
                "SELECT title FROM ProjectGroup WHERE title = '%s'",
                title.replace("'", "''")
        );
        return !db.query(sql, "title").isEmpty();
    }

    public void addStudentToGroup(String student, String groupTitle) {
        String sql = String.format(
                "INSERT INTO GroupMember (studentName, groupTitle) VALUES ('%s', '%s')",
                student.replace("'", "''"),
                groupTitle.replace("'", "''")
        );
        db.cmd(sql);
    }

    // ----------------------------
    public void closeDatabase() {
        if (db != null) db.close();
    }
}