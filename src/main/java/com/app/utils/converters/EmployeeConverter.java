package com.app.utils.converters;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.utils.Dialog;
import javafx.util.StringConverter;

public class EmployeeConverter extends StringConverter<Employee> {

    private String id;

    @Override
    public String toString(Employee employee) {
        if (employee == null) {
            return null;
        }

        setId(employee.getId());

        return employee.getName() + " (" + employee.getRole() + ") - " + employee.getEmploymentType();
    }

    @Override
    public Employee fromString(String s) {
        Response<Employee> response = Api.employees().get(getId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }
}
