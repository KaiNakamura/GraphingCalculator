package com.github.graphingcalculator.expressionparser;

import com.github.graphingcalculator.expression.*;

public class SimpleExpressionParser implements ExpressionParser {
	/**
	 * Grammar:
	 * S -> A | P
	 * A -> A+M | A-M | M
	 * M -> M*E | M/E | E
	 * E -> P^E | P
	 * P -> (S) | L | V
	 * L -> <float>
	 * V -> x
	 */
	public Expression parse(String str) throws ExpressionParseException {
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		return expression;
	}

	/**
	 * S -> A | P
	 */
	protected Expression parseExpression(String str) {
		Expression expression = parseAdditiveExpression(str);
		if (expression == null) {
			expression = parseParentheticalExpression(str);
		}

		return expression;
	}

	/**
	 * A -> A+M | A-M | M
	 */
	protected Expression parseAdditiveExpression(String str) {
		for (int i = 1; i < str.length() - 1; i++) {
			Expression before = parseAdditiveExpression(str.substring(0, i));
			Expression after = parseMultiplicativeExpression(str.substring(i + 1));
			char c = str.charAt(i);
			if (before != null && after != null) {
				if (c == '+') {
					CompoundExpression expression = new AdditiveExpression();
					expression.addSubexpression(before);
					expression.addSubexpression(after);
					return expression;
				} else if (c == '-') {
					CompoundExpression expression = new SubtractiveExpression();
					expression.addSubexpression(before);
					expression.addSubexpression(after);
					return expression;
				}
			}
		}

		return parseMultiplicativeExpression(str);
	}

	/**
	 * M -> M*E | M/E | E
	 */
	protected Expression parseMultiplicativeExpression(String str) {
		for (int i = 1; i < str.length() - 1; i++) {
			Expression before = parseMultiplicativeExpression(str.substring(0, i));
			Expression after = parseExponentialExpression(str.substring(i + 1));
			char c = str.charAt(i);
			if (before != null && after != null) {
				if (c == '*') {
					CompoundExpression expression = new MultiplicativeExpression();
					expression.addSubexpression(before);
					expression.addSubexpression(after);
					return expression;
				} else if (c == '/') {
					CompoundExpression expression = new DivisionalExpression();
					expression.addSubexpression(before);
					expression.addSubexpression(after);
					return expression;
				}
			}
		}

		return parseExponentialExpression(str);
	}

	/**
	 * E -> P^E | P
	 */
	protected Expression parseExponentialExpression(String str) {
		for (int i = 1; i < str.length() - 1; i++) {
			Expression before = parseParentheticalExpression(str.substring(0, i));
			Expression after = parseExponentialExpression(str.substring(i + 1));
			char c = str.charAt(i);
			if (before != null && after != null) {
				if (c == '^') {
					CompoundExpression expression = new ExponentialExpression();
					expression.addSubexpression(before);
					expression.addSubexpression(after);
					return expression;
				}
			}
		}

		return parseParentheticalExpression(str);
	}

	/**
	 * P -> (S) | L | V
	 */
	protected Expression parseParentheticalExpression(String str) {
		if (str.length() >= 2 &&
			str.charAt(0) == '(' &&
			str.charAt(str.length() - 1) == ')') {
			Expression inside = parseExpression(str.substring(1, str.length() - 1));
			if (inside != null) {
				CompoundExpression parentheticalExpression = new ParentheticalExpression();
				parentheticalExpression.addSubexpression(inside);
				return parentheticalExpression;
			}
		}

		Expression expression = parseLiteralExpression(str);
		if (expression == null) {
			expression = parseVariableExpression(str);
		}
		return expression;
	}

	/**
	 * L -> <float>
	 */
	protected LiteralExpression parseLiteralExpression(String str) {
		// From https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double/22936891:
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
		    ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
		    "[+-]?(" +         // Optional sign character
		    "NaN|" +           // "NaN" string
		    "Infinity|" +      // "Infinity" string

		// A decimal floating-point string representing a finite positive
		// number without a leading sign has at most five basic pieces:
		// Digits . Digits ExponentPart FloatTypeSuffix
		//
		// Since this method allows integer-only strings as input
		// in addition to strings of floating-point literals, the
		// two sub-patterns below are simplifications of the grammar
		// productions from the Java Language Specification, 2nd
		// edition, section 3.10.2.

		// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
		"((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

		// . Digits ExponentPart_opt FloatTypeSuffix_opt
		"(\\.("+Digits+")("+Exp+")?)|"+

		// Hexadecimal strings
		"((" +
		// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
		"(0[xX]" + HexDigits + "(\\.)?)|" +

		// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
		"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

		")[pP][+-]?" + Digits + "))" +
		"[fFdD]?))" +
		"[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (str.matches(fpRegex)) {
			return new LiteralExpression(Double.valueOf(str).toString());
		}

		return null;
	}

	/**
	 * V -> x
	 */
	protected VariableExpression parseVariableExpression(String str) {
		if (str.equals("x")) {
			return new VariableExpression();
		}
		return null;
	}
}
