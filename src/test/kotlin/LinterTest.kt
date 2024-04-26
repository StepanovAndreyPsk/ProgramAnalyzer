import analyzer.FileParser
import linter.Linter
import linter.LinterWarning
import linter.rules.IsInCamelCase
import linter.rules.NamingRule
import linter.rules.StartsWithLowercaseLetter
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LinterTest {
    @Test
    fun `function naming warnings test`() {
        val linter = Linter("src/test/resources/linter/FunctionNamingExample.kt")
        linter.runChecks()
        val warnings: List<LinterWarning> = linter.getWarnings()

        assertEquals(6, warnings.size)

        checkWarning(
            warnings[0],
            "FunctionStartsWithUppercaseLetter",
            "functionStartsWithUppercaseLetter",
            listOf(StartsWithLowercaseLetter)
        )

        checkWarning(
            warnings[1],
            "function_in_camelcase",
            "functionInCamelcase",
            listOf(IsInCamelCase)
        )

        checkWarning(
            warnings[2],
            "Very_wrong_named_function",
            "veryWrongNamedFunction",
            listOf(
                StartsWithLowercaseLetter,
                IsInCamelCase
            )
        )

        checkWarning(
            warnings[3],
            "MethodStartsWithUppercase",
            "methodStartsWithUppercase",
            listOf(StartsWithLowercaseLetter)
        )

        checkWarning(
            warnings[4],
            "method_in_camelcase",
            "methodInCamelcase",
            listOf(IsInCamelCase)
        )

        checkWarning(
            warnings[5],
            "Very_wrong_named_method",
            "veryWrongNamedMethod",
            listOf(
                StartsWithLowercaseLetter,
                IsInCamelCase
            )
        )


        println(linter.generateReport())
    }

    private fun checkWarning(
        warning: LinterWarning,
        expectedOriginalName: String,
        expectedSuggestedName: String,
        expectedViolatedRules: List<NamingRule>
    ) {
        val (actualOriginalName, actualSuggestedName, actualViolatedRules) = warning
        assertEquals(expectedOriginalName, actualOriginalName)
        assertEquals(expectedSuggestedName, actualSuggestedName)
        assertEquals(expectedViolatedRules, actualViolatedRules)
    }
}