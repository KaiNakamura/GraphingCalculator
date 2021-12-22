package com.github.graphingcalculator.expression;

public interface Expression {
	/**
	 * Given the value of the dependent variable x, evaluate this expression.
	 * @param x the given value of x
	 * @return the value of the expression
	 */
	double evaluate(double x);

	/**
	 * Gets the expression's parent.
	 * @return the expression's parent
	 */
	CompoundExpression getParent();

	/**
	 * Sets the parent be the specified expression.
	 * @param parent the CompoundExpression that should be the parent of the target object
	 */
	void setParent(CompoundExpression parent);

	/**
	 * Recursively flattens the expression as much as possible
	 * throughout the entire tree. Specifically, in every multiplicative
	 * or additive expression x whose first or last
	 * child c is of the same type as x, the children of c will be added to x, and
	 * c itself will be removed. This method modifies the expression itself.
	 */
	void flatten();

	/**
	 * Creates a String representation by recursively printing out (using indentation) the
	 * tree represented by this expression, starting at the specified indentation level.
	 * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
	 * @return a String representation of the expression tree
	 */
	String convertToString(int indentLevel);

	/**
	 * Static helper method to indent a specified number of times from the left margin, by
	 * appending tab characters to the specified StringBuilder.
	 * @param stringBuilder the StringBuilder to which to append tab characters
	 * @param indentLevel the number of tabs to append
	 */
	static void indent(StringBuilder stringBuilder, int indentLevel) {
		stringBuilder.append("\t".repeat(Math.max(0, indentLevel)));
	}
}
