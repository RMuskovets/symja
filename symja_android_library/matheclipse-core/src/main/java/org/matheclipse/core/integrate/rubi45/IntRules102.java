package org.matheclipse.core.integrate.rubi45;


import static org.matheclipse.core.expression.F.*;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.*;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctions.*;
import org.matheclipse.core.interfaces.IAST;

/** 
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 *  
 */
public class IntRules102 { 
  public static IAST RULES = List( 
ISetDelayed(Int(Power(Sinh(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT),x_Symbol),
    Condition(Int(Power(Plus(Times(C1D2,Power(Times(c,Power(x,n)),b)),Negate(Power(Times(C2,Power(Times(c,Power(x,n)),b)),-1))),p),x),And(FreeQ(c,x),RationalQ(b,n,p)))),
ISetDelayed(Int(Power(Cosh(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT),x_Symbol),
    Condition(Int(Power(Plus(Times(C1D2,Power(Times(c,Power(x,n)),b)),Power(Times(C2,Power(Times(c,Power(x,n)),b)),-1)),p),x),And(FreeQ(c,x),RationalQ(b,n,p)))),
ISetDelayed(Int(Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,x,Plus(p,C2),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Plus(p,C1),-1)),Times(x,Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1))),And(And(FreeQ(List(a,b,c,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(x,Plus(p,C2),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Plus(p,C1),-1)),Times(CN1,x,Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1))),And(And(FreeQ(List(a,b,c,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Sqrt(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Times(x,Sqrt(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n))))))),Power(Plus(CN1,Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C4,Power(n,-1))))),CN1D2),Int(Times(Sqrt(Plus(CN1,Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C4,Power(n,-1)))))),Power(x,-1)),x)),And(FreeQ(List(a,b,c,n),x),ZeroQ(Plus(Times(b,n),Negate(C2)))))),
ISetDelayed(Int(Sqrt(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Times(x,Sqrt(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n))))))),Power(Plus(C1,Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C4,Power(n,-1))))),CN1D2),Int(Times(Sqrt(Plus(C1,Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C4,Power(n,-1)))))),Power(x,-1)),x)),And(FreeQ(List(a,b,c,n),x),ZeroQ(Plus(Times(b,n),Negate(C2)))))),
ISetDelayed(Int(Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT),x_Symbol),
    Condition(Int(ExpandIntegrand(Power(Plus(Times(CN1,Power(E,Times(CN1,a,b,n,p)),Power(Times(C2,b,n,p),-1),Power(Times(c,Power(x,n)),Negate(Power(Times(n,p),-1)))),Times(Power(E,Times(a,b,n,p)),Power(Times(C2,b,n,p),-1),Power(Times(c,Power(x,n)),Power(Times(n,p),-1)))),p),x),x),And(And(FreeQ(List(a,b,c,n),x),PositiveIntegerQ(p)),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT),x_Symbol),
    Condition(Int(ExpandIntegrand(Power(Plus(Times(C1D2,Power(E,Times(CN1,a,b,n,p)),Power(Times(c,Power(x,n)),Negate(Power(Times(n,p),-1)))),Times(C1D2,Power(E,Times(a,b,n,p)),Power(Times(c,Power(x,n)),Power(Times(n,p),-1)))),p),x),x),And(And(FreeQ(List(a,b,c,n),x),PositiveIntegerQ(p)),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),x_Symbol),
    Condition(Plus(Times(CN1,x,Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)),-1)),Times(b,n,x,Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)),-1))),And(FreeQ(List(a,b,c,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)))))),
ISetDelayed(Int(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),x_Symbol),
    Condition(Plus(Times(CN1,x,Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)),-1)),Times(b,n,x,Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)),-1))),And(FreeQ(List(a,b,c,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)))))),
