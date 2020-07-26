// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.voml.voml_intellij.language.psi.VomlTypes.*;
import static com.github.voml.voml_intellij.language.psi.VomlParserUtil.*;
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
  // expression*
  static boolean Voml(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Voml")) return false;
    while (true) {
      int c = current_position_(b);
      if (!expression(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Voml", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "---"
  public static boolean back_top(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "back_top")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BACK_TOP, "<back top>");
    r = consumeToken(b, "---");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // escaped | NON_ESCAPE
  static boolean char_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "char_$")) return false;
    if (!nextTokenIs(b, "", ESCAPE, NON_ESCAPE)) return false;
    boolean r;
    r = escaped(b, l + 1);
    if (!r) r = consumeToken(b, NON_ESCAPE);
    return r;
  }

  /* ********************************************************** */
  // EQ | COLON
  static boolean eq(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eq")) return false;
    if (!nextTokenIs(b, "", COLON, EQ)) return false;
    boolean r;
    r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, COLON);
    return r;
  }

  /* ********************************************************** */
  // ESCAPE (ESCAPE|NON_ESCAPE)
  public static boolean escaped(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "escaped")) return false;
    if (!nextTokenIs(b, ESCAPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ESCAPE);
    r = r && escaped_1(b, l + 1);
    exit_section_(b, m, ESCAPED, r);
    return r;
  }

  // ESCAPE|NON_ESCAPE
  private static boolean escaped_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "escaped_1")) return false;
    boolean r;
    r = consumeToken(b, ESCAPE);
    if (!r) r = consumeToken(b, NON_ESCAPE);
    return r;
  }

  /* ********************************************************** */
  // scope
  //     | back_top
  //     | include_statement
  //     | inherit_statement
  //     | insert_pair
  //     | insert_item
  //     | SEMICOLON
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = scope(b, l + 1);
    if (!r) r = back_top(b, l + 1);
    if (!r) r = include_statement(b, l + 1);
    if (!r) r = inherit_statement(b, l + 1);
    if (!r) r = insert_pair(b, l + 1);
    if (!r) r = insert_item(b, l + 1);
    if (!r) r = consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // SYMBOL*
  static boolean include_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_inner")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, SYMBOL)) break;
      if (!empty_element_parsed_guard_(b, "include_inner", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // INCLUDE [string_prefix] string_inline (AS SYMBOL <<paired include_inner>>)
  public static boolean include_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_statement")) return false;
    if (!nextTokenIs(b, INCLUDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INCLUDE);
    r = r && include_statement_1(b, l + 1);
    r = r && string_inline(b, l + 1);
    r = r && include_statement_3(b, l + 1);
    exit_section_(b, m, INCLUDE_STATEMENT, r);
    return r;
  }

  // [string_prefix]
  private static boolean include_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_statement_1")) return false;
    string_prefix(b, l + 1);
    return true;
  }

  // AS SYMBOL <<paired include_inner>>
  private static boolean include_statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_statement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, AS, SYMBOL);
    r = r && paired(b, l + 1, VomlParser::include_inner);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INHERIT (SYMBOL | [string_prefix] string_inline)
  public static boolean inherit_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inherit_statement")) return false;
    if (!nextTokenIs(b, INHERIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INHERIT);
    r = r && inherit_statement_1(b, l + 1);
    exit_section_(b, m, INHERIT_STATEMENT, r);
    return r;
  }

  // SYMBOL | [string_prefix] string_inline
  private static boolean inherit_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inherit_statement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYMBOL);
    if (!r) r = inherit_statement_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [string_prefix] string_inline
  private static boolean inherit_statement_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inherit_statement_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inherit_statement_1_1_0(b, l + 1);
    r = r && string_inline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [string_prefix]
  private static boolean inherit_statement_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inherit_statement_1_1_0")) return false;
    string_prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (DOT|STAR) value
  public static boolean insert_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "insert_item")) return false;
    if (!nextTokenIs(b, "<insert item>", DOT, STAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INSERT_ITEM, "<insert item>");
    r = insert_item_0(b, l + 1);
    r = r && value(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DOT|STAR
  private static boolean insert_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "insert_item_0")) return false;
    boolean r;
    r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  /* ********************************************************** */
  // [DOT] pair
  public static boolean insert_pair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "insert_pair")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INSERT_PAIR, "<insert pair>");
    r = insert_pair_0(b, l + 1);
    r = r && pair(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [DOT]
  private static boolean insert_pair_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "insert_pair_0")) return false;
    consumeToken(b, DOT);
    return true;
  }

  /* ********************************************************** */
  // str | SYMBOL | INTEGER
  static boolean key_like(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_like")) return false;
    boolean r;
    r = str(b, l + 1);
    if (!r) r = consumeToken(b, SYMBOL);
    if (!r) r = consumeToken(b, INTEGER);
    return r;
  }

  /* ********************************************************** */
  // [DOT*] symbol_path
  public static boolean key_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_path")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, KEY_PATH, "<key path>");
    r = key_path_0(b, l + 1);
    r = r && symbol_path(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [DOT*]
  private static boolean key_path_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_path_0")) return false;
    key_path_0_0(b, l + 1);
    return true;
  }

  // DOT*
  private static boolean key_path_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_path_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, DOT)) break;
      if (!empty_element_parsed_guard_(b, "key_path_0_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // [SIGN] (INTEGER | DECIMAL) [number_suffix] | [SIGN] BYTE | other_num
  static boolean num(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = num_0(b, l + 1);
    if (!r) r = num_1(b, l + 1);
    if (!r) r = other_num(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [SIGN] (INTEGER | DECIMAL) [number_suffix]
  private static boolean num_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = num_0_0(b, l + 1);
    r = r && num_0_1(b, l + 1);
    r = r && num_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [SIGN]
  private static boolean num_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_0_0")) return false;
    consumeToken(b, SIGN);
    return true;
  }

  // INTEGER | DECIMAL
  private static boolean num_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_0_1")) return false;
    boolean r;
    r = consumeToken(b, INTEGER);
    if (!r) r = consumeToken(b, DECIMAL);
    return r;
  }

  // [number_suffix]
  private static boolean num_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_0_2")) return false;
    number_suffix(b, l + 1);
    return true;
  }

  // [SIGN] BYTE
  private static boolean num_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = num_1_0(b, l + 1);
    r = r && consumeToken(b, BYTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // [SIGN]
  private static boolean num_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num_1_0")) return false;
    consumeToken(b, SIGN);
    return true;
  }

  /* ********************************************************** */
  // SYMBOL
  public static boolean number_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number_suffix")) return false;
    if (!nextTokenIs(b, SYMBOL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYMBOL);
    exit_section_(b, m, NUMBER_SUFFIX, r);
    return r;
  }

  /* ********************************************************** */
  // NAN
  static boolean other_num(PsiBuilder b, int l) {
    return consumeToken(b, NAN);
  }

  /* ********************************************************** */
  // symbol_path eq value
  public static boolean pair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pair")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PAIR, "<pair>");
    r = symbol_path(b, l + 1);
    r = r && eq(b, l + 1);
    r = r && value(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PARENTHESIS_L <<param>> PARENTHESIS_R
  //   | BRACKET_L <<param>> BRACKET_R
  //   | BRACE_L <<param>> BRACE_R
  public static boolean paired(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "paired")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = paired_0(b, l + 1, _param);
    if (!r) r = paired_1(b, l + 1, _param);
    if (!r) r = paired_2(b, l + 1, _param);
    exit_section_(b, m, PAIRED, r);
    return r;
  }

  // PARENTHESIS_L <<param>> PARENTHESIS_R
  private static boolean paired_0(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "paired_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PARENTHESIS_L);
    r = r && _param.parse(b, l);
    r = r && consumeToken(b, PARENTHESIS_R);
    exit_section_(b, m, null, r);
    return r;
  }

  // BRACKET_L <<param>> BRACKET_R
  private static boolean paired_1(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "paired_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACKET_L);
    r = r && _param.parse(b, l);
    r = r && consumeToken(b, BRACKET_R);
    exit_section_(b, m, null, r);
    return r;
  }

  // BRACE_L <<param>> BRACE_R
  private static boolean paired_2(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "paired_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACE_L);
    r = r && _param.parse(b, l);
    r = r && consumeToken(b, BRACE_R);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // CITE symbol_path
  public static boolean ref(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ref")) return false;
    if (!nextTokenIs(b, CITE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CITE);
    r = r && symbol_path(b, l + 1);
    exit_section_(b, m, REF, r);
    return r;
  }

  /* ********************************************************** */
  // <<paired scope_inner>>
  public static boolean scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCOPE, "<scope>");
    r = paired(b, l + 1, VomlParser::scope_inner);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // [scope_mark] key_path
  static boolean scope_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_inner")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scope_inner_0(b, l + 1);
    r = r && key_path(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [scope_mark]
  private static boolean scope_inner_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_inner_0")) return false;
    scope_mark(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ACCENT | ANGLE_L+ | ANGLE_R
  public static boolean scope_mark(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_mark")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCOPE_MARK, "<scope mark>");
    r = consumeToken(b, ACCENT);
    if (!r) r = scope_mark_1(b, l + 1);
    if (!r) r = consumeToken(b, ANGLE_R);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ANGLE_L+
  private static boolean scope_mark_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_mark_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ANGLE_L);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, ANGLE_L)) break;
      if (!empty_element_parsed_guard_(b, "scope_mark_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [string_prefix] (string_inline|string_multi)
  static boolean str(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "str")) return false;
    if (!nextTokenIs(b, "", QUOTATION, SYMBOL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = str_0(b, l + 1);
    r = r && str_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [string_prefix]
  private static boolean str_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "str_0")) return false;
    string_prefix(b, l + 1);
    return true;
  }

  // string_inline|string_multi
  private static boolean str_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "str_1")) return false;
    boolean r;
    r = string_inline(b, l + 1);
    if (!r) r = string_multi(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // QUOTATION char* QUOTATION
  public static boolean string_inline(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_inline")) return false;
    if (!nextTokenIs(b, QUOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUOTATION);
    r = r && string_inline_1(b, l + 1);
    r = r && consumeToken(b, QUOTATION);
    exit_section_(b, m, STRING_INLINE, r);
    return r;
  }

  // char*
  private static boolean string_inline_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_inline_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!char_$(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "string_inline_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // QUOTATION char* QUOTATION
  public static boolean string_multi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_multi")) return false;
    if (!nextTokenIs(b, QUOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUOTATION);
    r = r && string_multi_1(b, l + 1);
    r = r && consumeToken(b, QUOTATION);
    exit_section_(b, m, STRING_MULTI, r);
    return r;
  }

  // char*
  private static boolean string_multi_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_multi_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!char_$(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "string_multi_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // SYMBOL
  public static boolean string_prefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_prefix")) return false;
    if (!nextTokenIs(b, SYMBOL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYMBOL);
    exit_section_(b, m, STRING_PREFIX, r);
    return r;
  }

  /* ********************************************************** */
  // key_like (DOT key_like)*
  public static boolean symbol_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_path")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SYMBOL_PATH, "<symbol path>");
    r = key_like(b, l + 1);
    r = r && symbol_path_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (DOT key_like)*
  private static boolean symbol_path_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_path_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!symbol_path_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "symbol_path_1", c)) break;
    }
    return true;
  }

  // DOT key_like
  private static boolean symbol_path_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_path_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && key_like(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [type_hint] <<paired table_inner>>
  public static boolean table(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TABLE, "<table>");
    r = table_0(b, l + 1);
    r = r && paired(b, l + 1, VomlParser::table_inner);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [type_hint]
  private static boolean table_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_0")) return false;
    type_hint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // [table_item (COMMA table_item)* [COMMA]]
  static boolean table_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_inner")) return false;
    table_inner_0(b, l + 1);
    return true;
  }

  // table_item (COMMA table_item)* [COMMA]
  private static boolean table_inner_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_inner_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = table_item(b, l + 1);
    r = r && table_inner_0_1(b, l + 1);
    r = r && table_inner_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA table_item)*
  private static boolean table_inner_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_inner_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!table_inner_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "table_inner_0_1", c)) break;
    }
    return true;
  }

  // COMMA table_item
  private static boolean table_inner_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_inner_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && table_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean table_inner_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_inner_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // pair | value
  static boolean table_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_item")) return false;
    boolean r;
    r = pair(b, l + 1);
    if (!r) r = value(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // SYMBOL
  public static boolean type_hint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_hint")) return false;
    if (!nextTokenIs(b, SYMBOL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYMBOL);
    exit_section_(b, m, TYPE_HINT, r);
    return r;
  }

  /* ********************************************************** */
  // NULL | BOOLEAN | num | ref | str | table
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = consumeToken(b, NULL);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = num(b, l + 1);
    if (!r) r = ref(b, l + 1);
    if (!r) r = str(b, l + 1);
    if (!r) r = table(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
