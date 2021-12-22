package com.github.graphingcalculator.expressionparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for SimpleExpressionParser
 */
public class SimpleExpressionParserTest {
	private ExpressionParser _parser;

	/**
	 * Instantiates the actors and movies graphs
	 */
	@BeforeEach
	public void setUp() {
		_parser = new SimpleExpressionParser();
	}

	/**
	 * Verifies that the SimpleExpressionParser could be instantiated
	 */
	@Test
	public void finishedLoading() {
		assertTrue(true);
		// Yay! We didn't crash
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testExpression1() throws ExpressionParseException {
		final String expressionStr = "x+x";
		final String parseTreeStr = "+\n\tx\n\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testExpression2() throws ExpressionParseException {
		final String expressionStr = "13*x";
		final String parseTreeStr = "*\n\t13.0\n\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testExpression3() throws ExpressionParseException {
		final String expressionStr = "4*(x-5*x)";
		final String parseTreeStr = "*\n\t4.0\n\t()\n\t\t-\n\t\t\tx\n\t\t\t*\n\t\t\t\t5.0\n\t\t\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testException1() {
		final String expressionStr = "1+2+";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testException2() {
		final String expressionStr = "((()))";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}

	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	@Test
	public void testException3() {
		final String expressionStr = "()()";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}

	/**
	 * Verifies that a specific expression is evaluated correctly.
	 */
	@Test
	public void testEvaluate1() throws ExpressionParseException {
		final String expressionStr = "4*(x+5*x)";
		assertEquals(72, (int) _parser.parse(expressionStr).evaluate(3));
	}

	/**
	 * Verifies that a specific expression is evaluated correctly.
	 */
	@Test
	public void testEvaluate2() throws ExpressionParseException {
		final String expressionStr = "x";
		assertEquals(1, (int) _parser.parse(expressionStr).evaluate(1));
	}

	/**
	 * Verifies that a specific expression is evaluated correctly.
	 */
	@Test
	public void testEvaluate3() throws ExpressionParseException {
		final String expressionStr = "x^2";
		assertEquals(1, (int) _parser.parse(expressionStr).evaluate(1));
	}

	/**
	 * Verifies that a specific expression is evaluated correctly.
	 */
	@Test
	public void testEvaluate4() throws ExpressionParseException {
		final String expressionStr = "2^x";
		assertEquals(8, (int) _parser.parse(expressionStr).evaluate(3));
	}

	/**
	 * Verifies that a specific expression is evaluated correctly.
	 */
	@Test
	public void testEvaluate5() throws ExpressionParseException {
		final String expressionStr = "x^3^2";
		assertEquals(262144, (int) _parser.parse(expressionStr).evaluate(4));
	}
}
