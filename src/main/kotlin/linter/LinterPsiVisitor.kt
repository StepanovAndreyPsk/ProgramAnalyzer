package linter

import linter.utils.ElementType
import linter.utils.checkRulesAndGenerateWarning
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtVariableDeclaration
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class LinterPsiVisitor(private val tracker: Linter.Tracker): PsiVisitor {
    override fun visit(function: KtNamedFunction) {
        val functionName = function.name
        if (functionName.isNullOrEmpty()) {
            throw RuntimeException("Invalid function name at offset: ${function.startOffset}")
        }
        tracker.functionCounterInc()
        val warning: LinterWarning? = checkRulesAndGenerateWarning(ElementType.FUNCTION, functionName)
        warning?.let {
            tracker.addWarning(it)
            tracker.addViolatingFunction(function)
        }
    }

    override fun visit(variableDeclaration: KtVariableDeclaration) {
        val variableName = variableDeclaration.name
        if (variableName.isNullOrEmpty()) {
            throw RuntimeException("Invalid variable name at offset: ${variableDeclaration.startOffset}")
        }
        tracker.variableCounterInc()
        if (variableDeclaration.isVar) {
            val warning: LinterWarning? = checkRulesAndGenerateWarning(ElementType.VARIABLE, variableName)
            warning?.let {
                tracker.addWarning(it)
                tracker.addViolatingVariable(variableDeclaration)
            }
        } else {
            val warning: LinterWarning? = checkRulesAndGenerateWarning(ElementType.CONST_VARIABLE, variableName)
            warning?.let {
                tracker.addWarning(it)
                tracker.addViolatingVariable(variableDeclaration)
            }
        }
    }

    override fun visit(classDeclaration: KtClassOrObject) {
        val className = classDeclaration.name
        if (className.isNullOrEmpty()) {
            throw RuntimeException("Invalid class name at offset: ${classDeclaration.startOffset}")
        }
        tracker.classCounterInc()
        val warning: LinterWarning? = checkRulesAndGenerateWarning(ElementType.CLASS, className)
        warning?.let {
            tracker.addWarning(it)
            tracker.addViolatingClass(classDeclaration)
        }
    }
}