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

@include <STRING_HINT>json</STRING_HINT> <STRING>"some/path/test.json"</STRING> <KEYWORD>as</KEYWORD> json;
@include <STRING>"https:example.org/test.voml"</STRING> {
	<KEY_SYMBOL>external_key</KEY_SYMBOL> <KEYWORD>as</KEYWORD> <KEY_SYMBOL>external</KEY_SYMBOL>
}

[<SCOPE_SYMBOL>literals</SCOPE_SYMBOL>]
<KEY_SYMBOL>boolean</KEY_SYMBOL> = [true, false]

[<SCOPE_SYMBOL>literals</SCOPE_SYMBOL>.<SCOPE_SYMBOL>number</SCOPE_SYMBOL>]
<KEY_SYMBOL>integer</KEY_SYMBOL>  = <INTEGER>10</INTEGER><NUMBER_HINT>cm</NUMBER_HINT>
<KEY_SYMBOL>decimal</KEY_SYMBOL>  = <DECIMAL>0.1</DECIMAL><NUMBER_HINT>m</NUMBER_HINT>
<KEY_SYMBOL>string</KEY_SYMBOL>   = <STRING>"string"</STRING>
<KEY_SYMBOL>escape</KEY_SYMBOL>   = <STRING>"\n"</STRING>

[<SCOPE_SYMBOL>keywords</SCOPE_SYMBOL>]
// remove this key-value pair
<KEY_SYMBOL>key</KEY_SYMBOL> = <NULL>null</NULL>

[<SCOPE_SYMBOL>scopes</SCOPE_SYMBOL>]
	[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>a1</SCOPE_SYMBOL>]
	<KEY_SYMBOL>key1</KEY_SYMBOL> = "scopes.b1.key1"
	[<SCOPE_MARK>^</SCOPE_MARK><SCOPE_SYMBOL>a2</SCOPE_SYMBOL>]  // {^.b2}
	<KEY_SYMBOL>key2</KEY_SYMBOL> = "scopes.b2.key2"
		[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>b1</SCOPE_SYMBOL>]
		<KEY_SYMBOL>key3</KEY_SYMBOL> = "a.a2.b1.key3"
	[<SCOPE_MARK><</SCOPE_MARK>]
	<KEY_SYMBOL>key4</KEY_SYMBOL> = "scopes.b1.key4"
		[<SCOPE_MARK>></SCOPE_MARK><SCOPE_SYMBOL>b1</SCOPE_SYMBOL>]
		<KEY_SYMBOL>key5</KEY_SYMBOL> = "a.a2.b1.key5"
	[<SCOPE_MARK><</SCOPE_MARK><SCOPE_SYMBOL>a2</SCOPE_SYMBOL>]  // same as [<][^a2]
	<KEY_SYMBOL>key<KEY_SYMBOL> = "scopes.b1.key"

<SCOPE_MARK>---</SCOPE_MARK> // Back2Top

v = [
	<ANNOTATION>@merge</ANNOTATION>(override)
	<ANNOTATION>@merge_as_source</ANNOTATION>(unset)
	<ANNOTATION>@merge_as_target</ANNOTATION>(ignore)
	a = Some(1)
    b = None()
]

[name]
  <INSERT_MARK>.</INSERT_MARK> a = 2
  <INSERT_MARK>*</INSERT_MARK> 1
  <INSERT_MARK>*</INSERT_MARK> [true]
"""

    override fun getAdditionalHighlightingTagToDescriptorMap() = annotatorTags
}
