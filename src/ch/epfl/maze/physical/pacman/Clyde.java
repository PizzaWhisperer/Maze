package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Orange ghost from the Pac-Man game, alternates between direct chase if far
 * from its target and SCATTER if close.
 * 
 */

public class Clyde extends Predator {
	/**
	 * Constructs a Clyde with a starting position.
	 * 
	 * @param position
	 *            Starting position of Clyde in the labyrinth
	 */

	public Clyde(Vector2D position) {
		super(position);
	}


	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		
		// 1. mode scatter
		if (scatter(getCompteurChase(), getCompteurScatter())){
			return decision(choices, getHomePlace());
		}
		
		// 2. mode Chase
		else {
			if (daedalus.getPreys().isEmpty()) {
				return move(choices);
			}
			else {
				Prey prey = daedalus.getPreys().get(0);
				if (temporaryScatter(prey)){
					return decision(choices, getHomePlace());
				}
				else {
					return decision(choices, prey.getPosition());
				}
			}

		}
	}

	@Override
	public Animal copy() {
		return new Clyde(getHomePlace());
	}



	private boolean temporaryScatter(Prey prey){
		double distance = 0;
		distance = (getPosition().sub(prey.getPosition())).dist();
		if (distance <= 4) return true;
		else return false;
	}


}
