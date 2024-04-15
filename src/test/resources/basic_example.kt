fun functionWith3ConditionalStatements(a: Int, b: Int) {
    var c = 10
    if (a < b) {
        c++
    }
    while (c < a) {
        c += 5
    }
    when(b % 2) {
        0 -> println("b is even")
        1 -> println("b is odd")
    }
}

fun functionWith2ConditionalStatements(s: String) {
    for (i in 0..100) {
        println(i)
    }

    val s2 = when(s.length) {
        0 -> "<empty string>"
        else -> s
    }
}

fun functionWith1ConditionalStatement(x: Int, y: Int): Int {
    return if (x > 0) {
        x
    }
    else {
        y
    }
}

