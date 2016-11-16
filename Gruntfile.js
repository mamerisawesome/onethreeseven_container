module.exports = function(grunt) {

  grunt.initConfig({
    exec: {
      setup_project: {
        stdout: true,
        stderr: true,
        command: [
          "mkdir -p bin",
          "npm install"
        ].join(" && ")
      }
    },
    run_java: {
      options: {
        stdout: true,
        stderr: true,
        stdin: true,
        failOnError: true
      },
      compile_server: {
        execOptions:{
          cwd: "."
        }, 
        command: "javac",
        sourceFiles: ["src/chat_module/Server.java"],
        javaOptions: {
          "d": "./bin/chat_server"
        },
      },
      compile_client: {
        execOptions:{
          cwd: "."
        }, 
        command: "javac",
        sourceFiles: ["src/chat_module/Client.java", "src/chat_module/Chat.java"],
        javaOptions: {
          "d": "./bin/chat_client"
        },
      },
      start_server: {
        command: function () {
          return "cd bin/chat_server && java Server 8080"
        }
      },
    },
  });

  grunt.loadNpmTasks("grunt-run-java");
  grunt.loadNpmTasks('grunt-exec');

  grunt.registerTask("default", "Desc", function () {
    grunt.log.writeln(JSON.stringify({"message":"Hello"}));
  });

  var sh = require("shelljs");
  grunt.registerTask("server_start", "Starts Server", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("javac src/chat_module/Server.java -d bin/chat_server");
    sh.cd("bin/chat_server");
    sh.exec("java Server");
  });
  
  grunt.registerTask("client_start", "Starts Client", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("javac src/chat_module/Client.java src/chat_module/Chat.java -d bin/chat_client");
    sh.cd("bin/chat_client");
    sh.exec("java Client");
  });
  
  grunt.registerTask("jarify_server", "Starts Server", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("mkdir -p output");

    sh.exec("javac src/chat_module/Server.java -d bin/chat_server");
    
    sh.cd("bin/chat_server");
    sh.exec("jar cvfm ../../output/Server.jar ../../manifest/MANIFEST_server.txt *.class");
  });

  grunt.registerTask("jarify_client", "Starts Client", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("mkdir -p output");

    sh.exec("javac src/chat_module/Client.java src/chat_module/Chat.java -d bin/chat_client");

    sh.cd("bin/chat_client");
    sh.exec("jar cvfm ../../output/Client.jar ../../manifest/MANIFEST_client.txt *.class");
  });

};