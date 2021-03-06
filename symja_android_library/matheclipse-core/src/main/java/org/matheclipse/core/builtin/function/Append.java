package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * 
 * <p>
 * See the online Symja function reference: <a href="https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/Append">Append</a>
 * </p>
 *
 */
public class Append extends AbstractCoreFunctionEvaluator {

	public Append() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 3);
		IExpr arg1 = engine.evaluate(ast.arg1());
		IAST arg1AST = Validate.checkASTType(arg1);
		IExpr arg2 = engine.evaluate(ast.arg2());
		return arg1AST.appendClone(arg2);
	}

}