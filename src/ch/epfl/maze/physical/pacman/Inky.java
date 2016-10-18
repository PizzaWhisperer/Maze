package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */

public class Inky extends Predator {

	/**
	 * Constructs a Inky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Inky in the labyrinth
	 */

	public Inky(Vector2D position) {
		super(position);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		if (scatter(getCompteurChase(), getCompteurScatter())) {
			return decision(choices, getHomePlace());
		} 
		else
		{
			if (daedalus.getPreys().isEmpty()) {
				setPrevious(move(choices));
			} 
			else {
				Prey prey = pickAPrey(daedalus.getPreys());
				Vector2D blinkyPos = null;
				for (Predator predator : daedalus.getPredators()) {
					if (predator instanceof Blinky)
						blinkyPos = predator.getPosition();
				}
				if (blinkyPos == null)
					return Direction.NONE;
				else {
					return decision(choices, caseCible(blinkyPos, prey));
				}
			}
			return getPrevious();
		}
	}

	@Override
	public Animal copy() {
		return new Inky(getHomePlace());
	}

	public Vector2D caseCible(Vector2D blinkyPos, Prey prey) {
		return prey.getPosition().mul(2).sub(blinkyPos);
	}
}
