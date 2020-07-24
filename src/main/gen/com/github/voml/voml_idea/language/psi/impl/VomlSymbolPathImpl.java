// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_idea.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.voml.voml_idea.language.psi.VomlTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.voml.voml_idea.language.psi.*;

public class VomlSymbolPathImpl extends ASTWrapperPsiElement implements VomlSymbolPath {

  public VomlSymbolPathImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull VomlVisitor visitor) {
    visitor.visitSymbolPath(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof VomlVisitor) accept((VomlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<VomlStringInline> getStringInlineList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, VomlStringInline.class);
  }

  @Override
  @NotNull
  public List<VomlStringMulti> getStringMultiList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, VomlStringMulti.class);
  }

  @Override
  @NotNull
  public List<VomlStringPrefix> getStringPrefixList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, VomlStringPrefix.class);
  }

}
