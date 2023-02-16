# Irrigation API

### Implementaion Assumptions
The following assumptions are made:

*  time slot is implemented as a repetitive weekly schedule. For example if you schedule an irrigation for Monday at time 16:00:00(4pm), it will run every Monday at same time unless changed.
* you can configure more than one schedule for a given day. e.g Monday 08:00:00(8am) and Monday 16:00:00(4pm). It means irrigation will run twice on Monday at 8am and 4pm.
* volume of water is pre-calculated and simply registered on the system during land setup. (It can be automatic though)

### Implementaion Stack
* Programming Language: Java
* Database : h2(Memory DB)
* Framework: Spring Boot

### Possible Improvements
* Add audit log to keep history of all the irrigations
* add created_at and modified_at to tables Land and Schedule and fields that could be used to calculate volume of water needed automatically

### How to run
1. Clone the project
2. Open the project with Visual Studio Code
3. Install REST Client https://marketplace.visualstudio.com/items?itemName=humao.rest-client
4. Open terminal in Visual Studio Code to the project root directory and then run command **./mvnw spring-boot:run** . Then wait a few secs for the app to spin up
5. Open the **requests** folder and then run the requests in the landRequest.http. Please make sure you complete step 3 else you will not be able to test the endpoints.

