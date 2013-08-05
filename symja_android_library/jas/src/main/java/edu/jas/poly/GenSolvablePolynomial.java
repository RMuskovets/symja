/*
 * $Id: GenSolvablePolynomial.java 4478 2013-07-04 21:35:37Z kredel $
 */

package edu.jas.poly;


import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import edu.jas.structure.RingElem;
import edu.jas.structure.NotInvertibleException;


/**
 * GenSolvablePolynomial generic solvable polynomials implementing RingElem.
 * n-variate ordered solvable polynomials over C. Objects of this class are
 * intended to be immutable. The implementation is based on TreeMap respectively
 * SortedMap from exponents to coefficients by extension of GenPolybomial. Only
 * the coefficients are modeled with generic types, the exponents are fixed to
 * ExpVector with long entries (this will eventually be changed in the future).
 * @param <C> coefficient type
 * @author Heinz Kredel
 */

public class GenSolvablePolynomial<C extends RingElem<C>> extends GenPolynomial<C> {


    //not possible: implements RingElem< GenSolvablePolynomial<C> > {


    /**
     * The factory for the solvable polynomial ring. Hides super.ring.
     */
    public final GenSolvablePolynomialRing<C> ring;


    private static final Logger logger = Logger.getLogger(GenSolvablePolynomial.class);


    private final boolean debug = false; //logger.isDebugEnabled();


