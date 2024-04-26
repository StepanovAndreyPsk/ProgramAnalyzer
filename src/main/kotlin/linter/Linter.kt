package linter

import analyzer.FileParser
import linter.rules.NamingRule
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.*
import java.math.RoundingMode
import java.text.DecimalFormat

class Linter(filePath: String) {
    private val tracker = Tracker()
    private val ktFile: KtFile
    init {
        ktFile = FileParser(filePath).ktFile()
    }
    fun runChecks() {
        val visitor = LinterPsiVisitor(tracker)
        traverse(ktFile, visitor)
    }

    fun getWarnings(): List<LinterWarning> = tracker.linterWarnings

    fun generateReport(): LinterReport {
        return LinterReport(
            tracker.functionCounter,
            tracker.functionViolationCounter,
            tracker.variableCounter,
            tracker.variableViolationCounter,
            tracker.classCounter,
            tracker.classViolationCounter,
            tracker.getViolationPercentage()
        )
    }

    private fun traverse(psiElement: PsiElement, visitor: PsiVisitor) {
        visitor.visit(psiElement)
        for (child in psiElement.children) {
            traverse(child, visitor)
        }
    }

    class Tracker {
        val functionCounter: Int
            get() = _functionCounter
        val classCounter: Int
            get() = _classCounter
        val variableCounter: Int
            get() = _variableCounter

        val functionViolationCounter: Int
            get() = violatingFunctions.size
        val classViolationCounter: Int
            get() = violatingClasses.size
        val variableViolationCounter: Int
            get() = violatingVariables.size

        val linterWarnings: List<LinterWarning>
            get() = _warnings

        private var violatingFunctions: MutableList<KtFunction> = mutableListOf()
        private var violatingClasses: MutableList<KtClassOrObject> = mutableListOf()
        private var violatingVariables: MutableList<KtVariableDeclaration> = mutableListOf()
        private var _functionCounter = 0
        private var _classCounter = 0
        private var _variableCounter = 0

        private var _warnings: MutableList<LinterWarning> = mutableListOf()

        fun functionCounterInc() = _functionCounter++
        fun classCounterInc() = _classCounter++
        fun variableCounterInc() = _variableCounter++
        fun addViolatingFunction(violatingFunction: KtFunction) = violatingFunctions.add(violatingFunction)
        fun addViolatingClass(violatingClass: KtClassOrObject) = violatingClasses.add(violatingClass)
        fun addViolatingVariable(violatingVariable: KtVariableDeclaration) = violatingVariables.add(violatingVariable)

        fun getViolationPercentage(): Double {
            val overallNames = functionCounter + variableCounter + classCounter
            val violationsNumber = functionViolationCounter + variableViolationCounter + classViolationCounter
            val percentage = (violationsNumber.toDouble() / overallNames.toDouble() * 100)
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_UP
            return df.format(percentage).toDouble()
        }

        fun addWarning(warning: LinterWarning) = _warnings.add(warning)
    }
}
data class LinterWarning(
    val originalName: String,
    val suggestedName: String,
    val violatedRules: List<NamingRule>,
    val warningMessage: String,
    ) {
    override fun toString(): String {
        return warningMessage
    }
}

data class LinterReport(
    val overallFunctionNumber: Int,
    val functionViolationNumber: Int,
    val overallVariableNumber: Int,
    val variableViolationNumber: Int,
    val overallClassNumber: Int,
    val classViolationNumber: Int,
    val violationPercentage: Double
)