package com.github.voml.voml_idea.ide.colors

import com.github.voml.voml_idea.ide.VomlSyntaxHighlighter
import com.github.voml.voml_idea.ide.icons.VomlIcons
import com.github.voml.voml_idea.language.VomlBundle
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage

class VomlColorSettingsPage : ColorSettingsPage {
    private val attrs = VomlColor
        .values()
        .map { it.attributesDescriptor }
        .toTypedArray()

    private val annotatorTags = VomlColor
        .values()
        .associateBy({ it.name }, { it.textAttributesKey })

    override fun getAttributeDescriptors() = attrs

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName() =  VomlBundle.message("filetype.voml.name")

    override fun getIcon() = VomlIcons.FILE

    override fun getHighlighter() = VomlSyntaxHighlighter()

    override fun getDemoText() =
"""<extension>#![enable(</extension>extension<extension>)]</extension>
// Line comment
<object_name>MyConfig</object_name> ( // End of line comment
    <key>simple_tuple</key>: (1, 2.0, 1.0e9, true, false),
    <key>anonymous_struct</key>: (
        <key>raw_string</key>: r#"Raw string containing "anything" you want"#,
        <key>also_raw_string</key>: r##"Also raw string containing "anything" you want"##,
        <key>name</key>: "John",
        <key>surname</key>: "Doe",
    ),
    <key>normal_struct</key>: <object_name>NormalStruct</object_name> (
        <key>foo</key>: <object_name>Bar</object_name>(20)
    ),
    <key>enum</key>: <object_name>Enum</object_name>,
    /*
    Multiline comment
    */
    <key>dictionary</key>: {
        0: <object_name>SomeStructWithOption</object_name>(None),
        1: <object_name>SomeStructWithOption</object_name>(Some(42)),
        "cat": "Meow",
        "dog": "Bark",
    },
    <key>array</key>: [
        (<key>owner</key>: None, <key>price</key>: 0, <key>name</key>: None),
        (<key>owner</key>: Some("self"), <key>price</key>: 20, <key>name</key>: Some("name")),
        (<key>owner</key>: Some("Jakob"), <key>price</key>: 20, <key>name</key>: Some("other_name")),
    ]
)"""

    override fun getAdditionalHighlightingTagToDescriptorMap() = annotatorTags
}
