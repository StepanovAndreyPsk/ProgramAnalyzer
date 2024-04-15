package analyzer

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_RELATIVE_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiManager
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtFunction
import java.io.File


class FileParser(private val filePath: String) {
    private val project by lazy {
        val configuration = CompilerConfiguration()
        configuration.put(
            CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
            PrintingMessageCollector(System.err, PLAIN_RELATIVE_PATHS, false)
        )
        KotlinCoreEnvironment.createForProduction(
            Disposer.newDisposable(),
            configuration,
            EnvironmentConfigFiles.JVM_CONFIG_FILES
        ).project
    }

    private val ktFile by lazy {
        val file = File(filePath)
        PsiManager.getInstance(project)
            .findFile(
                LightVirtualFile(file.name, KotlinFileType.INSTANCE, file.readText())
            ) as KtFile
    }

    fun parseFunctions(): List<KtFunction> {
        return ktFile.getFunctions()
    }
}

fun PsiElement.getFunctions(): List<KtFunction> {
    val funcList = ArrayList<KtFunction>()
    this.children.forEach {  psiElement ->
        when(psiElement) {
            is KtNamedFunction -> funcList.add(psiElement)
            else -> if (psiElement.children.isNotEmpty()) {
                funcList.addAll(psiElement.getFunctions())
            }
        }
    }
    return funcList
}
