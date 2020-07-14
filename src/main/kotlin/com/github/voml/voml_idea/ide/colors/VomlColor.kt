package com.github.voml.voml_idea.ide.colors

import com.github.voml.voml_idea.language.VomlBundle
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.OptionsBundle
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import java.util.function.Supplier

enum class VomlColor(humanName: Supplier<@NlsContexts.AttributeDescriptor String>, default: TextAttributesKey? = null) {
    // 字面量
    NULL(VomlBundle.messagePointer("color.settings.toml.null"), Default.KEYWORD),
    BOOLEAN(VomlBundle.messagePointer("color.settings.toml.boolean"), Default.KEYWORD),
    NUMBER(OptionsBundle.messagePointer("options.language.defaults.number"), Default.NUMBER),
    INTEGER(OptionsBundle.messagePointer("options.language.defaults.number"), Default.NUMBER),
    STRING(OptionsBundle.messagePointer("options.language.defaults.string"), Default.KEYWORD),
    IDENTIFIER(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.IDENTIFIER),
    //
    OBJECT_NAME(VomlBundle.messagePointer("color.settings.toml.object_name"), Default.IDENTIFIER),
    KEY_NAME(VomlBundle.messagePointer("color.settings.toml.key_name"), Default.STATIC_FIELD),
    // 标点符号
    PARENTHESES(OptionsBundle.messagePointer("options.language.defaults.parentheses"), Default.PARENTHESES),
    BRACKETS(OptionsBundle.messagePointer("options.language.defaults.brackets"), Default.BRACKETS),
    BRACES(OptionsBundle.messagePointer("options.language.defaults.braces"), Default.BRACES),
    DOT(OptionsBundle.messagePointer("options.language.defaults.dot"), Default.DOT),
    COMMA(OptionsBundle.messagePointer("options.language.defaults.comma"), Default.COMMA),
    SEMICOLON(OptionsBundle.messagePointer("options.language.defaults.semicolon"), Default.SEMICOLON),
    // 注释
    LINE_COMMENT(OptionsBundle.messagePointer("options.language.defaults.line.comment"), Default.LINE_COMMENT),
    BLOCK_COMMENT(OptionsBundle.messagePointer("options.language.defaults.block.comment"), Default.BLOCK_COMMENT),
    // 错误
    COLON(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.OPERATION_SIGN),
    BAD_CHAR(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.IDENTIFIER),
    OPTION(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.IDENTIFIER),
    EXTENSION(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.IDENTIFIER),

    ;



    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("voml.lang.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}
