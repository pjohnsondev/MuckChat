package muck.core;

/**
 * Pair is an immutable datastructure. Pair used as a generic container of two
 * items.
 */
public class Pair<L, R> {
	private final L _left;
	private final R _right;

	public Pair() {
		_left = null;
		_right = null;
	}

	public Pair(final L l, final R r) {
		_left = l;
		_right = r;
	}

	@Override
	public int hashCode() {
		final int lprime = 31;
		final int rprime = 37;
		int result = 1;
		result = lprime * result + ((_left == null) ? 0 : _left.hashCode());
		result = rprime * result + ((_right == null) ? 0 : _right.hashCode());
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
		var other = (Pair) obj;
		if (_left == null) {
			if (other._left != null)
				return false;
		} else if (!_left.equals(other._left))
			return false;
		if (_right == null) {
			if (other._right != null)
				return false;
		} else if (!_right.equals(other._right))
			return false;
		return true;
	}

	/** @returns left side of pair */
	public L left() {
		return _left;
	}

	/** @returns right side of pair */
	public R right() {
		return _right;
	}

	/**
	 * @param right - The new right value
	 * @return A new pair with the old left value and a new right value
	 */
	public <NR> Pair<L, NR> updateRight(NR right) {
		return updateRight(this, right);
	}

	/**
	 * @param left - The new left value
	 * @return A new pair with the old right value and a new left value
	 */
	public <NL> Pair<NL, R> updateLeft(NL left) {
		return updateLeft(this, left);
	}

	/**
	 * Static method for updating pairs with a new left value.
	 *
	 * @param pair - The old pair parameterised by the types of the old left and old
	 *             right
	 * @param left - The new left value.
	 * @return New pair using old right value from the pair param and new, provided
	 *         left value
	 */
	public static <OL, OR, NL> Pair<NL, OR> updateLeft(Pair<OL, OR> pair, NL left) {
		return new Pair<NL, OR>(left, pair.right());
	}

	/**
	 * Static method for updating pairs with a new left value.
	 *
	 * @param pair - The old pair parameterised by the types of the old left and old
	 *             right
	 * @param left - The new right value.
	 * @return New pair using old left value from the pair param and new, provided
	 *         right value
	 */
	public static <OL, OR, NR> Pair<OL, NR> updateRight(Pair<OL, OR> pair, NR right) {
		return new Pair<OL, NR>(pair.left(), right);
	}
}
