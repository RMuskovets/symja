package org.matheclipse.core.reflection.system;

import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Power;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.IIndexFunction;
import org.matheclipse.core.eval.util.IndexTableGenerator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Vandermonde matrix, defined by A<sub>i,j</sub> = vector[i]^(j-1). See
 * <a href="http://en.wikipedia.org/wiki/Vandermonde_matrix">Vandermonde
 * matrix</a>
 * 
 */
public class VandermondeMatrix extends AbstractFunctionEvaluator {
	public VandermondeMatrix() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);
		if (ast.arg1().isList()) {
			final IAST lst = (IAST) ast.arg1();
			final int len0 = lst.size() - 1;

			final IAST resultList = List();
			final int[] indexArray = new int[2];
			indexArray[0] = len0;
			indexArray[1] = len0;

			final IIndexFunction<IExpr> function = new IIndexFunction<IExpr>() {
				@Override
				public IExpr evaluate(int[] index) {
					return Power(lst.get(index[0] + 1), F.integer(index[1]));
				}
			};
			final IndexTableGenerator generator = new IndexTableGenerator(indexArray, resultList, function);
			final IAST matrix = (IAST) generator.table();
			matrix.addEvalFlags(IAST.IS_MATRIX);
			return matrix;
		}

		return F.NIL;
	}
}
