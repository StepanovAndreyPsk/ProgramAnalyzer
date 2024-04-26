package cli.styles

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import com.github.ajalt.mordant.rendering.TextStyles


val warning = TextColors.red
val progress = TextColors.blue + TextStyles.bold
val reportProperty = TextColors.yellow
val success = TextColors.green

val percentageLow = TextColors.brightGreen
val percentageHigh = TextColors.brightRed

fun hyperlink(destination: String): String? = TextStyles.hyperlink("file:///$destination").hyperlink
