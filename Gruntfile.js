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
  sh.cp("-r", "lib", ["bin"]);

  // CHAT TASKS
  var port = grunt.option("port");
  var addr = grunt.option("addr");
  var nump = grunt.option("nump");
  var name = grunt.option("name");
  grunt.registerTask("schat", "Starts Chat Server", function () {
    sh.exec("javac src/com/chat_module/Server*.java src/com/chat_module/Chat*.java -d .");
    sh.exec("java bin/com/chat_module/Server");
  });

  grunt.registerTask("cchat", "Starts Chat Client", function () {
    if (!addr || !name) {
      sh.echo("[OOPS] Usage: grunt cchat --addr=server_address --name=name_of_user");
    } else {
      sh.exec("javac src/com/chat_module/Client*.java src/com/chat_module/Chat*.java -d .");
      sh.exec("java bin/com/chat_module/Client " + addr + " " + name);
    }
  });

  // GAME TASKS
  grunt.registerTask("sgame", "Starts Server Game", function () {
    if (!nump) {
      sh.echo("[OOPS] Usage: grunt sgame --nump=number_of_players");
    } else {
      sh.exec("javac src/com/game_module/Server.java src/com/game_module/Game*.java -d .");
      sh.exec("java bin/com/game_module/Server " + nump);
    }
  });

  grunt.registerTask("cgame", "Starts Client Game", function () {
    if (!addr || !name) {
      sh.echo("[OOPS] Usage: grunt sgame --addr=address_of_server --name=name_of_player");
    } else {
      sh.exec("javac src/com/game_module/Client.java src/com/game_module/Game*.java -d .");
      sh.exec("java bin/com/game_module/Client " + addr + " " + name);
    }
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
