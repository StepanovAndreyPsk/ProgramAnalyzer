import kotlin.test.Test
import kotlin.test.assertEquals
import analyzer.ComplexityAnalyzer
import analyzer.FunctionComplexity

internal class ComplexityAnalyzerTest {

    @Test
    fun `basic test`() {
        val analyzer = ComplexityAnalyzer("src/test/resources/complexity/basic_example.kt")
        val analyzerResult = analyzer.getMostComplexFunctions()
        assertEquals(3, analyzerResult.size)

        val (top1, top2, top3) = analyzerResult

        assertEquals(FunctionComplexity("functionWith3ConditionalStatements", 3), top1)
        assertEquals(FunctionComplexity("functionWith2ConditionalStatements", 2), top2)
        assertEquals(FunctionComplexity("functionWith1ConditionalStatement", 1), top3)
    }

    @Test
    fun `nested conditional statements test`() {
        val analyzer = ComplexityAnalyzer("src/test/resources/complexity/nested_conditional_statements.kt")
        val analyzerResult = analyzer.getMostComplexFunctions(1)
        assertEquals( 1, analyzerResult.size)

        val (top1) = analyzerResult
        assertEquals(FunctionComplexity("nested", 3), top1)
    }

    @Test
    fun `class methods test`() {
        val analyzer = ComplexityAnalyzer("src/test/resources/complexity/class_methods.kt")
        val analyzerResult = analyzer.getMostComplexFunctions(4)
        assertEquals(4, analyzerResult.size)

        val (top1, top2, top3, top4) = analyzerResult
        assertEquals(FunctionComplexity("divisionBy4", 4), top1)
        assertEquals(FunctionComplexity("privateFunction", 3), top2)
        assertEquals(FunctionComplexity("protectedMethod", 1), top3)
        assertEquals(FunctionComplexity("gcd", 1), top4)
    }
}