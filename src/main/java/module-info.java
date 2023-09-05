module com.app.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;


    opens com.app.main to javafx.fxml;
    exports com.app.main;
    exports com.app.controllers;
    exports com.app.model;
    opens com.app.controllers to javafx.fxml;
    exports com.app.router;
    opens com.app.router to javafx.fxml;
    exports com.app.controllers.employee;
    opens com.app.controllers.employee to javafx.fxml;
    exports com.app.controllers.ticket;
    opens com.app.controllers.ticket to javafx.fxml;
    exports com.app.controllers.team;
    opens com.app.controllers.team to javafx.fxml;
    exports com.app.controllers.project;
    opens com.app.controllers.project to javafx.fxml;
    exports com.app.controllers.dashboard;
    opens com.app.controllers.dashboard to javafx.fxml;
    exports com.app.router.generic;
    opens com.app.router.generic to javafx.fxml;
    exports com.app.controllers.log;
    opens com.app.controllers.log to javafx.fxml;
}