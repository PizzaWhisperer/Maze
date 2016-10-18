package ch.epfl.maze.util;

/**
 * Immutable action that encapsulates a choice made by an animal and information
 * about it, such as if it was successful or not, and if the animal will die
 * <i>while</i> performing it.
 * 
 */

public final class Action {

	/* variables defining the action */
	private final Direction mDirection;
	private final boolean mSuccess;
	private final boolean mDies;

	/**
	 * Constructs a successful action of an animal towards a specified
	 * direction, that does not die between two squares.
	 * 
	 * @param dir
	 *            Direction towards which the action needs to be performed
	 */

	public Action(Direction dir) {
		if (dir != null) {
			mDirection = dir;
		} else {
			mDirection = Direction.NONE;
		}
		mSuccess = true;
		mDies = false;
	}

	/**
	 * Constructs an action of an animal towards a specified direction, with a
	 * specified success, that does not die between two squares.
	 * 
	 * @param dir
	 *            Direction towards which the action needs to be performed
	 * @param successful
	 *            Determines whether this action was successful
	 */

	public Action(Direction dir, boolean successful) {
		mDirection = dir;
		mSuccess = successful;
		mDies = false;
	}

	/**
	 * Constructs an action of an animal towards a specified direction, with a
	 * specified success, and that also specifies if the animal dies between two
	 * squares.
	 * 
	 * @param dir
	 *            Direction towards which the action needs to be performed
	 * @param successful
	 *            Determines whether this action was successful
	 * @param dies
	 *            Determines whether the action will die between two squares
	 */

	public Action(Direction dir, boolean successful, boolean dies) {
		mDirection = dir;
		mSuccess = successful;
		mDies = dies;
	}

	/**
	 * Retrieves the direction towards which the action shall move.
	 * 
	 * @return Direction of the action
	 */

	public Direction getDirection() {
		return mDirection;
	}

	/**
	 * Determines if the action was successful or not.
	 * 
	 * @return <b>true</b> if the action was successful, <b>false</b> otherwise
	 */

	public boolean isSuccessful() {
		return mSuccess;
	}

	/**
	 * Determines if the animal performing the action dies while moving from a
	 * square to another.
	 * 
	 * @return <b>true</b> if the action dies between two squares, <b>false</b>
	 *         otherwise
	 */

	public boolean diesBetweenSquares() {
		return mDies;
	}
}
