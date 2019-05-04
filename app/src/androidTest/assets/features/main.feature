Feature: MainActivity
    Test UI functions

    @e2e
    @main-feature
    Scenario: Gizmo reveals Widgets
        Given I start the application
        And I see the Gizmos list is not empty
        And I click on the first Gizmo
        Then I should see a list of Widgets

    @e2e
    @main-feature
    Scenario: Menu About item navigates correctly
        Given I am on the main screen
        And I click on the menu button
        And I select the About menu item
        Then I should see the About screen

    @e2e
    @main-feature
    Scenario: Menu Settings item navigates correctly
        Given I am on the main screen
        And I click on the menu button
        And I select the Settings menu item
        Then I should see the Settings screen

    @e2e
    @main-feature
    Scenario: Menu Close item closes application
        Given I am on the main screen
        And I click on the menu button
        And I select the Close menu item
        Then I should see the application close