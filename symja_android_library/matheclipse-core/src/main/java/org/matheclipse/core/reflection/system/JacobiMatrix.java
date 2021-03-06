package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Create a Jacobian matrix.
 * 
 * See: <a href="http://en.wikipedia.org/wiki/Jacobian">Jacobian</a>
 * 
 */
public class JacobiMatrix extends AbstractFunctionEvaluator {
	public JacobiMatrix() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 3);
		
		if (ast.arg1().isVector() >= 0) {
			IAST variables = F.NIL;
			if (ast.arg2().isSymbol()) {
				variables = F.List();
			} else if (ast.arg2().isVector() >= 0) {
				variables = (IAST) ast.arg2();
			}
			if (variables.isPresent()) {
				IAST vector = (IAST) ast.arg1();
				IAST jacobiMatrix = F.List();
				IAST jacobiRow = null;
				for (int i = 1; i < vector.size(); i++) {
					jacobiRow = F.List();
					for (int j = 1; j < variables.size(); j++) {
						jacobiRow.add(F.D(vector.get(i), variables.get(j)));
					}
					jacobiMatrix.add(jacobiRow);
				}
				return jacobiMatrix;
			}
		}

		return F.NIL;
	}

}
