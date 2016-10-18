package ch.epfl.maze.physical.zoo;

import java.util.LinkedList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */

public class Monkey extends Animal {

	/**
	 * Constructs a monkey with a starting position.
	 * 
	 * @param position
	 *            Starting position of the monkey in the labyrinth
	 */

	public Monkey(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to the relative left wall that the monkey has to follow.
	 */

	@Override
	public Direction move(Direction[] choices) {

		LinkedList<Direction> dirRef = new LinkedList<>();
		// on exprime les choices dans le ref du singe
		for (Direction choice : choices)
			dirRef.add(getPrevious().relativeDirection(choice));

		if (choices.length == 1)
			setPrevious(choices[0]);
		else {
			// On prend la decision conmpte tenu d une hierarchie des choix

			if (dirRef.contains(Direction.LEFT)) {
				// On exprime notre direction selon le ref du labyrinthe

				setPrevious(getPrevious().unRelativeDirection(Direction.LEFT));

			} else if (dirRef.contains(Direction.UP)) {
				setPrevious(getPrevious().unRelativeDirection(Direction.UP));

			} else if (dirRef.contains(Direction.RIGHT)) {
				setPrevious(getPrevious().unRelativeDirection(Direction.RIGHT));
			} else {
				setPrevious(getPrevious().unRelativeDirection(Direction.DOWN));
			}
		}

		return getPrevious();
	}

	@Override
	public Animal copy() {
		return new Monkey(getPosition());
	}
}