package org.matheclipse.core.eval;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.reflection.system.Times;

/**
 * 
 */
public class TimesOp {

	/**
	 * Evaluate <code>Times(a1, a2,...)</code>.
	 * 
	 * @param a0
	 * @param a1
	 * @return
	 */
	public static IExpr times(IAST timesAST) {
		IAST temp = EvalEngine.get().evalFlatOrderlessAttributesRecursive(timesAST);
		IExpr expr = Times.CONST.evaluate(temp);
		if (expr == null) {
			return timesAST.getOneIdentity(F.C0);
		}
		return expr;
	}

	/**
	 * Evaluate <code>a0 * a2</code>.
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static IExpr times(IExpr a1, IExpr a2) {
		IAST times = F.Times(a1, a2);
		IExpr expr = Times.CONST.evaluate(times);
		if (expr == null) {
			return times;
		}
		return expr;
	}
}