ISetDelayed(Int(Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,x,Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(b,n,p,x,Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C1))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(CN1,Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1),Int(Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),x))),And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Greater(p,C1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,x,Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(b,n,p,x,Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C1))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1),Int(Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),x))),And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Greater(p,C1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(x,Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,x,Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(CN1,Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),x))),And(And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)))))),
ISetDelayed(Int(Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,x,Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1)),Times(x,Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),x))),And(And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(C1)))))),
ISetDelayed(Int(Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Times(x,Power(Plus(Times(CN1,Power(E,Negate(a)),Power(Times(c,Power(x,n)),Negate(b))),Times(Power(E,a),Power(Times(c,Power(x,n)),b))),p),Power(Times(Plus(Times(b,n,p),C1),Power(Plus(C2,Times(CN1,C2,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b)))),p)),-1),Hypergeometric2F1(Negate(p),Times(CN1,Plus(C1,Times(b,n,p)),Power(Times(C2,b,n),-1)),Plus(C1,Times(CN1,Plus(C1,Times(b,n,p)),Power(Times(C2,b,n),-1))),Times(Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b))))),And(FreeQ(List(a,b,c,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Times(x,Power(Plus(Times(Power(E,Negate(a)),Power(Times(c,Power(x,n)),Negate(b))),Times(Power(E,a),Power(Times(c,Power(x,n)),b))),p),Power(Times(Plus(Times(b,n,p),C1),Power(Plus(C2,Times(C2,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b)))),p)),-1),Hypergeometric2F1(Negate(p),Times(CN1,Plus(C1,Times(b,n,p)),Power(Times(C2,b,n),-1)),Plus(C1,Times(CN1,Plus(C1,Times(b,n,p)),Power(Times(C2,b,n),-1))),Times(CN1,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b))))),And(FreeQ(List(a,b,c,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(p,C2),Power(x,Plus(m,C1)),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Plus(m,C1),Plus(p,C1)),-1)),Times(Power(x,Plus(m,C1)),Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1))),And(And(And(FreeQ(List(a,b,c,m,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))))),NonzeroQ(Plus(p,C1))),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(Plus(p,C2),Power(x,Plus(m,C1)),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Plus(m,C1),Plus(p,C1)),-1)),Times(CN1,Power(x,Plus(m,C1)),Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1))),And(And(And(FreeQ(List(a,b,c,m,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))))),NonzeroQ(Plus(p,C1))),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(Power(C2,p),-1),Int(ExpandIntegrand(Times(Power(x,m),Power(Plus(Times(CN1,Plus(m,C1),Power(Times(b,n,p),-1),Power(E,Times(CN1,a,b,n,p,Power(Plus(m,C1),-1))),Power(Times(c,Power(x,n)),Times(CN1,Plus(m,C1),Power(Times(n,p),-1)))),Times(Plus(m,C1),Power(Times(b,n,p),-1),Power(E,Times(a,b,n,p,Power(Plus(m,C1),-1))),Power(Times(c,Power(x,n)),Times(Plus(m,C1),Power(Times(n,p),-1))))),p)),x),x)),And(And(FreeQ(List(a,b,c,m,n),x),PositiveIntegerQ(p)),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(Power(C2,p),-1),Int(ExpandIntegrand(Times(Power(x,m),Power(Plus(Times(Power(E,Times(a,b,n,p,Power(Plus(m,C1),-1))),Power(Times(c,Power(x,n)),Times(Plus(m,C1),Power(Times(n,p),-1)))),Times(Power(E,Times(CN1,a,b,n,p,Power(Plus(m,C1),-1))),Power(Times(c,Power(x,n)),Times(CN1,Plus(m,C1),Power(Times(n,p),-1))))),p)),x),x)),And(And(FreeQ(List(a,b,c,m,n),x),PositiveIntegerQ(p)),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))),-1)),Times(b,n,Power(x,Plus(m,C1)),Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))),-1))),And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))))),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))),-1)),Times(b,n,Power(x,Plus(m,C1)),Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))),-1))),And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))))),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(b,n,p,Power(x,Plus(m,C1)),Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C1))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(CN1,Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1),Int(Times(Power(x,m),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))),RationalQ(p)),Greater(p,C1)),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(b,n,p,Power(x,Plus(m,C1)),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C1))),Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1),Int(Times(Power(x,m),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))),RationalQ(p)),Greater(p,C1)),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(CN1,Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Times(Power(x,m),Power(Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2))),x))),And(And(And(And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))))),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,C1)),Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(b,n,Plus(p,C1)),-1)),Times(Plus(m,C1),Power(x,Plus(m,C1)),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Times(Power(x,m),Power(Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2))),x))),And(And(And(And(And(FreeQ(List(a,b,c,m,n),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,C2))),Negate(Sqr(Plus(m,C1)))))),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sinh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Times(Power(x,Plus(m,C1)),Power(Plus(Times(CN1,Power(E,Negate(a)),Power(Times(c,Power(x,n)),Negate(b))),Times(Power(E,a),Power(Times(c,Power(x,n)),b))),p),Power(Times(Plus(m,Times(b,n,p),C1),Power(Plus(C2,Times(CN1,C2,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b)))),p)),-1),Hypergeometric2F1(Negate(p),Times(CN1,Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(CN1,Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b))))),And(FreeQ(List(a,b,c,m,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cosh(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Times(Power(x,Plus(m,C1)),Power(Plus(Times(Power(E,Negate(a)),Power(Times(c,Power(x,n)),Negate(b))),Times(Power(E,a),Power(Times(c,Power(x,n)),b))),p),Power(Times(Plus(m,Times(b,n,p),C1),Power(Plus(C2,Times(C2,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b)))),p)),-1),Hypergeometric2F1(Negate(p),Times(CN1,Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(CN1,Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(CN1,Power(E,Times(CN2,a)),Power(Times(c,Power(x,n)),Times(CN2,b))))),And(FreeQ(List(a,b,c,m,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Power(Sech(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT),x_Symbol),
    Condition(Times(Power(C2,p),Int(Power(Times(Power(Times(c,Power(x,n)),b),Power(Plus(C1,Power(Times(c,Power(x,n)),Times(C2,b))),-1)),p),x)),And(FreeQ(c,x),RationalQ(b,n,p)))),
ISetDelayed(Int(Power(Csch(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT),x_Symbol),
    Condition(Times(Power(C2,p),Int(Power(Times(Power(Times(c,Power(x,n)),b),Power(Plus(CN1,Power(Times(c,Power(x,n)),Times(C2,b))),-1)),p),x)),And(FreeQ(c,x),RationalQ(b,n,p)))),
ISetDelayed(Int(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),x_Symbol),
    Condition(Times(C2,Power(E,Times(CN1,a,b,n)),Int(Times(Power(Times(c,Power(x,n)),Power(n,-1)),Power(Plus(Power(E,Times(CN2,a,b,n)),Power(Times(c,Power(x,n)),Times(C2,Power(n,-1)))),-1)),x)),And(FreeQ(List(a,b,c,n),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)))))),
ISetDelayed(Int(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),x_Symbol),
    Condition(Times(CN2,b,n,Power(E,Times(CN1,a,b,n)),Int(Times(Power(Times(c,Power(x,n)),Power(n,-1)),Power(Plus(Power(E,Times(CN2,a,b,n)),Negate(Power(Times(c,Power(x,n)),Times(C2,Power(n,-1))))),-1)),x)),And(FreeQ(List(a,b,c,n),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(C1)))))),
ISetDelayed(Int(Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(Plus(p,Negate(C2)),x,Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Plus(p,Negate(C1)),-1)),Times(x,Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1))),And(And(FreeQ(List(a,b,c,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)))),NonzeroQ(Plus(p,Negate(C1)))))),
ISetDelayed(Int(Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,Plus(p,Negate(C2)),x,Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Plus(p,Negate(C1)),-1)),Times(CN1,x,Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1))),And(And(FreeQ(List(a,b,c,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)))),NonzeroQ(Plus(p,Negate(C1)))))),
ISetDelayed(Int(Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(x,Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(x,Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1)),Times(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1),Int(Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),x))),And(And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Greater(p,C1)),Unequal(p,C2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)))))),
ISetDelayed(Int(Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,x,Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(CN1,x,Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1)),Times(CN1,Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1),Int(Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),x))),And(And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Greater(p,C1)),Unequal(p,C2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(C1)))))),
ISetDelayed(Int(Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,b,n,p,x,Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C1)),Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(CN1,x,Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(Sqr(b),Sqr(n),p,Plus(p,C1),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1),Int(Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),x))),And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Less(p,CN1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_),x_Symbol),
    Condition(Plus(Times(CN1,b,n,p,x,Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(CN1,x,Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1)),Times(CN1,Sqr(b),Sqr(n),p,Plus(p,C1),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)),-1),Int(Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2)),x))),And(And(And(FreeQ(List(a,b,c,n),x),RationalQ(p)),Less(p,CN1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT),x_Symbol),
    Condition(Times(Power(C2,p),x,Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),C1),p),Power(Plus(Times(b,n,p),C1),-1),Power(Times(Power(E,a),Power(Times(c,Power(x,n)),b),Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),C1),-1)),p),Hypergeometric2F1(p,Times(Plus(Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(Plus(Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(CN1,Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))))),And(FreeQ(List(a,b,c,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT),x_Symbol),
    Condition(Times(x,Power(Plus(C2,Times(CN1,C2,Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b)))),p),Power(Plus(Times(b,n,p),C1),-1),Power(Times(Power(E,a),Power(Times(c,Power(x,n)),b),Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),Negate(C1)),-1)),p),Hypergeometric2F1(p,Times(Plus(Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(Plus(Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))))),And(FreeQ(List(a,b,c,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sech(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(C2,p),Int(Times(Power(x,m),Power(Times(Power(Times(c,Power(x,n)),b),Power(Plus(C1,Power(Times(c,Power(x,n)),Times(C2,b))),-1)),p)),x)),And(FreeQ(c,x),RationalQ(b,m,n,p)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Csch(Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(C2,p),Int(Times(Power(x,m),Power(Times(Power(Times(c,Power(x,n)),b),Power(Plus(CN1,Power(Times(c,Power(x,n)),Times(C2,b))),-1)),p)),x)),And(FreeQ(c,x),RationalQ(b,m,n,p)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sec(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Times(C2,Power(E,Times(CN1,a,b,n,Power(Plus(m,C1),-1))),Int(Times(Power(x,m),Power(Times(c,Power(x,n)),Times(Plus(m,C1),Power(n,-1))),Power(Plus(Power(E,Times(CN2,a,b,n,Power(Plus(m,C1),-1))),Power(Times(c,Power(x,n)),Times(C2,Plus(m,C1),Power(n,-1)))),-1)),x)),And(FreeQ(List(a,b,c,m,n),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Csc(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT))))))),x_Symbol),
    Condition(Times(CN2,b,n,Power(Plus(m,C1),-1),Power(E,Times(CN1,a,b,n,Power(Plus(m,C1),-1))),Int(Times(Power(x,m),Power(Times(c,Power(x,n)),Times(Plus(m,C1),Power(n,-1))),Power(Plus(Power(E,Times(CN2,a,b,n,Power(Plus(m,C1),-1))),Negate(Power(Times(c,Power(x,n)),Times(C2,Plus(m,C1),Power(n,-1))))),-1)),x)),And(FreeQ(List(a,b,c,m,n),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(Plus(p,Negate(C2)),Power(x,Plus(m,C1)),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Plus(m,C1),Plus(p,Negate(C1))),-1)),Times(Power(x,Plus(m,C1)),Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1))),And(And(And(FreeQ(List(a,b,c,m,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Sqr(Plus(m,C1))))),NonzeroQ(Plus(m,C1))),NonzeroQ(Plus(p,Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(p,Negate(C2)),Power(x,Plus(m,C1)),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Plus(m,C1),Plus(p,Negate(C1))),-1)),Times(CN1,Power(x,Plus(m,C1)),Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1))),And(And(And(FreeQ(List(a,b,c,m,n,p),x),ZeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Sqr(Plus(m,C1))))),NonzeroQ(Plus(m,C1))),NonzeroQ(Plus(p,Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Tanh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(Plus(m,C1),Power(x,Plus(m,C1)),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1)),Times(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(Sqr(Plus(m,C1)))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1),Int(Times(Power(x,m),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,m,n),x),RationalQ(p)),Greater(p,C1)),Unequal(p,C2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,C1)),Coth(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1)),Times(CN1,Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(Sqr(Plus(m,C1)))),Power(Times(Sqr(b),Sqr(n),Plus(p,Negate(C1)),Plus(p,Negate(C2))),-1),Int(Times(Power(x,m),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,m,n),x),RationalQ(p)),Greater(p,C1)),Unequal(p,C2)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(Plus(p,Negate(C2)))),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(CN1,b,n,p,Power(x,Plus(m,C1)),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C1)),Sinh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(Sqr(b),Sqr(n),p,Plus(p,C1),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1),Int(Times(Power(x,m),Power(Sech(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2))),x))),And(And(And(FreeQ(List(a,b,c,m,n),x),RationalQ(p)),Less(p,CN1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Plus(m,C1),Power(x,Plus(m,C1)),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),p),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(CN1,b,n,p,Power(x,Plus(m,C1)),Cosh(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C1)),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1)),Times(CN1,Sqr(b),Sqr(n),p,Plus(p,C1),Power(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))),-1),Int(Times(Power(x,m),Power(Csch(Plus(a,Times(b,Log(Times(c,Power(x,n)))))),Plus(p,C2))),x))),And(And(And(FreeQ(List(a,b,c,m,n),x),RationalQ(p)),Less(p,CN1)),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sech(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(C2,p),Power(x,Plus(m,C1)),Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),C1),p),Power(Plus(m,Times(b,n,p),C1),-1),Power(Times(Power(E,a),Power(Times(c,Power(x,n)),b),Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),C1),-1)),p),Hypergeometric2F1(p,Times(Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(CN1,Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))))),And(FreeQ(List(a,b,c,m,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Csch(Plus(a_DEFAULT,Times(b_DEFAULT,Log(Times(c_DEFAULT,Power(x_,n_DEFAULT)))))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(C2,p),Power(x,Plus(m,C1)),Power(Plus(Times(CN1,Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),C1),p),Power(Plus(m,Times(b,n,p),C1),-1),Power(Times(Power(E,a),Power(Times(c,Power(x,n)),b),Power(Plus(Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))),Negate(C1)),-1)),p),Hypergeometric2F1(p,Times(Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1)),Plus(C1,Times(Plus(m,Times(b,n,p),C1),Power(Times(C2,b,n),-1))),Times(Power(E,Times(C2,a)),Power(Times(c,Power(x,n)),Times(C2,b))))),And(FreeQ(List(a,b,c,m,n,p),x),NonzeroQ(Plus(Times(Sqr(b),Sqr(n),Sqr(p)),Negate(Sqr(Plus(m,C1)))))))),
