package com.github.graphingcalculator.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Expression
 */
public class ExpressionTest {
	/**
	 * Checks that a symbol is created at the correct indentation level
	 */
	@Test
	public void testExpressionToStringIndentationLevel() {
		assertEquals("a\n", new LiteralExpression("a").convertToString(0));
		assertEquals("\tb\n", new LiteralExpression("b").convertToString(1));
		assertEquals("\t\tc\n", new LiteralExpression("c").convertToString(2));

		assertEquals("()\n", new ParentheticalExpression().convertToString(0));
		assertEquals("\t()\n", new ParentheticalExpression().convertToString(1));
		assertEquals("\t\t()\n", new ParentheticalExpression().convertToString(2));

		assertEquals("+\n", new AdditiveExpression().convertToString(0));
		assertEquals("\t+\n", new AdditiveExpression().convertToString(1));
		assertEquals("\t\t+\n", new AdditiveExpression().convertToString(2));

		assertEquals("*\n", new MultiplicativeExpression().convertToString(0));
		assertEquals("\t*\n", new MultiplicativeExpression().convertToString(1));
		assertEquals("\t\t*\n", new MultiplicativeExpression().convertToString(2));
	}

	/**
	 * Checks that subexpressions are converted to a string correctly
	 */
	@Test
	public void testExpressionToStringSubexpressions() {
		AdditiveExpression additiveExpression1 = new AdditiveExpression();

		CompoundExpression multiplicativeExpression1 = new MultiplicativeExpression();
		multiplicativeExpression1.addSubexpression(new LiteralExpression("10"));
		multiplicativeExpression1.addSubexpression(new LiteralExpression("x"));
		multiplicativeExpression1.addSubexpression(new LiteralExpression("z"));

		CompoundExpression multiplicativeExpression2 = new MultiplicativeExpression();

		CompoundExpression parentheticalExpression = new ParentheticalExpression();

		CompoundExpression additiveExpression2 = new AdditiveExpression();
		additiveExpression2.addSubexpression(new LiteralExpression("15"));
		additiveExpression2.addSubexpression(new LiteralExpression("y"));

		parentheticalExpression.addSubexpression(additiveExpression2);

		multiplicativeExpression2.addSubexpression(new LiteralExpression("2"));
		multiplicativeExpression2.addSubexpression(parentheticalExpression);

		additiveExpression1.addSubexpression(multiplicativeExpression1);
		additiveExpression1.addSubexpression(multiplicativeExpression2);

		assertEquals(
			"+\n\t*\n\t\t10\n\t\tx\n\t\tz\n\t*\n\t\t2\n\t\t()\n\t\t\t+\n\t\t\t\t15\n\t\t\t\ty\n",
			additiveExpression1.convertToString(0)
		);
	}

	/**
	 * Checks that a flattened literal is just the literal
	 */
	@Test
	public void testFlattenLiteralIsLiteral() {
		AbstractExpression expression = new LiteralExpression("a");
		expression.flatten();

		assertEquals("a\n", expression.convertToString(0));
	}

	/**
	 * Checks that an expression with a child of the same type is flattened
	 */
	@Test
	public void testFlattenCombinesSameTypes() {
		AbstractCompoundExpression expression1 = new MultiplicativeExpression();
		expression1.addSubexpression(new LiteralExpression("10"));

		AbstractCompoundExpression expression2 = new MultiplicativeExpression();
		expression2.addSubexpression(new LiteralExpression("x"));
		expression2.addSubexpression(new LiteralExpression("y"));
		expression1.addSubexpression(expression2);

		expression1.flatten();

		assertEquals(
			"*\n\t10\n\tx\n\ty\n",
			expression1.convertToString(0)
		);
	}

	/**
	 * Checks that an expression with a child of the same type is flattened
	 * and that a child of a different type will not be flattened
	 */
	@Test
	public void testFlattenDoesNotCombineDifferentTypes() {
		AbstractCompoundExpression expression1 = new MultiplicativeExpression();
		expression1.addSubexpression(new LiteralExpression("10"));

		AbstractCompoundExpression expression2 = new MultiplicativeExpression();
		expression2.addSubexpression(new LiteralExpression("x"));
		expression2.addSubexpression(new LiteralExpression("y"));
		expression1.addSubexpression(expression2);

		AbstractCompoundExpression expression3 = new AdditiveExpression();
		expression3.addSubexpression(new LiteralExpression("a"));
		expression3.addSubexpression(new LiteralExpression("b"));
		expression1.addSubexpression(expression3);

		expression1.flatten();

		assertEquals(
			"*\n\t10\n\tx\n\ty\n\t+\n\t\ta\n\t\tb\n",
			expression1.convertToString(0)
		);
	}

	/**
	 * Checks that an expression with a child of the same type is flattened
	 * and children are also flattened recursively
	 */
	@Test
	public void testFlattenIsRecursive() {
		AbstractCompoundExpression expression1 = new MultiplicativeExpression();
		expression1.addSubexpression(new LiteralExpression("10"));

		AbstractCompoundExpression expression2 = new MultiplicativeExpression();
		expression2.addSubexpression(new LiteralExpression("x"));
		expression2.addSubexpression(new LiteralExpression("y"));

		AbstractCompoundExpression expression3 = new MultiplicativeExpression();
		expression3.addSubexpression(new LiteralExpression("a"));
		expression3.addSubexpression(new LiteralExpression("b"));
		expression2.addSubexpression(expression3);

		expression1.addSubexpression(expression2);

		expression1.flatten();

		assertEquals(
			"*\n\t10\n\tx\n\ty\n\ta\n\tb\n",
			expression1.convertToString(0)
		);
	}
}
