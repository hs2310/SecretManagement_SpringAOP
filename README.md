# Secret Management service using Spring AOP

- This project is mainly focused towards building a system, which <strong>keeps track of secrets</strong> created by and shared with different users. It performs <strong>access checks</strong> on every read operations to make sure the integrity of system is maintained. It also provides <strong>statistics such as most_trusted_user, worst_secret_keeper, best_known_secret,</strong> etc.

Key points:

- I have used various Data structures such as <strong>HashMap, HashSet, ArrayList, etc.</strong> and Alogorithms such as <strong>Breadth First Search</strong> to traverse through the user network of a secret, and find whether the requesting user has the access or not.
- I have utilised Aspect Oriented Programming paradigm to increase modularity by allowing the separation of cross-cutting concerns. This concerns include
  - checking for access
  - validating user input data
  - taking actions upon exceptions such as IOException, NotAuthorizedException, IllegalArgumentException, etc.
  - updating stats
  - logging

# Class Diagram for the system : 
![](https://user-images.githubusercontent.com/52833369/98454187-8cca5700-2116-11eb-8105-cff4754d579b.png)
