import cli.Analyzer
import cli.CalculateComplexityCommand
import cli.CheckCodestyleCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal

fun main(args: Array<String>) {

        Analyzer().context {
            terminal = Terminal(ansiLevel = AnsiLevel.TRUECOLOR, interactive = true)
        }.subcommands(CalculateComplexityCommand(), CheckCodestyleCommand()).main(args)
}
