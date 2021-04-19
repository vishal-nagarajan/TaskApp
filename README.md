# TaskApp
A Native Android Application that helps the user manage their project's tasks and generate a work log of the tasks. This Application is specifically built for *Freelancers*/*Project Managers*.

## Features
- Plan a To-do for every day from **Tentative To-do**,which consists a list of tasks that had *starting date* today or started previously but not yet completed.
- Update the work done in the **To-do**.Consists a list of tasks that are planned for the day from Tentative To-do. 
- List of various tasks are segregated with respect to their ending date and the progress .
  - **Completed Tasks** - Consists a list of tasks for which status is updated as *completed* from To-do.
  - **Task In Progress** - Consists a list of tasks for which status is *in progress* and the *Ending date* has not yet crossed.
  - **Task Over Due** - Consists a list of tasks for which status is *in progress* and the current date has exceeded the *Ending date*.
  - **Task Yet To Be Due** -  Consists a list of tasks for which current date has exceeded the start date and have not been completed.
- A general **Work Log** can be generated for all the tasks which will have the list of tasks in one sheet(.xlsx) and the workdone for particular task in another sheet.
- A **Custom Work Log** can be generated for a particular **Client** or a particular **Project**.
