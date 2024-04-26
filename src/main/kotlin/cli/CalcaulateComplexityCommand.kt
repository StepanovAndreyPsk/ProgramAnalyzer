package cli

import analyzer.ComplexityAnalyzer
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.rendering.*
import java.io.File
import cli.styles.*

class CalculateComplexityCommand: CliktCommand(
    name = "calculate-complexity",
    help = "Calculates complexity score for all functions and methods in the given file " +
            "and returns the names of the functions with the most complexity score"
) {
    private val directoryPath by argument(help = "Directory path where to find kt and java files to analyze", name = "directory-path")
    private val count by option(help="Number of functions with the most complexity that will be given as output (default 3)").int().default(3)
    override fun run() {
        val directory = File(directoryPath)
        validatePathToDirectory(directoryPath)
        directory.listFiles()?.forEach { file ->
            if (file.extension == "kt") {
                echo(progress("Analyzing complexity scores for functions/methods in ${hyperlink(file.absolutePath)}"))
                analyze(file.path)
                echo('\n')
            }
        }
    }

    private fun analyze(filePath: String) {
        val complexityAnalyser = ComplexityAnalyzer(filePath)
        val analyzerResult = complexityAnalyser.getMostComplexFunctions(count)
        echo(progress("Printing functions with the biggest complexity score"))
        for (record in analyzerResult) {
            echo("`${reportProperty(record.functionName)}`: complexity score = ${record.complexityScore}")
        }
    }



}