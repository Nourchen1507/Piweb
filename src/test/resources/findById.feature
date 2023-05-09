Feature: Getting reclamation

 
 Scenario Outline: get By id
  
  Given reclamation with id <id> <exist>
  When user try to get this reclamation with <id>
  Then The reclamation retrieved should be <null>
  
  
    Examples: 
    | id      |  null     | exist  |
    | "1"     |  "no"     |  "yes" |
    | "2"     | "yes"     |  "no"  |
       