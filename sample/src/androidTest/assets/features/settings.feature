Feature: SettingsActivity
  Test UI functions

  @e2e
  @settings-feature
  Scenario: Toolbar back arrow returns to main screen
    Given I am in the the Settings screen
    And I click the back home arrow
    Then I should return to the main screen