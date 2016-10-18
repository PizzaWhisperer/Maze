package ch.epfl.maze.physical.pacman;

import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */

public class PacMan extends Prey {

	public PacMan(Vector2D position) {
		super(position);
		// TODO
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
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
		// TODO
		return null;
	}
}