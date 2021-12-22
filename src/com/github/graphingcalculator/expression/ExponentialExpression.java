package com.github.graphingcalculator.expression;

public class ExponentialExpression extends AbstractOperativeExpression {
	@Override
	public String getSymbol() {
		return "^";
	}

	@Override
	public double operation(double a, double b) {
		return Math.pow(a, b);
	}
}
