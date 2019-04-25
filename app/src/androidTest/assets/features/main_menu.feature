Feature: Main Menu
    Perform clicks on menu items

    @e2e
    @menu-feature
    Scenario: Menu About item navigates correctly
        Given I start the application again
        And I click on the menu button
        And I select the About menu item
        Then I should see the About screen


    @e2e
    @menu-feature
    Scenario: Menu Settings item navigates correctly
        And I click on the menu button
        And I select the Settings menu item
        Then I should see the Settings screen

    @e2e
    @menu-feature
    Scenario: Menu Close item closes application
        Given I click on the menu button
        And I select the Close menu item
        Then I should see the application close
