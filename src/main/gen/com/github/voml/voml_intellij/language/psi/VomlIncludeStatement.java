// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface VomlIncludeStatement extends PsiElement {

  @Nullable
  VomlKeySymbol getKeySymbol();

  @NotNull
  List<VomlScopeSymbol> getScopeSymbolList();

  @NotNull
  VomlStringInline getStringInline();

  @Nullable
  VomlStringPrefix getStringPrefix();

}
