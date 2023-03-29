	Feature: user can update a reclamation.
Scenario Outline: user update reclamation by id
    Given  User retrieve reclamation with id <id> ,old feedback <oldFeedback>
    When User update reclamation with id <id> at its feedback <oldFeedback> with the new feedback <newFeedback>
    Then Assert that reclamation has new feedback <newFeedback>

    Examples: 
      |     id              |   oldFeedback               |           newFeedback       |
      |    "35"             |   "je suis satisfait"       | "je ne suis pas satisfait"  |
       
 