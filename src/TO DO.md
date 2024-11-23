# To do:

- Final stretch:
  - Implement DAO's for all new jobs
    - EngineeringDAO (done!) 
    - MedicalDAO (current task!)
    - ManagementDAO
    
  - Integrate to UI
    - CreateJob()
      - CreateMedicalJob()
      - CreateManagementJob()
      
    - DeleteJob()
    
    - ReviewPostings()?
    
  - Clean SQL
    - Get init.sql
    
  - PUSH!!!!!!!


## Other notes:

- Employer.java, on create posting
  - selectJobType:
    1. Engineering
      - createEngineeringPosting
    2. Medical
      - createMedicalPosting
    3. Management
      - createManagementPosting
    4. Other
      - Job()


- In mySQL:
  - ~~engineeringapplications~~
  - ~~engineeringoccupations~~
  - ~~medicalapplications~~
  - ~~medicaloccupations~~
  - ~~managementapplications~~
  - ~~managementoccupations~~
--------------------------------------------------------------------------------------------------------

Subclasses:

MedicalJob:
-shiftType: The type of work shift (e.g., "day", "night", "rotating").
-department: The medical department (e.g., "Emergency", "Surgery", "Pediatrics").


EngineeringJob:
-projectType(Software, Mechanical, Civil)
-workLocation(onsite, remote, flexible)

ManagementJob:
teamSize: Number of people in the team to be managed.
leadershipLevel: The level of management (e.g., "Team Lead", "Manager", "Director").
departmentManaged: The department being managed (e.g., "HR", "Sales", "Operations")

.