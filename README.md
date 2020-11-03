# Visual Transit Simulator: Project Iteration 2

## The Visual Transit Simulator Software

In the project iterations, you will be working on a visual transit simulator (VTS) software. The current VTS software models bus transit around the University of Minnesota campus. The software simulates the behavior of busses and passengers on campus. Specifically, the busses go along a route, make stops, and pick up/drop off passengers. The simulation operates over a certain number of time units. In each time unit, the VTS software updates the state of the simulation by creating passengers at stops, moving busses along the routes, allowing a bus to pick up passengers at a stop, etc. The simulation is configured using a *configuration* file that specifies the simulation routes, the stops of the routes, and how likely it is that a passenger will show up at a certain stop in each time unit. Routes must be defined in pairs, that is, there should be both an outgoing and incoming route and the routes should be specified one after the other. The ending stop of the outgoing route should be at the same location as the starting stop of the incoming route and the ending stop of the incoming route should be at the same location as the starting stop of the outgoing route. However, stops between the starting and ending stops of outgoing and incoming routes can be at different locations. After a bus has passed a stop, it is possible for passengers to show up at stops that the bus has already passed. For this reason, the simulator supports the creation of multiple busses and these busses will go and pick up the new passengers. Each bus has its own understanding of its own route, but the stops have relationships with multiple buses. Buses do not make more than one trip through their routes. When a bus finish both of its routes (outbound and inbound), the bus exits the simulation.

The VTS software is divided into two main modules: the *visualization module* and the *simulator module*. The visualization module displays the state of the simulation in a browser, while the simulator module performs the simulation. The visualization module is a web client application that runs in a browser and it is written in Javascript and HTML. The visualization module code is inside the `<dir>/src/main/webapp/web_graphics` directory of this repo (where `<dir>` is the directory of the repo). The simulator module is a web server application written in Java. The simulator module code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project` directory. The simulator module is divided into two parts: *domain classes* and the *web server*. The domain classes model real-world entities (e.g., the concept of a bus) and the code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory. The web server includes the code that orchestrates the simulation and is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory. The visualization module and the simulator module communicate with each other using [websockets](https://www.baeldung.com/java-websockets). In the project iterations, you will largely focus on the code of the simulator module.

The user of the VTS software interacts with the visualization module using the browser and can specific how long the simulation will run (i.e., how many time units) and how often new busses will be added to a route in the simulation. The users also specifies when to start the simulation. The image below depicts the graphical user interface (GUI) of the VTS software. 

![GUI of the VTS Software](https://www-users.cs.umn.edu/~mfazzini/teaching/csci3081w/vs_iteration_2.png)

### VTS Software Details

#### Simulation Configuration
The simulation is based on the `<dir>/src/main/resources/config.txt` configuration file. The following excerpt of the configration file defines a route.

```
ROUTE, East Bound

STOP, Blegen Hall, 44.972392, -93.243774, .15
STOP, Coffman, 44.973580, -93.235071, .3
STOP, Oak Street at University Avenue, 44.975392, -93.226632, .025
STOP, Transitway at 23rd Avenue SE, 44.975837, -93.222174, .05
STOP, Transitway at Commonwealth Avenue, 44.980753, -93.180669, .05
STOP, State Fairgrounds Lot S-108, 44.983375, -93.178810, .01
STOP, Buford at Gortner Avenue, 44.984540, -93.181692, .01
STOP, St. Paul Student Center, 44.984630, -93.186352, 0

ROUTE, West Bound

STOP, St. Paul Student Center, 44.984630, -93.186352, .35
STOP, Buford at Gortner Avenue, 44.984482, -93.181657, .05
STOP, State Fairgrounds Lot S-108, 44.983703, -93.178846, .01
STOP, Transitway at Commonwealth Avenue, 44.980663, -93.180808, .01
STOP, Thompson Center & 23rd Avenue SE, 44.976397, -93.221801, .025
STOP, Ridder Arena, 44.978058, -93.229176, .05
STOP, Pleasant Street at Jones-Eddy Circle, 44.978366, -93.236038, .1
STOP, Bruininks Hall, 44.974549, -93.236927, .3
STOP, Blegen Hall, 44.972638, -93.243591, 0

