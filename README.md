## Complexity analyzer for Kotlin and Java 
### Description
Command line application for analyzing kotlin files. 

### Usage example
![](demo.gif)

### Outline
1. Parse Kotlin or Java files using kotlin compiler parser -> get PSI format as an output (class `FileParser`)
2. Filter out functions and methods from the parsed PSI elements (`FileParser.parseFunctions()`).
3. Iterate through the methods and functions and for each of them calculate the "complexity" (the number of conditional statements). Store the result for each function. (class `ComplexityAnalyzer`)
4. Sort the results based on the complexity score in descending order and get the subarray of the needed size as an output (`ComplexityAnalyzer.getMostComplexFunctions()`). 