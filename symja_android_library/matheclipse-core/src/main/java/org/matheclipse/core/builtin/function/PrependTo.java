package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import com.google.common.base.Function;

public class PrependTo extends AbstractCoreFunctionEvaluator {

	class PrependToFunction implements Function<IExpr, IExpr> {
		private final IExpr value;

		public PrependToFunction(final IExpr value) {
			this.value = value;
		}

		@Override
		public IExpr apply(final IExpr symbolValue) {
			if (!symbolValue.isAST()) {
				return null;
			}
			return ((IAST) symbolValue).clone().prepend(value);
		}

	}

	public PrependTo() {
		super();
	}

	@Override
	public IExpr evaluate(final IAST ast) {
		Validate.checkSize(ast, 3);
		ISymbol sym = Validate.checkSymbolType(ast, 1);
		IExpr arg2 = F.eval(ast.get(2));
		Function<IExpr, IExpr> function = new PrependToFunction(arg2);
		IExpr[] results = sym.reassignSymbolValue(function);
		if (results != null) {
			return results[1];
		}

		return null;
	}

	@Override
	public void setUp(final ISymbol symbol) {
		symbol.setAttributes(ISymbol.HOLDFIRST);
	}
}