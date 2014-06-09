package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.<br />
 * See GIT repository at: <a href="https://bitbucket.org/axelclk/symjaunittests">https://bitbucket.org/axelclk/symjaunittests</a>.
 */
public interface PowerRules {
  final public static IAST RULES = List(
    Set(Power(E,Times(CC(0L,1L,3L,2L),Pi)),
      CNI),
    SetDelayed(Power(E,Times(Pi,$p(c,Complex))),
      Module(List(Set(r,Re(c)),Set(j,Im(c))),Condition(If(EvenQ(j),C1,CN1),And(Equal(r,C0),IntegerQ(j))))),
    Set(Power(E,CInfinity),
      CInfinity),
    Set(Power(E,CNInfinity),
      C0),
    Set(Power(E,Times(CI,CInfinity)),
      Indeterminate),
    Set(Power(E,Times(CNI,CInfinity)),
      Indeterminate),
    Set(Power(E,CComplexInfinity),
      Indeterminate),
    SetDelayed(Power(E,Log(x_)),
      x),
    SetDelayed(Power(Tan(x_),$p(m,IntegerQ)),
      Condition(Power(Cot(x),Times(CN1,m)),Less(m,C0))),
    SetDelayed(Power(Cot(x_),$p(m,IntegerQ)),
      Condition(Power(Tan(x),Times(CN1,m)),Less(m,C0))),
    SetDelayed(Power(Sec(x_),$p(m,IntegerQ)),
      Condition(Power(Cos(x),Times(CN1,m)),Less(m,C0))),
    SetDelayed(Power(Cos(x_),$p(m,IntegerQ)),
      Condition(Power(Sec(x),Times(CN1,m)),Less(m,C0))),
    SetDelayed(Power(Csc(x_),$p(m,IntegerQ)),
      Condition(Power(Sin(x),Times(CN1,m)),Less(m,C0))),
    SetDelayed(Power(Sin(x_),$p(m,IntegerQ)),
      Condition(Power(Csc(x),Times(CN1,m)),Less(m,C0)))
  );
}