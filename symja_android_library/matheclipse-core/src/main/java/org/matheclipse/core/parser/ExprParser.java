/*
 * Copyright 2005-2013 Axel Kramer (axelclk@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.matheclipse.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.function.Blank;
import org.matheclipse.core.builtin.function.Complex;
import org.matheclipse.core.builtin.function.Pattern;
import org.matheclipse.core.builtin.function.Rational;
import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.NumStr;
//import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.VisitorExpr;
import org.matheclipse.parser.client.SyntaxError;

/**
 * Create an expression of the <code>ASTNode</code> class-hierarchy from a math
 * formulas string representation
 * 
 * See
 * <a href="http://en.wikipedia.org/wiki/Operator-precedence_parser">Operator
 * -precedence parser</a> for the idea, how to parse the operators depending on
 * their precedence.
 */
public class ExprParser extends ExprScanner {
	static {
		F.initSymbols(null, null, true);
	}

	static class NVisitorExpr extends VisitorExpr {
		final int fPrecision;

		NVisitorExpr(int precision) {
			super();
			fPrecision = precision;
		}

		public IExpr visit(INum element) {
			if (element instanceof NumStr) {
				Apfloat apfloatValue = new Apfloat(((NumStr) element).getFloatStr(), fPrecision);
				int exponent = ((NumStr) element).getExponent();
				if (exponent != 1) {
					// value * 10 ^ exponent
					return F.num(apfloatValue.multiply(ApfloatMath.pow(new Apint(10), new Apint(exponent))));
				}
				return F.num(apfloatValue);
			}
			return element;
		}
	}

	public final static ISymbol DERIVATIVE = F.Derivative;

	private final boolean fRelaxedSyntax;
	private List<IExpr> fNodeList = null;
	private final EvalEngine fEngine;

	public ExprParser(final EvalEngine engine) {
		this(engine, ExprParserFactory.MMA_STYLE_FACTORY, engine.isRelaxedSyntax(), false);
	}

	/**
	 * 
	 * @param relaxedSyntax
	 *            if <code>true</code>, use '('...')' as brackets for arguments
	 * @throws SyntaxError
	 */
	public ExprParser(final EvalEngine engine, final boolean relaxedSyntax) throws SyntaxError {
		this(engine, ExprParserFactory.MMA_STYLE_FACTORY, relaxedSyntax);
	}

	public ExprParser(final EvalEngine engine, final boolean relaxedSyntax, boolean packageMode) throws SyntaxError {
		this(engine, ExprParserFactory.MMA_STYLE_FACTORY, relaxedSyntax, packageMode);
	}

	/**
	 * 
	 * @param factory
	 * @param relaxedSyntax
	 *            if <code>true</code>, use '('...')' as brackets for arguments
	 * @throws SyntaxError
	 */
	public ExprParser(final EvalEngine engine, IExprParserFactory factory, final boolean relaxedSyntax)
			throws SyntaxError {
		this(engine, factory, relaxedSyntax, false);
	}

	public ExprParser(final EvalEngine engine, IExprParserFactory factory, final boolean relaxedSyntax,
			boolean packageMode) throws SyntaxError {
		super(packageMode);
		this.fRelaxedSyntax = relaxedSyntax;
		this.fFactory = factory;
		this.fEngine = engine;
		if (packageMode) {
			fNodeList = new ArrayList<IExpr>(256);
		}
	}

	public void setFactory(final IExprParserFactory factory) {
		this.fFactory = factory;
	}

	public IExprParserFactory getFactory() {
		return fFactory;
	}

	/**
	 * construct the arguments for an expression
	 * 
	 */
	private void getArguments(final IAST function) throws SyntaxError {
		do {
			function.add(parseOperators(parsePrimary(), 0));

			if (fToken != TT_COMMA) {
				break;
			}

			getNextToken();
		} while (true);
	}

	/**
	 * Determine the current PrefixOperator
	 * 
	 * @return <code>null</code> if no prefix operator could be determined
	 */
	private PrefixExprOperator determinePrefixOperator() {
		AbstractExprOperator oper = null;
		for (int i = 0; i < fOperList.size(); i++) {
			oper = fOperList.get(i);
			if (oper instanceof PrefixExprOperator) {
				return (PrefixExprOperator) oper;
			}
		}
		return null;
	}

	/**
	 * Determine the current PostfixOperator
	 * 
	 * @return <code>null</code> if no postfix operator could be determined
	 */
	private PostfixExprOperator determinePostfixOperator() {
		AbstractExprOperator oper = null;
		for (int i = 0; i < fOperList.size(); i++) {
			oper = fOperList.get(i);
			if (oper instanceof PostfixExprOperator) {
				return (PostfixExprOperator) oper;
			}
		}
		return null;
	}