ROUTE, East Bound Express

STOP, Blegen Hall, 44.972392, -93.243774, .15
STOP, Transitway at Commonwealth Avenue, 44.980753, -93.180669, .05
STOP, St. Paul Student Center, 44.984630, -93.186352, 0

ROUTE, West Bound Express

STOP, St. Paul Student Center, 44.984630, -93.186352, .35
STOP, Thompson Center & 23rd Avenue SE, 44.976397, -93.221801, .025
STOP, Blegen Hall, 44.972638, -93.243591, 0
```
The line `ROUTE, East Bound` defines a the outgoing route. The lines after the line that defines a route are stops for the route. Each stop has a name, a longitude, a latitude, and the probability to generate a passenger at the stop. For example, for `STOP, Blegen Hall, 44.972392, -93.243774, .15`, `Blegen Hall` is the name of the route, `44.972392` is the longitude, `-93.243774` is the latitude, and `.15` (i.e., `0.15`) is the probability to generate a passenger at the stop.

#### Running the VTS Software
To run the VTS software, you have to first start the simulator module and then start the visualization module. To start the simulator module, go to `<dir>` and run `./gradlew appRun`. To start the visualization module, open a browser and paste this link `http://localhost:7777/project/web_graphics/project.html` in its search bar. To stop the simulator module, press the enter/return key in the terminal where you started the module. To stop the visualization module, close the tab of browser where you started the module. In rare occasions, you might experience some issues in starting the simulator module because a previous instance of the module is still running. To kill old instances, run `ps aux | grep gretty | awk '{print $2}' | xargs -L 1 kill` and this command will terminate previous instances. (The comman is killing the web server container running the simulator module.) The command works on VOLE3D and on the class VM.

