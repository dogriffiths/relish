package com.example.steps;

import com.example.components.AddTaskPage;
import com.example.components.EditTaskPage;
import com.example.components.TaskPage;

import uk.co.blackpepper.relish.core.TableRow;

import java.util.List;
import java.util.stream.Collectors;

import cucumber.api.DataTable;
import cucumber.api.Transpose;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SomeSteps
{
    private TaskPage taskPage = new TaskPage();
    private AddTaskPage addTaskPage = new AddTaskPage();
    private EditTaskPage editTaskPage = new EditTaskPage();

    @Given("^I am on the task list$")
    public void iAmOnTheTaskList()
    {
        taskPage.launch();
    }

    @Then("^the list of tasks will be empty$")
    public void theListOfTasksWillBeEmpty()
    {
        taskPage.taskTable().assertEmpty();
    }

    @When("^I choose to add these tasks$")
    public void iChooseToAddTheseTasks(DataTable dataTable)
    {
        List<TableRow>tasks = dataTable.asList(TableRow.class);
        for(TableRow task : tasks)
        {
            taskPage.addButton().click();
            addTaskPage.form().set(task);
            addTaskPage.form().saveButton().click();
        }
    }

    @Then("^I will see this on the list of tasks$")
    public void iWillSeeThisOnTheListOfTasks(DataTable dataTable)
    {
        List<TableRow>tasks = dataTable.asList(TableRow.class);
        taskPage.taskTable().matches(tasks);
    }

    @When("^I will select these tasks$")
    public void iWillSelectTheseTasks(DataTable dataTable)
    {
        List<TableRow>tasks = dataTable.asList(TableRow.class);
        taskPage.taskTable().matches(tasks.stream().map(t -> t.except("select")).collect(Collectors.toList()));
        taskPage.taskTable().set(tasks.stream().map(t -> t.except("name", "priority")).collect(Collectors.toList()));
    }

    @When("^I choose to delete the selected tasks$")
    public void iChooseToDeleteTheSelectedTasks()
    {
        taskPage.deleteButton().click();
    }

    @Then("^the delete button is disabled$")
    public void theDeleteButtonIsDisabled()
    {
        taskPage.deleteButton().assertDisabled();
    }

    @When("^I edit the '([^']*)' task$")
    public void iEditTheBuyTask(String name)
    {
        taskPage.taskTable().findFirst(row -> row.get("name").equals(name)).getWidget(4).click();
    }

    @Then("^the edit form will contain$")
    public void theEditFormWillContain(@Transpose DataTable dataTable)
    {
        List<TableRow>tasks = dataTable.asList(TableRow.class);
        editTaskPage.form().matches(tasks.get(0));
    }

    @When("^I save these changes$")
    public void iSaveTheseChanges(@Transpose DataTable dataTable)
    {
        List<TableRow>tasks = dataTable.asList(TableRow.class);
        editTaskPage.form().set(tasks.get(0));
        editTaskPage.form().saveButton().click();
    }
}
