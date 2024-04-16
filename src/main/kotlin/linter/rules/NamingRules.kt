package linter.rules

/**
 * This file collects most of the naming rules from [Kotlin Docs](https://kotlinlang.org/docs/coding-conventions.html#naming-rules)
 */

object StartsWithLowercaseLetter: NamingRule {
    override fun check(name: String): Boolean = name[0].isLowerCase()
    override fun apply(oldName: String): String = oldName[0].lowercase() + oldName.substring(1)
    override fun warn(name: String): String = "`$name` should start with a lowercase letter!"
}

object StartsWithUppercaseLetter: NamingRule {
    override fun check(name: String): Boolean = name[0].isUpperCase()
    override fun apply(oldName: String): String = oldName[0].uppercase() + oldName.substring(1)
    override fun warn(name: String): String = "`$name` should start with an uppercase letter!"
}

object IsInCamelCase: NamingRule {
    override fun check(name: String): Boolean = !name.contains('_')
    override fun apply(oldName: String): String {
        val pattern = "_([a-z])".toRegex()
        return oldName.replace(pattern) { it.groupValues[1].uppercase() }
    }
    override fun warn(name: String): String = "`$name` should be in camelcase!"
}

object IsInUppercase: NamingRule {
    override fun check(name: String): Boolean = name.all { it.isUpperCase() || !it.isLetter() }
    override fun apply(oldName: String): String = oldName.uppercase()
    override fun warn(name: String): String = "`$name` should be in uppercase!"
}

internal val FUNCTION_NAMING_RULES: List<NamingRule> = listOf(
    StartsWithLowercaseLetter,
    IsInCamelCase
)

internal val CLASS_NAMING_RULES: List<NamingRule> = listOf(
    StartsWithUppercaseLetter,
    IsInCamelCase
)

internal val VARIABLE_NAMING_RULES: List<NamingRule> = listOf(
    StartsWithLowercaseLetter,
    IsInCamelCase
)

internal val CONST_PROPERTIES_RULES: List<NamingRule> = listOf(
    IsInCamelCase,
    IsInUppercase
)

