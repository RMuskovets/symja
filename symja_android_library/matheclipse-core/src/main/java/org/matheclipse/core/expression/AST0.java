package org.matheclipse.core.expression;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * <p>
 * Immutable (A)bstract (S)yntax (T)ree of a given function with <b>no
 * argument</b>.
 * </p>
 * 
 * <p>
 * In Symja, an abstract syntax tree (AST), is a tree representation of the
 * abstract syntactic structure of the Symja source code. Each node of the tree
 * denotes a construct occurring in the source code. The syntax is 'abstract' in
 * the sense that it does not represent every detail that appears in the real
 * syntax. For instance, grouping parentheses are implicit in the tree
 * structure, and a syntactic construct such as a <code>Sin[x]</code> expression
 * will be denoted by an AST with 2 nodes. One node for the header
 * <code>Sin</code> and one node for the argument <code>x</code>.
 * </p>
 * 
 * Internally an AST is represented as a <code>java.util.List</code> which
 * contains
 * <ul>
 * <li>the operator of a function (i.e. the &quot;header&quot;-symbol: Sin, Cos,
 * Inverse, Plus, Times,...) at index <code>0</code> and</li>
 * <li>the <code>n</code> arguments of a function in the index
 * <code>1 to n</code></li>
 * </ul>
 * 
 * See <a href="http://en.wikipedia.org/wiki/Abstract_syntax_tree">Abstract
 * syntax tree</a>.
 * 
 * @see AST
 */
public class AST0 extends AbstractAST implements List<IExpr>, Cloneable, Externalizable, RandomAccess {

	private final static int SIZE = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5023978098877603499L;

	/**
	 * The head of this function.
	 */
	protected IExpr arg0;

	/**
	 * Constructs a new instance
	 */
	public AST0() {
	}

	/**
	 * Create a function with no arguments (i.e. <code>head[ ]</code>).
	 * 
	 * @param head
	 *            the head of the function
	 */
	protected AST0(IExpr head) {
		this.arg0 = head;
	}

	/**
	 * Get the first argument (i.e. the second element of the underlying list
	 * structure) of the <code>AST</code> function (i.e. get(1) ). <br />
	 * <b>Example:</b> for the AST representing the expression
	 * <code>Sin(x)</code>, <code>arg1()</code> returns <code>x</code>.
	 * 
	 * @return the first argument of the function represented by this
	 *         <code>AST</code>.
	 * @see IExpr#head()
	 */
	public IExpr arg1() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the second argument (i.e. the third element of the underlying list
	 * structure) of the <code>AST</code> function (i.e. get(2) ). <br />
	 * <b>Example:</b> for the AST representing the expression <code>x^y</code>
	 * (i.e. <code>Power(x, y)</code>), <code>arg2()</code> returns
	 * <code>y</code>.
	 * 
	 * @return the second argument of the function represented by this
	 *         <code>AST</code>.
	 * @see IExpr#head()
	 */
	public IExpr arg2() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the third argument (i.e. the fourth element of the underlying list
	 * structure) of the <code>AST</code> function (i.e. get(3) ).<br />
	 * <b>Example:</b> for the AST representing the expression
	 * <code>f(a, b, c)</code>, <code>arg3()</code> returns <code>c</code>.
	 * 
	 * @return the third argument of the function represented by this
	 *         <code>AST</code>.
	 * @see IExpr#head()
	 */
	public IExpr arg3() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the fourth argument (i.e. the fifth element of the underlying list
	 * structure) of the <code>AST</code> function (i.e. get(4) ).<br />
	 * <b>Example:</b> for the AST representing the expression
	 * <code>f(a, b ,c, d)</code>, <code>arg4()</code> returns <code>d</code>.
	 * 
	 * @return the fourth argument of the function represented by this
	 *         <code>AST</code>.
	 * @see IExpr#head()
	 */
	public IExpr arg4() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the fifth argument (i.e. the sixth element of the underlying list
	 * structure) of the <code>AST</code> function (i.e. get(5) ).<br />
	 * <b>Example:</b> for the AST representing the expression
	 * <code>f(a, b ,c, d, e)</code>, <code>arg5()</code> returns <code>e</code>
	 * .
	 * 
	 * @return the fifth argument of the function represented by this
	 *         <code>AST</code>.
	 * @see IExpr#head()
	 */
	public IExpr arg5() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<IExpr> asSet() {
		// empty set:
		return new HashSet<IExpr>();
	}

