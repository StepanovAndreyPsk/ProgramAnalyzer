package linter.utils

import linter.LinterWarning
import linter.rules.*
import linter.rules.CLASS_NAMING_RULES
import linter.rules.CONST_PROPERTIES_RULES
import linter.rules.FUNCTION_NAMING_RULES
import linter.rules.VARIABLE_NAMING_RULES

enum class ElementType(val strName: String) {
    FUNCTION("Function"),
    VARIABLE("Variable"),
    CONST_VARIABLE("Constant variable"),
    CLASS("Class")
}
fun checkRulesAndGenerateWarning(elementType: ElementType, elementName: String): LinterWarning? {
    val ruleSet = when(elementType) {
        ElementType.FUNCTION -> FUNCTION_NAMING_RULES
        ElementType.VARIABLE -> VARIABLE_NAMING_RULES
        ElementType.CONST_VARIABLE -> CONST_PROPERTIES_RULES
        ElementType.CLASS -> CLASS_NAMING_RULES
    }

    val violatedRules: MutableList<NamingRule> = mutableListOf()
    var suggestedName: String = elementName
    val warning: StringBuilder = StringBuilder()
    for (rule in ruleSet) {
        if (!rule.check(elementName)) {
            violatedRules.add(rule)
        }
    }
    if (violatedRules.isNotEmpty()) {
        warning.append("${elementType.strName} `$elementName` violates Kotlin naming convention:\n")
        for (violatedRule in violatedRules) {
            warning.append("*  ${violatedRule.warn(elementName)}\n")
            suggestedName = violatedRule.apply(suggestedName)
        }
        warning.append("Consider changing it to `$suggestedName`\n")
        return LinterWarning(elementName, suggestedName, violatedRules, warning.toString())
    }
    return null
}