import kotlin.test.Test
import kotlin.test.assertEquals
import analyzer.ComplexityAnalyzer
import analyzer.FileParser
import analyzer.FunctionComplexity

internal class ComplexityAnalyzerTest {

    @Test
    fun basicTest() {
        val fileParser = FileParser("src/test/resources/basic_example.kt")
        val functions = fileParser.parseFunctions()
        assertEquals( 3, functions.size)

        val analyzer = ComplexityAnalyzer(functions)
        val analyzerResult = analyzer.getMostComplexFunctions()
        assertEquals(3, analyzerResult.size)

        val (top1, top2, top3) = analyzerResult

        assertEquals(FunctionComplexity("functionWith3ConditionalStatements", 3), top1)
        assertEquals(FunctionComplexity("functionWith2ConditionalStatements", 2), top2)
        assertEquals(FunctionComplexity("functionWith1ConditionalStatement", 1), top3)
    }

    @Test
    fun nestedConditionalStatementsTest() {
        val fileParser = FileParser("src/test/resources/nested_conditional_statements.kt")
        val functions = fileParser.parseFunctions()
        assertEquals(1, functions.size)

        val analyzer = ComplexityAnalyzer(functions)
        val analyzerResult = analyzer.getMostComplexFunctions(1)
        assertEquals( 1, analyzerResult.size)

        val (top1) = analyzerResult
        assertEquals(FunctionComplexity("nested", 3), top1)
    }

    @Test
    fun classMethodsTest() {
        val fileParser = FileParser("src/test/resources/class_methods.kt")
        val functions = fileParser.parseFunctions()
        assertEquals( 5, functions.size)

        val analyzer = ComplexityAnalyzer(functions)
        val analyzerResult = analyzer.getMostComplexFunctions(4)
        assertEquals(4, analyzerResult.size)

        val (top1, top2, top3, top4) = analyzerResult
        assertEquals(FunctionComplexity("divisionBy4", 4), top1)
        assertEquals(FunctionComplexity("privateFunction", 3), top2)
        assertEquals(FunctionComplexity("protectedMethod", 1), top3)
        assertEquals(FunctionComplexity("gcd", 1), top4)
    }
}