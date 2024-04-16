package linter

fun FunctionStartsWithUppercaseLetter() {}

fun function_in_camelcase() {}

fun Very_wrong_named_function() {}

class WrongNamedFunctions {
    fun MethodStartsWithUppercase() {}
    private fun method_in_camelcase() {}
    companion object {
        fun Very_wrong_named_method() {}
        fun goodNamedFunction() {}
    }
}
