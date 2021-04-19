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
  - This application makes use of Apache POI to write the data in Microsoft Excel Document whenever the user chooses to generate a work log.
- #### [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
  - This application makes use of coroutines to asynchronously call database function calls without disturbing the main thread in which the UI is running.
- #### [Material Design](https://material.io/design)
  - This application uses various Material design components in the User Interface.
## Download & Test the application
- **Step-1** : Download the .apk file from the [link](https://drive.google.com/file/d/1DHMeCO8N0SMesectjyb2fQL-yMYnVlHM/view?usp=sharing)
- **Step-2** : Install the application your Android device by allowing installation from third party.
- **Step-3** : Now the Task App can be used from your mobile.
## Difference from a typical To-do Application
- Most of the to-do apps allows the users to schedule a task by mentioning the tentative end date but not the tentative start date.Task App allows users to mention a tentative start date.
- Once a part/full Task is done the work done can be updated in this app which stores the timing, place, people and nature of work done.
- The task status can be viewed in the application.
- Work Log can be generated either specific to a client/project or as a whole.
- Users can not only add tasks prior to start date and plan when popped but also can add the task on that particular date.
- Eventhough the above mentioned features are seperately found in some applications but not as a whole in one application.
## Screen shots:
### Tentative To do
![New Project (1)](https://user-images.githubusercontent.com/56826029/115232664-9f6ca180-a134-11eb-9d1b-7944d449618b.png)
### To do
![New Project (2)](https://user-images.githubusercontent.com/56826029/115232947-e9558780-a134-11eb-8379-1c90344eb548.png)
### Main menu | Add Task
![New Project (3)](https://user-images.githubusercontent.com/56826029/115233207-405b5c80-a135-11eb-8af4-3e2110ed9197.png)
### Edit Task 
![New Project (4)](https://user-images.githubusercontent.com/56826029/115233384-74cf1880-a135-11eb-9c65-a5120c4ed552.png)
### Main menu | Task in progress
![New Project (5)](https://user-images.githubusercontent.com/56826029/115233613-c11a5880-a135-11eb-95eb-2fb8c633edfc.png)
### Work log | Custom work log
![New Project (6)](https://user-images.githubusercontent.com/56826029/115233781-f32bba80-a135-11eb-94b3-eadaada40ede.png)
### Sample Work log generated from the application [worklog.xlsx](https://github.com/vishal-nagarajan/TaskApp/files/6335931/worklog.xlsx)


