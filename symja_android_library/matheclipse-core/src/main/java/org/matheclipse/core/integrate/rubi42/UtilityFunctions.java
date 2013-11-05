package org.matheclipse.core.integrate.rubi42;

import static org.matheclipse.core.expression.F.$p;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.If;
import static org.matheclipse.core.expression.F.Integrate;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.UnsameQ;
import static org.matheclipse.core.expression.F.ast;
import static org.matheclipse.core.expression.F.binary;
import static org.matheclipse.core.expression.F.quaternary;
import static org.matheclipse.core.expression.F.ternary;
import static org.matheclipse.core.expression.F.unary;
import static org.matheclipse.core.integrate.rubi.UtilityFunctionCtors.Dist;
import static org.matheclipse.core.integrate.rubi.UtilityFunctionCtors.ZeroQ;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.Symbol;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * UtilityFunctions from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi - rule-based integrator</a>.
 * 
 * TODO a lot of functions are only placeholders at the moment.
 * 
 */
public class UtilityFunctions {
	public final static String PACKAGE_NAME = "org.matheclipse.core.integrate.rubi";

	public final static String CLASS_NAME = "UtilityFunctions";

	public final static String INTEGRATE_PREFIX = "Integrate::";

	public static IAST F(final IExpr a) {
		// TODO fix this
		return unary($s(INTEGRATE_PREFIX + "F"), a);
	}

	public static IAST G(final IExpr a) {
		// TODO fix this
		return unary($s(INTEGRATE_PREFIX + "G"), a);
	}

	public static IAST Rt(final IExpr a0) {
		// TODO fix this
		return unary($s(INTEGRATE_PREFIX + "Rt"), a0);
	}

	/**
	 * Convert to Integrate[]
	 * 
	 * @param a0
	 * @param a1
	 * @return
	 */
	public static IAST Int(final IExpr a0, final IExpr a1) {
		// Integrate.setAttributes(ISymbol.CONSOLE_OUTPUT);
		return binary(Integrate, a0, a1);
	}

	public static ISymbol $sDBG(final String symbolName) {
		return $s(symbolName);
		// ISymbol sym = $s(symbolName, false);
		// sym.setAttributes(sym.getAttributes() | ISymbol.CONSOLE_OUTPUT);
		// return sym;
	}

	//
	// public static IAST FunctionOfQ(final IExpr a0, final IExpr a1, final IExpr a2) {
	// // call the constructor with 4 arguments
	// return UtilityFunctionCtors.FunctionOfQ(a0, a1, a2, F.False);
	// }

	// public static IAST FunctionOfQ(final IExpr a0, final IExpr a1, final
	// IExpr a2, final IExpr a3) {
	// // call the constructor with 4 arguments
	// return UtilityFunctionCtors.FunctionOfQ(a0, a1, a2, a3);
	// }

	public static IAST IntegrateMonomialSum(final IExpr a0, final IExpr a1) {
		// TODO
		return binary($sDBG(INTEGRATE_PREFIX + "IntegrateMonomialSum"), a0, a1);
	}

	public static IAST SplitFreeIntegrate(final IExpr a0, final IExpr a1) {
		// TODO
		return binary($sDBG(INTEGRATE_PREFIX + "SplitFreeIntegrate"), a0, a1);
	}

	public static IAST TryTanSubst(final IExpr a0, final IExpr a1) {
		// TODO
		return binary($sDBG(INTEGRATE_PREFIX + "TryTanSubst"), a0, a1);
	}

	public static IAST LinearSinCosQ(final IExpr a0, final IExpr a1) {
		// TODO
		return binary($sDBG(INTEGRATE_PREFIX + "LinearSinCosQ"), a0, a1);
	}

	public static IAST LinearSinhCoshQ(final IExpr a0, final IExpr a1) {
		// TODO
		return binary($sDBG(INTEGRATE_PREFIX + "LinearSinhCoshQ"), a0, a1);
	}

	public static IExpr isList(IAST ast) {
		if (ast.size() > 1) {
			return F.bool(ast.get(1).isList());
		}
		return F.False;
	}

	static ISymbol ListQ = F.method(INTEGRATE_PREFIX + "ListQ", PACKAGE_NAME, CLASS_NAME, "isList");

	public static IAST ListQ(final IExpr a) {
		return unary(ListQ, a);
	}

	static ISymbol IntIntegerQ = new Symbol(INTEGRATE_PREFIX + "IntIntegerQ", new IntIntegerQ());

	public static IAST IntIntegerQ(final IExpr a) {
		return unary(IntIntegerQ, a);
	}

	static ISymbol IntPolynomialQ = new Symbol(INTEGRATE_PREFIX + "IntPolynomialQ", new IntPolynomialQ());

	public static IAST IntPolynomialQ(final IExpr a0, final IExpr a1) {
		return binary(IntPolynomialQ, a0, a1);
	}

