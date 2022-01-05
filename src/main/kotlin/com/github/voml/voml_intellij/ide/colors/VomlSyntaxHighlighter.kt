package com.github.voml.voml_intellij.ide.colors

import com.github.voml.voml_intellij.language.VomlLexerAdapter
import com.github.voml.voml_intellij.language.psi.VomlTypes.*
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
            //
            INCLUDE, INHERIT, AS -> VomlColor.KEYWORD
            ANNOTATION, ANNOTATION_MARK -> VomlColor.ANNOTATION
            PREDEFINED_SYMBOL -> VomlColor.PREDEFINED
            //
            STRING_PREFIX -> VomlColor.STRING_HINT
            NUMBER_SUFFIX -> VomlColor.NUMBER_HINT
            TYPE_HINT -> VomlColor.TYPE_HINT
            BACK_TOP, ANGLE_L, ANGLE_R, ACCENT -> VomlColor.SCOPE_MARK
            INSERT_DOT, INSERT_STAR -> VomlColor.INSERT_MARK
            //
            PARENTHESIS_L, PARENTHESIS_R -> VomlColor.PARENTHESES
            BRACKET_L, BRACKET_R -> VomlColor.BRACKETS
            BRACE_L, BRACE_R -> VomlColor.BRACES
            COLON, EQ -> VomlColor.SET
            COMMA -> VomlColor.COMMA
            // atom
            NULL -> VomlColor.NULL
            BOOLEAN -> VomlColor.BOOLEAN

            NUMBER_SUFFIX -> VomlColor.NUMBER_HINT
            INTEGER -> VomlColor.INTEGER
            DECIMAL, DECIMAL_BAD -> VomlColor.DECIMAL

            STRING_PREFIX -> VomlColor.STRING_HINT
            STRING, STRING_INLINE, STRING_MULTI -> VomlColor.STRING

            SYMBOL -> getSymbolColor(tokenType)
            // 注释
            COMMENT -> VomlColor.LINE_COMMENT
            BLOCK_COMMENT -> VomlColor.BLOCK_COMMENT
            // 错误
            TokenType.BAD_CHARACTER -> VomlColor.BAD_CHARACTER
            else -> null
        }
    }

    private fun getSymbolColor(symbol: IElementType): VomlColor? {
        return null
    }
}
