package com.github.voml.voml_intellij.ide.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

abstract class CheckerBase : AnnotatorBase() {
    sealed class CheckerAnnotatorResult {
        object Ok : CheckerAnnotatorResult()
        data class Error(val errorText: String, val subRange: TextRange) : CheckerAnnotatorResult()
    }

    protected abstract fun check(element: PsiElement, holder: AnnotationHolder): CheckerAnnotatorResult
    override fun annotateInternal(element: PsiElement, holder: AnnotationHolder) {
        when (val result = check(element, holder)) {
            CheckerAnnotatorResult.Ok -> {}
            is CheckerAnnotatorResult.Error -> {
                val (errorText, subRange) = result
                holder
                    .newAnnotation(HighlightSeverity.ERROR, errorText)
                    .range(subRange)
                    .create()
            }
        }
    }

}

