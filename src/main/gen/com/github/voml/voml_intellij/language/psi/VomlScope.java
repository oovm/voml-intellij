// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface VomlScope extends PsiElement {

  @Nullable
  VomlScopeMark getScopeMark();

  @NotNull
  List<VomlScopeSymbol> getScopeSymbolList();

  @NotNull
  List<VomlStringInline> getStringInlineList();

}
