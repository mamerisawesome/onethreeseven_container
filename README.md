# Shot!

## Requirements

### Java 1.7 or higher
Language for development of the game.
```sh
sudo apt-get install java
```

### Grunt
Taskrunner for the application that will aide in automating tasks.
```sh
sudo apt-get install grunt-cli
```

### IDEs

#### Visual Studio Code
Preferred IDE for convenience of running Grunt tasks as it is not bloated.
```sh
sudo wget http://go.microsoft.com/fwlink/?LinkID=534108 
```

#### Alternatives
- [Eclipse](https://eclipse.org/downloads/)
- [Jetbeans IntelliJ](https://download.jetbrains.com/idea/ideaIU-2016.2.5.tar.gz)

## Taskrunners Available

### Project Initialization
Before starting the project, initialize the modules and directories necessary for the application to work properly. Grunt will be used alongside the project for tasks.
```grunt
grunt exec
```

### Source Compilation
To compile all java files within the project, use the 'run_java' task. This task also starts the server on port 8080.
```grunt
grunt run_java
```

### Grunt Testing
To check if grunt is working fine without jeopardizing the application, use the default task within the project.
```grunt
grunt default || grunt
```
