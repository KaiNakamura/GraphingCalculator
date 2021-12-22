package com.github.graphingcalculator.expression;

public class LiteralExpression extends AbstractExpression {
	String _symbol;

	public LiteralExpression(String symbol) {
		_symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return _symbol;
	}

	@Override
	public double evaluate(double x) {
		return Double.parseDouble(_symbol);
	}
}
