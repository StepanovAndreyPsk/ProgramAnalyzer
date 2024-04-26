package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import linter.Linter
import java.io.File
import cli.styles.*
import linter.LinterReport

class CheckCodestyleCommand: CliktCommand(
    name = "check-codestyle",
    help = "Checks that functions, variables and classes named according to Kotlin naming convention"
) {
    private val directoryPath by argument()

    override fun run() {
        validatePathToDirectory(directoryPath)
        val directory = File(directoryPath)
        directory.listFiles()?.forEach { file ->
            if (file.extension == "kt") {
                echo("${progress("Checking codestyle in")} ${hyperlink(file.absolutePath)}")
                checkCodestyle(file.path)
            }
        }
    }

    private fun checkCodestyle(filePath: String) {
        val linter = Linter(filePath)
        linter.runChecks()
        val warnings = linter.getWarnings()
        if (warnings.isEmpty()) {
            echo(success("No warnings were generated!\n"))
        }
        for (warning in warnings) {
            echo(warning(warning.toString()))
        }
        echo(progress("Generating report for ${hyperlink(filePath)}"))
        val report = linter.generateReport()
        printReport(report)
    }

    private fun printReport(report: LinterReport) {
        echo("${reportProperty("Overall number of functions")}: ${report.overallFunctionNumber}")
        echo("${reportProperty("Number of functions violating naming convention")}: ${report.functionViolationNumber}\n")
        echo("${reportProperty("Overall number of variables")}: ${report.overallVariableNumber}")
        echo("${reportProperty("Number of variables violating naming convention")}: ${report.variableViolationNumber}\n")
        echo("${reportProperty("Overall number of classes")}: ${report.overallClassNumber}")
        echo("${reportProperty("Number of classes violating naming convention")}: ${report.classViolationNumber}\n")

        val percentageViolations = report.violationPercentage
        var percentageStyle = percentageLow
        if (percentageViolations > 50) {
            percentageStyle = percentageHigh
        }
        echo(percentageStyle("Percentage of violations: $percentageViolations%\n\n"))
    }
}
