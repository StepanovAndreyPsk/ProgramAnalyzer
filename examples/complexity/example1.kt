import java.lang.Math.random

class Example {
    fun divisionBy4(x: Int) {
        if (x % 4 == 0) {                       // Conditional Statement 1
            println("x = 4a")
        } else if (x % 4 == 1) {                // Conditional Statement 2
            println("x = 4a + 1")
        } else if (x % 4 == 2) {                // Conditional Statement 3
            println("x = 4a + 2")
        } else if (x % 4 == 3) {                // Conditional Statement 4
            println("x = 4a + 3")
        }
    }

    class Inner {
        fun gcd(a: Int, b: Int): Int {
            var num1 = a
            var num2 = b
            while (num2 != 0) {                 // Conditional Statement 1
                val TEMP = num2
                num2 = num1 % num2
                num1 = TEMP
            }
            return num1
        }
    }

    private fun privateFunction() {
        for (i in 0..100) {               // Conditional Statement 1
            if (i % 5 == 0) {                  //  Conditional Statement 2
                var x = i
                while (x > 10) {                // Conditional Statement 3
                    x /= 5
                }
            }
        }
    }

    private fun methodWithoutConditions() {
        println("No conditions here!")
    }

    protected fun protectedMethod() {
        when((0..100).random()) {            // Conditional Statement 1
            0 -> print(0)
            1 -> print(1)
            2 -> print(2)
            3 -> print(3)
            else -> print("not from 0 t0 3")
        }
    }
}