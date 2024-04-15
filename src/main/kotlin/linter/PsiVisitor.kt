package linter

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtVariableDeclaration

interface PsiVisitor {
    fun visit(function: KtFunction)
    fun visit(variableDeclaration: KtVariableDeclaration)
    fun visit(classDeclaration: KtClassOrObject)
    fun visit(default: PsiElement) {
        when(default) {
            is KtFunction -> visit(default)
            is KtVariableDeclaration -> visit(default)
            is KtClassOrObject -> visit(default)
        }
    }
}