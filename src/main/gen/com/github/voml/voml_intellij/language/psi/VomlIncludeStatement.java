// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface VomlIncludeStatement extends PsiElement {

  @NotNull
  List<VomlKeySymbol> getKeySymbolList();

  @NotNull
  VomlStringInline getStringInline();

  @Nullable
  VomlStringPrefix getStringPrefix();

}
