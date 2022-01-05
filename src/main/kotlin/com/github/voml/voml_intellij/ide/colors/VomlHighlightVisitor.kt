package com.github.voml.voml_intellij.ide.colors

import com.github.voml.voml_intellij.language.VomlFile
import com.github.voml.voml_intellij.language.psi.*
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class VomlHighlightVisitor : VomlVisitor(), HighlightVisitor {
    private var infoHolder: HighlightInfoHolder? = null

    override fun visitScope(o: VomlScope) {
        for (symbol in o.children) {
            val color = when (symbol) {
                is VomlScopeSymbol -> VomlColor.SCOPE_SYMBOL
                is VomlScopeMark -> VomlColor.SCOPE_MARK
                else -> null
            }
            if (color != null) {
                highlight(symbol, color)
            }
        }
        super.visitScope(o)
    }

    override fun visitKeySymbol(o: VomlKeySymbol) {
        super.visitKeySymbol(o)
    }

    private fun highlight(element: PsiElement, color: VomlColor) {
        val builder = HighlightInfo.newHighlightInfo(HighlightInfoType.INFORMATION)
        builder.textAttributes(color.textAttributesKey)
        builder.range(element)

        infoHolder?.add(builder.create())
    }

    override fun analyze(
        file: PsiFile, updateWholeFile: Boolean, holder: HighlightInfoHolder, action: Runnable
    ): Boolean {
        infoHolder = holder
        action.run()

        return true
    }

    override fun clone(): HighlightVisitor = VomlHighlightVisitor()

    override fun suitableForFile(file: PsiFile): Boolean = file is VomlFile

    override fun order(): Int = 0

    override fun visit(element: PsiElement) = element.accept(this)
}