#### Simulation Workflow
Because the VTS software is a web application, the software does not have a `main` method. When you load the visualization module in the browser, the visualization module opens a connection to the simulator module (using a websocket). The opening of the connection triggers the execution of the `MyWebServerSession.onOpen` method in the simulator module. When you click `Start` in the GUI of the visualization module, the module starts sending messages/commands to the simulator module. The messages/commands exchanged by the two modules are [JSON objects](https://www.w3schools.com/js/js_json_objects.asp). You can see the messages/commands created by the visualization module inside `<dir>/src/main/webapp/web_graphics/sketch.js`. The simulator module processes messages received by the visualization model inside the `MyWebServerSession.onMessage` method. The simulator module sends messages to the visualization module using the `MyWebServerSession.sendJson` method. Finally, once you start the simulation you can restart it only by reloading the visualization module in the browser (i.e., reloading the web page of the visualization module).

## Tasks and Deliverables
In this project iteration, you will need to further understand, extend, and test the VTS software. The tasks of this project iteration can be grouped into three types of activities: updating the software documentation, making software changes, and testing. The following table provides a summary of the tasks you need to perform in this project iteration. For each task, the table reports the task ID, the activity associated with the task, a summary description of the task, the deliverable associated with the task, and the major deliverable that includes the task deliverable.

| ID | Activity | Task Summary Description | Task Deliverable | Deliverable |
|---------|----------|--------------------------|------------------|----------------------|
| Task 1 | Software documentation | Update the UML class diagram describing the domain classes to account for the changes/additions that are required in this project iteration | UML Class Diagram | HTML Javadoc |
| Task 2 | Software documentation | Update the UML class diagram describing the web server classes to account for the changes/additions that are required in this project iteration | UML Class Diagram | HTML Javadoc |
| Task 3 | Software documentation | Create a Javadoc documentation for the code in the simulator module | HTML Javadoc | HTML Javadoc|
| Task 4 | Software documentation | Make sure that the code conforms to the Google Java code style guidelines | Source Code | Source Code |
| Task 5 | Software changes | Refactoring 1 - Random generation of small, regular, and large busses needs to use the factory method design pattern | Source Code | Source Code |
| Task 6 | Software changes | Feature 1 - Add "time of day" deployment strategies for small, regular, and large busses. This feature needs to use the factory method design pattern | Source Code | Source Code |
| Task 7 | Software changes | Feature 2 - Bus deployment selection | Source Code | Source Code |
| Task 8 | Software changes | Feature 3 - Display relevant bus information using the observer design pattern | Source Code | Source Code |
| Task 9 | Software changes | Feature 4 - Display relevant stop information using the observer design pattern | Source Code | Source Code |
| Task 10 | Testing | Create unit tests for all the domain classes | Test Code | Test Code |

### Suggested Timeline for the Tasks

We suggest you define a tasks-oriented timeline for your team so that you can make progress throughout this project iteration. The schedule for project iterations is very tight, and it is important that the team keeps up with the project. We suggest the following timeline. However, you are free to define your own timeline. All the major deliverables are due at the end of the project iteration.

| Date | Milestone Description | Tasks |
|-----------------|-----|-----------------|
| 11/09/2020 at 8:00 am | Software documentation and testing | [Task 1], [Task 2], [Task 10] |
| 11/16/2020 at 8:00 am | Software changes and software documentation | [Task 5],[Task 6],[Task 7],[Task 8],[Task 9],[Task 3],[Task 4] |
| 11/23/2020 at 8:00 am | Software testing, and revision of the software documentation | [Task 1],[Task 2],[Task 10],[Task 3],[Task 4] |

### Tasks Detailed Description
This section details the tasks that your team **needs to perform** in this project iteration.

#### Task 1: Update the UML class diagram describing the domain classes
In this task, you should update the UML class diagram (`<dir>/doc/diagrams/domain_diagram.png`) describing the domain classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should place the diagram as am image file in `<dir>/doc/diagrams`. The file should be called `domain_diagram` with the suitable extension for the type of image file your team created. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 2: Update the UML class diagram describing the web server classes
In this task, you should update the UML class diagram (`<dir>/doc/diagrams/webserver_diagram.png`) describing the web server classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should place the diagram as am image file in `<dir>/doc/diagrams`. The file should be called `webserver_diagram` with the suitable extension for the type of image file your team created. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 3: Create a Javadoc documentation for the code in the simulator module
You should create Javadoc comments according to the Google Java code style guidelines we provided. In other words, add comments where the Google Java code style guidelines require. You can generate the HTML Javadoc documentation by running `./gradlew clean javadoc`.

#### Task 4: Make sure that the code conforms to the Google Java code style guidelines
Consistency in code organization, naming conventions, file structure, and formatting makes code easier to read and integrate. In this project, your team will follow the Google Java code style guidelines. These guidelines are provided in the `<dir>/config/checkstyle/google_checks.xml` code style file. Your team needs to make sure that the code produced in this project iteration (both source and test code) complies with the rules in `<dir>/config/checkstyle/google_checks.xml`. You can check if the code conforms to the code style rules by running `./gradlew clean check`.

#### Task 5: Refactoring 1 - Random generation of small, regular, and large busses
This repo implements random generation of small, regular, and large busses without using any design patters. In this project iteration, you should refactor the code in this repo to generate small, regular, and large busses using the factory method design pattern. All the classes associated with this feature needs to be in the domain classes component (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`). Your team needs to create a GitHub issue to indicate that your team is working on this refactoring (in this project iteration we are using issues to work on features). The title of the issues should be "Refactoring 1 - Random generation of small, regular, and large busses". This task needs to be performed in a branch called `Refactoring1`. Your team needs to create a GitHub pull request to merge the changes into master. When your team is happy with the solution to this task, one of the team members needs to merge the `Refactoring1` branch into the `main` branch.

#### Task 6: Feature 1 - Add "time of day" deployment strategies for small, regular, and large busses
To enhance the simulator, your team needs to create the "time of day" bus deployment strategy. The "time of day" bus deployment strategy determines the size of the bus based on the time of the day. Specifically, this bus deployment strategy needs to adhere to the following specifications:

```
Determine the current local time
If the current local time is:
5am or later but before 8 am
		Deploy the bus using Strategy1
8am or later but before 4pm 
		Deploy the bus using Strategy2
4pm or later but before 9pm
		Deploy the bus using Strategy3
Otherwise
		Deploy a small bus
```

The deployment strategies you need to implement are as follows:

```
Strategy1 deploys busses in the following repeating sequence:
	small, regular, small, regular, etc. (the sequence keeps repeating)
Strategy2 deploys busses in the following repeating sequence:
	regular, large, regular, large, etc. (the sequence keeps repeating)
Strategy3 deploys busses in the following repeating sequence:
	small, regular, large, small, regular, large, etc. (the sequence keeps repeating)
```
Each time the simulation is required to create a new bus, it must use the type of bus returned by the strategy that is currently in effect. Note, when a strategy function/method is called for strategies 1, 2, and 3, it will return the next size bus in a repeating sequence. One way to implement this functionality is to enable each strategy to keep track of its state. You might find useful to use the `java.time.LocalDateTime` [class](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html) from the standard Java library to implement the feature associated with this task.

All the classes associated with this feature needs to be in the domain classes component (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`) and must be implemented using the factory method design pattern (this task needs to use "the same" factory method pattern designed and implemented for Task 5). Your team needs to create a GitHub issue to indicate that your team is working on this feature (in this project iteration we are using issues to work on features). The title of the issues should be "Feature 1 - Add "time of day" deployment strategies for small, regular, and large busses". This task needs to be performed in a branch called `Feature1`. Your team needs to create a GitHub pull request to merge the changes into master. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature1` branch into the `main` branch.

#### Task 7: Feature 2 - Bus deployment selection
Task 5 and 6 provide two ways to deploy busses. In this task, your team should make sure that the simulator uses the deployment method implemented in Task 5 during the 1st and the 15th day of the month. For the other days, the simulator needs to use the deployment method implemented in Task 6. You might find useful to look at the `java.time.LocalDateTime` [class](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html) from the standard Java library to implement this feature. Your team needs to create a GitHub issue to indicate that your team is working on this feature (in this project iteration we are using issues to work on features). The title of the issues should be "Feature 2 - Bus deployment selection". This task needs to be performed in a branch called `Feature2`. Your team needs to create a GitHub pull request to merge the changes into master. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature2` branch into the `main` branch.

#### Task 8: Feature 3 - Display relevant bus information

In this task, you need to extend the simulator module so that the VTS software is able to display relevant bus information in the rectangular box of visualization module (rectangular box with a black edge on the right of the map). The user can requests this information by clicking on a bus in the visualization module. The visualization module should display the bus id (`ID`), the position of the bus (`X` and `Y`), the number of passengers on the bus (`NUM_PASS`), and the bus capacity (`CAP`). Only the information of one bus should be display at a time. The information displayed in the visualization module for a bus must have the following format:

```
Bus ID
-----------------------------
* Position: (X,Y)
* Passengers: NUM_PASS
* Capacity: CAP
```

This feature must be implemented using the observer design pattern. This feature must also suitably extend the command pattern used by other parts of the simulator. This feature must respond to the `listenBus` command that "carries" the `id` of the observed bus. Your team needs to create a GitHub issue to indicate that your team is working on this feature (in this project iteration we are using issues to work on features). The title of the issues should be "Feature 3 - Display relevant bus information". This task needs to be performed in a branch called `Feature3`. Your team needs to create a GitHub pull request to merge the changes into master. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature3` branch into the `main` branch.

#### Task 9: Feature 4 - Display relevant stop information
In this task, you need to extend the simulator module so that the VTS software is able to display relevant stop information in the rectangular box of visualization module (rectangular box with a black edge on the right of the map). (If the user selects to "observe" both a bus and a stop, the simulator should display both information.) The user can requests this information by selected the stop in the drop down menu on the left of the map (below the `Start` and `Pause`/`Resume` buttons). The visualization module should display the stop id (`ID`), the position of the stop (`X` and `Y`), and the number of people waiting at the stop (`NUM_PEOPLE`). Only the information of one stop should be display at a time. (Bus and stop information can be displayed at the same time.) The information displayed for a stop in the visualization module must have the following format:

```
Stop ID
-----------------------------
* Position: (X,Y)
* Number of People: NUM_PEOPLE
```

This feature must be implemented using the observer design pattern. This feature must also suitably extend the command pattern used by other parts of the simulator. This feature must respond to the `listenStop ` command that "carries" the `id` of the observed stop. Your team needs to create a GitHub issue to indicate that your team is working on this feature (in this project iteration we are using issues to work on features). The title of the issues should be "Feature 4 - Display relevant stop information". This task needs to be performed in a branch called `Feature4`. Your team needs to create a GitHub pull request to merge the changes into master. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature4` branch into the `main` branch.

#### Task 10: Create unit tests for all the domain classes
In this project iteration, your team needs to create unit tests for all the domain classes. We are interested in seeing at least one test cases per method and your team does not need to create test cases for getter and setter methods. Your team has to document what each test is supposed to do by adding a Javadoc comment to the test. A sample set of test cases is provided in the `<dir>/src/test/java/edu/umn/cs/csci3081w/project/model` folder. In this task, your team can also reuse (and we encourage you to do so) the test cases that the team built during project iteration 1. We encourage your team to write the test cases before making any change to the codebase. These tests should all pass. After creating the test cases, you can perform the refactoring task and use the tests to ensure that your team did not introduce errors in the code. (Some test cases might also need refactoring.) When you add the new features, you should also add new test cases for the features (when applicable). All the test cases you create should pass. Your team can create test cases in any branch but the final set of test cases should be in the `main` branch. You can run tests with the command `./gradlew clean test`.

#### Important Notes

To complete the tasks of this project iteration, your team can reuse any of the design documents, code, and tests that your team created in project iteration 1. However, beware of the following points:

* You might have added classes, methods, and attributes that are not present in this repo.
* You should preserve and extend the functionality provided by this repo. In particular, this repo **does not** implement the functionality of skipping stops and that feature **must not** be implemented in the solution for this project iteration.
* Small, regular, and large busses must be implemented using three different classes.
* You should design and implement your classes for Tasks 6, and 7 so that you are able to test them. In other words, you need to be able to set the day and the time used by the simulator for testing purposes. (You **do not** need to test the random generation of busses.)

## Submission
**All the team members should submit the commit ID of the solution to this project iteration on Gradescope**. The commit ID should be from the `main` branch of this repo. We use the commit ID to be sure that all team members agree on the final solution submitted.

### General Submission Reminders
* Use GitHub issues and pull requests as you develop your solution.
* Use the branches we listed to produce your team solution.
* Make sure the files of your solution are in the repo.
* Do no add the content of the `build` directory to the repo.
* Make sure your code compiles.
* Make sure the code conforms to the Google Java code style guidelines we provided.
* Make sure the HTML Javadoc documentation can be generated using `./gradlew clean javadoc`
* Make sure all test cases pass.

## Assessment
The following list provides a breakdown of how this project iteration will be graded.

* Software documentation: 28 points
* Software changes: 52 points
* Testing: 20 points

## Resources

* [A Guide to the Java API for WebSocket](https://www.baeldung.com/java-websockets)
* [JSON objects](https://www.w3schools.com/js/js_json_objects.asp)
* [Google Maps](http://maps.google.com)
* [LocalDateTime class from the standard Java library](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html)
