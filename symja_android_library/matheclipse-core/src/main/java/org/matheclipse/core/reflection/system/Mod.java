package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * 
 * See: <a href="http://en.wikipedia.org/wiki/Modular_arithmetic">Wikipedia -
 * Modular arithmetic</a>
 */
public class Mod extends AbstractArg2 {
	public Mod() {
	}

	@Override
	public IExpr e2IntArg(final IInteger i0, final IInteger i1) {
		if (i1.isNegative()) {
			return i0.negate().mod(i1.negate()).negate();
		}
		return i0.mod(i1);
	}

	@Override
	public void setUp(final ISymbol symbol) {
		symbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
		super.setUp(symbol);
	}

}
