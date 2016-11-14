# Shot!

## Requirements
- Grunt
```sh
sudo apt-get install grunt-cli
```
- Java 1.7 or higher
```sh
sudo apt-get install java
```
- Visual Studio Code
```sh
sudo wget http://go.microsoft.com/fwlink/?LinkID=534108 
```

## Taskrunners Available

Before starting the project, initialize the modules and directories necessary for the application to work properly. Grunt will be used alongside the project for tasks.
```grunt
grunt exec
```

To compile all java files within the project, use the 'run_java' task. This task also starts the server on port 8080.
```grunt
grunt run_java
```

To check if grunt is working fine without jeopardizing the application, use the default task within the project.
```grunt
grunt default || grunt
```
