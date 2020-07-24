package com.github.voml.voml_idea.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.voml.voml_idea.language.psi.VomlTypes.*;

%%

%{
  public _VomlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _VomlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

COMMENT="//"[^\r\n]*
BLOCK_COMMENT=[/][*][^*]*[*]+([^/*][^*]*[*]+)*[/]
BOOLEAN=true|false
SYMBOL=[A-Za-z_][A-Za-z0-9_]*
BYTE=(0[bBoOxX][0-9A-Fa-f][0-9A-Fa-f_]*)
INTEGER=(0|[1-9][0-9_]*)
FLOAT=([0-9]+\.[0-9]*([*][*][0-9]+)?)|(\.[0-9]+([Ee][0-9]+)?)
SIGN=[+-]
NON_ESCAPE=[^\\]

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  "null"               { return NULL; }
  "@include"           { return INCLUDE; }
  "@inherit"           { return INHERIT; }
  "as"                 { return AS; }
  "("                  { return PARENTHESIS_L; }
  ")"                  { return PARENTHESIS_R; }
  "["                  { return BRACKET_L; }
  "]"                  { return BRACKET_R; }
  "{"                  { return BRACE_L; }
  "}"                  { return BRACE_R; }
  "^"                  { return ACCENT; }
  "<"                  { return ANGLE_L; }
  ">"                  { return ANGLE_R; }
  "\""                 { return QUOTATION; }
  "\\"                 { return ESCAPE; }
  "="                  { return EQ; }
  "nan"                { return NAN; }
  ":"                  { return COLON; }
  ";"                  { return SEMICOLON; }
  ","                  { return COMMA; }
  "$"                  { return CITE; }
  "."                  { return DOT; }
  "*"                  { return STAR; }
  "DECIMAL"            { return DECIMAL; }

  {COMMENT}            { return COMMENT; }
  {BLOCK_COMMENT}      { return BLOCK_COMMENT; }
  {BOOLEAN}            { return BOOLEAN; }
  {SYMBOL}             { return SYMBOL; }
  {BYTE}               { return BYTE; }
  {INTEGER}            { return INTEGER; }
  {FLOAT}              { return FLOAT; }
  {SIGN}               { return SIGN; }
  {NON_ESCAPE}         { return NON_ESCAPE; }

}

[^] { return BAD_CHARACTER; }
