package com.github.voml.voml_intellij.ide.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class VomlStringEscapingAnnotator : AnnotatorBase() {
    override fun annotateInternal(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            // is VomlStringInline -> annotateStringInline(element, holder)
            else -> {}
        }
    }

    private fun annotateStringInline(element: PsiElement, holder: AnnotationHolder) {
        val message = "invalid escape";
        holder.newAnnotation(HighlightSeverity.ERROR, message).create()
    }
}
