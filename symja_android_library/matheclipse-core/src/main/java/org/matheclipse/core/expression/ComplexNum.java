package org.matheclipse.core.expression;

import org.apache.commons.math4.complex.Complex;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.IVisitor;
import org.matheclipse.core.visit.IVisitorBoolean;
import org.matheclipse.core.visit.IVisitorInt;
import org.matheclipse.core.visit.IVisitorLong;

/**
 * <code>IComplexNum</code> implementation which wraps a
 * <code>org.apache.commons.math4.complex.Complex</code> value to represent a
 * numeric complex floating-point number.
 */
public class ComplexNum extends ExprImpl implements IComplexNum {

	/**
	 * Be cautious with this method, no new internal couble complex is created
	 * 
	 * @param numerator
	 * @return
	 */
	protected static ComplexNum newInstance(final Complex value) {
		ComplexNum d = new ComplexNum(0.0, 0.0);
		d.fComplex = value;
		return d;
	}

	public static ComplexNum valueOf(final INum d) {
		return newInstance(new Complex(d.getRealPart(), 0.0));
	}

	public static ComplexNum valueOf(final Complex c) {
		return newInstance(c);
	}

	public static ComplexNum valueOf(final double real) {
		return newInstance(new Complex(real, 0.0));
	}

	public static ComplexNum valueOf(final double real, final double imaginary) {
		return newInstance(new Complex(real, imaginary));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6033055105824482264L;

	/** The square root of -1. A number representing "0.0 + 1.0i" */
	public static final ComplexNum I = valueOf(0.0, 1.0);

	/** A complex number representing "NaN + NaNi" */
	public static final ComplexNum NaN = valueOf(Double.NaN, Double.NaN);

	/** A complex number representing "1.0 + 0.0i" */
	public static final ComplexNum ONE = valueOf(1.0, 0.0);

	/** A complex number representing "0.0 + 0.0i" */
	public static final ComplexNum ZERO = valueOf(0.0, 0.0);

	Complex fComplex;

	private ComplexNum(final double r, final double i) {
		fComplex = new Complex(r, i);
	}

	/**
	 * @return
	 */
	@Override
	public double getImaginaryPart() {
		double temp = fComplex.getImaginary();
		if (temp == (-0.0)) {
			temp = 0.0;
		}
		return temp;
	}

	/** 
	 * @return
	 */
	@Override
	public double getRealPart() {
		double temp = fComplex.getReal();
		if (temp == (-0.0)) {
			temp = 0.0;
		}
		return temp;
	}

	@Override
	public boolean isZero() {
		return F.isZero(fComplex.getReal())  && F.isZero(fComplex.getImaginary());
	}

	@Override
	public int hierarchy() {
		return DOUBLECOMPLEXID;
	}

	@Override
	public IComplexNum add(final IComplexNum val) {
		return newInstance(fComplex.sum(((ComplexNum) val).fComplex));
	}

	public ComplexNum add(final ComplexNum that) {
		return newInstance(fComplex.sum(that.fComplex));
	}

	public Apcomplex apcomplexValue(long precision) {
		return new Apcomplex(new Apfloat(fComplex.getReal(), precision),
				new Apfloat(fComplex.getImaginary(), precision));
	}

	@Override
	public ApcomplexNum apcomplexNumValue(long precision) {
		return ApcomplexNum.valueOf(apcomplexValue(precision));

	}

	@Override
	public ComplexNum complexNumValue() {
		return this;
	}

	@Override
	public IComplexNum multiply(final IComplexNum val) {
		return newInstance(fComplex.multiply(((ComplexNum) val).fComplex));
	}

	@Override
	public IComplexNum pow(final IComplexNum val) {
		return newInstance(fComplex.pow(((ComplexNum) val).fComplex));
	}

	/**
	 * @param that
	 * @return
	 */
	public Complex add(final Complex that) {
		return fComplex.sum(that);
	}

	/** {@inheritDoc} */
	@Override
	public IComplexNum conjugate() {
		return newInstance(fComplex.conjugate());
	}

	/**
	 * @param that
	 * @return
	 */
	public Complex divide(final Complex that) {
		return fComplex.divide(that);
	}

	public ComplexNum divide(final ComplexNum that) throws ArithmeticException {
		return newInstance(fComplex.divide(that.fComplex));
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof ComplexNum) {
			return fComplex.equals(((ComplexNum) obj).fComplex);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public IExpr evaluate(EvalEngine engine) {
		if (engine.isNumericMode() && engine.isApfloat()) {
			return ApcomplexNum.valueOf(getRealPart(), getImaginaryPart(), engine.getNumericPrecision());
		}
		if (F.isZero(getImaginaryPart())) {
			return F.num(getRealPart());
		}
		return F.NIL;
	}

	@Override
	public boolean isSame(IExpr expression, double epsilon) {
		if (expression instanceof ComplexNum) {
			return F.isZero(fComplex.getReal() - ((ComplexNum) expression).fComplex.getReal(), epsilon)
					&& F.isZero(fComplex.getImaginary() - ((ComplexNum) expression).fComplex.getImaginary(), epsilon);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public double dabs() {
		return dabs(fComplex);
	}

	public static double dabs(Complex c) {
		if (c.isNaN()) {
			return Double.NaN;
		}

		if (c.isInfinite()) {
			return Double.POSITIVE_INFINITY;
		}

		if (Math.abs(c.getReal()) < Math.abs(c.getImaginary())) {
			if (F.isZero(c.getImaginary())) {
				return Math.abs(c.getReal());
			}
			final double q = c.getReal() / c.getImaginary();
			return Math.abs(c.getImaginary()) * Math.sqrt(1 + q * q);
		} else {
			if (F.isZero(c.getReal())) {
				return Math.abs(c.getImaginary());
			}
			final double q = c.getImaginary() / c.getReal();
			return Math.abs(c.getReal()) * Math.sqrt(1 + q * q);
		}
	}

	@Override
	public Num eabs() {
		return Num.valueOf(dabs());
	}

	/** {@inheritDoc} */
	@Override
	public int compareAbsValueToOne() {
		double temp = dabs();
		return Double.compare(temp, 1.0);
	}

	@Override
	public double getImaginary() {
		return fComplex.getImaginary();
	}

	@Override
	public double getReal() {
		return fComplex.getReal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return fComplex.hashCode();
	}

	/**
	 * @return
	 */
	public boolean isInfinite() {
		return fComplex.isInfinite();
	}

	/**
	 * @return
	 */
	public boolean isNaN() {
		return fComplex.isNaN();
	}

	/**
	 * @param that
	 * @return
	 */
	public ComplexNum multiply(final ComplexNum that) {
		return newInstance(fComplex.multiply(that.fComplex));
	}

	/**
	 * @return
	 */
	@Override
	public ComplexNum negate() {
		return newInstance(fComplex.negate());
	}

	/**
	 * @return
	 */
	@Override
	public INumber opposite() {
		return newInstance(fComplex.negate());
	}

	/**
	 * @param that
	 * @return
	 */
	@Override
	public IExpr plus(final IExpr that) {
		if (that instanceof ComplexNum) {
			return newInstance(fComplex.sum(((ComplexNum) that).fComplex));
		}
		if (that instanceof ApcomplexNum) {
			ApcomplexNum acn = (ApcomplexNum) that;
			return ApcomplexNum.valueOf(getRealPart(), getImaginaryPart(), acn.fApcomplex.precision()).add(acn);
		}
		if (that instanceof ApfloatNum) {
			ApfloatNum afn = (ApfloatNum) that;
			return ApcomplexNum.valueOf(getRealPart(), getImaginaryPart(), afn.fApfloat.precision())
					.add(ApcomplexNum.valueOf(afn.fApfloat, Apcomplex.ZERO));
		}
		if (that instanceof Num) {
			return add(ComplexNum.valueOf(((Num) that).getRealPart()));
		}
		return super.plus(that);
	}

	@Override
	public IExpr inverse() {
		final double tmp = (fComplex.getReal() * fComplex.getReal())
				+ (fComplex.getImaginary() * fComplex.getImaginary());
		return valueOf(fComplex.getReal() / tmp, -fComplex.getImaginary() / tmp);
	}

	/**
	 * @param that
	 * @return
	 */
	public Complex subtract(final Complex that) {
		return fComplex.subtract(that);
	}

	public ComplexNum subtract(final ComplexNum that) {
		return newInstance(fComplex.subtract(that.fComplex));
	}

	/**
	 * @param that
	 * @return
	 */
	@Override
	public IExpr times(final IExpr that) {
		if (that instanceof ComplexNum) {
			return newInstance(fComplex.multiply(((ComplexNum) that).fComplex));
		}
		if (that instanceof ApcomplexNum) {
			ApcomplexNum acn = (ApcomplexNum) that;
			return ApcomplexNum.valueOf(getRealPart(), getImaginaryPart(), acn.fApcomplex.precision()).multiply(acn);
		}
		if (that instanceof ApfloatNum) {
			ApfloatNum afn = (ApfloatNum) that;
			return ApcomplexNum.valueOf(getRealPart(), getImaginaryPart(), afn.fApfloat.precision())
					.multiply(ApcomplexNum.valueOf(afn.fApfloat, Apcomplex.ZERO));
		}
		if (that instanceof Num) {
			return multiply(ComplexNum.valueOf(((Num) that).getRealPart()));
		}
		return super.times(that);
	}

	@Override
	public String toString() {
		try {
			StringBuilder sb = new StringBuilder();
			OutputFormFactory.get().convertDoubleComplex(sb, this, Integer.MIN_VALUE, OutputFormFactory.NO_PLUS_CALL);
			return sb.toString();
		} catch (Exception e1) {
			// fall back to simple output format
			return fComplex.toString();
		}
	}

	@Override
	public String internalFormString(boolean symbolsAsFactoryMethod, int depth) {
		return internalJavaString(symbolsAsFactoryMethod, depth, false);
	}

	@Override
	public String internalScalaString(boolean symbolsAsFactoryMethod, int depth) {
		return internalJavaString(symbolsAsFactoryMethod, depth, true);
	}

	@Override
	public String internalJavaString(boolean symbolsAsFactoryMethod, int depth, boolean useOperators) {
		return "complexNum(" + fComplex.getReal() + "," + fComplex.getImaginary() + ")";
	}

	@Override
	public int complexSign() {
		final int i = (int) Math.signum(fComplex.getReal());
		if (i == 0) {
			return (int) Math.signum(fComplex.getImaginary());
		}
		return i;
	}

	public int compareTo(final Complex that) {
		if (fComplex.getReal() < that.getReal()) {
			return -1;
		}
		if (fComplex.getReal() > that.getReal()) {
			return 1;
		}
		long l1 = Double.doubleToLongBits(fComplex.getReal());
		long l2 = Double.doubleToLongBits(that.getReal());
		if (l1 < l2) {
			return -1;
		}
		if (l1 > l2) {
			return 1;
		}
		if (fComplex.getImaginary() < that.getImaginary()) {
			return -1;
		}
		if (fComplex.getImaginary() > that.getImaginary()) {
			return 1;
		}
		l1 = Double.doubleToLongBits(fComplex.getImaginary());
		l2 = Double.doubleToLongBits(that.getImaginary());
		if (l1 < l2) {
			return -1;
		}
		if (l1 > l2) {
			return 1;
		}
		return 0;
	}

	/**
	 * Compares this expression with the specified expression for order. Returns
	 * a negative integer, zero, or a positive integer as this expression is
	 * canonical less than, equal to, or greater than the specified expression.
	 */
	@Override
	public int compareTo(final IExpr expr) {
		if (expr instanceof ComplexNum) {
			return compareTo(((ComplexNum) expr).fComplex);
		}
		return super.compareTo(expr);
	}

	@Override
	public ISymbol head() {
		return F.Complex;
	}

	public Complex complexValue() {
		return fComplex;
	}

	public Complex getCMComplex() {
		return new Complex(fComplex.getReal(), fComplex.getImaginary());
	}

	/** {@inheritDoc} */
	@Override
	public <T> T accept(IVisitor<T> visitor) {
		return visitor.visit(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean accept(IVisitorBoolean visitor) {
		return visitor.visit(this);
	}

	/** {@inheritDoc} */
	@Override
	public int accept(IVisitorInt visitor) {
		return visitor.visit(this);
	}

	/** {@inheritDoc} */
	@Override
	public long accept(IVisitorLong visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean equalsInt(int i) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public ISignedNumber getIm() {
		return F.num(getImaginaryPart());
	}

	/** {@inheritDoc} */
	@Override
	public ISignedNumber getRe() {
		return F.num(getRealPart());
	}

	@Override
	public INumber ceilFraction() throws ArithmeticException {
		return F.complex(NumberUtil.toLong(Math.ceil(fComplex.getReal())),
				NumberUtil.toLong(Math.ceil(fComplex.getImaginary())));
	}

	@Override
	public INumber floorFraction() throws ArithmeticException {
		return F.complex(NumberUtil.toLong(Math.floor(fComplex.getReal())),
				NumberUtil.toLong(Math.floor(fComplex.getImaginary())));
	}

}