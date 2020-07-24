package com.github.voml.voml_idea.ide

import com.github.voml.voml_idea.language.psi.VomlTypes
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class VomlQuoteHandler : SimpleTokenSetQuoteHandler(VomlTypes.STRING_INLINE)
