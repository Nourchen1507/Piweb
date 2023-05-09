	Feature: User can delete a reclamation.
Scenario Outline: User delete reclamation by id
    Given  User delete reclamation with id <id> 
    When User retrieve all reclamations after the delete
    Then The size of oldList must be more than new list

    Examples: 
      | id  |   
      | 5   | 
      
 