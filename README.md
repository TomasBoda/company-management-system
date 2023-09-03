# Company Management System
by [Tomáš Boďa](https://github.com/TomasBoda)

## About
This project is a company management system desktop application written in Java that uses the JavaFX framework for building the user interface and H2 database in embedded mode.

## Installation & Usage
To run the application on your local machine, follow the steps below:
1. clone the project to your local machine using `git clone https://github.com/TomasBoda/company-management-system.git`
2. navigate to the project root using `cd company-management-system`
3. add the `h2-2.2.222.jar` file to the classpath
4. run `mvn clean install`
5. run the application (entry file `/src/main/java/com/app/main/Main.java`)

## User Manual
The following sections provide a manual to the user in order to learn how to use the application and navigate through it for testing purposes.

### General Information
The application servers as a company management tool. It allows the user to manage company employees, teams of employees, projects and see the overall state of the company and its financials.

The user can take the following actions:
- add a new employee (name, e-mail, role, employment type, salary)
- edit an existing employee
- remove an employee
- add a new team (name, list of team's members)
- edit an existing team
- remove a team
- add a new project (name, description, monthly budget, team, start date, end date, project's tickets)
- edit an existing project
- remove a project
- add a new project ticket (title, description, status, points, assignee, reviewer, reporter)
- edit an existing project ticket
- remove a project ticket

All actions taken by the user are added to the logs list for later history analysis and debugging purposes.

### Navigation
The application's layout consists of a navigation sidebar located on the left-hand side of the window and the main content located on the right-hand side of the window.

#### Sidebar
The sidebar serves as a quick navigation between the main pages of the application. You can redirect to a page by clicking on any of the links located in the sidebar.

The application routing cosists of five main pages:
- **Dashboard** - general dashboard with statistics and graphs
- **Employees** - a list of employees and further corresponding actions
- **Teams** - a list of teams and further corresponding actions
- **Projects** - a list of projects and further corresponding actions
- **Logs** - a list of logs which serve as a debugging tool for the actions taken by the user

#### Content
The rest of the application's window is occupied by the contents of the currently opened page.

### Pages Structure
Below is the complete structure of the application's pages:
- Dashboard
- Employees
  - Add Employee
  - Edit Employee
- Teams
  - Add Team
  - Edit Team
- Projects
  - Add Project
  - Edit Project
    - Add Ticket
    - Edit Ticket
- Logs

### Dashboard
The dashboard page consists of four main panels showing analytical information about the company's current status. It displays information such as the total number of employees, teams and projects, the monthly revenue, spendings and overall income and finally a pie chart of projects' budget and a pie chart of company's salaries by role.

![Alt](/screenshots/dashboard.png)

### Employees
The employees page consists of a vertical list of current employees. The employees list can be filtered by a search query or sorted by name, role or employment type.

![Alt](/screenshots/employee-list.png)

#### Add new Employee
To add a new employee, the user needs to click on the `New Employee` button located on top of the page.

![Alt](/screenshots/employee-list-add.png)

After clicking the button, the user will be navigated to the edit page where they can add the employee's information. After filling in the information, the user needs to click the `Add` button to add the new employee.

![Alt](/screenshots/employee-add.png)

After clicking the button, the new employee will be added and the user will be navigated to the employees page.

#### Edit existing Employee
An existing employee can be edited by clicking on the employee card in the employees list.

![Alt](/screenshots/employee-list-edit.png)

This action will navigate the user to a separate page where the employee's information can be updated. After editing the employee's information, the user needs to click on the `Update` button to save the changes.

![Alt](/screenshots/employee-edit-update.png)

#### Remove existing Employee
To remove an existing employee, navigate to the edit page again and click on the `Remove` button.

![Alt](/screenshots/employee-edit-remove.png)

This action will remove the employee and navigate the user to the employees page.

### Teams
The teams page consists of a vertical list of current teams. The teams list can be filtered by a search query or sorted by name.

![Alt](/screenshots/team-list.png)

#### Add new Team
To add a new team, the user needs to click on the `New Team` button located on top of the page.

![Alt](/screenshots/team-list-add.png)

After clicking the button, the user will be navigated to the edit page where they can add the team's information. After filling in the information, the user needs to click the `Add` button to add the new team.

![Alt](/screenshots/team-add.png)

After clicking the button, the new team will be added and the user will be navigated to the teams page.

#### Edit existing Team
An existing team can be edited by clicking on the team card in the teams list.

![Alt](/screenshots/team-list-edit.png)

This action will navigate the user to a separate page where the team's information can be updated. After editing the team's information, the user needs to click on the `Update` button to save the changes.

![Alt](/screenshots/team-edit-update.png)

#### Remove existing Team
To remove an existing team, navigate to the edit page again and click on the `Remove` button.

![Alt](/screenshots/team-edit-remove.png)

### Projects
The projects page consists of a vertical list of current projects. The projects list can be filtered by a search query or sorted by name or deadline.

![Alt](/screenshots/project-list.png)

#### Add new Project
To add a new project, the user needs to click on the `New Project` button located on top of the page.

![Alt](/screenshots/project-list-add.png)

After clicking the button, the user will be navigated to the edit page where they can add the project's information. After filling in the information, the user needs to click the `Add` button to add the new project.

![Alt](/screenshots/project-add.png)

After clicking the button, the new project will be added and the user will be navigated to the projects page.

#### Edit existing Project
An existing project can be edited by clicking on the project card in the projects list.

![Alt](/screenshots/project-list-edit.png)

This action will navigate the user to a separate page where the project's information can be updated. After editing the project's information, the user needs to click on the `Update` button to save the changes.

![Alt](/screenshots/project-edit-update.png)

#### Remove existing Project
To remove an existing project, navigate to the edit page again and click on the `Remove` button.

![Alt](/screenshots/project-edit-remove.png)

This action will remove the project and navigate the user to the projects page.

### Tickets
To access a project's tickets, the user needs to navigate to the edit page of the desired project and can see the tickets list on the bottom of the page.

![Alt](/screenshots/ticket-list.png)

#### Add new Ticket
To add a new ticket, the user needs to click on the `New Ticket` button located on top of the tickets list.

![Alt](/screenshots/ticket-list-add.png)

After clicking the button, the user will be navigated to the edit page where they can add the ticket's information. After filling in the information, the user needs to click the `Add` button to add the new ticket.

![Alt](/screenshots/ticket-add.png)

After clicking the button, the new ticket will be added and the user will be navigated back to the project's edit page.

#### Edit existing Ticket
An existing ticket can be edited by clicking on the ticket card in the tickets list.

![Alt](/screenshots/ticket-list-edit.png)

This action will navigate the user to a separate page where the ticket's information can be updated. After editing the ticket's information, the user needs to click on the `Update` button to save the changes.

![Alt](/screenshots/ticket-edit-update.png)

#### Remove existing Ticket
To remove an existing ticket, navigate to the edit page again and click on the `Remove` button.

![Alt](/screenshots/ticket-edit-remove.png)

This action will remove the ticket and navigate the user back to the project's edit page.

### Logs
The projects page consists of a vertical list of the entire logs history. The logs list can be sorted by name or timestamp.

![Alt](/screenshots/log-list.png)

by [Tomáš Boďa](https://github.com/TomasBoda)
