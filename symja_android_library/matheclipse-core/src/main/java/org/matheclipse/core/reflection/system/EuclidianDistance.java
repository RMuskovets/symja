package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * EuclidianDistance of two vectors
 */
public class EuclidianDistance extends AbstractEvaluator {

	public EuclidianDistance() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 3);
		IExpr arg1 = ast.arg1();
		IExpr arg2 = ast.arg2();

		int dim1 = arg1.isVector();
		if (dim1 > (-1)) {
			int dim2 = arg2.isVector();
			if (dim1 == dim2) {
				if (dim1 == 0) {
					return F.C0;
				}
				IAST a1 = ((IAST) arg1);
				IAST a2 = ((IAST) arg2);
				IAST plusAST = F.Plus();
				for (int i = 1; i < a1.size(); i++) {
					plusAST.add(F.Sqr(F.Abs(F.Subtract(a1.get(i), a2.get(i)))));
				}
				return F.Sqrt(plusAST);
			}
		}
		return F.NIL;
	}

	@Override
	public void setUp(ISymbol symbol) {

	}

}
