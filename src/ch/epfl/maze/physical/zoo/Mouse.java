package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import java.util.Random;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */
public class Mouse extends Animal {

	/**
	 * Constructs a mouse with a starting position.
	 * 
	 * @param position
	 *            Starting position of the mouse in the labyrinth
	 */

	public Mouse(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to an improved version of a <i>random walk</i> : the
	 * mouse does not directly retrace its steps.
	 */

	@Override
	public Direction move(Direction[] choices) {

		Random random = new Random();

		if (choices.length == 1)
			setPrevious(choices[0]);

		if (choices.length > 1) {
			int r = random.nextInt(choices.length);
			while (choices[r].isOpposite(getPrevious())) {
				r = random.nextInt(choices.length);
			}
			setPrevious(choices[r]);
		}

		return getPrevious();
	}

	@Override
	public Animal copy() {
		return new Mouse(getPosition());
	}
}
