---
title: Why Relish Was Written
author: David Griffiths
date: "2018-11-09 07:59"
---

*Test-Driven Development* (TDD) is a terrible name.

The problem is that *test-driven development* sounds like it's all
about testing code, when really it's about designing code. The fact
that you end up with a bunch of automated tests as a result is really
just happy side-effect.

A better name for TDD would be *Example-Driven Design*, but it's
probably far too late to change the name now, so for the rest of this
post I'll keep calling it TDD.

Most software houses really don't do TDD. Most probably do automated
testing, but the two things are really not the same. The defining
feature of test-driven development is that you write the tests before
the code, and yet this rarely happens. Why is this? I think there are
two reasons.

Firstly, some companies make writing tests a *compliance activity*.
There are two kinds of thing that you do in your working life: Stuff
that has value - you'll work pretty hard at that, because the harder
you work, the better it is; and compliance activities - you'll put in
just enough effort to... comply. You'll fill out a timesheet. You'll
wear a name badge. You'll write automated tests. No one cares if you
wrote the tests to help create a lean design, just so long as you check
them in as a delivered product. Test coverage is a great tool for
discovering untested code, but too often it's applied like a modern-day
equivalent of Samuel Goldwyn measuring the length of screenwriters'
pencils, to make sure they were working hard enough.

The second reason people don't write tests first is that, gosh-darn it,
some tests are just plain *difficult* to write. This is particularly
true for user interface tests.

When you write user interface code, you probably create a nicely
structured tree of interlocking components:

![React Tree](/images/react-tree.png)

But then this code is converted into an ugly, complex series of
lower-level components, like HTML elements of Android layout code.

![Testing Chaos](/images/testing_chaos.png)

