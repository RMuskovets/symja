package org.matheclipse.core.generic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.generic.interfaces.IArrayFunction;
import org.matheclipse.core.interfaces.IExpr;

public class UnaryArrayFunction implements IArrayFunction {
	final EvalEngine fEngine;

	final IExpr fValue;

	public UnaryArrayFunction(final EvalEngine engine, final IExpr value) {
		fEngine = engine;
		fValue = value;
	}

	public IExpr evaluate(final IExpr[] index) {
		return fEngine.evaluate(fValue);
	}
}
