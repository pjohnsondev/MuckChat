package muck.core;

/**
 * Pair is an immutable datastructure. Pair used as a generic container of two
 * items.
 */
public class Pair<L,R> {
	private final L _left;
	private final R _right;

    public Pair(final L l, final R r) {
		_left = l;
		_right = r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_left == null) ? 0 : _left.hashCode());
		result = prime * result + ((_right == null) ? 0 : _right.hashCode());
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

	public L left() {
		return _left;
	}

	public R right() {
		return _right;
	}
}
