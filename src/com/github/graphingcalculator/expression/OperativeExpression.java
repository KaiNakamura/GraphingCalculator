package com.github.graphingcalculator.expression;

public interface OperativeExpression extends CompoundExpression {
	/**
	 * Perform some operation on two values
	 * @param a the first value
	 * @param b the second value
	 * @return the result of the operation
	 */
	double operation(double a, double b);

	/**
	 * Combine all subexpressions into a single result
	 * @param x the x value to evaluate at
	 * @return the result
	 */
	double fold(double x);
}
