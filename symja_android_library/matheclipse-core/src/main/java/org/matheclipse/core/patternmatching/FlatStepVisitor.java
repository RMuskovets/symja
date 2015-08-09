package org.matheclipse.core.patternmatching;

import org.matheclipse.combinatoric.AbstractListStepVisitor;
import org.matheclipse.combinatoric.NumberPartitionsIterator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.PatternMatcher.StackMatcher;

/**
 * This visitor is used in an <code>NumberPartitionsIterator</code> to match
 * flat expressions in pattern matching.
 * 
 * @see PatternMatcher
 * @see NumberPartitionsIterator
 */
public class FlatStepVisitor extends AbstractListStepVisitor<IExpr> {

	protected ISymbol fSymbol;
	protected StackMatcher stackMatcher;
	protected PatternMap fPatternMap;
	protected IExpr[] patternValues;
	protected IAST fLhsPatternAST;
	protected boolean fOneIdentity;

	public FlatStepVisitor(final ISymbol sym, IAST lhsPatternAST, IAST lhsEvalAST, StackMatcher stackMatcher, PatternMap patternMap) {
		super(lhsEvalAST, 1, lhsEvalAST.size());
		this.fSymbol = sym;
		this.stackMatcher = stackMatcher;
		this.fPatternMap = patternMap;
		// copy pattern values to local variable
		this.patternValues = patternMap.copyPattern();
		this.fLhsPatternAST = lhsPatternAST;
		this.fOneIdentity = (sym.getAttributes() & ISymbol.ONEIDENTITY) == ISymbol.ONEIDENTITY;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.matheclipse.combinatoric.IStepVisitor#visit(int[][])
	 */
	public boolean visit(int[][] result) {
		if (matchSinglePartition(result, stackMatcher)) {
			return false; // stop iterating and calling this visitor
		}
		// reset pattern values:
		fPatternMap.resetPattern(patternValues);
		return true;
	}

	/**
	 * Match a single partition combination
	 * 
	 * @param result
	 * @param stackMatcher
	 * @return
	 */
	protected boolean matchSinglePartition(int[][] result, StackMatcher stackMatcher) {
		IAST partitionElement;
		// if (Config.SHOW_STACKTRACE == true) {
		// }
		int lastStackSize = stackMatcher.size();
		boolean matched = true;
		try {

			for (int j = 0; j < result.length; j++) {
				final int n = result[j].length;
				if (n == 1 && fOneIdentity) {
					// OneIdentity here
					if (!stackMatcher.push(fLhsPatternAST.get(j + 1), (IExpr) array[result[j][0]])) {
						matched = false;
						return false;
					}
				} else {
					partitionElement = F.ast(fSymbol, n, false);
					for (int i = 0; i < n; i++) {
						partitionElement.add((IExpr) array[result[j][i]]);
					}
					if (!stackMatcher.push(fLhsPatternAST.get(j + 1), partitionElement)) {
						matched = false;
						return false;
					}
				}
			}

			if (!stackMatcher.matchRest()) {
				matched = false;
				return false;
			}
			return true;
		} finally {
			if (!matched) {
				stackMatcher.removeFrom(lastStackSize);
			}
		}
	}
}
