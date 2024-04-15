package linter

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.*

class Linter(private val ktFile: KtFile, val edit: Boolean = false) {
    private val tracker = Tracker()
    fun run() {
        val visitor = LinterPsiVisitor(tracker)
        check(ktFile, visitor)
    }

    fun generateReport() {

    }

    private fun check(psiElement: PsiElement, visitor: PsiVisitor) {
        visitor.visit(psiElement)
        for (child in psiElement.children) {
            visitor.visit(child)
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

        private var violatingFunctions: MutableList<KtFunction> = mutableListOf()
        private var violatingClasses: MutableList<KtClassOrObject> = mutableListOf()
        private var violatingVariables: MutableList<KtVariableDeclaration> = mutableListOf()
        private var _functionCounter = 0
        private var _classCounter = 0
        private var _variableCounter = 0

        fun functionCounterInc() = _functionCounter++
        fun classCounterInc() = _classCounter++
        fun variableCounterInc() = _variableCounter++
        fun addViolatingFunction(violatingFunction: KtFunction) = violatingFunctions.add(violatingFunction)
        fun addViolatingClass(violatingClass: KtClassOrObject) = violatingClasses.add(violatingClass)
        fun addViolatingVariable(violatingVariable: KtVariableDeclaration) = violatingVariables.add(violatingVariable)
    }
}