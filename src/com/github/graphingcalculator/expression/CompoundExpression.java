package com.github.graphingcalculator.expression;

public interface CompoundExpression extends Expression {
	/**
	 * Adds the specified expression as a child.
	 * @param subexpression the child expression to add
	 */
	void addSubexpression(Expression subexpression);
}
