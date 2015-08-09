package org.matheclipse.core.interfaces;

/**
 * 
 */
public interface IComplexNum extends INumber {
	/**
	 * Return the absolute value of this complex number.
	 * <p>
	 * Returns <code>NaN</code> if either real or imaginary part is
	 * <code>NaN</code> and <code>Double.POSITIVE_INFINITY</code> if neither
	 * part is <code>NaN</code>, but at least one part takes an infinite value.
	 * 
	 * @return the absolute value
	 */
	public double dabs();

	public double getRealPart();

	public double getImaginaryPart();

	public IComplexNum conjugate();

	public IComplexNum add(IComplexNum val);

	public IComplexNum multiply(IComplexNum val);

	public IComplexNum pow(IComplexNum val);
}
