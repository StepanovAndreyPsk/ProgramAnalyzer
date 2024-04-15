package linter

import linter.rules.*
import linter.rules.CLASS_NAMING_RULES
import linter.rules.CONST_PROPERTIES_RULES
import linter.rules.FUNCTION_NAMING_RULES
import linter.rules.VARIABLE_NAMING_RULES
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtVariableDeclaration
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class LinterPsiVisitor(private val tracker: Linter.Tracker): PsiVisitor {
    override fun visit(function: KtNamedFunction) { // TODO
        val functionName = function.name
        println("visiting $functionName")
        if (functionName.isNullOrEmpty()) {
            throw RuntimeException("Invalid function name at offset: ${function.startOffset}")
        }
        tracker.functionCounterInc()
        val violatedRules: MutableList<NamingRule> = mutableListOf()
        var suggestedName: String = functionName
        for (rule in FUNCTION_NAMING_RULES) {
            if (!rule.check(functionName)) {
                violatedRules.add(rule)
            }
        }
        if (violatedRules.isNotEmpty()) {
            println("Function $functionName violates Kotlin naming convention:")
            for (violatedRule in violatedRules) {
                println("*  ${violatedRule.warn(functionName)}")
                suggestedName = violatedRule.apply(suggestedName)
            }
            println("Consider changing it to `$suggestedName`\n")
            tracker.addViolatingFunction(function)
        }
    }

    override fun visit(variableDeclaration: KtVariableDeclaration) { // TODO
        val variableName = variableDeclaration.name
        if (variableName.isNullOrEmpty()) {
            throw RuntimeException("Invalid variable name at offset: ${variableDeclaration.startOffset}")
        }
        tracker.variableCounterInc()
        val violatedRules: MutableList<NamingRule> = mutableListOf()
        var suggestedName: String = variableName
        if (!variableDeclaration.isVar) {
            for (rule in CONST_PROPERTIES_RULES) {
                if (!rule.check(variableName)) {
                    violatedRules.add(rule)
                }
            }
            if (violatedRules.isNotEmpty()) {
                println("Constant variable $variableName violates Kotlin naming convention:")
                for (violatedRule in violatedRules) {
                    println("*  ${violatedRule.warn(variableName)}")
                    suggestedName = violatedRule.apply(suggestedName)
                }
                println("Consider changing it to `$suggestedName`\n")
                tracker.addViolatingVariable(variableDeclaration)
            }
        } else {
            for (rule in VARIABLE_NAMING_RULES) {
                if (!rule.check(variableName)) {
                    violatedRules.add(rule)
                }
            }
            if (violatedRules.isNotEmpty()) {
                println("Variable $variableName violates Kotlin naming convention:")
                for (violatedRule in violatedRules) {
                    println("*  ${violatedRule.warn(variableName)}")
                    suggestedName = violatedRule.apply(suggestedName)
                }
                println("Consider changing it to `$suggestedName`\n")
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
        val violatedRules: MutableList<NamingRule> = mutableListOf()
        for (rule in CLASS_NAMING_RULES) {
            if (!rule.check(className)) {
                violatedRules.add(rule)
            }
        }
        if (violatedRules.isNotEmpty()) {
            var suggestedName: String = className
            println("Class $className violates Kotlin naming convention:")
            for (violatedRule in violatedRules) {
                println("*  ${violatedRule.warn(className)}")
                suggestedName = violatedRule.apply(suggestedName)
            }
            println("Consider changing it to `$suggestedName`\n")
            tracker.addViolatingClass(classDeclaration)
        }
    }

}