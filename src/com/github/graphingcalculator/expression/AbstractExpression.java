package com.github.graphingcalculator.expression;

public abstract class AbstractExpression implements Expression {
	CompoundExpression _parent;

	/**
	 * @return the symbol associated with this expression
	 */
	public abstract String getSymbol();

	/**
	 * @return the parent associated with this expression
	 */
	@Override
	public CompoundExpression getParent() {
		return _parent;
	}

	/**
	 * @param parent the CompoundExpression that should be the parent of the target object
	 */
	@Override
	public void setParent(CompoundExpression parent) {
		_parent = parent;
	}

	/**
	 * Flatten expression and subexpressions by combining identical expressions
	 */
	@Override
	public void flatten() { }

	/**
	 * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
	 * @return a String representation of the expression tree
	 */
	@Override
	public String convertToString(int indentLevel) {
		return makeStringBuilder(indentLevel).toString();
	}

	/**
	 * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
	 * @return a StringBuilder to build a String representation of the expression tree
	 */
	protected StringBuilder makeStringBuilder(int indentLevel) {
		StringBuilder stringBuilder = new StringBuilder();
		Expression.indent(stringBuilder, indentLevel);
		stringBuilder.append(getSymbol());
		stringBuilder.append("\n");
		return stringBuilder;
	}
}
