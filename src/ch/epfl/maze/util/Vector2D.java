package ch.epfl.maze.util;

/**
 * Immutable 2-dimensional vector (<i>x</i>, <i>y</i>).
 * 
 */

public final class Vector2D {

	/* shift constant to compute the hash */
	private static final int SHIFT = 1000;

	/* 2-dimension coordinates */
	private final int mX, mY;

	/**
	 * Constructs a 2-dimensional vector.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 */

	public Vector2D(int x, int y) {
		mX = x;
		mY = y;
	}

	/**
	 * Adds two coordinates to the vector.
	 * 
	 * @param x
	 *            Horizontal coordinate to add
	 * @param y
	 *            Vertical coordinate to add
	 * @return The result of an addition with two coordinates
	 */

	public Vector2D add(int x, int y) {
		return new Vector2D(mX + x, mY + y);
	}

	/**
	 * Adds a vector to the vector.
	 * 
	 * @param v
	 *            Vector to add
	 * @return The result of the addition with the vector
	 */

	public Vector2D add(Vector2D v) {
		return add(v.mX, v.mY);
	}

	/**
	 * Subtracts two coordinates to the vector.
	 * 
	 * @param x
	 *            Horizontal coordinate to subtract
	 * @param y
	 *            Vertical coordinate to subtract
	 * @return The result of the subtraction with the vector
	 */

	public Vector2D sub(int x, int y) {
		return new Vector2D(mX - x, mY - y);
	}

	/**
	 * Subtracts a vector to the vector.
	 * 
	 * @param v
	 *            Vector to subtract
	 * @return The result of the subtraction with the vector
	 */

	public Vector2D sub(Vector2D v) {
		return sub(v.mX, v.mY);
	}

	/**
	 * Negates the vector.
	 * 
	 * @return The negated version of the vector
	 */

	public Vector2D negate() {
		return new Vector2D(-mX, -mY);
	}

	/**
	 * Multiplies the coordinates of the vector by a scalar.
	 * 
	 * @param scalar
	 *            Number to multiply the coordinates with
	 * @return The result of the multiplication with a scalar
	 */

	public Vector2D mul(int scalar) {
		return new Vector2D(scalar * mX, scalar * mY);
	}

	/**
	 * Divides the coordinates of the vector by a scalar.
	 * 
	 * @param scalar
	 *            Number to divide the coordinates with
	 * @return The result of the division with a scalar
	 */

	public Vector2D div(int scalar) {
		return new Vector2D(scalar / mX, scalar / mY);
	}

	/**
	 * Normalizes the vector.
	 * 
	 * @return The normalized version of the vector
	 */

	public Vector2D normalize() {
		double dist = dist();
		return new Vector2D((int) (mX / dist), (int) (mY / dist));
	}

	/**
	 * The Euclidean distance of the vector.
	 * 
	 * @return The length of the vector
	 */

	public double dist() {
		return Math.sqrt(mX * mX + mY * mY);
	}

	/**
	 * Adds a direction to the vector
	 * 
	 * @param d
	 *            Direction to add
	 * @return The result of the addition with the direction
	 */

	public Vector2D addDirectionTo(Direction d) {
		switch (d) {
		case UP:
			return new Vector2D(mX, mY - 1);

		case DOWN:
			return new Vector2D(mX, mY + 1);

		case LEFT:
			return new Vector2D(mX - 1, mY);

		case RIGHT:
			return new Vector2D(mX + 1, mY);

		case NONE:
		default:
			return new Vector2D(mX, mY);
		}
	}

	/**
	 * Converts the vector to the closest corresponding direction.
	 * 
	 * @return The closest direction corresponding to the vector
	 */

	public Direction toDirection() {
		Vector2D normal = this.normalize();

		if (normal.mX == 0 && normal.mY == 1) {
			return Direction.DOWN;
		} else if (normal.mX == 0 && normal.mY == -1) {
			return Direction.UP;
		} else if (normal.mX == 1 && normal.mY == 0) {
			return Direction.RIGHT;
		} else if (normal.mX == -1 && normal.mY == 0) {
			return Direction.LEFT;
		} else {
			return Direction.NONE;
		}
	}

	/**
	 * Returns the horizontal coordinate of the vector.
	 * 
	 * @return x-coordinate of the vector
	 */

	public int getX() {
		return mX;
	}

	/**
	 * Returns the vertical coordinate of the vector.
	 * 
	 * @return y-coordinate of the vector
	 */

	public int getY() {
		return mY;
	}

	@Override
	public String toString() {
		return "(" + mX + ", " + mY + ")";
	}

	@Override
	public int hashCode() {
		return mX * SHIFT + mY;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}

		return o.hashCode() == this.hashCode();
	}
}
