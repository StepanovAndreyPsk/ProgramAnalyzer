package linter.utils

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.psi.KtModifierListOwner

enum class Modifiers(val keyword: String) {
    CONST("const"),
    VAL("val"),
    VAR("var")
}

fun KtModifierListOwner.hasModifier(mod: Modifiers): Boolean {
    return this.modifierList?.hasModifier(KtModifierKeywordToken.keywordModifier(mod.keyword)) ?: false
}
