package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 */
public class First extends AbstractCoreFunctionEvaluator {

	public First() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);
		IExpr arg1 = engine.evaluate(ast.arg1());
		if (arg1.isAST()) {
			final IAST sublist = (IAST) arg1;

			if (sublist.size() > 1) {
				return sublist.arg1();
			}
		}
		return F.NIL;
	}
}
