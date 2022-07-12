// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface VomlAnnotation extends PsiElement {

  @NotNull
  VomlAnnotationMark getAnnotationMark();

  @NotNull
  List<VomlKeySymbol> getKeySymbolList();

  @NotNull
  List<VomlPredefinedSymbol> getPredefinedSymbolList();

  @NotNull
  List<VomlValue> getValueList();

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