The structure, if not completely lost, is at least well hidden. Add to
that the fact that modern apps are frequently asynchronous, and your
testing code has to interact with some complex, shape-shifting
mesomorph. Is it any wonder that most developers would sooner write
[unit tests for JavaScript
code](http://corgibytes.com/blog/2016/12/13/i-hate-angular-testing/)
than write automated UI tests?

It might be, quite literally, a Tower of [Babel](https://babeljs.io/).

The consequence of this is that user interface code typically has few
automated tests, and those that do exist are particularly hard to write
before the implementation. They will probably also be flaky. They might
work in the morning, and fail in the afternoon.

Now if you're not developing the UI test-first, you are less likely to
write tests first in the rest of the application too. The user interface
has clear objectives; it does things that are important to the customer,
like buying shoes or recording time; the kind of things mentioned in a
user story. But the code that underpins the user interface is more
abstract. It reads data from data stores and translates data types. The
abstract and complex nature of generated or asynchronous front-end code
makes it less likely that developers will write code test-first for
those parts of the application.

## Relish UI Component Testing

That's why, at Black Pepper, we've created a library to make writing
UI tests faster and simpler. It's called
[Relish](https://github.com/BlackPepperSoftware/relish), and its aim is
to give your tests a simpler view of the user interface that more
closely resembles the component-tree model that you create in the
implementation code:

![Relish Tests](/images/relish_tests.png)

*Relish* allows you to create testing components that shadow the actual
components in your application. When you tell a component to do
something, it will handle the details of finding the right bit of HTML
or Android layout code to talk to.

You can create composite *Relish* components by plugging together other
*Relish* components. The library will automatically only look for
sub-components that appear within their parent components. That reduces
the need for complex CSS selectors, XPath expressions, or Espresso view
interactions. *Relish* also plays nicely with BDD testing frameworks
like [Cucumber](https://cucumber.io/) so that you will write less glue
code to pass data from tests to the user-interface.

But this all sounds a little abstract. Let's do the right thing, and
create an example.

## The development cycle

This is a typical development cycle using *Relish*:

![Devcycle](/images/devcycle.png)

Let's say we're going to build a simple web-based task management
application. Rather than jump straight in with a complex series of UI
designs and architecture, we'll work strictly through user stories.

## 1. Create a user story

The first thing we need to do is create a user story. Here's an
example:

    Feature: A list of tasks can be managed by the application
    As a user
    I want to be able to create, read, update and delete tasks
    So that I can manage my time

A user story has a standardised *As a... I want... So that...* format.
They are the basic materials used in an agile project to specify
requirements. They are typically numbered, so we'll make this *User
Story \#1*.

The user story here is written in [Gherkin format](https://github.com/cucumber/cucumber/wiki/Gherkin), which is a
very simple text-based format for specifying requirements. Gherkin files
can be executed by a library like [Cucumber JVM](https://github.com/cucumber/cucumber-jvm) as complete running
tests. We'll store the user story in it's own Gherkin-formatted file
called

[0001-can-manage-list-of-tasks.feature](https://github.com/dogriffiths/relish/blob/master/examples/selenide/example-selenide/src/test/resources/features/0001-can-manage-list-of-tasks.feature).

Now we need to create an example *scenario*, which describes a sequence
of actions and expected results for the user story.

## 2. Create a scenario (5 mins)

We'll begin with the simplest scenario: the case where we have no tasks
recorded:


    Feature: A list of tasks can be managed by the application
     As a user
     I want to be able to create, read, update and delete tasks
     So that I can manage my time
    Scenario: Initially the list of tasks is empty
     Given I am on the task list
     Then the list of tasks will be empty

There will be multiple scenarios for each user story. Each scenario
follows a *Given... When... Then...* structure. The *Given..* part is
the setup required by the scenario, the *When...* describes some action
that that needs to be performed, and the *Then...* part is the expected
output. Here the scenario is so simple that there is no *When...* step;
we'll just open the application and check that no tasks are displayed.

## 3. Sketch out the interface (5 mins)

Now we can create a sketch of the user interface. At this point we need
a sketch that is purely functional. We don't care what it looks like,
or how it's laid out. We just need something that tells us what
interface elements are required, with some notes about how they will be
used.

![Init All 2 1280X780](/images/init_all-2-1280x780.jpg)

Later on, the detailed design of the interface can be developed as other
stories are written. So long as the designs don't change this
functional design, there should be few problems with UX designers
working in parallel with developers.

## 4. Create the testing components (5 mins)

So far we've been describing a typical agile development sequence. Now
for the first time we come to the part where the *Relish* library will
be used.

We have a sketch of a page that will display all of the tasks, and we
know it will contain a table with the current list of tasks, and the
page itself will have the URL /index.html.

We can create a *Relish* Page component for this:

    public class TaskPage extends Page
    {
        public TaskPage()
        {
            super("/index.html");
        }
        public Table taskTable()
        {
            return new Table(By.className(\"tasks\"), this);
        }
    }

It's a simple Java object that extends the *Relish* Page class. It
contains very little. In the constructor it specifies the path of the
page, and we have a single method returning a *Relish* Table object,
which we're going to assume will have the CSS class-name tasks.

Notice that we provide no details beyond that. We're not even
specifying which columns are in the table.

Now we've defined a testing object for the page, we can go ahead and
write some test code that uses it.

## 5. Write the Cucumber tests (10 mins)

We now need to write some *Cucumber* code that will turn this scenario:

    Scenario: Initially the list of tasks is empty
     Given I am on the task list
     Then the list of tasks will be empty

Into executable code. We'll this by writing come *glue* code that will
say exactly how to execute the lines in the scenario. This is the glue
code:

    public class SomeSteps
    {
        private TaskPage taskPage = new TaskPage();
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
    }

There's one method for each step in the scenario. When *Cucumber* runs
the scenario, it will call `iAmOnTheTaskList()`, followed by
`theListOfTasksWillBeEmpty()`. The first method will ask *Relish* to
launch the Task page. The task page knows the URL of the page, so it
will automatically launch the browser at `/index.html` (NB: There are some
other bits of configuration you will need to do when creating your
testing project, such as the network address of the application under
test, but we're not going to cover those here).

The second method will check that the task table is empty. It doesn't
care what columns appear in the table, because it doesn't need to know.
There's also no need to check for things like whether the page is
loaded, or whether the table has appeared. This things are taken care of
by *Relish*.

That brings us to the really important step....

## 6. Implement the code (??? mins)

Having spent about 15 minutes creating the tests, you're now able to go
and write the code. Chances are it will take longer to write the code
than it took to create the tests. But, you now have a better grasp on
what's required. You know that you need a new page. You know what its
address will be. You know that you need to create a HTML table for the
tasks with class name tasks.

For the purposes of this scenario, you should do the least amount of
work required to get the test running. That means you'll probably just
create a hard-coded web page.

Here's a screenshot of the page from the
[tutorial](/#/tutorial/tutorial):

![Frontpage](/images/frontpage.png)

We began with a completely trivial scenario, but it has forced us to
create a working environment and publish a web page at the correct
address. We'll take things a little further by creating one more
scenario.

## Implementing a second scenario

We'll create a second Gherkin scenario at the end of our
0001-can-manage-list-of-tasks.feature file:

    ...
    Scenario: I can add tasks
    Given I am on the task list
    When I choose to add these tasks
      | Name            | Priority  | Status   |
      | Buy some bread  | H         | ready    |
      | Buy some milk   | L         | waiting  |
    Then I will see this on the list of tasks
      | Name            | Priority  | Status  |
      | Buy some bread  | High      | Ready   |
      | Buy some milk   | Low       | Waiting |

We'll need a lot more working code to implement this scenario, but how
much more test code will we need?

Let's begin as we did before, with a sketch of the user interface
needed. First, we'll add a button to the front page:

![New All](/images/new_all.jpg)

And we'll add a second page, containing a form where we can enter a new
task:

![Add Task]('/images/add_task.jpg)

Now we make sure we have the *Relish* components up to date. First,
we'll add a button to our TaskPage class:

    public class TaskPage extends Page
    {
        public TaskPage()
        {
            super("/index.html");
        }
        public Table taskTable()
        {
            return new Table(By.className("tasks"), this);
        }
        public SelenideWidget addButton()
        {
            return new SelenideWidget(By.className("addButton"), this);
        }
    }

Our new addButton() returns a general SelenideWidget component, which is
something that we can click.

Next, we'll create a testing component for our *Add Task* page:

    public class AddTaskPage extends Page {
        public AddTaskPage() {
            super("/add.html");
        }
        public InputText name() {
            return new InputText(By.id("name"), this);
        }
        public DropDown priority() {
            return new DropDown(By.id("priority"), this);
        }
        public RadioButtons status() {
            return new RadioButtons(By.name("status"), this);
        }
        public SelenideWidget saveButton() {
            return new SelenideWidget(By.className("saveButton"), this);
        }
    }

We have one method for each of the components on the screen. This code
is transcribed from the sketch. The only extra detail we've added is a
set of CSS selectors where we'll expect to find the components on the
page.

OK, so the testing components only took a few minutes to write, but the
glue code for the tests is likely to take far longer, right? After all
the scenario has a lot of detail about what needs to happen:

    Scenario: I can add tasks
      Given I am on the task list
      When I choose to add these tasks
      | Name           | Priority | Status  |
      | Buy some bread | H        | ready   |
      | Buy some milk  | L        | waiting |
      Then I will see this on the list of tasks
      | Name           | Priority | Status  |
      | Buy some bread | High     | Ready   |
      | Buy some milk  | Low      | Waiting |

To implement this scenario, we'll need these two step-methods in our
glue-code:

    ...
    private AddTaskPage addTaskPage = new AddTaskPage();
    ...
        @When("^I choose to add these tasks$")
        public void iChooseToAddTheseTasks(List<TableRow> tasks) {
            for(TableRow task : tasks) {
                taskPage.addButton().click();
                addTaskPage.set(task);
                addTaskPage.saveButton().click();
            }
        }
    @Then("^I will see this on the list of tasks$")
    public void iWillSeeThisOnTheListOfTasks(List<TableRow> tasks) {
        taskPage.taskTable().matches(tasks);
    }

All of the tabular information in the steps will be passed as lists of
*Relish* TableRow objects. TableRow objects are designed to interact
nicely with *Relish* testing components. For example, if we want to fill
out the new task form with a row of data from the scenario, we do it
with a single line of code:

addTaskPage.set(task);

*Relish* will take this data:

| Name           | Priority | Status |
|----------------|----------|--------|
| Buy some bread | H        | ready  |

And update the components in the AddTaskPage class that match the
*Name*, *Priority* and *Status* values. For the *Name* value, it will
enter text into the text field, for *Priority* it will choose the value
from a drop-down select list, and for *Status* it will click the
matching radio button.

And for the list of tasks that we expect to find in the table:

| Name           | Priority | Status  |
|----------------|----------|---------|
| Buy some bread | High     | Ready   |
| Buy some milk  | Low      | Waiting |

It will check the entire table with a single line of code:

    taskPage.taskTable().matches(tasks);

NB: If you have a more complex table, containing interactive elements,
you get *Relish* to do that for you as well. See the
[tutorial](/#/tutorial/tutorial-6)
for details.

You are now ready to implement the code for the scenario. How long would
it take to write the tests? In the case of this second example, it might
take 15-30 mins. To implement the code? Probably a couple of hours.

### **Summary**

UI tests can be complex, but they don't need to be. You shouldn't
write *all* of your tests at the UI level, in fact you probably
shouldn't even write most of them. But unless you are able to write UI
tests before you start the implementation, you are far less likely to do
test-driven development. *Relish* is still in the early stages of
development, but we hope that you'll find it useful. Please give it a
try and send us feedback.