    /**
     * Constructor for zero GenSolvablePolynomial.
     * @param r solvable polynomial ring factory.
     */
    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r) {
        super(r);
        ring = r;
    }


    /**
     * Constructor for GenSolvablePolynomial.
     * @param r solvable polynomial ring factory.
     * @param c coefficient.
     * @param e exponent.
     */
    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, C c, ExpVector e) {
        this(r);
        if (c != null && !c.isZERO()) {
            val.put(e, c);
        }
    }


    /**
     * Constructor for GenSolvablePolynomial.
     * @param r solvable polynomial ring factory.
     * @param c coefficient.
     */
    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, C c) {
        this(r, c, r.evzero);
    }


    /**
     * Constructor for GenSolvablePolynomial.
     * @param r solvable polynomial ring factory.
     * @param v the SortedMap of some other (solvable) polynomial.
     */
    protected GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, SortedMap<ExpVector, C> v) {
        this(r);
        val.putAll(v); // assume no zero coefficients
    }


    /**
     * Get the corresponding element factory.
     * @return factory for this Element.
     * @see edu.jas.structure.Element#factory()
     */
    @Override
    public GenSolvablePolynomialRing<C> factory() {
        return ring;
    }


    /**
     * Clone this GenSolvablePolynomial.
     * @see java.lang.Object#clone()
     */
    @Override
    public GenSolvablePolynomial<C> copy() {
        return new GenSolvablePolynomial<C>(ring, this.val);
    }


    /**
     * Comparison with any other object.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object B) {
        if (!(B instanceof GenSolvablePolynomial)) {
            return false;
        }
        return super.equals(B);
    }


    /**
     * GenSolvablePolynomial multiplication.
     * @param Bp GenSolvablePolynomial.
     * @return this*Bp, where * denotes solvable multiplication.
     */
    public GenSolvablePolynomial<C> multiply(GenSolvablePolynomial<C> Bp) {
        if (Bp == null || Bp.isZERO()) {
            return ring.getZERO();
        }
        if (this.isZERO()) {
            return this;
        }
        assert (ring.nvar == Bp.ring.nvar);
        if (debug) {
            logger.debug("ring = " + ring);
        }
        ExpVector Z = ring.evzero;
        GenSolvablePolynomial<C> Cp = ring.getZERO().copy();
        GenSolvablePolynomial<C> zero = ring.getZERO().copy();
        C one = ring.getONECoefficient();

        GenSolvablePolynomial<C> C1 = null;
        GenSolvablePolynomial<C> C2 = null;
        Map<ExpVector, C> A = val;
        Map<ExpVector, C> B = Bp.val;
        Set<Map.Entry<ExpVector, C>> Bk = B.entrySet();
        for (Map.Entry<ExpVector, C> y : A.entrySet()) {
            C a = y.getValue();
            ExpVector e = y.getKey();
            if (debug)
                logger.debug("e = " + e);
            int[] ep = e.dependencyOnVariables();
            int el1 = ring.nvar + 1;
            if (ep.length > 0) {
                el1 = ep[0];
            }
            int el1s = ring.nvar + 1 - el1;
            for (Map.Entry<ExpVector, C> x : Bk) {
                C b = x.getValue();
                ExpVector f = x.getKey();
                if (debug)
                    logger.debug("f = " + f);
                int[] fp = f.dependencyOnVariables();
                int fl1 = 0;
                if (fp.length > 0) {
                    fl1 = fp[fp.length - 1];
                }
                int fl1s = ring.nvar + 1 - fl1;
                if (debug) {
                    logger.debug("el1s = " + el1s + " fl1s = " + fl1s);
                }
                GenSolvablePolynomial<C> Cs = null;
                if (el1s <= fl1s) { // symmetric
                    ExpVector g = e.sum(f);
                    //if ( debug ) logger.debug("g = " + g);
                    Cs = (GenSolvablePolynomial<C>) zero.sum(one, g); // symmetric!
                    //Cs = new GenSolvablePolynomial<C>(ring,one,g); // symmetric!
                } else { // unsymmetric
                    // split e = e1 * e2, f = f1 * f2
                    ExpVector e1 = e.subst(el1, 0);
                    ExpVector e2 = Z.subst(el1, e.getVal(el1));
                    ExpVector e4;
                    ExpVector f1 = f.subst(fl1, 0);
                    ExpVector f2 = Z.subst(fl1, f.getVal(fl1));
                    //if ( debug ) logger.debug("e1 = " + e1 + " e2 = " + e2);
                    //if ( debug ) logger.debug("f1 = " + f1 + " f2 = " + f2);
                    TableRelation<C> rel = ring.table.lookup(e2, f2);
                    //logger.info("relation = " + rel);
                    Cs = rel.p; //ring.copy( rel.p ); // do not clone() 
                    if (rel.f != null) {
                        C2 = (GenSolvablePolynomial<C>) zero.sum(one, rel.f);
                        Cs = Cs.multiply(C2);
                        if (rel.e == null) {
                            e4 = e2;
                        } else {
                            e4 = e2.subtract(rel.e);
                        }
                        ring.table.update(e4, f2, Cs);
                    }
                    if (rel.e != null) {
                        C1 = (GenSolvablePolynomial<C>) zero.sum(one, rel.e);
                        Cs = C1.multiply(Cs);
                        ring.table.update(e2, f2, Cs);
                    }
                    if (!f1.isZERO()) {
                        C2 = (GenSolvablePolynomial<C>) zero.sum(one, f1);
                        Cs = Cs.multiply(C2);
                        //ring.table.update(?,f1,Cs)
                    }
                    if (!e1.isZERO()) {
                        C1 = (GenSolvablePolynomial<C>) zero.sum(one, e1);
                        Cs = C1.multiply(Cs);
                        //ring.table.update(e1,?,Cs)
                    }
                }
                //C c = a.multiply(b);
                Cs = Cs.multiply(a, b); // now non-symmetric // Cs.multiply(c); is symmetric!
                //if ( debug ) logger.debug("Cs = " + Cs);
                Cp = (GenSolvablePolynomial<C>) Cp.sum(Cs);
            }
        }
        return Cp;
    }


    /**
     * GenSolvablePolynomial left and right multiplication. Product with two
     * polynomials.
     * @param S GenSolvablePolynomial.
     * @param T GenSolvablePolynomial.
     * @return S*this*T.
     */
    public GenSolvablePolynomial<C> multiply(GenSolvablePolynomial<C> S, GenSolvablePolynomial<C> T) {
        if (S.isZERO() || T.isZERO() || this.isZERO()) {
            return ring.getZERO();
        }
        if (S.isONE()) {
            return multiply(T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply(T);
    }


    /**
     * GenSolvablePolynomial multiplication. Product with coefficient ring
     * element.
     * @param b coefficient.
     * @return this*b, where * is coefficient multiplication.
     */
    @Override
    public GenSolvablePolynomial<C> multiply(C b) {
        GenSolvablePolynomial<C> Cp = ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        Map<ExpVector, C> Cm = Cp.val; //getMap();
        Map<ExpVector, C> Am = val;
        for (Map.Entry<ExpVector, C> y : Am.entrySet()) {
            ExpVector e = y.getKey();
            C a = y.getValue();
            C c = a.multiply(b);
            if (!c.isZERO()) {
                Cm.put(e, c);
            }
        }
        return Cp;
    }


    /**
     * GenSolvablePolynomial left and right multiplication. Product with
     * coefficient ring element.
     * @param b coefficient.
     * @param c coefficient.
     * @return b*this*c, where * is coefficient multiplication.
     */
    public GenSolvablePolynomial<C> multiply(C b, C c) {
        GenSolvablePolynomial<C> Cp = ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        if (c == null || c.isZERO()) {
            return Cp;
        }
        Map<ExpVector, C> Cm = Cp.val; //getMap();
        Map<ExpVector, C> Am = val;
        for (Map.Entry<ExpVector, C> y : Am.entrySet()) {
            ExpVector e = y.getKey();
            C a = y.getValue();
            C d = b.multiply(a).multiply(c);
            if (!d.isZERO()) {
                Cm.put(e, d);
            }
        }
        return Cp;
    }


    /**
     * GenSolvablePolynomial multiplication. Product with exponent vector.
     * @param e exponent.
     * @return this * x<sup>e</sup>, where * denotes solvable multiplication.
     */
    @Override
    public GenSolvablePolynomial<C> multiply(ExpVector e) {
        if (e == null || e.isZERO()) {
            return this;
        }
        C b = ring.getONECoefficient();
        return multiply(b, e);
    }


    /**
     * GenSolvablePolynomial left and right multiplication. Product with
     * exponent vector.
     * @param e exponent.
     * @param f exponent.
     * @return x<sup>e</sup> * this * x<sup>f</sup>, where * denotes solvable
     *         multiplication.
     */
    public GenSolvablePolynomial<C> multiply(ExpVector e, ExpVector f) {
        if (e == null || e.isZERO()) {
            return this;
        }
        if (f == null || f.isZERO()) {
            return this;
        }
        C b = ring.getONECoefficient();
        return multiply(b, e, b, f);
    }


    /**
     * GenSolvablePolynomial multiplication. Product with ring element and
     * exponent vector.
     * @param b coefficient.
     * @param e exponent.
     * @return this * b x<sup>e</sup>, where * denotes solvable multiplication.
     */
    @Override
    public GenSolvablePolynomial<C> multiply(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return ring.getZERO();
        }
        GenSolvablePolynomial<C> Cp = new GenSolvablePolynomial<C>(ring, b, e);
        return multiply(Cp);
    }


    /**
     * GenSolvablePolynomial left and right multiplication. Product with ring
     * element and exponent vector.
     * @param b coefficient.
     * @param e exponent.
     * @param c coefficient.
     * @param f exponent.
     * @return b x<sup>e</sup> * this * c x<sup>f</sup>, where * denotes
     *         solvable multiplication.
     */
    public GenSolvablePolynomial<C> multiply(C b, ExpVector e, C c, ExpVector f) {
        if (b == null || b.isZERO()) {
            return ring.getZERO();
        }
        if (c == null || c.isZERO()) {
            return ring.getZERO();
        }
        GenSolvablePolynomial<C> Cp = new GenSolvablePolynomial<C>(ring, b, e);
        GenSolvablePolynomial<C> Dp = new GenSolvablePolynomial<C>(ring, c, f);
        return multiply(Cp, Dp);
    }


    /**
     * GenSolvablePolynomial multiplication. Left product with ring element and
     * exponent vector.
     * @param b coefficient.
     * @param e exponent.
     * @return b x<sup>e</sup> * this, where * denotes solvable multiplication.
     */
    public GenSolvablePolynomial<C> multiplyLeft(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return ring.getZERO();
        }
        GenSolvablePolynomial<C> Cp = new GenSolvablePolynomial<C>(ring, b, e);
        return Cp.multiply(this);
    }


    /**
     * GenSolvablePolynomial multiplication. Left product with exponent vector.
     * @param e exponent.
     * @return x<sup>e</sup> * this, where * denotes solvable multiplication.
     */
    public GenSolvablePolynomial<C> multiplyLeft(ExpVector e) {
        if (e == null || e.isZERO()) {
            return this;
        }
        C b = ring.getONECoefficient();
        GenSolvablePolynomial<C> Cp = new GenSolvablePolynomial<C>(ring, b, e);
        return Cp.multiply(this);
    }


    /**
     * GenSolvablePolynomial multiplication. Left product with coefficient ring
     * element.
     * @param b coefficient.
     * @return b*this, where * is coefficient multiplication.
     */
    public GenSolvablePolynomial<C> multiplyLeft(C b) {
        GenSolvablePolynomial<C> Cp = ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        Map<ExpVector, C> Cm = Cp.val; //getMap();
        Map<ExpVector, C> Am = val;
        for (Map.Entry<ExpVector, C> y : Am.entrySet()) {
            ExpVector e = y.getKey();
            C a = y.getValue();
            C c = b.multiply(a);
            if (!c.isZERO()) {
                Cm.put(e, c);
            }
        }
        return Cp;
    }


    /**
     * GenSolvablePolynomial multiplication. Left product with 'monomial'.
     * @param m 'monomial'.
     * @return m * this, where * denotes solvable multiplication.
     */
    public GenSolvablePolynomial<C> multiplyLeft(Map.Entry<ExpVector, C> m) {
        if (m == null) {
            return ring.getZERO();
        }
        return multiplyLeft(m.getValue(), m.getKey());
    }


    /**
     * GenSolvablePolynomial monic, i.e. leadingCoefficient == 1. If leadingCoefficient
     * is not invertible returns this unmodified.
     * @return monic(this).
     */
    public GenSolvablePolynomial<C> monic() {
        if (this.isZERO()) {
            return this;
        }
        C lc = leadingBaseCoefficient();
        //System.out.println("lc = "+lc);
        if (!lc.isUnit()) {
            return this;
        }
        try {
            C lm = lc.inverse();
            //System.out.println("lm = "+lm);
            return multiplyLeft(lm);
        } catch(NotInvertibleException e) {
            //e.printStackTrace();
        }
        return this;
    }

}
