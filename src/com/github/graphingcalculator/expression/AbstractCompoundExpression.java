package com.github.graphingcalculator.expression;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCompoundExpression extends AbstractExpression implements CompoundExpression {
	List<Expression> _subexpressions = new ArrayList<>();

	@Override
	public void addSubexpression(Expression subexpression) {
		_subexpressions.add(subexpression);
	}

	@Override
	public void flatten() {
		List<Expression> flattenedSubexpressions = new ArrayList<>();
		for (Expression subexpression : _subexpressions) {
			if (subexpression instanceof AbstractExpression) {
				subexpression.flatten();
			}
			if (subexpression.getClass().equals(getClass())) {
				flattenedSubexpressions.addAll(((AbstractCompoundExpression) subexpression)._subexpressions);
			} else {
				flattenedSubexpressions.add(subexpression);
			}
		}
		_subexpressions = flattenedSubexpressions;
	}

	@Override
	protected StringBuilder makeStringBuilder(int indentLevel) {
		StringBuilder stringBuilder = super.makeStringBuilder(indentLevel);
		for (Expression subexpression : _subexpressions) {
			stringBuilder.append(subexpression.convertToString(indentLevel + 1));
		}
		return stringBuilder;
	}
}
