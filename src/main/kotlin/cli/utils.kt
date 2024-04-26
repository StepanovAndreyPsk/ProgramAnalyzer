package cli

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.FileNotFound
import com.github.ajalt.mordant.rendering.TextColors
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

fun validatePathToDirectory(pathString: String) {
    val path = Path(pathString)
    if (!path.exists()) {
        throw FileNotFound(pathString)
    }
    if (!path.isDirectory()) {
        throw BadParameterValue("Specified file is not a directory")
    }
}