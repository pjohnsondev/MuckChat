package muck.core;

/**
   Triple is an immutable datastructure.
   Triple used as a generic container of three items.
 */
class Triple<L, M, R> {
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

}
