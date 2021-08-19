package muck.core;

/**
 * Triple is an immutable datastructure. Triple used as a generic container of
 * three items.
 */
public class Triple<L, M, R> {
	private final L left;
	private final M middle;
	private final R right;

	public L left() {
		return left;
	}

	public M middle() {
		return middle;
	}

	public R right() {
		return right;
	}

	public Triple(final L left, final M middle, final R right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int lprime = 31;
		final int mprime = 37;
		final int rprime = 41;
		int result = 1;
		result = lprime * result + ((left == null) ? 0 : left.hashCode());
		result = mprime * result + ((middle == null) ? 0 : middle.hashCode());
		result = rprime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple other = (Triple) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (middle == null) {
			if (other.middle != null)
				return false;
		} else if (!middle.equals(other.middle))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	/**
	 * @param right - The new right value
	 * @return A new triple with the old left value and a new right value
	 */
	public <NR> Triple<L, M, NR> updateRight(NR right) {
		return updateRight(this, right);
	}

	/**
	 * @param left - The new left value
	 * @return A new triple with the old right value and a new left value
	 */
	public <NL> Triple<NL, M, R> updateLeft(NL left) {
		return updateLeft(this, left);
	}

	/**
	 * @param middle - The new middle value
	 * @return A new triple with the old right and left value, and the new middle
	 *         value
	 */
	public <NM> Triple<L, NM, R> updateMiddle(NM middle) {
		return updateMiddle(this, middle);
	}

	/**
	 * Static method for updating triples with a new left value.
	 *
	 * @param triple - The old triple parameterised by the types of the old left and
	 *               old right
	 * @param left   - The new left value.
	 * @return New triple using old right and middle values from the triple param
	 *         and new, provided left value
	 */
	public static <OL, OM, OR, NL> Triple<NL, OM, OR> updateLeft(Triple<OL, OM, OR> triple, NL left) {
		return new Triple<NL, OM, OR>(left, triple.middle(), triple.right());
	}

	/**
	 * Static method for updating triples with a new left value.
	 *
	 * @param triple - The old triple parameterised by the types of the old left and
	 *               old right
	 * @param left   - The new right value.
	 * @return New triple using old left and middle values from the triple param and
	 *         new, provided right value
	 */
	public static <OL, OM, OR, NR> Triple<OL, OM, NR> updateRight(Triple<OL, OM, OR> triple, NR right) {
		return new Triple<OL, OM, NR>(triple.left(), triple.middle(), right);
	}

	/**
	 * Static method for updating triples with a new left value.
	 *
	 * @param triple - The old triple parameterised by the types of the old left,
	 *               middle, right values
	 * @param middle - The new middle value.
	 * @return New triple using old left and right values from the triple param and
	 *         new, provided middle value
	 */
	public static <OL, OM, OR, NM> Triple<OL, NM, OR> updateMiddle(Triple<OL, OM, OR> triple, NM middle) {
		return new Triple<OL, NM, OR>(triple.left(), middle, triple.right());
	}
}
