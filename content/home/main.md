# What is Relish?

Relish is a set of libraries designed to make it easier to create automated tests for user-interfaces. Our aim in creating Relish was to make tests so straightforward to create that developers would be able to write tests *before* creating their user-interfaces.

# What does a Relish test look like?

Relish is typically used combined with Cucumber tests and a UI testing library like Selenide (web) or Espresso (Android). 

If you create a Cucumber test like the following:

    Scenario: I can log in 
    	Given I have logged in with username 'freda' and password 'password1'
    	Then I will be on the home page
    	

You will need to create *step* code for the lines beginning `Given...` and `Then...`. With Relish, these steps will look like this:

    ...
    @Given("^I have logged in with username '([^']*)' and password '([^']*)'$")
    public void givenIHaveLoggedIn(String username, String password)
    {
        loginPage.launch();
        loginPage.username().setStringValue(username);
        loginPage.password().setStringValue(password);
        loginPage.loginButton().click();
    }
    
    @Then("^I will be on the home page$")
    public void thenIWillBeOnTheHomePage()
    {
        homePage.assertVisible();
    }
    ...
    
The steps interact with Relish testing components like `loginPage.username()` which represent components that appear within a web page. Relish makes it easy to create and re-use these components, and to set and check their values. 

# Installing Relish

If you are using Relish for web testing and you are building your test project with `gradle`, these are the dependencies you will need in your `build.gradle` file:

    apply plugin: 'java'
    
    sourceCompatibility = 1.8
    
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    
    dependencies {
        testCompile group: 'junit', name: 'junit', version: '4.12'
        testCompile group: 'info.cukes', name: 'cucumber-java', version: '1.2.5'
        testCompile group: 'info.cukes', name: 'cucumber-junit', version: '1.2.5'
        testCompile group: 'com.codeborne', name: 'selenide', version: '4.8'
        testCompile group: 'org.hamcrest', name:'java-hamcrest', version: '2.0.0.0'
        testCompile 'com.github.dogriffiths.relish:relish-core:0.0.204'
        testCompile 'com.github.dogriffiths.relish:relish-selenide:0.0.204'
    }

    