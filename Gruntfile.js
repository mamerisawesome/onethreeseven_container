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
        sourceFiles: ["src/com/chat_module/Server.java"],
        javaOptions: {
          "d": "./bin/chat_server"
        },
      },
      compile_client: {
        execOptions:{
          cwd: "."
        },
        command: "javac",
        sourceFiles: ["src/com/chat_module/Client.java", "src/com/chat_module/Client_Chat.java"],
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
  var port = grunt.option("port");
  var addr = grunt.option("addr");
  var nump = grunt.option("nump");
  var name = grunt.option("name");

  var command = "javac";

  var modules = [
    "src/com/chat_module/*.java",
    "src/com/game_module/*.java",
    "src/com/project/*.java",
  ].join(" ");

  var lib = [
    "-cp",
    "\".;/lib/lwjgl/lwjgl.jar;/lib/slick/slick.jar\"",
  ].join(" ");

  var out = [
    "-d ."
  ].join(" ");

  sh.exec([command, lib, modules, out].join(" "));

  grunt.registerTask("pserver", "Starts Server", function () {
    if (!nump) {
      return console.error("[OOPS] --nump missing");
    }

    sh.exec(["java", "bin/com/project/Server", nump].join(" "));
  });

  grunt.registerTask("pclient", "Starts Project", function () {
    if (!addr || !name) {
      return console.error("[OOPS] --addr or --name missing");
    }

    sh.exec(["java", "bin/com/project/Screen", addr, name].join(" "));
  });

  // JAR TASKS
  grunt.registerTask("jarify_server", "Starts Server", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("mkdir -p output");

    sh.exec("javac src/com/chat_module/Server.java -d bin/chat_server");

    sh.cd("bin/chat_server");
    sh.exec("jar cvfm ../../output/Server.jar ../../manifest/MANIFEST_server.txt *.class");
  });

  grunt.registerTask("jarify_client", "Starts Client", function () {
    sh.exec("mkdir -p bin");
    sh.exec("mkdir -p bin/chat_server");
    sh.exec("mkdir -p output");

    sh.exec("javac src/com/chat_module/Client.java src/com/chat_module/Client_Chat.java -d bin/chat_client");

    sh.cd("bin/chat_client");
    sh.exec("jar cvfm ../../output/Client.jar ../../manifest/MANIFEST_client.txt *.class");
  });

};
