// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface VomlScope extends PsiElement {

  @NotNull
  List<VomlScopeSymbol> getScopeSymbolList();

  @NotNull
  List<VomlStringInline> getStringInlineList();

  @Nullable
  PsiElement getAccent();

  @Nullable
  PsiElement getAngleR();

  @Nullable
  PsiElement getBraceL();

  @Nullable
  PsiElement getBraceR();

  @Nullable
  PsiElement getBracketL();

  @Nullable
  PsiElement getBracketR();

  @Nullable
  PsiElement getParenthesisL();

  @Nullable
  PsiElement getParenthesisR();

}
