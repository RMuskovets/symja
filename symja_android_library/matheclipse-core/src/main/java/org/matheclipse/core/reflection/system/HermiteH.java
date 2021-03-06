package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.polynomials.PolynomialsUtils;

public class HermiteH extends AbstractFunctionEvaluator {

	public HermiteH() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 3);
		int degree = Validate.checkIntType(ast.arg1());

		return PolynomialsUtils.createHermitePolynomial(degree, ast.arg2());
	}

}
