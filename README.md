# Assignment 2

Our goal was to develop a movie ticket booking system for FancyCinemas, with the purpose of reducing waiting time for customers + providing them with the choice of viewing/booking movies online.

### Sprint 3

- Our main focus for this sprint was to integrate booking tickets, transaction and different user roles together. Reports were generated for staff and managers. By the end of the sprint, we finalised our product and was able to successfully demonstrate to the stakeholder.

### Important!!!

Command to cleanly run program: first run "gradle clean build" to generate jar files, and run "java -jar app/build/libs/app-all.jar" to make the password masking work correctly.

To check code coverage + build history, go here: http://139.180.183.109/job/SOFT2412ASS2/213/

### COMMAND PROCESS

1. **FOLLOW THIS TO GRAB NEWEST CHANGES FROM GITHUB**, merging with your local branch files!!

* git checkout master --> git pull —> git checkout your_branch_name --> git merge master
 
2. **FOLLOW THIS TO SUBMIT ONLY YOUR BRANCH/PART ONTO GITHUB!!**

* git add . (adds all your changes) OR git add (file) --> git commit -m “message” → git push -u origin branch_name

3. **FOLLOW THIS TO MERGE UR CHANGES TO MASTER!!** [ALWAYS pull before pushing!]

* git pull (this prevents merge conflicts) -> git checkout master --> git merge branch_name --> git push

4. **WHAT IF I ACCIDENTALLY ADDED FILES BUT ADDED TO THE WRONG PLACE?**

* E.g. You accidentally added to master but wanted to add to your branch (haven't committed yet) --> UNDO action by using: git reset <file>
 
### Working with Gradle

Check the build.gradle file for more details on plugins, dependencies used and test.
- `gradle run` --> builds + compiles all your files
- `gradle clean build`  --> initialises directory for storing reports
- `gradle clean` --> cleans up any leftovers from previous builds + prevents getting side effects like causing build to fail
- `gradle test`  --> compiles and runs your test files
- `gradle test JacocoTestReport` --> generates a report where you can see how much coverage you've done
- `gradle javadoc` --> generates a set of HTML pages of API documentation


### Tools used

- Check miro for latest UML + HiFi UI diagrams: https://miro.com/app/board/o9J_lyZ2s-c=/?fromRedirect=1

- Jira boards, backlogs + report: https://soft2412-r18g2.atlassian.net/jira/software/projects/A2/boards/1

