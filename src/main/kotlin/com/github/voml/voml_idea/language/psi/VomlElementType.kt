package com.github.voml.voml_idea.language.psi

import com.github.voml.voml_idea.language.VomlLanguage
import com.intellij.psi.tree.IElementType

class VomlElementType(debugName: String) : IElementType(debugName, VomlLanguage.INSTANCE)