	/**
	 * Adds the specified object at the end of this {@code ArrayList}.
	 * 
	 * @param object
	 *            the object to add.
	 * @return always true
	 */
	@Override
	public boolean add(IExpr object) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Inserts the specified object into this {@code ArrayList} at the specified
	 * location. The object is inserted before any previous element at the
	 * specified location. If the location is equal to the size of this
	 * {@code ArrayList}, the object is added at the end.
	 * 
	 * @param location
	 *            the index at which to insert the object.
	 * @param object
	 *            the object to add.
	 * @throws IndexOutOfBoundsException
	 *             when {@code location < 0 || > size()}
	 */
	@Override
	public void add(int location, IExpr object) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds the objects in the specified collection to this {@code ArrayList}.
	 * 
	 * @param collection
	 *            the collection of objects.
	 * @return {@code true} if this {@code ArrayList} is modified, {@code false}
	 *         otherwise.
	 */
	@Override
	public boolean addAll(Collection<? extends IExpr> collection) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Inserts the objects in the specified collection at the specified location
	 * in this List. The objects are added in the order they are returned from
	 * the collection's iterator.
	 * 
	 * @param location
	 *            the index at which to insert.
	 * @param collection
	 *            the collection of objects.
	 * @return {@code true} if this {@code ArrayList} is modified, {@code false}
	 *         otherwise.
	 * @throws IndexOutOfBoundsException
	 *             when {@code location < 0 || > size()}
	 */
	@Override
	public boolean addAll(int location, Collection<? extends IExpr> collection) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(List<? extends IExpr> ast) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(List<? extends IExpr> ast, int startPosition, int endPosition) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IAST addOneIdentity(IAST subAST) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes all elements from this {@code ArrayList}, leaving it empty.
	 * 
	 * @see #isEmpty
	 * @see #size
	 */
	@Override
	public void clear() {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a new {@code HMArrayList} with the same elements, the same size
	 * and the same capacity as this {@code HMArrayList}.
	 * 
	 * @return a shallow copy of this {@code ArrayList}
	 * @see java.lang.Cloneable
	 */
	@Override
	public IAST clone() {
		return new AST(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public IAST copy() {
		return new AST0(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object object) {
		return arg0.equals(object);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof AbstractAST) {
			if (obj == this) {
				return true;
			}
			IAST list = (IAST) obj;
			if (list.size() != SIZE) {
				return false;
			}
			return arg0.equals(list.head());
		}
		return false;
	}

	@Override
	public IExpr get(int location) {
		if (location == 0) {
			return arg0;
		}
		throw new IndexOutOfBoundsException("Index: " + Integer.valueOf(location) + ", Size: 1");
	}

	@Override
	public int hashCode() {
		if (hashValue == 0) {
			hashValue = 391 + arg0.hashCode();
		}
		return hashValue;
	}

	@Override
	public final IExpr head() {
		return arg0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameHead(IExpr head) {
		return arg0.equals(head);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameHead(IExpr head, int length) {
		return arg0.equals(head) && length == SIZE;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameHead(IExpr head, int minLength, int maxLength) {
		return arg0.equals(head) && minLength <= SIZE && maxLength >= SIZE;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameHeadSizeGE(IExpr head, int length) {
		return arg0.equals(head) && length <= SIZE;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
		this.fEvalFlags = objectInput.readShort();

		int size;
		byte attributeFlags = objectInput.readByte();
		if (attributeFlags != 0) {
			size = attributeFlags;
			int exprIDSize = objectInput.readByte();
			for (int i = 0; i < exprIDSize; i++) {
				set(i, F.GLOBAL_IDS[objectInput.readShort()]);
			}
			for (int i = exprIDSize; i < size; i++) {
				set(i, (IExpr) objectInput.readObject());
			}
			return;
		}

		size = objectInput.readInt();
		for (int i = 0; i < size; i++) {
			set(i, (IExpr) objectInput.readObject());
		}
	}

	/**
	 * Removes the object at the specified location from this list.
	 * 
	 * @param location
	 *            the index of the object to remove.
	 * @return the removed object.
	 * @throws IndexOutOfBoundsException
	 *             when {@code location < 0 || >= size()}
	 */
	@Override
	public IExpr remove(int location) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object object) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the objects in the specified range from the start to the end, but
	 * not including the end index.
	 * 
	 * @param start
	 *            the index at which to start removing.
	 * @param end
	 *            the index one after the end of the range to remove.
	 * @throws IndexOutOfBoundsException
	 *             when {@code start < 0, start > end} or {@code end > size()}
	 */
	@Override
	protected void removeRange(int start, int end) {
		hashValue = 0;
		throw new UnsupportedOperationException();
	}

	/**
	 * Replaces the element at the specified location in this {@code ArrayList}
	 * with the specified object.
	 * 
	 * @param location
	 *            the index at which to put the specified object.
	 * @param object
	 *            the object to add.
	 * @return the previous element at the index.
	 * @throws IndexOutOfBoundsException
	 *             when {@code location < 0 || >= size()}
	 */
	@Override
	public IExpr set(int location, IExpr object) {
		hashValue = 0;
		if (location == 0) {
			IExpr result;
			result = arg0;
			arg0 = object;
			return result;
		}
		throw new IndexOutOfBoundsException("Index: " + Integer.valueOf(location) + ", Size: 1");
	}

	/**
	 * Returns the number of elements in this {@code ArrayList}.
	 * 
	 * @return the number of elements in this {@code ArrayList}.
	 */
	@Override
	public int size() {
		return SIZE;
	}

	/**
	 * Returns a new array containing all elements contained in this
	 * {@code ArrayList}.
	 * 
	 * @return an array of the elements from this {@code ArrayList}
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[SIZE];
		result[0] = arg0;
		return result;
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeShort(fEvalFlags);

		int size = size();
		byte attributeFlags = (byte) 0;

		ExprID temp = F.GLOBAL_IDS_MAP.get(head());
		if (temp != null) {
			short exprID = temp.getExprID();
			if (exprID <= Short.MAX_VALUE) {
				int exprIDSize = 1;
				short[] exprIDArray = new short[size];
				exprIDArray[0] = exprID;
				for (int i = 1; i < size; i++) {
					temp = F.GLOBAL_IDS_MAP.get(get(i));
					if (temp == null) {
						break;
					}
					exprID = temp.getExprID();
					if (exprID <= Short.MAX_VALUE) {
						exprIDArray[i] = exprID;
						exprIDSize++;
					} else {
						break;
					}
				}
				// optimized path
				attributeFlags = (byte) size;
				objectOutput.writeByte(attributeFlags);
				objectOutput.writeByte((byte) exprIDSize);
				for (int i = 0; i < exprIDSize; i++) {
					objectOutput.writeShort(exprIDArray[i]);
				}
				for (int i = exprIDSize; i < size; i++) {
					objectOutput.writeObject(get(i));
				}
				return;
			}
		}

		objectOutput.writeByte(attributeFlags);
		objectOutput.writeInt(size);
		for (int i = 0; i < size; i++) {
			objectOutput.writeObject(get(i));
		}
	}

	private Object writeReplace() throws ObjectStreamException {
		return optional(F.GLOBAL_IDS_MAP.get(this));
	}

}
