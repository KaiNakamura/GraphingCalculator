package com.github.graphingcalculator.expressionparser;

/**
 * Exception thrown when a String cannot be parsed.
 */
public class ExpressionParseException extends Exception {
	/**
	 * Public constructor that takes a message to describe the exception.
	 * @param message describes the exception.
	 */
	public ExpressionParseException(String message) {
		super(message);
	}
}