ISetDelayed(Int(Times(Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Sinh(Times(a_DEFAULT,x_,Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Cosh(Times(a,x,Power(Log(Times(b,x)),p))),Power(a,-1)),Times(CN1,p,Int(Times(Sinh(Times(a,x,Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x))),And(And(FreeQ(List(a,b),x),RationalQ(p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Cosh(Times(a_DEFAULT,x_,Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Sinh(Times(a,x,Power(Log(Times(b,x)),p))),Power(a,-1)),Times(CN1,p,Int(Times(Cosh(Times(a,x,Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x))),And(And(FreeQ(List(a,b),x),RationalQ(p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Sinh(Times(a_DEFAULT,Power(x_,n_),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n,Power(x,Plus(n,Negate(C1)))),-1)),Times(CN1,p,Power(n,-1),Int(Times(Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x)),Times(Plus(n,Negate(C1)),Power(Times(a,n),-1),Int(Times(Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Power(x,n),-1)),x))),And(And(FreeQ(List(a,b),x),RationalQ(n,p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Cosh(Times(a_DEFAULT,Power(x_,n_),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n,Power(x,Plus(n,Negate(C1)))),-1)),Times(CN1,p,Power(n,-1),Int(Times(Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x)),Times(Plus(n,Negate(C1)),Power(Times(a,n),-1),Int(Times(Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Power(x,n),-1)),x))),And(And(FreeQ(List(a,b),x),RationalQ(n,p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Sinh(Times(a_DEFAULT,Power(x_,n_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(CN1,Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n),-1)),Times(CN1,p,Power(n,-1),Int(Times(Power(x,Plus(n,Negate(C1))),Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x))),And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(n),C1))),RationalQ(p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Cosh(Times(a_DEFAULT,Power(x_,n_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n),-1)),Times(CN1,p,Power(n,-1),Int(Times(Power(x,Plus(n,Negate(C1))),Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x))),And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(n),C1))),RationalQ(p)),Greater(p,C0)))),
ISetDelayed(Int(Times(Power(x_,m_),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Sinh(Times(a_DEFAULT,Power(x_,n_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n),-1)),Times(CN1,p,Power(n,-1),Int(Times(Power(x,m),Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x)),Times(CN1,Plus(m,Negate(n),C1),Power(Times(a,n),-1),Int(Times(Power(x,Plus(m,Negate(n))),Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p)))),x))),And(And(And(FreeQ(List(a,b),x),RationalQ(m,n,p)),Greater(p,C0)),NonzeroQ(Plus(m,Negate(n),C1))))),
ISetDelayed(Int(Times(Power(x_,m_),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT),Cosh(Times(a_DEFAULT,Power(x_,n_DEFAULT),Power(Log(Times(b_DEFAULT,x_)),p_DEFAULT)))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Times(a,n),-1)),Times(CN1,p,Power(n,-1),Int(Times(Power(x,m),Cosh(Times(a,Power(x,n),Power(Log(Times(b,x)),p))),Power(Log(Times(b,x)),Plus(p,Negate(C1)))),x)),Times(CN1,Plus(m,Negate(n),C1),Power(Times(a,n),-1),Int(Times(Power(x,Plus(m,Negate(n))),Sinh(Times(a,Power(x,n),Power(Log(Times(b,x)),p)))),x))),And(And(And(FreeQ(List(a,b),x),RationalQ(m,n,p)),Greater(p,C0)),NonzeroQ(Plus(m,Negate(n),C1)))))
  );
}
