package ch.epfl.maze.physical.zoo;

import java.util.LinkedList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Bear A.I. that implements the Pledge Algorithm.
 * 
 */

public class Bear extends Animal {

	private final Direction prefered = Direction.DOWN;

	/**
	 * Constructs a bear with a starting position.
	 * 
	 * @param position
	 *            Starting position of the bear in the labyrinth
	 */
	private int compteur = 0;

	public Bear(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to the <i>Pledge Algorithm</i> : the bear tries to move
	 * towards a favorite direction until it hits a wall. In this case, it will
	 * turn right, put its paw on the left wall, count the number of times it
	 * turns right, and subtract to this the number of times it turns left. It
	 * will repeat the procedure when the counter comes to zero, or until it
	 * leaves the maze.
	 */

	@Override
	public Direction move(Direction[] choices) {

		LinkedList<Direction> choix = new LinkedList<>();
		LinkedList<Direction> directionViewBear = new LinkedList<>();

		for (Direction c : choices) {
			choix.add(c);
			directionViewBear.add(getPrevious().relativeDirection(c));
		}
		// si il a la possibilite d'emprunter sa direction preferee et que le
		// compteur est a 0, il la prend

		if (choix.contains(prefered) && compteur == 0) {
			setPrevious(prefered);
		}

		// si il ne peut pas prendre cette direction, il est face a un
		// mur, il adopte donc la methode du singe
		// on cree un tableau dans le ref de l ours

		// cependant il doit d abord se tourner a droite s il est a une
		// intersection, soit faire demi tour, pour que sa patte gauche touche
		// le mur
		else {
			if (compteur == 0 && directionViewBear.contains(Direction.RIGHT)) {
				setPrevious(getPrevious().unRelativeDirection(Direction.RIGHT));
				compteur++;
			} else if (compteur == 0 && directionViewBear.contains(Direction.DOWN)) {
				setPrevious(getPrevious().unRelativeDirection(Direction.DOWN));
				compteur += 2;
			} else if (compteur == 0 && directionViewBear.contains(Direction.LEFT)) {
				setPrevious(getPrevious().unRelativeDirection(Direction.LEFT));
				compteur += 3;
			} else {
				if (directionViewBear.contains(Direction.LEFT)) {
					--compteur;
					setPrevious(getPrevious().unRelativeDirection(Direction.LEFT));
				} else if (directionViewBear.contains(Direction.UP)) {
					setPrevious(getPrevious().unRelativeDirection(Direction.UP));
				} else if (directionViewBear.contains(Direction.RIGHT)) {
					++compteur;
					setPrevious(getPrevious().unRelativeDirection(Direction.RIGHT));
				} else {
					setPrevious(getPrevious().unRelativeDirection(Direction.DOWN));
					compteur += 2;
				}
			}
		}
		return getPrevious();
	}

	@Override
	public Animal copy() {
		return new Bear(getPosition());
	}
}
