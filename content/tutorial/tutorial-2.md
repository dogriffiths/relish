---
title: Create Our Tests
author: David Griffiths
image: images/Relish.png
date: "2018-11-09 07:59"
tags:
  - Tutorial
---


We're going to create an application that will allow a user to create, read, update and delete tasks. Let's say this is our first user story:

> **Story 1: A list of tasks can be managed by the application**
> 
> As a user
> 
> I want to be able to create, read, update and delete tasks
> 
> So that I can manage my time

We'll create a new Cucumber `feature` file in the `src/resources/features/` folder called `0001-can-manage-list-of-tasks.feature`

    Feature: A list of tasks can be managed by the application
      As a user
      I want to be able to create, read, update and delete tasks
      So that I can manage my time

Now we'll add our first scenario&#x2013;a test that checks that when the app is first started, that there are no tasks recorded. When we write the application, we'll expect the main page to look something like this:

![img](images/notasks.png)

We can write a scenario to check that this is true:

    Feature: A list of tasks can be managed by the application
      As a user
      I want to be able to create, read, update and delete tasks
      So that I can manage my time
    
      Scenario: Initially the list of tasks is empty
        Given I am on the task list
        Then the list of tasks will be empty

This scenario contains two steps:

* Launch the app on the task list page
* Check that the table of lists is empty

We'll need to write methods for each of these in the `SomeSteps.java` file that will automate the application for us.

And this is where Relish finally makes an appearance&#x2026;

&lt; [Creating the project](/#/tutorial/tutorial-1) | ^ [Tutorial](/#/tutorial/tutorial) | [Relish components](/#/tutorial/tutorial-3) &gt;
