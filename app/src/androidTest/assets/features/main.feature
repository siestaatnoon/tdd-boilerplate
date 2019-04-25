Feature: Main Screen
    Perform clicks on sample data

    @e2e
    @main-feature
    Scenario: Gizmo reveals Widgets
        Given I start the application
        And I see the Gizmos list is not empty
        And I click on the first Gizmo
        Then I should see a list of Widgets