	/**
	 * Determine the current BinaryOperator
	 * 
	 * @return <code>null</code> if no binary operator could be determined
	 */
	private InfixExprOperator determineBinaryOperator() {
		AbstractExprOperator oper = null;
		for (int i = 0; i < fOperList.size(); i++) {
			oper = fOperList.get(i);
			if (oper instanceof InfixExprOperator) {
				return (InfixExprOperator) oper;
			}
		}
		return null;
	}

	private IExpr parseArguments(IExpr lhs) {
		if (fRelaxedSyntax) {
			if (fToken == TT_ARGUMENTS_OPEN) {
				IAST ast = getFunctionArguments(lhs);
				return convert(ast);
			} else if (fToken == TT_PRECEDENCE_OPEN) {
				IAST ast = getFunction(lhs);
				return convert(ast);
			}
		} else {
			if (fToken == TT_ARGUMENTS_OPEN) {
				IAST ast = getFunctionArguments(lhs);
				return convert(ast);
			}
		}
		return lhs;
	}

	private IExpr parsePrimary() {
		if (fToken == TT_OPERATOR) {
			if (fOperatorString.equals(".")) {
				fCurrentChar = '.';
				// fToken = TT_DIGIT;
				// return getPart();
				return getNumber(false);
			}
			final PrefixExprOperator prefixOperator = determinePrefixOperator();
			if (prefixOperator != null) {
				getNextToken();
				final IExpr temp = parseLookaheadOperator(prefixOperator.getPrecedence());
				if (prefixOperator.getFunctionName().equals("PreMinus")) {
					// special cases for negative numbers
					if (temp.isNumber()) {
						return temp.negate();
					}
				}
				return prefixOperator.createFunction(fFactory, temp);
			}
			throwSyntaxError("Operator: " + fOperatorString + " is no prefix operator.");

		}
		return getPart();
	}

	private IExpr parseLookaheadOperator(final int min_precedence) {
		IExpr rhs = parsePrimary();

		while (true) {
			final int lookahead = fToken;
			if (fToken == TT_NEWLINE) {
				return rhs;
			}
			if ((fToken == TT_LIST_OPEN) || (fToken == TT_PRECEDENCE_OPEN) || (fToken == TT_IDENTIFIER)
					|| (fToken == TT_STRING) || (fToken == TT_DIGIT)) {
				// if (fPackageMode && fRecursionDepth < 1) {
				// return rhs;
				// }
				// lazy evaluation of multiplication
				InfixExprOperator timesOperator = (InfixExprOperator) fFactory.get("Times");
				if (timesOperator.getPrecedence() > min_precedence) {
					rhs = parseOperators(rhs, timesOperator.getPrecedence());
					continue;
				} else if ((timesOperator.getPrecedence() == min_precedence)
						&& (timesOperator.getGrouping() == InfixExprOperator.RIGHT_ASSOCIATIVE)) {
					rhs = parseOperators(rhs, timesOperator.getPrecedence());
					continue;
				}
			} else {
				if (lookahead != TT_OPERATOR) {
					break;
				}
				InfixExprOperator infixOperator = determineBinaryOperator();
				if (infixOperator != null) {
					if (infixOperator.getPrecedence() > min_precedence
							|| ((infixOperator.getPrecedence() == min_precedence)
									&& (infixOperator.getGrouping() == InfixExprOperator.RIGHT_ASSOCIATIVE))) {
						if (infixOperator.getOperatorString().equals(";")) {
							IExpr lhs = rhs;
							rhs = F.Null;
							if (fPackageMode && fRecursionDepth < 1) {
								return createInfixFunction(infixOperator, lhs, rhs);
							}
						}
						rhs = parseOperators(rhs, infixOperator.getPrecedence());
						continue;
					}

					// if (infixOperator.getPrecedence() > min_precedence) {
					// IExpr compoundExpressionNull =
					// parseCompoundExpressionNull(infixOperator, rhs);
					// if (compoundExpressionNull != null) {
					// return compoundExpressionNull;
					// }
					// rhs = parseOperators(rhs, infixOperator.getPrecedence());
					// continue;
					// } else if ((infixOperator.getPrecedence() ==
					// min_precedence)
					// && (infixOperator.getGrouping() ==
					// InfixOperator.RIGHT_ASSOCIATIVE)) {
					// rhs = parseOperators(rhs, infixOperator.getPrecedence());
					// continue;
					// }
				} else {
					PostfixExprOperator postfixOperator = determinePostfixOperator();
					if (postfixOperator != null) {
						if (postfixOperator.getPrecedence() > min_precedence) {
							getNextToken();
							// rhs =
							// F.$(F.$s(postfixOperator.getFunctionName()),
							// rhs);
							rhs = postfixOperator.createFunction(fFactory, rhs);
							continue;
						}
					}
				}
			}
			break;
		}
		return rhs;

	}

