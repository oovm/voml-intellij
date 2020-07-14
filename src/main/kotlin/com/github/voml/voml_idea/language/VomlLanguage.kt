package com.github.voml.voml_idea.language

import com.intellij.lang.Language
import com.github.voml.voml_idea.ide.icons.VomlIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class VomlLanguage private constructor() : Language("Voml") {
    companion object {
        @JvmStatic
        val INSTANCE = VomlLanguage()
    }
}

class VomlFileType private constructor() : LanguageFileType(VomlLanguage.INSTANCE) {
    override fun getName(): String = VomlBundle.messagePointer("filetype.voml.name").get()

    override fun getDescription(): String = VomlBundle.messagePointer("filetype.voml.description").get()

    override fun getDefaultExtension(): String = "Voml"

    override fun getIcon(): Icon = VomlIcons.FILE

    companion object {
        @JvmStatic
        val INSTANCE = VomlFileType()
    }
}

class VomlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VomlLanguage.INSTANCE) {
    override fun getFileType(): FileType = VomlFileType.INSTANCE

    override fun toString(): String = "Voml File"
}
