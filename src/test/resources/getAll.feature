      Feature: User can display all reclamations.
Scenario Outline: User display reclamation
    Given list of reclamation in database is <isempty>
    When user retrieve all reclamation from database
    Then list retrieved should be <isempty>

    Examples: 
      |    isempty    | 
      |     "yes"     |
      |      "no"     |
        
