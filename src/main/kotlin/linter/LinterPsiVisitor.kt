package linter

import linter.rules.CLASS_NAMING_RULES
import linter.rules.CONST_PROPERTIES_RULES
import linter.rules.FUNCTION_NAMING_RULES
import linter.rules.VARIABLE_NAMING_RULES
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtVariableDeclaration
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class LinterPsiVisitor(private val tracker: Linter.Tracker): PsiVisitor {
    override fun visit(function: KtFunction) {
        val functionName = function.name
        println("visiting $functionName")
        if (functionName.isNullOrEmpty()) {
            throw RuntimeException("Invalid function name at offset: ${function.startOffset}")
        }
        tracker.functionCounterInc()
        for (rule in FUNCTION_NAMING_RULES) {
            if (!rule.check(functionName)) {
                println("Function $functionName violates Kotlin naming convention:")
                println(rule.warn(functionName))
                tracker.addViolatingFunction(function)
            }
        }
    }

    override fun visit(variableDeclaration: KtVariableDeclaration) {
        val variableName = variableDeclaration.name
        println("visiting $variableName")
        if (variableName.isNullOrEmpty()) {
            throw RuntimeException("Invalid variable name at offset: ${variableDeclaration.startOffset}")
        }
        tracker.variableCounterInc()
        if (variableDeclaration.isVar) {
            for (rule in CONST_PROPERTIES_RULES) {
                if (!rule.check(variableName)) {
                    println("Constant variable $variableName violates Kotlin naming convention:")
                    println(rule.warn(variableName))
                    tracker.addViolatingVariable(variableDeclaration)
                }
            }
        } else {
            for (rule in VARIABLE_NAMING_RULES) {
                if (!rule.check(variableName)) {
                    println("Variable $variableName violates Kotlin naming convention:")
                    println(rule.warn(variableName))
                    tracker.addViolatingVariable(variableDeclaration)
                }
            }
        }
    }

    override fun visit(classDeclaration: KtClassOrObject) {
        val className = classDeclaration.name
        println("visiting $className")
        if (className.isNullOrEmpty()) {
            throw RuntimeException("Invalid class name at offset: ${classDeclaration.startOffset}")
        }
        tracker.classCounterInc()
        for (rule in CLASS_NAMING_RULES) {
            if (!rule.check(className)) {
                println("Class $className violates Kotlin naming convention:")
                println(rule.warn(className))
                tracker.addViolatingClass(classDeclaration)
            }
        }
    }

}