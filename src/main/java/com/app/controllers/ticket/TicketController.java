package com.app.controllers.ticket;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Ticket;
import com.app.router.generic.Component;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketController extends Component<Ticket> {

    @FXML
    private Label title;
    @FXML
    private Label assignee;
    @FXML
    private Label points;

    @Override
    public void onData(Ticket data) {
        title.setText(data.getTitle());
        points.setText(data.getPoints() + " points");

        Employee employee = getEmployee(data.getAssigneeId());
        assignee.setText(employee.getName());
    }

    @FXML
    private void navigateToEditTicketPage() {
        Router.navigateWithData(Pages.EDIT_TICKET, getData());
    }

    private Employee getEmployee(String id) {
        Response<Employee> response = Api.employees().get(id);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }
}
