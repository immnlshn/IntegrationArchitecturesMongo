# High Performance Application [HPE]

## About
In this repository the implementation of the first assignment in the Lecture **Integration Architectures** at the Hochschule Bonn-Rhein-Sieg is stored. The assignment was about implementing a Interface and adding full CRUD functionality to it.

For the backend we had to use MongoDB and implement everything using Java.

Goal of the assignment was to get familiar with [MongoDB](https://www.mongodb.com/).

--------------------------------------------
### Features
- **Adding** Salesmen
- **Reading** Salesmen
- **Deleting** Salesmen
- **Adding** goals to Salesmen
- **Removing** goals from Salesmen
- **Reading** goals from Salesmen

--------------------------------------------

### Commands
**Salesman**
- ``create-salesman``/``cs``: Creates a new Salesman
- ``read-salesman``/``rs``: Reads a Salesman
- ``delete-salesman``/``ds``: Deletes a Salesman
- ``read-all-salesmen``/``ras``: Reads all Salesmen

**Goals**
- ``read-goals``/``rg``: Reads all goals of a Salesman
- ``add-goal``/``ag``: Adds a goal to a Salesman
- ``delete-goal``/``dg``: Removes a goal from a Salesman

--------------------------------------------

### Questions:
Here are the questions that we were asked during the assignment and the answers to them.

**Are there any CRUD-operations missing?**
- Yes, the following operations ``Delete (D)`` and ``Update (U)`` were missing.
- In the assignment was stated that we only have to provide update functionality when we add or remove a goal to/from a salesman.

**How do you integrate the year?**
- The year is implemented into the ``SocialPerformanceRating``-Object as an Integer-Attribute. We did not add any special type.

--------------------------------------------

## Installation
To install the project you need to have the following tools installed:
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MongoDB](https://www.mongodb.com/try/download/community) or use a [Docker Container](https://hub.docker.com/_/mongo)

--------------------------------------------

### Dependencies
The project uses the following dependencies:
- [MongoDB Java Legacy Driver (4.1.0)](https://mongodb.github.io/mongo-java-driver/4.1/apidocs/)
- [Spring Boot (2.7.18)](https://docs.spring.io/spring-boot/docs/2.7.x/reference/htmlsingle/)
- [Spring Shell (2.1.15)](https://docs.spring.io/spring-shell/docs/2.1.0/site/reference/htmlsingle/)
- [JUnit (5.8.1)](https://junit.org/junit5/)

--------------------------------------------

### Running the Application
To run the application just open a terminal at the ``.jar``-file location and run the following command:
```shell
java -jar hpe.jar
```

--------------------------------------------

### Setup
1. Clone the repository
2. Open the project in your favorite IDE
3. Run the project
4. Use the commands to interact with the application
5. Have fun!
