// This is a generated file. Not intended for manual editing.
package com.github.voml.voml_intellij.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.voml.voml_intellij.language.psi.VomlTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.voml.voml_intellij.language.psi.*;

public class VomlScopeImpl extends ASTWrapperPsiElement implements VomlScope {

  public VomlScopeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull VomlVisitor visitor) {
    visitor.visitScope(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof VomlVisitor) accept((VomlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<VomlScopeSymbol> getScopeSymbolList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, VomlScopeSymbol.class);
  }

  @Override
  @NotNull
  public List<VomlStringInline> getStringInlineList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, VomlStringInline.class);
  }

  @Override
  @Nullable
  public PsiElement getAccent() {
    return findChildByType(ACCENT);
  }

  @Override
  @Nullable
  public PsiElement getAngleR() {
    return findChildByType(ANGLE_R);
  }

  @Override
  @Nullable
  public PsiElement getBraceL() {
    return findChildByType(BRACE_L);
  }

  @Override
  @Nullable
  public PsiElement getBraceR() {
    return findChildByType(BRACE_R);
  }

  @Override
  @Nullable
  public PsiElement getBracketL() {
    return findChildByType(BRACKET_L);
  }

  @Override
  @Nullable
  public PsiElement getBracketR() {
    return findChildByType(BRACKET_R);
  }

  @Override
  @Nullable
  public PsiElement getParenthesisL() {
    return findChildByType(PARENTHESIS_L);
  }

  @Override
  @Nullable
  public PsiElement getParenthesisR() {
    return findChildByType(PARENTHESIS_R);
  }

}
