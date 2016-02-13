package org.matheclipse.core.eval.interfaces;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 *
 */
public abstract class AbstractSymbolEvaluator implements ISymbolEvaluator {

	public IExpr evaluate(final ISymbol symbol) {
		return F.NIL;
	}

	public IExpr numericEval(final ISymbol symbol) {
		return evaluate(symbol);
	}

	public IExpr apfloatEval(ISymbol symbol, EvalEngine engine) {
		return numericEval(symbol);
	}

	public void setUp(final ISymbol symbol) {
		// do nothing
	}
}
