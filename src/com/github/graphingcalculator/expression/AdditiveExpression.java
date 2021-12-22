package com.github.graphingcalculator.expression;

public class AdditiveExpression extends AbstractOperativeExpression {
	@Override
	public String getSymbol() {
		return "+";
	}

	@Override
	public double operation(double a, double b) {
		return a + b;
	}
}