	//
	// should be buit-in ?
	//
	public static IAST Protect(final IExpr a) {
		// TODO fix this
		return unary($s("Protect"), a);
	}

	// public static IAST Unprotect(final IExpr a) {
	// // TODO fix this
	// return unary($s("Unprotect"), a);
	// }

	// public static IAST DownValues(final IExpr a) {
	// // TODO fix this
	// return unary($s("DownValues"), a);
	// }

	public static IAST Divisible(final IExpr a0, final IExpr a1) {
		// TODO fix this
		return binary($s("Divisible"), a0, a1);
	}
	
	public static IAST EllipticPi(final IExpr a0, final IExpr a1, final IExpr a2) {
		// TODO fix this
		return ternary($s("EllipticPi"), a0, a1, a2);
	}
	
	public static IAST Hypergeometric2F1(final IExpr a0, final IExpr a1, final IExpr a2, final IExpr a3) {
		// TODO fix this
		return quaternary($s("Hypergeometric2F1"), a0, a1, a2, a3);
	}

	public static IAST AppellF1(final IExpr a0, final IExpr a1, final IExpr a2, final IExpr a3, final IExpr a4, final IExpr a5) {
		// TODO fix this
		return F.senary($s("AppellF1"), a0, a1, a2, a3, a4, a5);
	}

	public static IAST CosIntegral(final IExpr a) {
		// TODO fix this
		return unary($s("CosIntegral"), a);
	}

	public static IAST EllipticE(final IExpr a0, final IExpr a1) {
		return binary($s("EllipticE"), a0, a1);
	}

	public static IAST EllipticF(final IExpr a0, final IExpr a1) {
		return binary($s("EllipticF"), a0, a1);
	}

	public static IAST FresnelC(final IExpr a) {
		// TODO fix this
		return unary($s("FresnelC"), a);
	}

	public static IAST FresnelS(final IExpr a) {
		// TODO fix this
		return unary($s("FresnelS"), a);
	}

	public static IAST SinIntegral(final IExpr a) {
		// TODO fix this
		return unary($s("SinIntegral"), a);
	}

	public static IAST CoshIntegral(final IExpr a) {
		// TODO fix this
		return unary($s("CoshIntegral"), a);
	}

	public static IAST SinhIntegral(final IExpr a) {
		// TODO fix this
		return unary($s("SinhIntegral"), a);
	}

	public static IAST Erfi(final IExpr a) {
		return unary($s("Erfi"), a);
	}

	public static IAST ExpIntegralEi(final IExpr a) {
		return unary($s("ExpIntegralEi"), a);
	}

	public static IAST LogIntegral(final IExpr a) {
		return unary($s("LogIntegral"), a);
	}

	public static IAST PolyLog(final IExpr a0, final IExpr a1) {
		return binary($s("PolyLog"), a0, a1);
	}

	public static IAST ProductLog(final IExpr a) {
		return unary($s("ProductLog"), a);
	}

	public static IAST Erfc(final IExpr a) {
		return unary($s("Erfc"), a);
	}

	public static IAST LogGamma(final IExpr a0) {
		return unary($s("LogGamma"), a0);
	}

	public static IAST Zeta(final IExpr a0, final IExpr a1) {
		return binary($s("Zeta"), a0, a1);
	}

	public static IAST PolyGamma(final IExpr a0, final IExpr a1) {
		return binary($s("PolyGamma"), a0, a1);
	}

	public static IAST ExpIntegralE(final IExpr a0, final IExpr a1) {
		return binary($s("ExpIntegralE"), a0, a1);
	}

	public static void init() {
		// Dist[u_,v_]+Dist[w_,v_] := If[ZeroQ[u+w], 0, Dist[u+w,v]],
		org.matheclipse.core.reflection.system.Plus.CONST.setUpHashRule(Dist($p("u"), $p("v")), Dist($p("w"), $p("v")),
				If(ZeroQ(Plus($s("u"), $s("w"))), C0, Dist(Plus($s("u"), $s("w")), $s("v"))), null);
		// Dist[u_,v_]-Dist[w_,v_] := If[ZeroQ[u-w], 0, Dist[u-w,v]],
		org.matheclipse.core.reflection.system.Plus.CONST.setUpHashRule(Dist($p("u"), $p("v")), Times(CN1, Dist($p("w"), $p("v"))),
				If(ZeroQ(Plus($s("u"), Times(CN1, $s("w")))), C0, Dist(Plus($s("u"), Times(CN1, $s("w"))), $s("v"))), null);
		// Dist[u_,v_]*w_ := Dist[w*u,v] /; w=!=-1
		org.matheclipse.core.reflection.system.Times.CONST.setUpHashRule(Dist($p("u"), $p("v")), $p("w"),
				Dist(Times($s("w"), $s("u")), $s("v")), UnsameQ($s("w"), CN1));
	}
}