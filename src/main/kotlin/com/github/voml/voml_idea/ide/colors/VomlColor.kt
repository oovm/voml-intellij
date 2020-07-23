package com.github.voml.voml_idea.ide.colors

import com.github.voml.voml_idea.language.VomlBundle
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.OptionsBundle
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import java.util.function.Supplier


// TODO: 分类
enum class VomlColor(humanName: Supplier<@NlsContexts.AttributeDescriptor String>, default: TextAttributesKey? = null) {
    // 特殊关键词
    KEYWORD(VomlBundle.messagePointer("color.settings.toml.keyword"), Default.KEYWORD),
    // 字面量
    NULL(VomlBundle.messagePointer("color.settings.toml.null"), Default.KEYWORD),
    BOOLEAN(VomlBundle.messagePointer("color.settings.toml.boolean"), Default.KEYWORD),
    DECIMAL(VomlBundle.messagePointer("color.settings.toml.decimal"), Default.NUMBER),
    INTEGER(VomlBundle.messagePointer("color.settings.toml.integer"), Default.NUMBER),
    STRING(OptionsBundle.messagePointer("options.language.defaults.string"), Default.KEYWORD),
    IDENTIFIER(OptionsBundle.messagePointer("options.language.defaults.identifier"), Default.IDENTIFIER),
    //
    TYPE_HINT(VomlBundle.messagePointer("color.settings.toml.type_hint"), Default.IDENTIFIER),
    NUMBER_HINT(VomlBundle.messagePointer("color.settings.toml.number_hint"), Default.IDENTIFIER),
    STRING_HINT(VomlBundle.messagePointer("color.settings.toml.string_hint"), Default.IDENTIFIER),
    OBJECT_NAME(VomlBundle.messagePointer("color.settings.toml.object_name"), Default.IDENTIFIER),
    KEY_NAME(VomlBundle.messagePointer("color.settings.toml.key_name"), Default.STATIC_FIELD),
    // 标点符号
    PARENTHESES(OptionsBundle.messagePointer("options.language.defaults.parentheses"), Default.PARENTHESES),
    BRACKETS(OptionsBundle.messagePointer("options.language.defaults.brackets"), Default.BRACKETS),
    BRACES(OptionsBundle.messagePointer("options.language.defaults.braces"), Default.BRACES),
    DOT(OptionsBundle.messagePointer("options.language.defaults.dot"), Default.DOT),
    COMMA(OptionsBundle.messagePointer("options.language.defaults.comma"), Default.COMMA),
    COLON(VomlBundle.messagePointer("color.settings.toml.colon"), Default.OPERATION_SIGN),
    SEMICOLON(OptionsBundle.messagePointer("options.language.defaults.semicolon"), Default.SEMICOLON),
    // 注释
    LINE_COMMENT(OptionsBundle.messagePointer("options.language.defaults.line.comment"), Default.LINE_COMMENT),
    BLOCK_COMMENT(OptionsBundle.messagePointer("options.language.defaults.block.comment"), Default.BLOCK_COMMENT),
    // 错误
    BAD_CHARACTER(OptionsBundle.messagePointer("options.java.attribute.descriptor.bad.character"), HighlighterColors.BAD_CHARACTER),
    // 废弃
    EXTENSION(OptionsBundle.messagePointer("options.language.defaults.metadata"), Default.METADATA),
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("voml.lang.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}
