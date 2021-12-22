package com.github.graphingcalculator.expression;

public abstract class AbstractOperativeExpression extends AbstractCompoundExpression implements OperativeExpression {
	@Override
	public abstract double operation(double a, double b);

	@Override
	public double fold(double x) {
		if (_subexpressions.size() == 0) {
			return 0;
		}

		double result = _subexpressions.get(0).evaluate(x);
		for (int i = 1; i < _subexpressions.size(); i++) {
			result = operation(result, _subexpressions.get(i).evaluate(x));
		}
		return result;
	}

	@Override
	public double evaluate(double x) {
		return fold(x);
	}
}
