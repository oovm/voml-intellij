package com.github.voml.voml_idea.ide

import com.github.voml.voml_idea.ide.colors.VomlColor
import com.github.voml.voml_idea.language.VomlLexerAdapter
import com.github.voml.voml_idea.language.psi.VomlTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class VomlSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return VomlLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return pack(getTokenColor(tokenType)?.textAttributesKey)
    }

    private fun getTokenColor(tokenType: IElementType): VomlColor? {
        return when (tokenType) {
            VomlTypes.COLON -> VomlColor.COLON
            VomlTypes.COMMA -> VomlColor.COMMA
            VomlTypes.BOOLEAN -> VomlColor.BOOLEAN
            VomlTypes.IDENT -> VomlColor.IDENTIFIER
            TokenType.BAD_CHARACTER -> VomlColor.BAD_CHAR
            else -> getTokenColorSpecial(tokenType)
        }
    }

    private fun getTokenColorSpecial(tokenType: IElementType): VomlColor? {
        return when (tokenType) {
            VomlTypes.PARENTHESIS_L, VomlTypes.PARENTHESIS_R -> VomlColor.PARENTHESES
            VomlTypes.BRACKETL, VomlTypes.BRACKETR -> VomlColor.BRACKETS
            VomlTypes.BRACEL, VomlTypes.BRACER -> VomlColor.BRACES
            VomlTypes.INTEGER, VomlTypes.FLOAT -> VomlColor.NUMBER
            VomlTypes.STRING, VomlTypes.RAW_STRING, VomlTypes.CHAR -> VomlColor.STRING
            VomlTypes.SOME, VomlTypes.NONE -> VomlColor.OPTION
            VomlTypes.COMMENT, VomlTypes.BLOCK_COMMENT -> VomlColor.LINE_COMMENT
            else -> null
        }
    }
}
