## Complexity analyzer for Kotlin  
### Description
Command line application for analyzing kotlin files. 

### Demo
![](demo.gif)

### Running instructions
```bash
gradle wrapper
./analyzer --help # outputs the list of supported commands
```

### Example Usage
Directories `examples/complexity` and `examples/linter` contains sample kotlin files to demonstrate the main features of the application.

Bash script `analyzer` provides conciseness running of the application. You can always just use `./gradlew run --args="<command> [<options>] <args> "` to run the application.

To quickly check out the application performance just run:
```bash
./analizer "calculate-complexity examples/complexity"   # for complexity analysis or
./analyser "check-codestyle examples/linter"            # for codestyle analysis
```

### Running tests
Tests can be run by the command
```bash
./gradlew test
```
### Features
#### Complexity analysis
Application calculates _complexity score_ for each function or method in kotlin files located in the specified directory. For simplicity _complexity score_ here is just the number of conditional statements in a function. As an output
application provides the list of the function names with the biggest _complexity score_ alongside with there complexity scores. The number of functions to output can be changed by the option `--count` of the `calculate-complexity` command. 

#### Codestyle check
Application also has a feature of checking whether the program elements satisfy [Kotlin naming convention](https://kotlinlang.org/docs/coding-conventions.html#naming-rules) 
It checks the names of variables, functions and classes and outputs warnings if any violations detected. The warnings contain information about the violating variable, function or class name and a proposed name according to the convention regulations.

After listing the warnings (if present) command `check-codestyle` outputs generated report containing the number of functions, variables and classes as well as the number of them that violate the convention. Finally, it provides with the overall percentage of violations.  

### Libraries used in the application
* [__Kotlin-compiler__](https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-compiler-embeddable) for parsing kotlin files.
* [__Clikt__](https://ajalt.github.io/clikt/) for command line interface development and user input validation.
* [__Mordant__](https://ajalt.github.io/mordant/) for making intuitive and visually appealing commandline output.

### Internal design of the application
Application was designed using object-oriented programming paradigm with respect to SOLID design principles. During the development process code clarity, conciseness and readability were prioritized. 

* Class `analyzer.FileParser` is responsible for parsing kotlin files and providing PSI format as an output to allow further work with program elements. 
* Class `analyzer.ComplexityAnalyzer` is responsible for evaluating the complexity score of functions and maintaining the list of the functions with the biggest complexity score.
* Class `linter.LinterPsiVisitor` is an implementation of visitor pattern. It allows to generate warnings depending on the type of the program element (variable, function or class)
* Interface `linter.rules.NamingRule` is an abstraction that includes three abstract methods: `check` (to check the corresponding naming rule), `apply` (to suggest how to change provided name to satisfy the convention) and `warn` to 
generate a correct warning for further output to the user.
* You can find different implementation of `NamingRule` in the file `linter/rules/NamingRules.kt` reflecting most of the convention principles.
* Class `linter.Linter` is responsible for running naming checks (based on the naming rules mentioned earlier) and generating final report.
* Classes in package `cli` are responsible for command line commands.

[//]: # (1. Parse Kotlin or Java files using kotlin compiler parser -> get PSI format as an output &#40;class `FileParser`&#41;)
[//]: # (2. Filter out functions and methods from the parsed PSI elements &#40;`FileParser.parseFunctions&#40;&#41;`&#41;.)
[//]: # (3. Iterate through the methods and functions and for each of them calculate the "complexity" &#40;the number of conditional statements&#41;. Store the result for each function. &#40;class `ComplexityAnalyzer`&#41;)
[//]: # (4. Sort the results based on the complexity score in descending order and get the subarray of the needed size as an output &#40;`ComplexityAnalyzer.getMostComplexFunctions&#40;&#41;`&#41;. )