# TaskApp
A Native Android Application that helps a user to add, classify, schedule and manage tasks and generate a work log based on tasks completed. This Application is specifically built for *Freelancers/Project Managers* who work on multiple projects for multiple clients at any point of time.
## Features
- Plan a To-do for every day from **Tentative To-do**, which consists of tasks planned for the day (the current date) or started previously but not yet completed.
- Update the work done in the **To-do**.Consists a list of tasks that are planned for the day.
- List of various tasks are segregated with respect to their ending date and the progress .
  - **Completed Tasks** - Consists a list of tasks for which status is updated as *completed* from To-do.
  - **Task In Progress** - Consists a list of tasks for which status is *in progress* and the *Ending date* has not yet crossed.
  - **Task Over Due** - Consists a list of tasks for which status is *in progress* and the current date has exceeded the *Ending date*.
  - **Task Yet To Be Due** -  Consists a list of tasks for which current date has exceeded the start date and have not been completed.
- A general **Work Log** can be generated for all the tasks which will have the list of tasks in one sheet(.xlsx) and the workdone for particular task in another sheet.
- A **Custom Work Log** can be generated for a particular **Client** or a particular **Project**.

## Tech
- ### Architecture
  - This application uses *Single-Activity Architecture*. In this architecture an Activity hosts a fragment and from there bussiness logic is writen in fragments rather than in seperate activities. This architecture makes the most out of Navigation components like passing arguments between fragments and navigating back and forth fragments.
- #### [Android Jetpack Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)
  - This application makes use of Navigation library to navigate from one fragment to another and pass required data between fragments seemlessly
- #### [Android Jetpack Room Database](https://developer.android.com/jetpack/androidx/releases/room)
  - This applications is built using room persistance library which allows the developer to write SQL queries and access them as objects(DAO).
- #### Kotlin Synthetics
  - This application uses kotlin synthetics to find the elements from the .xml directly by their id rather than using a findViewById.
- #### [Apache POI](https://poi.apache.org/)
  -This application makes use of Apache POI to write the in Microsoft Excel Document whenever the user chooses to generate a work log.
- #### [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
  - This application makes use of coroutines to asynchronously call database function calls without disturbing the main thread in which the UI is running.
- #### [Material Design](https://material.io/design)
  - This application uses various Material design components in the User Interface.
