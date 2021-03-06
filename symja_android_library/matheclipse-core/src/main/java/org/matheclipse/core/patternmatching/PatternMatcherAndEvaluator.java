package org.matheclipse.core.patternmatching;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.matheclipse.core.builtin.function.Condition;
import org.matheclipse.core.builtin.function.Module;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.ConditionException;
import org.matheclipse.core.eval.exception.ReturnException;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.interfaces.ISymbol.RuleType;

public class PatternMatcherAndEvaluator extends PatternMatcher implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2241135467123931061L;

	private IExpr fRightHandSide;
	private ISymbol.RuleType fSetSymbol;

	/**
	 * Public constructor for serialization.
	 */
	public PatternMatcherAndEvaluator() {

	}

	/**
	 * Define a pattern-matching rule.
	 * 
	 * @param leftHandSide
	 *            could contain pattern expressions for "pattern-matching"
	 * @param rightHandSide
	 *            the result which should be evaluated if the "pattern-matching"
	 *            succeeds
	 */
	public PatternMatcherAndEvaluator(final IExpr leftHandSide, final IExpr rightHandSide) {
		this(ISymbol.RuleType.SET_DELAYED, leftHandSide, rightHandSide);
	}

	/**
	 * ine a pattern-matching rule.
	 * 
	 * @param setSymbol
	 *            the symbol which defines this pattern-matching rule (i.e. Set,
	 *            SetDelayed,...)
	 * @param leftHandSide
	 *            could contain pattern expressions for "pattern-matching"
	 * @param rightHandSide
	 *            the result which should be evaluated if the "pattern-matching"
	 *            succeeds
	 */
	public PatternMatcherAndEvaluator(final ISymbol.RuleType setSymbol, final IExpr leftHandSide,
			final IExpr rightHandSide) {
		super(leftHandSide);
		fSetSymbol = setSymbol;
		fRightHandSide = rightHandSide;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PatternMatcherAndEvaluator v = (PatternMatcherAndEvaluator) super.clone();
		v.fRightHandSide = fRightHandSide;
		v.fSetSymbol = fSetSymbol;
		return v;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof PatternMatcherAndEvaluator) {
			PatternMatcherAndEvaluator pm = (PatternMatcherAndEvaluator) obj;
			// don't compare fSetSymbol here
			if (super.equals(pm)) {
				// return equivalentRHS(fRightHandSide, pm.fRightHandSide);
				if (fRightHandSide.isCondition()) {
					if (pm.fRightHandSide.isCondition()) {
						return equivalentRHS(fRightHandSide.getAt(2), pm.fRightHandSide.getAt(2), fPatternMap,
								pm.fPatternMap);
					}
					return false;
				} else if (pm.fRightHandSide.isCondition()) {
					return false;
				} else if (fRightHandSide.isModule()) {
					if (pm.fRightHandSide.isModule()) {
						return equivalentRHS(fRightHandSide.getAt(2), pm.fRightHandSide.getAt(2), fPatternMap,
								pm.fPatternMap);
					}
					return false;
				} else if (pm.fRightHandSide.isModule()) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the two expressions are equivalent. (i.e. <code>f[x_,y_]</code>
	 * is equivalent to <code>f[a_,b_]</code> )
	 * 
	 * @param patternExpr1
	 * @param patternExpr2
	 * @param pm1
	 * @param pm2
	 * @return
	 */
	private static boolean equivalentRHS(final IExpr patternExpr1, final IExpr patternExpr2, final PatternMap pm1,
			final PatternMap pm2) {
		if (patternExpr1.isCondition()) {
			if (patternExpr2.isCondition()) {
				return equivalentRHS(patternExpr1.getAt(2), patternExpr2.getAt(2), pm1, pm2);
			}
			return false;
		} else if (patternExpr2.isCondition()) {
			return false;
		} else if (patternExpr1.isModule()) {
			if (patternExpr2.isModule()) {
				return equivalentRHS(patternExpr1.getAt(2), patternExpr2.getAt(2), pm1, pm2);
			}
			return false;
		} else if (patternExpr2.isModule()) {
			return false;
		}
		// TODO refine equivalent for RHS symbols which are patterns on the LHS.
		return equivalent(patternExpr1, patternExpr2, pm1, pm2);
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 53;
	}

	/**
	 * Check if the condition for the right-hand-sides
	 * <code>Module[] or Condition[]</code> expressions evaluates to
	 * <code>true</code>.
	 * 
	 * @return <code>true</code> if the right-hand-sides condition is fulfilled.
	 */
	@Override
	public boolean checkRHSCondition(EvalEngine engine) {
		if (!(fRightHandSide.isModule() || fRightHandSide.isCondition())) {
			return true;
		}
		if (!fPatternMap.isAllPatternsAssigned()) {
			return true;
		}
		IExpr substConditon = fPatternMap.substituteSymbols(fRightHandSide);
		if (substConditon.isCondition()) {
			return Condition.checkCondition(substConditon.getAt(1), substConditon.getAt(2), engine);
		} else if (substConditon.isModule()) {
			return Module.checkModuleCondition(substConditon.getAt(1), substConditon.getAt(2), engine);
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public IExpr eval(final IExpr leftHandSide) {
		// if(fRightHandSide.isAST("Condition")) {
		// System.out.println("2:"+fRightHandSide);
		// }
		if (isRuleWithoutPatterns()) {
			// no patterns found match equally:
			if (fLhsPatternExpr.equals(leftHandSide)) {
				IExpr result = fRightHandSide;
				try {
					return F.eval(result);
				} catch (final ConditionException e) {
					logConditionFalse(leftHandSide, fLhsPatternExpr, fRightHandSide);
					return F.NIL;
				} catch (final ReturnException e) {
					return e.getValue();
				}
			}
			if (!(fLhsPatternExpr.isOrderlessAST() && leftHandSide.isOrderlessAST())) {
				if (!(fLhsPatternExpr.isFlatAST() && leftHandSide.isFlatAST())) {
					return F.NIL;
				}
				// TODO implement equals matching for special cases, if the AST
				// is
				// Orderless or Flat
			}
		}

		fPatternMap.initPattern();
		if (matchExpr(fLhsPatternExpr, leftHandSide)) {
			IExpr result = fPatternMap.substituteSymbols(fRightHandSide);
			try {
				result = F.eval(result);
			} catch (final ConditionException e) {
				logConditionFalse(leftHandSide, fLhsPatternExpr, fRightHandSide);
				return F.NIL;
			} catch (final ReturnException e) {
				result = e.getValue();
			}
			return result;
		}

		if (fLhsPatternExpr.isAST() && leftHandSide.isAST()) {
			fPatternMap.initPattern();
			return evalAST((IAST) fLhsPatternExpr, (IAST) leftHandSide, fRightHandSide, new StackMatcher());
		}
		return F.NIL;
	}

	@Override
	public IExpr getRHS() {
		return IExpr.ofNullable(fRightHandSide);
	}

	public IAST getAsAST() {
		ISymbol setSymbol = getSetSymbol();
		IExpr condition = getCondition();
		if (condition != null) {
			return F.binaryAST2(setSymbol, getLHS(), F.Condition(getRHS(), condition));
			// ast = F.ast(setSymbol);
			// ast.add(getLHS());
			// ast.add(F.Condition(getRHS(), condition));
		}
		return F.binaryAST2(setSymbol, getLHS(), getRHS());
		// ast = F.ast(setSymbol);
		// ast.add(getLHS());
		// ast.add(getRHS());
	}

	/**
	 * Return <code>Set</code> or <code>SetDelayed</code> symbol.
	 * 
	 * @return <code>null</code> if no symbol was defined
	 */
	public ISymbol getSetSymbol() {
		if (fSetSymbol == ISymbol.RuleType.SET_DELAYED) {
			return F.SetDelayed;
		}
		if (fSetSymbol == ISymbol.RuleType.SET) {
			return F.Set;
		}
		if (fSetSymbol == ISymbol.RuleType.UPSET_DELAYED) {
			return F.UpSetDelayed;
		}
		if (fSetSymbol == ISymbol.RuleType.UPSET) {
			return F.UpSet;
		}
		return null;
	}

	@Override
	public int equivalent(final IPatternMatcher obj) {
		// don't compare fSetSymbol here
		int comp = super.equivalent(obj);
		if (comp == 0) {
			if (obj instanceof PatternMatcherAndEvaluator) {
				PatternMatcherAndEvaluator pm = (PatternMatcherAndEvaluator) obj;
				if (fRightHandSide != null) {
					if (fRightHandSide.isCondition()) {
						if (pm.fRightHandSide.isCondition()) {
							if (equivalentRHS(fRightHandSide.getAt(2), pm.fRightHandSide.getAt(2), fPatternMap,
									pm.fPatternMap)) {
								return 0;
							}
							return 1;
						}
						return -1;
					} else if (pm.fRightHandSide.isCondition()) {
						return 1;
					} else if (fRightHandSide.isModule()) {
						if (pm.fRightHandSide.isModule()) {
							if (equivalentRHS(fRightHandSide.getAt(2), pm.fRightHandSide.getAt(2), fPatternMap,
									pm.fPatternMap)) {
								return 0;
							}
							return 1;
						}
						return -1;
					} else if (pm.fRightHandSide.isModule()) {
						return 1;
					}
				}
				return 0;
			}
		}
		return comp;
	}

	@Override
	public String toString() {
		return getAsAST().toString();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		short ordinal = (short) fSetSymbol.ordinal();
		if (fPatternCondition == null) {
			ordinal |= 0x8000;
		}
		objectOutput.writeShort(ordinal);
		objectOutput.writeObject(fLhsPatternExpr);
		objectOutput.writeObject(fRightHandSide);
		if (fPatternCondition != null) {
			objectOutput.writeObject(fPatternCondition);
		}
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
		short ordinal = objectInput.readShort();
		fSetSymbol = RuleType.values()[ordinal & 0x7FFF];
		fLhsPatternExpr = (IExpr) objectInput.readObject();
		fRightHandSide = (IExpr) objectInput.readObject();
		if ((ordinal & 0x8000) == 0x0000) {
			fPatternCondition = (IExpr) objectInput.readObject();
		}
		this.fPatternMap = new PatternMap();
		if (fLhsPatternExpr != null) {
			fPriority = fPatternMap.determinePatterns(fLhsPatternExpr);
		}
	}
}