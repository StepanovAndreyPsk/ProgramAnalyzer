## Complexity analyzer for Kotlin and Java 
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

### Complexity analysis
Application calculates _complexity score_ for each function or method in kotlin files located in the specified directory. For simplicity _complexity score_ here is just the number of conditional statements in a function. As an output
application provides the list of the function names with the biggest _complexity score_ alongside with there complexity scores. The number of functions to output can be changed by the option `--count` of the `calculate-complexity` command. 

### Codestyle check
Application also has a feature of checking whether the program elements satisfy [Kotlin naming convention](https://kotlinlang.org/docs/coding-conventions.html#naming-rules) 
It checks the names of variables, functions and classes and outputs warnings if any violations detected. The warnings contain information about the violating variable, function or class name and a proposed name according to the convention regulations.

After listing the warnings (if present) command `check-codestyle` outputs generated report containing the number of functions, variables and classes as well as the number of them that violate the convention. Finally, it provides with the overall percentage of violations.  

### Libraries used in the application
* [__Kotlin-compiler__](https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-compiler-embeddable) for parsing kotlin files.
* [__Clikt__](https://ajalt.github.io/clikt/) for command line interface development and user input validation.
* [__Mordant__](https://ajalt.github.io/mordant/) for making intuitive and visually appealing commandline output.

### Outline
1. Parse Kotlin or Java files using kotlin compiler parser -> get PSI format as an output (class `FileParser`)
2. Filter out functions and methods from the parsed PSI elements (`FileParser.parseFunctions()`).
3. Iterate through the methods and functions and for each of them calculate the "complexity" (the number of conditional statements). Store the result for each function. (class `ComplexityAnalyzer`)
4. Sort the results based on the complexity score in descending order and get the subarray of the needed size as an output (`ComplexityAnalyzer.getMostComplexFunctions()`). 