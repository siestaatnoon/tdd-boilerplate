Feature: AboutActivity
  Test UI functions

  @e2e
  @about-feature
  Scenario: Activity back arrow returns to main screen
    Given I am in the the About screen
    And I click the back arrow
    Then I should be back in the main screen

  @e2e
  @about-feature
  Scenario: Floating button opens email client
    Given I am in the the About screen
    And I click the Email button
    Then The email client should open