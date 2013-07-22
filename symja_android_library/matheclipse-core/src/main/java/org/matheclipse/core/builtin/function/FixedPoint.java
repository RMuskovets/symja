package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.IterationLimitExceeded;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.ICoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.parser.client.SyntaxError;

public class FixedPoint implements ICoreFunctionEvaluator {

	public FixedPoint() {
	}

	@Override
	public IExpr evaluate(final IAST ast) {
		Validate.checkRange(ast, 3, 4);

		try {

			final EvalEngine engine = EvalEngine.get();
			final int iterationLimit = engine.getIterationLimit();
			int iterationCounter = 1;

			IExpr f = ast.get(1);
			IExpr current = ast.get(2);
			int steps = Integer.MAX_VALUE;
			if (ast.size() == 4) {
				steps = Validate.checkIntType(ast, 3);
			}
			IExpr last;
			do {
				last = current;
				current = engine.evaluate(F.Apply(f, F.List(current)));
				if (iterationLimit >= 0 && iterationLimit <= ++iterationCounter) {
					IterationLimitExceeded.throwIt(iterationCounter, ast);
				}
			} while ((!current.isSame(last)) && (--steps > 0));
			return current;

		} finally {
			EvalEngine.get().setNumericMode(false);
		}

	}

	@Override
	public IExpr numericEval(IAST ast) {
		return evaluate(ast);
	}
	
	@Override
	public void setUp(final ISymbol symbol) throws SyntaxError {
		symbol.setAttributes(ISymbol.HOLDALL);
	}

	
}