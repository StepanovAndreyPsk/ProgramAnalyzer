fun nested(a: Int, b: Int) {
    if (a < b) {                            // Conditional Statement 1
        println("a < b")
        var x = a
        while (x < b) {                     // Conditional Statement 2
            x *= 2
            for (i in b..x) {         // Conditional Statement 3
                println(i)
            }
        }
    }
}