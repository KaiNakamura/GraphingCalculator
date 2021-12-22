package com.github.graphingcalculator.expression;

public class VariableExpression extends AbstractExpression {
	@Override
	public String getSymbol() {
		return "x";
	}

	@Override
	public double evaluate(double x) {
		return x;
	}
}
