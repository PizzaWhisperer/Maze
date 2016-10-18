package ch.epfl.maze.util;

/**
 * Directions that an animal can take to move. They represent the four cardinal
 * points ({@code DOWN, UP, RIGHT, LEFT}) from the frame of reference of the
 * labyrinth, plus a default one : {@code NONE}.
 * 
 */

public enum Direction {
	DOWN, UP, RIGHT, LEFT, NONE;

	/**
	 * Returns the integer value of the direction
	 * 
	 * @return Integer value of the direction
	 */

	public int intValue() {
		switch (this) {
		case DOWN:
			return 0;

		case UP:
			return 1;

		case RIGHT:
			return 2;

		case LEFT:
			return 3;

		case NONE:
		default:
			return 4;
		}
	}
	/**
	 * Converts the direction into an orthonormal vector, when possible.
	 * 
	 * @return Orthonormal {@code Vector2D} that represents the direction.
	 */

	public Vector2D toVector() {
		switch (this) {
		case DOWN:
			return new Vector2D(0, 1);

		case UP:
			return new Vector2D(0, -1);

		case RIGHT:
			return new Vector2D(1, 0);

		case LEFT:
			return new Vector2D(-1, 0);

		case NONE:
		default:
			return new Vector2D(0, 0);
		}
	}

	/**
	 * Reverses the direction.
	 * 
	 * @return The opposite direction.
	 */

	public Direction reverse() {
		switch (this) {
		case DOWN:
			return UP;

		case UP:
			return DOWN;

		case RIGHT:
			return LEFT;

		case LEFT:
			return RIGHT;

		case NONE:
		default:
			return NONE;
		}
	}

	/**
	 * Determines whether the argument is the opposite of another.
	 * 
	 * @param d
	 *            The direction to compare with
	 * @return <b>true</b> if the direction is the opposite the argument,
	 *         <b>false</b> otherwise
	 */

	public boolean isOpposite(Direction d) {
		return this == d.reverse();
	}

	/**
	 * Converts the argument relative to the frame of reference given by the
	 * direction that calls the method.
	 * 
	 * @param dir
	 *            The direction to convert
	 * @return The direction converted to the frame of reference given by the
	 *         direction called.
	 */

	public Direction relativeDirection(Direction dir) {
		switch (this) {
		case DOWN:
			return dir.reverse();

		case UP:
			return dir;

		case RIGHT:
			return dir.rotateLeft();

		case LEFT:
			return dir.rotateRight();

		case NONE:
		default:
			return NONE;
		}
	}

	/**
	 * Converts the argument back to the frame of reference of the labyrinth
	 * 
	 * @param dir
	 *            The direction to convert back
	 * @return The direction converted back to the frame of reference of the
	 *         labyrinth
	 */

	public Direction unRelativeDirection(Direction dir) {
		switch (this) {
		case DOWN:
			return dir.reverse();

		case UP:
			return dir;

		case RIGHT:
			return dir.rotateRight();

		case LEFT:
			return dir.rotateLeft();

		case NONE:
		default:
			return NONE;
		}
	}

	/**
	 * Rotates the direction to the right.
	 * 
	 * @return The rotated direction to the right
	 */

	public Direction rotateRight() {
		switch (this) {
		case DOWN:
			return LEFT;

		case UP:
			return RIGHT;

		case RIGHT:
			return DOWN;

		case LEFT:
			return UP;

		case NONE:
		default:
			return NONE;
		}
	}

	/**
	 * Rotates the direction to the left.
	 * 
	 * @return The rotated direction to the left
	 */

	public Direction rotateLeft() {
		switch (this) {
		case DOWN:
			return RIGHT;

		case UP:
			return LEFT;

		case RIGHT:
			return UP;

		case LEFT:
			return DOWN;

		case NONE:
		default:
			return NONE;
		}
	}

	/**
	 * Applies the change of coordinates relative to the frame of reference
	 * of the direction that calls the method to all the directions in the
	 * argument.
	 * 
	 * @param dir
	 *            The array of directions to convert
	 * @return The directions converted to the frame of reference given by the
	 *         direction which calls the method
	 */

	public Direction[] relativeDirections(Direction[] dir) {
		Direction[] relativeDirections = new Direction[dir.length];

		for (int i = 0; i < dir.length; i++) {
			relativeDirections[i] = this.relativeDirection(dir[i]);
		}

		return relativeDirections;
	}
}
