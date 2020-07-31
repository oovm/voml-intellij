package com.github.voml.voml_intellij.ide.colors

import com.github.voml.voml_intellij.ide.VomlSyntaxHighlighter
import com.github.voml.voml_intellij.ide.icons.VomlIcons
import com.github.voml.voml_intellij.language.VomlBundle
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
"""<KEYWORD>@inherit</KEYWORD> user;

@include json "some/path/test.json" as json;
@include "https:example.org/test.voml" {
	external_key as external
}

[literals]
boolean = [<BOOLEAN>true</BOOLEAN>, <BOOLEAN>false</BOOLEAN>]

[literals.number]
integer  = <INTEGER>10</INTEGER>cm
decimal  = <DECIMAL>0.1</DECIMAL>m
string   = "string"
escape   = "\n"

[keywords]
// remove this key-value pair
key = null

[scopes]
	[>a1]
	key1 = "scopes.b1.key1"
	[^a2]  # {^.b2}
	key2 = "scopes.b2.key2"
		[>b1]
		key3 = "a.a2.b1.key3"
	[<]
	key4 = "scopes.b1.key4"
		[>b1]
		key5 = "a.a2.b1.key5"
	[<a2]  // same as [<][^a2]
	key = "scopes.b1.key"

--- # 返回顶级

connection_max.a = 5cm
v = [
	@merge(override)
	@merge_as_source(unset)
	@merge_as_target(ignore)
	a = Some(1)
    b = None()
]

[name]
  . a = 2
  * a
  * b


// 标准键盘上不需要 shift 的符号
// [];',./`-=
// 小键盘上的符号
// /*-+."""

    override fun getAdditionalHighlightingTagToDescriptorMap() = annotatorTags
}
