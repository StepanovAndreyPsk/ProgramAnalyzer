package linter.rules

interface NamingRule {
    /** Verifies if [name] follows Kotlin naming convention.
     * Returns true if it does and false otherwise  */
    fun check(name: String): Boolean

    /** Transforms [oldName] and returns a new name that follows
     * Kotlin naming convention */
    fun apply(oldName: String): String

    /** Returns a warning message for the user */
    fun warn(name: String): String
}