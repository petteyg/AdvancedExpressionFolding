package com.intellij.advancedExpressionFolding;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

import java.util.List;

public class Sin extends Function implements ArithmeticExpression {
    public Sin(TextRange textRange, List<Expression> operands) {
        super(textRange, "sin", operands);
    }

    @Override
    protected Sin copy(List<Expression> newOperands) {
        return new Sin(textRange, newOperands);
    }
}
