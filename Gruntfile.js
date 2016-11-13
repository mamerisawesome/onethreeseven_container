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
      your_target: {
        execOptions:{
          cwd: "."
        }, 
        command: "javac",
        sourceFiles: ["src/chat_module/*"],
        javaOptions: {
          "d": "./bin"
        },
      },
    },
  });

  grunt.loadNpmTasks("grunt-run-java");
  grunt.loadNpmTasks('grunt-exec');

  grunt.registerTask("default", "Desc", function () {
    grunt.log.writeln(JSON.stringify({"message":"Hello"}));
  });

};