// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_idea.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.voml.voml_idea.language.psi.VomlTypes.*;
import static com.github.voml.voml_idea.language.psi.VomlParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class VomlParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return Voml(b, l + 1);
  }

  /* ********************************************************** */
  // [extensions] value
  static boolean Voml(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Voml")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Voml_0(b, l + 1);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [extensions]
  private static boolean Voml_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Voml_0")) return false;
    extensions(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // BOOLEAN
  public static boolean bool(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool")) return false;
    if (!nextTokenIs(b, BOOLEAN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BOOLEAN);
    exit_section_(b, m, BOOL, r);
    return r;
  }

  /* ********************************************************** */
  // CHAR
  static boolean char_$(PsiBuilder b, int l) {
    return consumeToken(b, CHAR);
  }

  /* ********************************************************** */
  // DECIMAL
  static boolean dec(PsiBuilder b, int l) {
    return consumeToken(b, DECIMAL);
  }

  /* ********************************************************** */
  // IDENT
  public static boolean enum_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_$")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENT);
    exit_section_(b, m, ENUM, r);
    return r;
  }

  /* ********************************************************** */
  // EXT_PREFIX ENABLE_KEYWORD PARENTHESIS_L IDENT PARENTHESIS_R BRACKET_R
  public static boolean ext(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ext")) return false;
    if (!nextTokenIs(b, EXT_PREFIX)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, EXT_PREFIX, ENABLE_KEYWORD, PARENTHESIS_L, IDENT, PARENTHESIS_R, BRACKET_R);
    exit_section_(b, m, EXT, r);
    return r;
  }

  /* ********************************************************** */
  // ext+
  public static boolean extensions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extensions")) return false;
    if (!nextTokenIs(b, EXT_PREFIX)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ext(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!ext(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "extensions", c)) break;
    }
    exit_section_(b, m, EXTENSIONS, r);
    return r;
  }

  /* ********************************************************** */
  // INTEGER
  static boolean int_$(PsiBuilder b, int l) {
    return consumeToken(b, INTEGER);
  }

  /* ********************************************************** */
  // BRACKETL [value (COMMA value)* [COMMA]] BRACKETR
  public static boolean list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list")) return false;
    if (!nextTokenIs(b, BRACKETL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACKETL);
    r = r && list_1(b, l + 1);
    r = r && consumeToken(b, BRACKETR);
    exit_section_(b, m, LIST, r);
    return r;
  }

  // [value (COMMA value)* [COMMA]]
  private static boolean list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1")) return false;
    list_1_0(b, l + 1);
    return true;
  }

  // value (COMMA value)* [COMMA]
  private static boolean list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = value(b, l + 1);
    r = r && list_1_0_1(b, l + 1);
    r = r && list_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA value)*
  private static boolean list_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!list_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "list_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA value
  private static boolean list_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean list_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // BRACEL [map_entry (COMMA map_entry)* [COMMA]] BRACER
  public static boolean map(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map")) return false;
    if (!nextTokenIs(b, BRACEL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACEL);
    r = r && map_1(b, l + 1);
    r = r && consumeToken(b, BRACER);
    exit_section_(b, m, MAP, r);
    return r;
  }

  // [map_entry (COMMA map_entry)* [COMMA]]
  private static boolean map_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_1")) return false;
    map_1_0(b, l + 1);
    return true;
  }

  // map_entry (COMMA map_entry)* [COMMA]
  private static boolean map_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = map_entry(b, l + 1);
    r = r && map_1_0_1(b, l + 1);
    r = r && map_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA map_entry)*
  private static boolean map_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!map_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "map_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA map_entry
  private static boolean map_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && map_entry(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean map_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // map_key COLON value
  public static boolean map_entry(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_entry")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MAP_ENTRY, "<map entry>");
    r = map_key(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && value(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // bool | num | string | char | enum
  public static boolean map_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_key")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MAP_KEY, "<map key>");
    r = bool(b, l + 1);
    if (!r) r = num(b, l + 1);
    if (!r) r = string(b, l + 1);
    if (!r) r = char_$(b, l + 1);
    if (!r) r = enum_$(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENT COLON value
  public static boolean named_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "named_field")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENT, COLON);
    r = r && value(b, l + 1);
    exit_section_(b, m, NAMED_FIELD, r);
    return r;
  }

  /* ********************************************************** */
  // NULL
  public static boolean null_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "null_$")) return false;
    if (!nextTokenIs(b, NULL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NULL);
    exit_section_(b, m, NULL, r);
    return r;
  }

  /* ********************************************************** */
  // int | dec
  static boolean num(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num")) return false;
    if (!nextTokenIs(b, "", DECIMAL, INTEGER)) return false;
    boolean r;
    r = int_$(b, l + 1);
    if (!r) r = dec(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // [IDENT] (object_body | tuple_body)
  public static boolean object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object")) return false;
    if (!nextTokenIs(b, "<object>", IDENT, PARENTHESIS_L)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OBJECT, "<object>");
    r = object_0(b, l + 1);
    r = r && object_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [IDENT]
  private static boolean object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_0")) return false;
    consumeToken(b, IDENT);
    return true;
  }

  // object_body | tuple_body
  private static boolean object_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1")) return false;
    boolean r;
    r = object_body(b, l + 1);
    if (!r) r = tuple_body(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // PARENTHESIS_L [object_entry (COMMA object_entry)* [COMMA]] PARENTHESIS_R
  public static boolean object_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body")) return false;
    if (!nextTokenIs(b, PARENTHESIS_L)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PARENTHESIS_L);
    r = r && object_body_1(b, l + 1);
    r = r && consumeToken(b, PARENTHESIS_R);
    exit_section_(b, m, OBJECT_BODY, r);
    return r;
  }

  // [object_entry (COMMA object_entry)* [COMMA]]
  private static boolean object_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body_1")) return false;
    object_body_1_0(b, l + 1);
    return true;
  }

  // object_entry (COMMA object_entry)* [COMMA]
  private static boolean object_body_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = object_entry(b, l + 1);
    r = r && object_body_1_0_1(b, l + 1);
    r = r && object_body_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA object_entry)*
  private static boolean object_body_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!object_body_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "object_body_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA object_entry
  private static boolean object_body_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && object_entry(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean object_body_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_body_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // named_field
  public static boolean object_entry(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_entry")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = named_field(b, l + 1);
    exit_section_(b, m, OBJECT_ENTRY, r);
    return r;
  }

  /* ********************************************************** */
  // SOME PARENTHESIS_L value PARENTHESIS_R | NONE
  public static boolean option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option")) return false;
    if (!nextTokenIs(b, "<option>", NONE, SOME)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION, "<option>");
    r = option_0(b, l + 1);
    if (!r) r = consumeToken(b, NONE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SOME PARENTHESIS_L value PARENTHESIS_R
  private static boolean option_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SOME, PARENTHESIS_L);
    r = r && value(b, l + 1);
    r = r && consumeToken(b, PARENTHESIS_R);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // STRING | RAW_STRING
  static boolean string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string")) return false;
    if (!nextTokenIs(b, "", RAW_STRING, STRING)) return false;
    boolean r;
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, RAW_STRING);
    return r;
  }

  /* ********************************************************** */
  // PARENTHESIS_L [value (COMMA value)* [COMMA]] PARENTHESIS_R
  public static boolean tuple_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body")) return false;
    if (!nextTokenIs(b, PARENTHESIS_L)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PARENTHESIS_L);
    r = r && tuple_body_1(b, l + 1);
    r = r && consumeToken(b, PARENTHESIS_R);
    exit_section_(b, m, TUPLE_BODY, r);
    return r;
  }

  // [value (COMMA value)* [COMMA]]
  private static boolean tuple_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body_1")) return false;
    tuple_body_1_0(b, l + 1);
    return true;
  }

  // value (COMMA value)* [COMMA]
  private static boolean tuple_body_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = value(b, l + 1);
    r = r && tuple_body_1_0_1(b, l + 1);
    r = r && tuple_body_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA value)*
  private static boolean tuple_body_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tuple_body_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tuple_body_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA value
  private static boolean tuple_body_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean tuple_body_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tuple_body_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // bool | num | string | char | option | list | map | object | enum | null
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = bool(b, l + 1);
    if (!r) r = num(b, l + 1);
    if (!r) r = string(b, l + 1);
    if (!r) r = char_$(b, l + 1);
    if (!r) r = option(b, l + 1);
    if (!r) r = list(b, l + 1);
    if (!r) r = map(b, l + 1);
    if (!r) r = object(b, l + 1);
    if (!r) r = enum_$(b, l + 1);
    if (!r) r = null_$(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
