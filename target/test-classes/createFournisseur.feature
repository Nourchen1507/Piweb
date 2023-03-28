      	Feature: User can make a reclamation.
Scenario Outline: User create reclamation
    Given  reclamation with <id> <isSignal> <isBan> <isRate>  <banDate> <FeedBack> <raison> <blockedBy>
    When User add this reclamation to list
    Then The result should be <result>

    Examples: 
      | id  |  isSignal  |  isBan  |  isRate  |  banDate     |  FeedBack |     raison   |  blockedBy |  exist  | result |   
      | 1   |  "yes"     |   "no"  |   "no"   |   "null"     |  "null"   |     "null"   |   "null"   |  "no"   | "yes"  |
      | 2   |  "no"      |  "yes"  |   "no"   | "2023-04-04" |  "null"   |     "scam"   |   "null"   |  "yes"  |  "no"  |
      
 
 
