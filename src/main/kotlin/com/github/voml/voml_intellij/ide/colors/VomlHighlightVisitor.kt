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


    override fun visitPredefinedSymbol(o: VomlPredefinedSymbol) {
        highlight(o, VomlColor.PREDEFINED)
    }

    override fun visitScopeSymbol(o: VomlScopeSymbol) {
        highlight(o, VomlColor.SCOPE_SYMBOL)
    }

    override fun visitKeySymbol(o: VomlKeySymbol) {
        highlight(o, VomlColor.KEY_SYMBOL)
    }

    override fun visitStringPrefix(o: VomlStringPrefix) {
        highlight(o, VomlColor.STRING_HINT)
    }

    override fun visitNumberSuffix(o: VomlNumberSuffix) {
        highlight(o, VomlColor.NUMBER_HINT)
    }

    override fun visitAnnotationMark(o: VomlAnnotationMark) {
        highlight(o, VomlColor.ANNOTATION)
    }

    override fun visitInsertPair(o: VomlInsertPair) {
        for (symbol in o.children) {
            val color = when (symbol) {
                is VomlInsertDot -> VomlColor.INSERT_MARK
                is VomlInsertStar -> VomlColor.INSERT_MARK
                else -> null
            }
            if (color != null) {
                highlight(symbol, color)
            }
        }
        super.visitInsertPair(o)
    }

    override fun visitInsertItem(o: VomlInsertItem) {
        for (symbol in o.children) {
            val color = when (symbol) {
                is VomlInsertDot -> VomlColor.INSERT_MARK
                is VomlInsertStar -> VomlColor.INSERT_MARK
                else -> null
            }
            if (color != null) {
                highlight(symbol, color)
            }
        }
        super.visitInsertItem(o)
    }

    private fun highlight(element: PsiElement, color: VomlColor) {
        val builder = HighlightInfo.newHighlightInfo(HighlightInfoType.INFORMATION)
        builder.textAttributes(color.textAttributesKey)
        builder.range(element)

        infoHolder?.add(builder.create())
    }

    override fun analyze(
        file: PsiFile,
        updateWholeFile: Boolean,
        holder: HighlightInfoHolder,
        action: Runnable
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