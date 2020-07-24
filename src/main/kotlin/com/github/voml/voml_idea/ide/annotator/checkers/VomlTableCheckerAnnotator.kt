package com.github.voml.voml_idea.ide.annotator.checkers

import com.github.voml.voml_idea.language.psi.VomlPair
import com.github.voml.voml_idea.language.psi.VomlTable
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement


class VomlTableCheckerAnnotator : CheckerAnnotator() {
    override fun check(element: PsiElement, holder: AnnotationHolder): CheckerAnnotatorResult =
        if (holder.isBatchMode) {
            CheckerAnnotatorResult.Ok
        } else {
            when (element) {
                is VomlPair -> checkPair(element)
                else -> CheckerAnnotatorResult.Ok
            }
        }

    private fun checkPair(mapEntry: VomlPair): CheckerAnnotatorResult {
//        val filteredEntries = (mapEntry.parent as VomlMap)
//            .mapEntryList
//            .asSequence()
//            .filterNot { it == mapEntry }
//
//        val duplicatesFound = filteredEntries.any { mapEntry.keyAsTextMatches(it.keyAsText) }
//
//        return if (duplicatesFound) {
//            CheckerAnnotatorResult.Error(
//                "Duplicate keys found in a dictionary",
//                mapEntry.mapKey.textRange
//            )
//        } else {
//            CheckerAnnotatorResult.Ok
//        }
        return CheckerAnnotatorResult.Ok
    }
}