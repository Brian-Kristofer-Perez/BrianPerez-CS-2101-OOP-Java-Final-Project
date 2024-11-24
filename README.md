# InsideJob: A Specialized Job Management Software
___

## Overview


InsideJob is a job management software that provides ease of use for both users and employers in managing their applications. Through an interactive Command-line interface (CLI), users could create postings, review applications, edit their documents, and many more with ease.


## Features: 
  - ### Resume Editing
    - Create and edit your own resume, and freely configure it to suit you!
    - Create a brief summary or description of your professional life, and educational history
    - Add your individial work experiences and list them down!
      
  - ### Contact Information
    - Keep you and your colleagues connected, by sharing your email and other contact information.
    - Easily update your contact information to keep your boss in touch!
      
  - ### Ease of Application
    - Easily browse through job categories, and identify the best opportunity for you!
    - Easily apply with an interactive Command Line Interface (CLI), displaying all of the job details you'll need
    - No need to fill it out over and over again, your personal resume is automatically sent to the employer!
   
  - ### Ease of Management
    - Create and Delete Job Postings with ease
    - Postings are grouped in terms of Job type for ease of access
    - There's no need to create the posting again when a position is left vacant
    - If an employee is fired or resigns, the position is immediately set open for applications!

  - ### Ease of Hiring
    - Applications and relevant employee information are presented in a concise format
    - Easily browse through job categories, and employee applications
    - View their resumes and easily hire employees with ease.

  ____
## Applications of OOP Principles:

  - ### Encapsulation
    - Attributes are mostly set to private, or protected, and methods that are only used within the class and not anywhere else are set to private.
      - Example: in `Employer` class, given that the menu options are mostly handled within the class and never outside, encapsulation is enforced by declaring those specific methods as private. Doing so enables the class to function properly without interference from outside functionalities.

    - When accessing private attributes, there is appropriate use of getter and setter methods
      - Example: in `Worker` class, most attributes are generally inaccessible from the outside, including the protected atributes inherited from the `User` class. Doing so ensures data integrity and safety from external influences.

  - ### Abstraction
    - Abstract classes are present, giving an avenue for classes to share functionality by inheriting the attributes from the specified abstract class. Abstract methods are also present, telling child classes what methods they have or could do, but leaving the implementation to be on the child class itself.
      - Example: `User` class has abstract methods `login()` and `editContactDetails()`, which will be up to the inheriting class to implement.
      - It also has attributes and non-abstract methods to be inherited for shared functionality between the child classes.

  - ### Inheritance
    - The application supports different inheritance hierarchies. Like mentioned above, inheritance is applied with the `User` abstract class, but inheritance is also applied with the `Job` superclass, serving as both an "object wrapper", shairing attributes, and even being a class with instantiable objects in and of itself.
    - Inheritance also allows the sharing of attributes and functionalities, allowing the parent class to provide a base of attributes and methods that child classes can build off of.            
    
  - ### Polymorphism
    - The application supports the use of polymorphism both in the forms of method overloading and overriding.
    - Example: `Job` superclass provides a baseline level of functionality, which the more "specialized" jobs (like for example, engineering) can inherit and override.
    - Method overloading is also present in the application. For example, in `Job` class, jobs can be printed, and it can be either printed in a numbered (list) format, or in a normal format contingent on whether you pass an integer "counter" value to it, or not.
___
## SDG Integration

The application supports SDG 8 (Decent Work and Economic Growth) and SDG 9 (Industry, Innovation, and Infrastructure) by providing ease of access to job management and applications, allowing for more workers to find their opportunity to contribute to the economy.
___
## Instructions for Running the Program

### Requirements: 
1. Install Java Development Kit (JDK 23), or higher
2. Install MySQL Server 8.0
3. Ensure the MySQL Connector/J is included in the project's lib folder.

   
