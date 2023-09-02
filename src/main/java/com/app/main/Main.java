package com.app.main;

import com.app.api.Api;
import com.app.model.*;
import com.app.utils.DateUtil;
import com.app.utils.Generator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 500;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        Api.connect();
        initializeMockedData();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        scene.getStylesheets().add(Main.class.getResource("/styles/navigation.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/styles/app.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/styles/styles.css").toExternalForm());

        stage.setTitle("Company Management System v1.0");
        stage.setScene(scene);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.show();
    }

    private void initializeMockedData() {
        String emp1Id = Generator.getId();
        String emp2Id = Generator.getId();
        String emp3Id = Generator.getId();
        String emp4Id = Generator.getId();
        String emp5Id = Generator.getId();

        Api.employees().add(new Employee(emp1Id, "John Doe", "johndoe@email.com", Role.SOFTWARE_DEVELOPER, EmploymentType.FULL_TIME, 4200));
        Api.employees().add(new Employee(emp2Id, "Jack Apple", "jackapple@email.com", Role.PRODUCT_OWNER, EmploymentType.FULL_TIME, 3800));
        Api.employees().add(new Employee(emp3Id, "Marry Johnson", "marryjohnson@email.com", Role.SOFTWARE_TESTER, EmploymentType.INTERNSHIP, 2200));
        Api.employees().add(new Employee(emp4Id, "Eric Wooden", "ericwooden@email.com", Role.PRODUCT_OWNER, EmploymentType.PART_TIME, 1900));
        Api.employees().add(new Employee(emp5Id, "Mayhem Brown", "mayhembrown@email.com", Role.SOFTWARE_DEVELOPER, EmploymentType.CONTRACT, 2300));

        String team1Id = Generator.getId();
        String team2Id = Generator.getId();

        Api.teams().add(new Team(team1Id, "RSU"));
        Api.teams().add(new Team(team2Id, "IAM"));

        String proj1id = Generator.getId();
        String proj2id = Generator.getId();

        Api.projects().add(new Project(proj1id, team1Id, "RSU", "Remote Software Update Tool for Siemens Mobility", 2000, DateUtil.toDate(1, 1, 2023), DateUtil.toDate(15, 10, 2023)));
        Api.projects().add(new Project(proj2id, team2Id, "IAM", "User Management Tool for Siemens Mobility", 3000, DateUtil.toDate(15, 3, 2023), DateUtil.toDate(15, 8, 2024)));

        Api.teams().addEmployeeToTeam(team1Id, emp1Id);
        Api.teams().addEmployeeToTeam(team1Id, emp2Id);
        Api.teams().addEmployeeToTeam(team1Id, emp3Id);
        Api.teams().addEmployeeToTeam(team2Id, emp4Id);
        Api.teams().addEmployeeToTeam(team2Id, emp5Id);

        Api.tickets().add(new Ticket(Generator.getId(), proj1id, "Fixed loading bug", "Fixes a loading bug in lazy loading", TicketStatus.IN_PROGRESS, 10, emp1Id, emp2Id, emp3Id));
    }

    public static void main(String[] args) {
        launch();
    }
}