	private IExpr createInfixFunction(InfixExprOperator infixOperator, IExpr lhs, IExpr rhs) {
		IExpr temp = infixOperator.createFunction(fFactory, lhs, rhs);
		if (temp.isAST()) {
			return convert((IAST) temp);
		}
		return temp;
		// if (infixOperator.getOperatorString().equals("//")) {
		// // lhs // rhs ==> rhs[lhs]
		// IAST function = F.ast(rhs);
		// function.add(lhs);
		// return function;
		// }
		// return F.$(F.$s(infixOperator.getFunctionName()), lhs, rhs);
	}

	private IExpr convertSymbol(final String nodeStr) {
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			if (nodeStr.length() == 1) {
				if (nodeStr.equals("I")) {
					// special - convert on input
					return F.CI;
				}
				return F.retrieveSymbol(nodeStr);
			}
			String lowercaseStr = nodeStr.toLowerCase(Locale.ENGLISH);
			if (lowercaseStr.equals("infinity")) {
				// special - convert on input
				return F.CInfinity;
			} else if (lowercaseStr.equals("complexinfinity")) {
				// special - convert on input
				return F.CComplexInfinity;
			}
			String temp = AST2Expr.PREDEFINED_ALIASES_MAP.get(lowercaseStr);
			if (temp != null) {
				return F.retrieveSymbol(temp);
			}
			return F.retrieveSymbol(lowercaseStr);
		} else {
			String lowercaseStr = nodeStr;
			if (fRelaxedSyntax) {
				lowercaseStr = nodeStr.toLowerCase(Locale.ENGLISH);
				String temp = AST2Expr.PREDEFINED_SYMBOLS_MAP.get(lowercaseStr);
				if (temp != null) {
					lowercaseStr = temp;
				}
			}

			if (Config.RUBI_CONVERT_SYMBOLS) {
				Integer num = AST2Expr.RUBI_STATISTICS_MAP.get(lowercaseStr);
				if (num == null) {
					AST2Expr.RUBI_STATISTICS_MAP.put(lowercaseStr, 1);
				} else {
					AST2Expr.RUBI_STATISTICS_MAP.put(lowercaseStr, num + 1);
				}
			}

			if (lowercaseStr.equals("I")) {
				// special - convert on input
				return F.CI;
			} else if (lowercaseStr.equals("Infinity")) {
				// special - convert on input
				return F.CInfinity;
			}
			return F.retrieveSymbol(lowercaseStr);
		}
	}

	private IExpr parseCompoundExpressionNull(InfixExprOperator infixOperator, IExpr rhs) {
		if (infixOperator.getOperatorString().equals(";")) {
			if (fToken == TT_ARGUMENTS_CLOSE || fToken == TT_LIST_CLOSE || fToken == TT_PRECEDENCE_CLOSE) {
				return createInfixFunction(infixOperator, rhs, F.Null);
				// return infixOperator.createFunction(fFactory, rhs,
				// fFactory.createSymbol("Null"));
			}
			if (fPackageMode && fRecursionDepth < 1) {
				return createInfixFunction(infixOperator, rhs, F.Null);
				// return infixOperator.createFunction(fFactory, rhs,
				// fFactory.createSymbol("Null"));
			}
		}
		return null;
	}

	/**
	 * See <a href="http://en.wikipedia.org/wiki/Operator-precedence_parser">
	 * Operator -precedence parser</a> for the idea, how to parse the operators
	 * depending on their precedence.
	 * 
	 * @param lhs
	 *            the already parsed left-hand-side of the operator
	 * @param min_precedence
	 * @return
	 */
	private IExpr parseOperators(IExpr lhs, final int min_precedence) {
		IExpr rhs = null;
		AbstractExprOperator oper;
		InfixExprOperator infixOperator;
		PostfixExprOperator postfixOperator;
		while (true) {
			if (fToken == TT_NEWLINE) {
				return lhs;
			}
			if ((fToken == TT_LIST_OPEN) || (fToken == TT_PRECEDENCE_OPEN) || (fToken == TT_IDENTIFIER)
					|| (fToken == TT_STRING) || (fToken == TT_DIGIT) || (fToken == TT_SLOT)
					|| (fToken == TT_SLOTSEQUENCE)) {
				// if (fPackageMode && fRecursionDepth < 1) {
				// return lhs;
				// }
				// if (fPackageMode && fToken == TT_IDENTIFIER && fLastChar ==
				// '\n') {
				// return lhs;
				// }
				// lazy evaluation of multiplication
				oper = fFactory.get("Times");
				if (oper.getPrecedence() >= min_precedence) {
					rhs = parseLookaheadOperator(oper.getPrecedence());
					lhs = F.$(F.$s(oper.getFunctionName()), lhs, rhs);
					// lhs =
					// fFactory.createFunction(fFactory.createSymbol(oper.getFunctionName()),
					// lhs, rhs);
					continue;
				}
			} else {
				if (fToken != TT_OPERATOR) {
					if (fToken == TT_DERIVATIVE) {
						int derivativeCounter = 1;
						getNextToken();
						while (fToken == TT_DERIVATIVE) {
							derivativeCounter++;
							getNextToken();
						}
						IAST deriv = F.$(DERIVATIVE, F.integer(derivativeCounter));
						lhs = F.$(deriv, lhs);
						lhs = parseArguments(lhs);
						continue;
					}
					break;
				}
				infixOperator = determineBinaryOperator();

				if (infixOperator != null) {
					if (infixOperator.getPrecedence() >= min_precedence) {
						getNextToken();
						IExpr compoundExpressionNull = parseCompoundExpressionNull(infixOperator, lhs);
						if (compoundExpressionNull != null) {
							return compoundExpressionNull;
						}

						while (fToken == TT_NEWLINE) {
							getNextToken();
						}
						rhs = parseLookaheadOperator(infixOperator.getPrecedence());
						lhs = createInfixFunction(infixOperator, lhs, rhs);
						// lhs = infixOperator.createFunction(fFactory, lhs,
						// rhs);

						continue;
					}
				} else {
					postfixOperator = determinePostfixOperator();

					if (postfixOperator != null) {
						if (postfixOperator.getPrecedence() >= min_precedence) {
							getNextToken();
							lhs = postfixOperator.createFunction(fFactory, lhs);
							lhs = parseArguments(lhs);
							continue;
						}
					} else {
						throwSyntaxError("Operator: " + fOperatorString + " is no infix or postfix operator.");
					}
				}
			}
			break;
		}
		return lhs;
	}

	/**
	 * Parse the given <code>expression</code> String into an IExpr.
	 * 
	 * @param expression
	 *            a formula string which should be parsed.
	 * @return the parsed IExpr representation of the given formula string
	 * @throws SyntaxError
	 */
	public IExpr parse(final String expression) throws SyntaxError {
		initialize(expression);
		final IExpr temp = parseOperators(parsePrimary(), 0);
		if (fToken != TT_EOF) {
			if (fToken == TT_PRECEDENCE_CLOSE) {
				throwSyntaxError("Too many closing ')'; End-of-file not reached.");
			}
			if (fToken == TT_LIST_CLOSE) {
				throwSyntaxError("Too many closing '}'; End-of-file not reached.");
			}
			if (fToken == TT_ARGUMENTS_CLOSE) {
				throwSyntaxError("Too many closing ']'; End-of-file not reached.");
			}

			throwSyntaxError("End-of-file not reached.");
		}

		return temp;
	}

	public List<IExpr> parsePackage(final String expression) throws SyntaxError {
		initialize(expression);
		while (fToken == TT_NEWLINE) {
			getNextToken();
		}
		IExpr temp = parseOperators(parsePrimary(), 0);
		fNodeList.add(temp);
		while (fToken != TT_EOF) {
			if (fToken == TT_PRECEDENCE_CLOSE) {
				throwSyntaxError("Too many closing ')'; End-of-file not reached.");
			}
			if (fToken == TT_LIST_CLOSE) {
				throwSyntaxError("Too many closing '}'; End-of-file not reached.");
			}
			if (fToken == TT_ARGUMENTS_CLOSE) {
				throwSyntaxError("Too many closing ']'; End-of-file not reached.");
			}
			while (fToken == TT_NEWLINE) {
				getNextToken();
			}
			if (fToken == TT_EOF) {
				return fNodeList;
			}
			temp = parseOperators(parsePrimary(), 0);
			fNodeList.add(temp);
			// throwSyntaxError("End-of-file not reached.");
		}

		return fNodeList;
	}

	/**
	 * Method Declaration.
	 * 
	 * @return
	 * @see
	 */
	private IExpr getNumber(final boolean negative) throws SyntaxError {
		IExpr temp = null;
		final Object[] result = getNumberString();
		String number = (String) result[0];
		final int numFormat = ((Integer) result[1]).intValue();
		try {
			if (negative) {
				number = '-' + number;
			}
			if (numFormat < 0) {
				temp = new NumStr(number);
				// temp = fFactory.createDouble(number);
			} else {
				temp = F.integer(number, numFormat);
				// temp = fFactory.createInteger(number, numFormat);
			}
		} catch (final Throwable e) {
			throwSyntaxError("Number format error: " + number, number.length());
		}
		getNextToken();
		return temp;
	}

	private int getIntegerNumber() throws SyntaxError {
		final Object[] result = getNumberString();
		final String number = (String) result[0];
		final int numFormat = ((Integer) result[1]).intValue();
		int intValue = 0;
		try {
			intValue = Integer.parseInt(number, numFormat);
		} catch (final NumberFormatException e) {
			throwSyntaxError("Number format error (not an int type): " + number, number.length());
		}
		getNextToken();
		return intValue;
	}

	/**
	 * Read the current identifier from the expression factories table
	 * 
	 * @return
	 * @see
	 */
	private IExpr getSymbol() throws SyntaxError {
		String identifier = getIdentifier();
		if (!fFactory.isValidIdentifier(identifier)) {
			throwSyntaxError("Invalid identifier: " + identifier + " detected.");
		}

		final IExpr symbol = convertSymbol(identifier);
		// final ISymbol symbol = F.$s(identifier);
		getNextToken();
		return symbol;
	}

	/**
	 * Get the string as IStringX.
	 * 
	 * @return
	 * @throws SyntaxError
	 */
	private IStringX getString() throws SyntaxError {
		final StringBuffer ident = getStringBuffer();

		getNextToken();

		return F.stringx(ident);
	}

	/**
	 * Get a list {...}
	 * 
	 */
	private IExpr getList() throws SyntaxError {
		final IAST function = F.List(); // fFactory.createFunction(fFactory.createSymbol(IConstantOperators.List));

		getNextToken();

		if (fToken == TT_LIST_CLOSE) {
			getNextToken();

			return function;
		}

		fRecursionDepth++;
		try {
			getArguments(function);
		} finally {
			fRecursionDepth--;
		}
		if (fToken == TT_LIST_CLOSE) {
			getNextToken();

			return function;
		}

		throwSyntaxError("'}' expected.");
		return null;
	}

	/**
	 * Get a function f[...][...]
	 * 
	 */
	IAST getFunction(final IExpr head) throws SyntaxError {
		final IAST function = F.ast(head);

		getNextToken();

		if (fRelaxedSyntax) {
			if (fToken == TT_PRECEDENCE_CLOSE) {
				getNextToken();
				if (fToken == TT_PRECEDENCE_OPEN) {
					return function;
				}
				if (fToken == TT_ARGUMENTS_OPEN) {
					return getFunctionArguments(function);
				}
				return function;
			}
		} else {
			if (fToken == TT_ARGUMENTS_CLOSE) {
				getNextToken();
				if (fToken == TT_ARGUMENTS_OPEN) {
					return getFunctionArguments(function);
				}
				return function;
			}
		}
		fRecursionDepth++;
		try {
			getArguments(function);
		} finally {
			fRecursionDepth--;
		}
		if (fRelaxedSyntax) {
			if (fToken == TT_PRECEDENCE_CLOSE) {
				getNextToken();
				if (fToken == TT_PRECEDENCE_OPEN) {
					return function;
				}
				if (fToken == TT_ARGUMENTS_OPEN) {
					return getFunctionArguments(function);
				}
				return function;
			}
		} else {
			if (fToken == TT_ARGUMENTS_CLOSE) {
				getNextToken();
				if (fToken == TT_ARGUMENTS_OPEN) {
					return getFunctionArguments(function);
				}
				return function;
			}
		}

		if (fRelaxedSyntax) {
			throwSyntaxError("')' expected.");
		} else {
			throwSyntaxError("']' expected.");
		}
		return null;

	}

	private IExpr convertN(final IAST function) {
		try {
			int precision = Validate.checkIntType(function.arg2());
			if (EvalEngine.isApfloat(precision)) {
				NVisitorExpr nve = new NVisitorExpr(precision);
				IExpr temp = function.arg1().accept(nve);
				if (temp.isPresent()) {
					function.set(1, temp);
				}
			}
		} catch (WrongArgumentType wat) {

		}
		return function;
	}

	/**
	 * Get a function f[...][...]
	 * 
	 */
	IAST getFunctionArguments(final IExpr head) throws SyntaxError {

		final IAST function = F.ast(head);
		fRecursionDepth++;
		try {
			getNextToken();

			if (fToken == TT_ARGUMENTS_CLOSE) {
				getNextToken();
				if (fToken == TT_ARGUMENTS_OPEN) {
					return getFunctionArguments(function);
				}
				return function;
			}

			getArguments(function);
		} finally {
			fRecursionDepth--;
		}
		if (fToken == TT_ARGUMENTS_CLOSE) {
			getNextToken();
			if (fToken == TT_ARGUMENTS_OPEN) {
				return getFunctionArguments(function);
			}
			return function;
		}

		throwSyntaxError("']' expected.");
		return null;

	}

	private IExpr getFactor() throws SyntaxError {
		IExpr temp;

		if (fToken == TT_IDENTIFIER) {
			final IExpr head = getSymbol();
			if (head.isSymbol()) {
				final ISymbol symbol = (ISymbol) head;
				temp = symbol;
				if (fToken == TT_BLANK) {
					// read '_'
					if (isWhitespace()) {
						temp = F.$p(symbol, null);
						getNextToken();
					} else {
						getNextToken();
						if (fToken == TT_IDENTIFIER) {
							final IExpr check = getSymbol();
							temp = F.$p(symbol, check);
							// temp = fFactory.createPattern(symbol, check);
						} else {
							temp = F.$p(symbol, null);
							// temp = fFactory.createPattern(symbol, null);
						}
					}
				} else if (fToken == TT_BLANK_BLANK) {
					// read '__'
					if (isWhitespace()) {
						temp = F.$ps(symbol, null);
						// temp = fFactory.createPattern2(symbol, null);
						getNextToken();
					} else {
						getNextToken();
						if (fToken == TT_IDENTIFIER) {
							final IExpr check = getSymbol();
							temp = F.$ps(symbol, check);
							// temp = fFactory.createPattern2(symbol, check);
						} else {
							temp = F.$ps(symbol, null);
							// temp = fFactory.createPattern2(symbol, null);
						}
					}
				} else if (fToken == TT_BLANK_BLANK_BLANK) {
					// read '___'
					if (isWhitespace()) {
						temp = F.$ps(symbol, null, false, true);
						// temp = fFactory.createPattern3(symbol, null);
						getNextToken();
					} else {
						getNextToken();
						if (fToken == TT_IDENTIFIER) {
							final IExpr check = getSymbol();
							temp = F.$ps(symbol, check, false, true);
							// temp = fFactory.createPattern3(symbol, check);
						} else {
							temp = F.$ps(symbol, null, false, true);
							// temp = fFactory.createPattern3(symbol, null);
						}
					}
				} else if (fToken == TT_BLANK_OPTIONAL) {
					// read '_.'
					if (isWhitespace()) {
						temp = F.$p(symbol, null, true);
						// temp = fFactory.createPattern(symbol, null, true);
						getNextToken();
					} else {
						getNextToken();
						if (fToken == TT_IDENTIFIER) {
							final IExpr check = getSymbol();
							temp = F.$p(symbol, check, true);
							// temp = fFactory.createPattern(symbol, check,
							// true);
						} else {
							temp = F.$p(symbol, null, true);
							// temp = fFactory.createPattern(symbol, null,
							// true);
						}
					}
				}
			} else {
				temp = head;
			}

			return parseArguments(temp);
		} else if (fToken == TT_BLANK) {
			if (isWhitespace()) {
				getNextToken();
				temp = F.$b();
				// temp = fFactory.createPattern(null, null);
			} else {
				getNextToken();
				if (fToken == TT_IDENTIFIER) {
					final IExpr check = getSymbol();
					temp = F.$b(check);
					// temp = fFactory.createPattern(null, check);
				} else {
					temp = F.$b();
					// temp = fFactory.createPattern(null, null);
				}
			}
			return parseArguments(temp);
		} else if (fToken == TT_BLANK_BLANK) {
			// read '__'
			if (isWhitespace()) {
				getNextToken();
				temp = F.$ps(null, null);
				// temp = fFactory.createPattern2(null, null);
			} else {
				getNextToken();
				if (fToken == TT_IDENTIFIER) {
					final IExpr check = getSymbol();
					temp = F.$ps(null, check);
					// temp = fFactory.createPattern2(null, check);
				} else {
					temp = F.$ps(null, null);
					// temp = fFactory.createPattern2(null, null);
				}
			}
			return parseArguments(temp);
		} else if (fToken == TT_BLANK_BLANK_BLANK) {
			// read '___'
			if (isWhitespace()) {
				getNextToken();
				temp = F.$ps(null, null, false, true);
				// temp = fFactory.createPattern3(null, null);
			} else {
				getNextToken();
				if (fToken == TT_IDENTIFIER) {
					final IExpr check = getSymbol();
					temp = F.$ps(null, check, false, true);
					// temp = fFactory.createPattern3(null, check);
				} else {
					temp = F.$ps(null, null, false, true);
					// temp = fFactory.createPattern3(null, null);
				}
			}
			return parseArguments(temp);
		} else if (fToken == TT_BLANK_OPTIONAL) {
			// read '_.'
			if (isWhitespace()) {
				getNextToken();
				temp = F.$b(null, true);
				// temp = fFactory.createPattern(null, null, true);
			} else {
				getNextToken();
				if (fToken == TT_IDENTIFIER) {
					final IExpr check = getSymbol();
					temp = F.$b(check, true);
					// temp = fFactory.createPattern(null, check, true);
				} else {
					temp = F.$b(null, true);
					// temp = fFactory.createPattern(null, null, true);
				}
			}
			return parseArguments(temp);
		} else if (fToken == TT_DIGIT) {
			return getNumber(false);
		} else if (fToken == TT_PRECEDENCE_OPEN) {
			fRecursionDepth++;
			try {
				getNextToken();

				temp = parseOperators(parsePrimary(), 0);

				if (fToken != TT_PRECEDENCE_CLOSE) {
					throwSyntaxError("\')\' expected.");
				}
			} finally {
				fRecursionDepth--;
			}
			getNextToken();
			if (fToken == TT_PRECEDENCE_OPEN) {
				return getTimes(temp);
			}
			return temp;

		} else if (fToken == TT_LIST_OPEN) {
			return getList();
		} else if (fToken == TT_STRING) {
			return getString();
		} else if (fToken == TT_PERCENT) {

			final IAST out = F.ast(F.Out);

			int countPercent = 1;
			getNextToken();
			if (fToken == TT_DIGIT) {
				countPercent = getIntegerNumber();
				out.add(F.integer(countPercent));
				return out;
			}

			while (fToken == TT_PERCENT) {
				countPercent++;
				getNextToken();
			}

			out.add(F.integer(-countPercent));
			return parseArguments(out);
		} else if (fToken == TT_SLOT) {

			getNextToken();
			if (fToken == TT_DIGIT) {
				final IAST slot = F.ast(F.Slot);
				slot.add(getNumber(false));
				return parseArguments(slot);
			} else {
				return parseArguments(F.Slot1);
			}

		} else if (fToken == TT_SLOTSEQUENCE) {

			getNextToken();
			final IAST slotSequencce = F.ast(F.SlotSequence);
			if (fToken == TT_DIGIT) {
				slotSequencce.add(getNumber(false));
			} else {
				slotSequencce.add(F.C1);
			}
			return parseArguments(slotSequencce);
			// final FunctionNode slotSequencce =
			// fFactory.createFunction(fFactory.createSymbol(IConstantOperators.SlotSequence));
			// if (fToken == TT_DIGIT) {
			// slotSequencce.add(getNumber(false));
			// } else {
			// slotSequencce.add(fFactory.createInteger(1));
			// }
			// return parseArguments(slotSequencce);
		}
		switch (fToken) {

		case TT_PRECEDENCE_CLOSE:
			throwSyntaxError("Too much closing ) in factor.");
			break;
		case TT_LIST_CLOSE:
			throwSyntaxError("Too much closing } in factor.");
			break;
		case TT_ARGUMENTS_CLOSE:
			throwSyntaxError("Too much closing ] in factor.");
			break;
		}

		throwSyntaxError("Error in factor at character: '" + fCurrentChar + "' (" + fToken + ")");
		return null;
	}

	private IExpr getTimes(IExpr temp) throws SyntaxError {
		// FunctionNode func = fFactory.createAST(new SymbolNode("Times"));
		IAST func = F.Times();
		func.add(temp);
		do {
			getNextToken();

			temp = parseOperators(parsePrimary(), 0);
			func.add(temp);
			if (fToken != TT_PRECEDENCE_CLOSE) {
				throwSyntaxError("\')\' expected.");
			}
			getNextToken();
		} while (fToken == TT_PRECEDENCE_OPEN);
		return func;
	}

	/**
	 * Get a <i>part [[..]]</i> of an expression <code>{a,b,c}[[2]]</code>
	 * &rarr; <code>b</code>
	 * 
	 */
	private IExpr getPart() throws SyntaxError {
		IExpr temp = getFactor();

		if (fToken != TT_PARTOPEN) {
			return temp;
		}

		IAST function = null;
		do {
			if (function == null) {
				function = F.Part(temp);
				// function =
				// fFactory.createFunction(fFactory.createSymbol(IConstantOperators.Part),
				// temp);
			} else {
				function = F.Part(function);
				// function =
				// fFactory.createFunction(fFactory.createSymbol(IConstantOperators.Part),
				// function);
			}

			fRecursionDepth++;
			try {
				do {
					getNextToken();

					if (fToken == TT_ARGUMENTS_CLOSE) {
						if (fInputString.length() > fCurrentPosition && fInputString.charAt(fCurrentPosition) == ']') {
							throwSyntaxError("Statement (i.e. index) expected in [[ ]].");
						}
					}

					function.add(parseOperators(parsePrimary(), 0));
				} while (fToken == TT_COMMA);

				if (fToken == TT_ARGUMENTS_CLOSE) {
					// scanner-step begin: (instead of getNextToken() call):
					if (fInputString.length() > fCurrentPosition) {
						if (fInputString.charAt(fCurrentPosition) == ']') {
							fCurrentPosition++;
							fToken = TT_PARTCLOSE;
						}
					}
					// scanner-step end
				}
				if (fToken != TT_PARTCLOSE) {
					throwSyntaxError("']]' expected.");
				}
				// }
			} finally {
				fRecursionDepth--;
			}
			getNextToken();
		} while (fToken == TT_PARTOPEN);

		return parseArguments(function);

	}

	private IExpr convert(IAST ast) {
		IExpr head = ast.head();
		if (ast.isAST(F.N, 3)) {
			return convertN(ast);
		} else if (ast.isAST(F.Sqrt, 2)) {
			// rewrite from input: Sqrt(x) => Power(x, 1/2)
			return F.Power(ast.arg1(), F.C1D2);
		} else if (ast.isAST(F.Exp, 2)) {
			// rewrite from input: Exp(x) => E^x
			return F.Power(F.E, ast.arg1());
		} else if (ast.isPower() && ast.arg1().isPower() && ast.arg2().isMinusOne()) {
			IAST arg1 = (IAST) ast.arg1();
			if (arg1.arg2().isNumber()) {
				// Division operator
				// rewrite from input: Power(Power(x, <number>),-1) => Power(x,
				// - <number>)
				return F.Power(arg1.arg1(), ((INumber) arg1.arg2()).negate());
			}
		} else if (ast.isASTSizeGE(F.GreaterEqual, 3)) {
			ISymbol compareHead = F.Greater;
			return rewriteLessGreaterAST(ast, compareHead);
		} else if (ast.isASTSizeGE(F.Greater, 3)) {
			ISymbol compareHead = F.GreaterEqual;
			return rewriteLessGreaterAST(ast, compareHead);
		} else if (ast.isASTSizeGE(F.LessEqual, 3)) {
			ISymbol compareHead = F.Less;
			return rewriteLessGreaterAST(ast, compareHead);
		} else if (ast.isASTSizeGE(F.Less, 3)) {
			ISymbol compareHead = F.LessEqual;
			return rewriteLessGreaterAST(ast, compareHead);
		} else if (head.equals(F.PatternHead)) {
			final IExpr expr = Pattern.CONST.evaluate(ast, fEngine);
			if (expr.isPresent()) {
				return expr;
			}
		} else if (head.equals(F.BlankHead)) {
			final IExpr expr = Blank.CONST.evaluate(ast, fEngine);
			if (expr.isPresent()) {
				return expr;
			}
		} else if (head.equals(F.Complex)) {
			final IExpr expr = Complex.CONST.evaluate(ast, fEngine);
			if (expr.isPresent()) {
				return expr;
			}
		} else if (head.equals(F.Rational)) {
			final IExpr expr = Rational.CONST.evaluate(ast, fEngine);
			if (expr.isPresent()) {
				return expr;
			}
		}
		return ast;
	}

	/**
	 * Convert less or greater relations on input. Example: convert expressions
	 * like <code>a<b<=c</code> to <code>Less[a,b]&&LessEqual[b,c]</code>.
	 * 
	 * @param ast
	 * @param compareHead
	 * @return
	 */
	private IExpr rewriteLessGreaterAST(final IAST ast, ISymbol compareHead) {
		IExpr temp;
		boolean evaled = false;
		IAST andAST = F.ast(F.And);
		for (int i = 1; i < ast.size(); i++) {
			temp = ast.get(i);
			if (temp.isASTSizeGE(compareHead, 3)) {
				IAST lt = (IAST) temp;
				andAST.add(lt);
				ast.set(i, lt.get(lt.size() - 1));
				evaled = true;
			}
		}
		if (evaled) {
			andAST.add(ast);
			return andAST;
		} else {
			return ast;
		}
	}
}