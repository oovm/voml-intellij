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

@include <STRING_HINT>json</STRING_HINT> "some/path/test.json" <KEYWORD>as</KEYWORD> json;
@include "https:example.org/test.voml" {
	external_key <KEYWORD>as</KEYWORD> external
}

[<SCOPE_SYMBOL>literals</SCOPE_SYMBOL>]
<KEY_SYMBOL>boolean</KEY_SYMBOL> = [<BOOLEAN>true</BOOLEAN>, <BOOLEAN>false</BOOLEAN>]

[<SCOPE_SYMBOL>literals</SCOPE_SYMBOL>.<SCOPE_SYMBOL>number</SCOPE_SYMBOL>]
<KEY_SYMBOL>integer</KEY_SYMBOL>  = <INTEGER>10</INTEGER>cm
<KEY_SYMBOL>decimal</KEY_SYMBOL>  = <DECIMAL>0.1</DECIMAL>m
<KEY_SYMBOL>string</KEY_SYMBOL>   = "string"
<KEY_SYMBOL>escape</KEY_SYMBOL>   = "\n"

[<SCOPE_SYMBOL>keywords</SCOPE_SYMBOL>]
// remove this key-value pair
<KEY_SYMBOL>key</KEY_SYMBOL> = <NULL>null</NULL>

[<SCOPE_SYMBOL>scopes</SCOPE_SYMBOL>]
	[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>a1</SCOPE_SYMBOL>]
	key1 = "scopes.b1.key1"
	[<SCOPE_MARK>^</SCOPE_MARK><SCOPE_SYMBOL>a2</SCOPE_SYMBOL>]  # {^.b2}
	key2 = "scopes.b2.key2"
		[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>b1</SCOPE_SYMBOL>]
		key3 = "a.a2.b1.key3"
	[<SCOPE_MARK><</SCOPE_MARK>]
	key4 = "scopes.b1.key4"
		[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>b1</SCOPE_SYMBOL>]
		key5 = "a.a2.b1.key5"
	[<SCOPE_MARK><</SCOPE_MARK><SCOPE_SYMBOL>a2</SCOPE_SYMBOL>]  // same as [<][^a2]
	key = "scopes.b1.key"

<SCOPE_MARK>---</SCOPE_MARK> # Back2Top

connection_max.a = 5cm
v = [
	@merge(override)
	@merge_as_source(unset)
	@merge_as_target(ignore)
	a = Some(1)
    b = None()
]

[name]
  <ITEM_MARK>.</ITEM_MARK> a = 2
  <ITEM_MARK>*</ITEM_MARK> a
  <ITEM_MARK>*</ITEM_MARK> b


// 标准键盘上不需要 shift 的符号
// [];',./`-=
// 小键盘上的符号
// /*-+."""

    override fun getAdditionalHighlightingTagToDescriptorMap() = annotatorTags
}
