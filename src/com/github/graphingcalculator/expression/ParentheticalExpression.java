package com.github.graphingcalculator.expression;

public class ParentheticalExpression extends AbstractCompoundExpression {
	@Override
	public String getSymbol() {
		return "()";
	}

	@Override
	public double evaluate(double x) {
		return _subexpressions.get(0).evaluate(x);
	}
}
