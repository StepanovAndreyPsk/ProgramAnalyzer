package analyzer;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpression

class ComplexityAnalyzer(filePath: String) {
    private val complexity = ArrayList<FunctionComplexity>()
    init {
        val fileParser = FileParser(filePath)
        val functions = fileParser.parseFunctions()
        functions.forEach {  func ->
            val complexityScore = func.getComplexityScore()
            complexity.add(FunctionComplexity(func.name ?: "<anonymous function>", complexityScore)
            )
        }
    }

    fun getMostComplexFunctions(k: Int = DEFAULT_NUMBER_OF_FUNCTIONS): List<FunctionComplexity> {
        if (complexity.size < k) {
            throw RuntimeException("Not enough functions/methods in the file! Expected at least: $k, got:${complexity.size}")
        }
        return complexity.sortedDescending().subList(0, k)
    }

    private fun KtFunction.getComplexityScore(): Int {
        return countConditionalStatements(this)
    }

    private fun countConditionalStatements(psiElement: PsiElement): Int {
        var counter = 0
        if (psiElement.isConditionalExpression()) {
            counter++
        }
        for (childElement in psiElement.children) {
            counter += countConditionalStatements(childElement)
        }
        return counter
    }

    private fun PsiElement.isConditionalExpression(): Boolean =
        when(this) {
            is KtIfExpression -> true
            is KtWhileExpression -> true
            is KtForExpression -> true
            is KtWhenExpression -> true
            else -> false
        }

    companion object {
        const val DEFAULT_NUMBER_OF_FUNCTIONS = 3
    }
}

data class FunctionComplexity(val functionName: String, val complexityScore: Int): Comparable<FunctionComplexity> {
    override fun compareTo(other: FunctionComplexity): Int {
        return if (this.complexityScore != other.complexityScore) {
            this.complexityScore.compareTo(other.complexityScore)
        }
        else {
            this.functionName.compareTo(other.functionName)
        }
    }

    override fun toString(): String {
        return "`$functionName` : $complexityScore"
    }
}


