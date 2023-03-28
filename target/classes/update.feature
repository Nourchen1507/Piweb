	Feature: user can update a reclamation.
Scenario Outline: user update reclamation by id
    Given  user retrieve reclamation with id <id> ,old feedback <oldFeedback> and new feedback <newFeedback>
    When user update reclamation with id <id> at its feedback <oldFeedback> with the new feedback <newFeedback>
    Then assert that reclamation has new name <newFeedback>

    Examples: 
      |     id              |   oldFeedback               |           newFeedback       |
      |    "35"             |   "je suis satisfait"       | "je ne suis pas satisfait"  |
       
 