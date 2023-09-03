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

### Employees
The employees page consists of a vertical list of current employees. The employees list can be filtered by a search query or sorted by name, role or employment type.

#### Add new Employee
To add a new employee, the user needs to click on the `New Employee` button located on top of the page.

#### Edit existing Employee
An existing employee can be edited by clicking on the employee card in the employees list. This action will navigate the user to a separate page where the employee's information can be updated. After editing the team's information, click on the `Update` button to save the changes.

#### Delete existing Employee
To remove an existing employee, navigate to the edit page and click on the `Remove` button.

### Teams
The teams page consists of a vertical list of current teams. The teams list can be filtered by a search query or sorted by name.

#### Add new Team
To add a new team, the user needs to click on the `New Team` button located on top of the page.

#### Edit existing Team
An existing team can be edited by clicking on the team card in the teams list. This action will navigate the user to a separate page where the team's information can be updated. After editing the team's information, click on the `Update` button to save the changes.

#### Remove existing Team
To remove an existing team, navigate to the edit page and click on the `Remove` button.

### Projects
The projects page consists of a vertical list of current projects. The projects list can be filtered by a search query or sorted by name or deadline.

#### Add new Project
To add a new project, the user needs to click on the `New Project` button located on top of the page.

#### Edit existing Project
An existing project can be edited by clicking on the project card in the projects list. This action will navigate the user to a separate page where the projects's information can be updated. After editing the projects's information, click on the `Update` button to save the changes.

#### Remove existing Project
To remove an existing project, navigate to the edit page and click on the `Remove` button.

### Tickets
Each project contains a list of project's tickets. These tickets serve as a todo list for the project. To list of tickets can be found by navigating to the edit page of a project and scrolling to the bottom part of the page.

#### Add new Ticket
To add a new ticket, the user needs to click on the `New Ticket` button located right above the tickets list. This action will take the user to a separate page where the ticket's information can be filled in. After filling in the ticket's information, the user needs to click on the `Add` button.

#### Edit existing Ticket
An existing ticket can be edited by clicking on the ticket card in the tickets list. This action will navigate the user to a separate page where the ticket's information can be updated. After editing the ticket's information, click on the `Update` button to save the changes.

#### Remove existing Ticket
To remove an existing ticket, navigate to the edit page and click on the `Remove` button.



