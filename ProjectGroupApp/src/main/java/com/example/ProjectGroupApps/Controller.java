package com.example.ProjectGroupApps;

import java.util.ArrayList;

public class Controller {

    private final Model model;


    public Controller(Model model) {
        this.model = model;
    }


    public ArrayList<String> getCourses() {
        return model.getCourses();
    }


    public ArrayList<String> getAllGroupTitles() {
        return model.getAllGroupTitles();
    }

    // Handle signup button: validate input and then call model
    public ArrayList<String> signupStudent(String studentName, String courseName) {
        if (studentName == null || studentName.isBlank() || courseName == null) {
            return new ArrayList<>(); // return empty list if invalid
        }


        model.signUpStudent(studentName, courseName);


        return model.getStudentActivities(studentName);
    }

    // Handle create group button
    public String createGroup(String title, String supervisor) {

        // Validate input
        if (title == null || title.isBlank()) {
            return "Please enter a group title.";
        }

        // Default supervisor if empty
        if (supervisor == null || supervisor.isBlank()) {
            supervisor = "None";
        }

        // Save group in database
        model.createProjectGroup(title, supervisor);

        // Return text to display in UI
        return "Group: " + title + "\n" +
                "Supervisor: " + supervisor;
    }

    // Handle join group button
    public String joinGroup(String groupTitle, String studentName) {

        // Validate input
        if (studentName == null || studentName.isBlank()) {
            return "Please enter a student name.";
        }

        if (groupTitle == null || groupTitle.isBlank()) {
            return "Please select a project group.";
        }

        // Check if group exists
        if (!model.groupExists(groupTitle)) {
            return "Group does not exist.";
        }

        // Add student to group in database
        model.addStudentToGroup(studentName, groupTitle);

        // Get supervisor info
        String supervisor = model.getGroupSupervisor(groupTitle);

        // Return text to display
        return "Group: " + groupTitle + "\n" +
                "Supervisor: " + (supervisor != null ? supervisor : "None") + "\n" +
                "Member: " + studentName;
    }

    // Get activities a student is signed up for
    public ArrayList<String> getStudentActivities(String studentName) {
        if (studentName == null || studentName.isBlank()) {
            return new ArrayList<>();
        }

        return model.getStudentActivities(studentName);
    }

    // Get courses with number of students (query result)
    public ArrayList<String> getCoursesWithStudentCount() {
        return model.getCoursesWithStudentCount();
    }

    // Close database connection when app exits
    public void cleanup() {
        model.closeDatabase();
    }
}