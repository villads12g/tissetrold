package com.example.ProjectGroupApps;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProjectGroupApp extends Application {

    // Controller connects UI with model and data
    private Controller controller;

    // UI components
    private ListView<String> activitiesList = new ListView<>();
    private ComboBox<String> projectDropdown = new ComboBox<>();
    private ComboBox<String> groupDropdown = new ComboBox<>();
    private TextArea groupDetails = new TextArea();
    private TextField studentName = new TextField();

    @Override
    public void start(Stage stage) {

        // Initialize MVC (Model + Controller)
        Model model = new Model();
        controller = new Controller(model);

        // Root layout (horizontal split)
        HBox root = new HBox(20);
        root.setPadding(new Insets(15));

        // Create UI panels
        VBox leftPanel = createLeftPanel();
        VBox rightPanel = createRightPanel();

        // Set panel widths
        leftPanel.setPrefWidth(300);
        rightPanel.setPrefWidth(300);

        // Add panels to root
        root.getChildren().addAll(leftPanel, rightPanel);

        // Create scene
        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Project Group Management System");
        stage.setScene(scene);

        // Cleanup database connection when closing app
        stage.setOnCloseRequest(e -> controller.cleanup());

        stage.show();
    }

    // LEFT SIDE: Student signup section
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));

        // Input for student name
        studentName.setPromptText("Student name");

        // Dropdown for selecting course (loaded from database via controller)
        projectDropdown.setPromptText("Select project");
        projectDropdown.getItems().setAll(controller.getCourses());

        // Signup button
        Button signupBtn = new Button("Signup");
        signupBtn.setMaxWidth(Double.MAX_VALUE);

        // When clicked: call controller to handle signup and update list
        signupBtn.setOnAction(e -> {
            activitiesList.getItems().setAll(
                    controller.signupStudent(
                            studentName.getText(),
                            projectDropdown.getValue()
                    )
            );
        });

        // List showing activities the student signed up for
        activitiesList.setPrefHeight(200);

        // Add all UI elements to left panel
        leftPanel.getChildren().addAll(
                new Label("Student name"),
                studentName,
                new Label("Project"),
                projectDropdown,
                signupBtn,
                new Label("Signed up for activities"),
                activitiesList
        );

        return leftPanel;
    }

    // RIGHT SIDE: Project group section
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));

        // Input fields for creating a group
        TextField titleField = new TextField();
        titleField.setPromptText("Group title");

        TextField supervisorField = new TextField();
        supervisorField.setPromptText("Supervisor");

        // Button to create a new group
        Button createGroupBtn = new Button("Create project group");
        createGroupBtn.setMaxWidth(Double.MAX_VALUE);

        // Dropdown showing existing groups (loaded from database)
        groupDropdown.setPromptText("Select group");
        groupDropdown.getItems().setAll(controller.getAllGroupTitles());

        // When clicked: create group via controller and update UI
        createGroupBtn.setOnAction(e -> {
            String result = controller.createGroup(
                    titleField.getText(),
                    supervisorField.getText()
            );

            groupDetails.setText(result);
            groupDropdown.getItems().setAll(controller.getAllGroupTitles());
        });

        // Button to join an existing group
        Button joinGroupBtn = new Button("Join group");

        // When clicked: join group via controller and display result
        joinGroupBtn.setOnAction(e -> {
            String result = controller.joinGroup(
                    groupDropdown.getValue(),
                    studentName.getText()
            );

            groupDetails.setText(result);
        });

        // Area showing group details/result
        groupDetails.setPrefHeight(200);
        groupDetails.setEditable(false);

        // Add all UI elements to right panel
        rightPanel.getChildren().addAll(
                new Label("Create new project group"),
                new Label("Title"),
                titleField,
                new Label("Supervisor"),
                supervisorField,
                createGroupBtn,
                new Label("or"),
                new Label("Add yourself to existing project group"),
                groupDropdown,
                joinGroupBtn,
                new Label("Selected project group"),
                groupDetails
        );

        return rightPanel;
    }

    public static void main(String[] args) {
        launch();
    }
}