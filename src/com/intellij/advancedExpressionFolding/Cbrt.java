package com.intellij.advancedExpressionFolding;

import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cbrt extends Function implements ArithmeticExpression {
    public Cbrt(TextRange textRange, List<Expression> operands) {
        super(textRange, "∛", operands);
    }

    @Override
    protected Cbrt copy(List<Expression> newOperands) {
        return new Cbrt(textRange, newOperands);
    }

    @Override
    public String format() {
        if (operands.get(0) instanceof NumberLiteral ||
                operands.get(0) instanceof Variable) {
            return "∛" + operands.get(0).format();
        } else {
            return "∛" + "(" + operands.get(0).format() + ")";
        }
    }

    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement element, @NotNull Document document) {
        ArrayList<FoldingDescriptor> descriptors = new ArrayList<>();
        FoldingGroup group = FoldingGroup.newGroup(Abs.class.getName());
        descriptors.add(new FoldingDescriptor(element.getNode(),
                TextRange.create(getTextRange().getStartOffset(),
                        operands.get(0).getTextRange().getStartOffset()), group) {
            @Nullable
            @Override
            public String getPlaceholderText() {
                return operands.get(0) instanceof NumberLiteral ||
                        operands.get(0) instanceof Variable ?
                "∛" : "∛(";
            }
        });
        descriptors.add(new FoldingDescriptor(element.getNode(),
                TextRange.create(operands.get(0).getTextRange().getEndOffset(),
                        getTextRange().getEndOffset()), group) {
            @Nullable
            @Override
            public String getPlaceholderText() {
                return operands.get(0) instanceof NumberLiteral ||
                        operands.get(0) instanceof Variable ?
                        "" : ")";
            }
        });
        if (operands.get(0).supportsFoldRegions(document, false)) {
            Collections.addAll(descriptors, operands.get(0).buildFoldRegions(element, document));
        }
        return descriptors.toArray(FoldingDescriptor.EMPTY);
    }
}
