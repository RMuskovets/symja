package org.matheclipse.core.builtin.function;

import java.util.function.Predicate;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Returns <code>True</code>, if the given expression is bound to a value.
 * 
 */
public class ValueQ extends AbstractCoreFunctionEvaluator implements Predicate<IExpr> {
	/**
	 * Constructor for the unary predicate
	 */
	public final static ValueQ CONST = new ValueQ();

	public ValueQ() {
	}

	/**
	 * Returns <code>True</code> if the 1st argument is an atomic object;
	 * <code>False</code> otherwise
	 */
	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);

		return F.bool(test(ast.arg1()));
	}

	@Override
	public boolean test(final IExpr expr) {
		return expr.isValue();
	}

}
