package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
import java.util.LinkedList;

/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */

public class Pinky extends Predator {
	private LinkedList<Vector2D> preysPosition;

	/**
	 * Constructs a Pinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Pinky in the labyrinth
	 */

	public Pinky(Vector2D position) {
		super(position);
		preysPosition = new LinkedList<>();
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		if (scatter(getCompteurChase(), getCompteurScatter())){
			setPrevious(bestWayTo(getHomePlace(), choices, getPrevious(), getPosition()));
			return getPrevious();
		}
		else {

			if (daedalus.getPreys().isEmpty()) {
				setPrevious(move(choices));
				return getPrevious();
			} else {
				Prey prey = pickAPrey(daedalus.getPreys());
				preysPosition.add(prey.getPosition());
				LinkedList<Direction> directions = new LinkedList<>();
				for (Direction t : choices) {
					directions.add(t);
				}
				// décision
				if (directions.size() == 0) return Direction.NONE;
				// impasse
				if (directions.size() == 1) {
					// on met a jour la liste pour éviter une place en mémoire inutile ici
					if (preysPosition.size() >= 2) {
						preysPosition.removeFirst();
					}
					// retour de la décision
					setPrevious(directions.get(0));
					return directions.get(0);
					
				// couloir : on avance dans la direction qui ne nous fait pas reculer
				} else if (directions.size() == 2) {
					// on met a jour la liste pour éviter une place en mémoire inutile ici
					if (preysPosition.size() >= 2) {
						preysPosition.removeFirst();
					}
					directions.remove(getPrevious().reverse());
					setPrevious(directions.getFirst());
					return directions.getFirst();
					
					
				// aux intersections, on applique le choix. 
				} else {
					Direction nextDir = bestWayTo(caseCible(prey), choices, getPrevious(), getPosition());
					setPrevious(nextDir);
					// on met a jour la liste pour éviter une place en mémoire inutile ici
					if (preysPosition.size() >= 2) {
						preysPosition.removeFirst();
					}
					return nextDir;
				}
			}
		}
	}

	@Override
	public Animal copy() {
		return new Pinky(getHomePlace());
	}

	/*
	 * method to obtain the position of the tile we want to go for Pinky
	 */

	public Vector2D caseCible(Prey prey) {
		Direction preyDirection;

		Vector2D preyPosition = prey.getPosition();

		// calculate the previous direction of the pray
		
		// if  2 steps are not done yet, pinky targets 4 tiles right to PacMan.
		if (preysPosition.size() <= 1) {
			return preyPosition.add(Direction.RIGHT.toVector().mul(4));
		} else {
			int i = preysPosition.size() - 1;
			preyDirection = (preysPosition.get(i).sub(preysPosition.get(i - 1))).toDirection();
			return prey.getPosition().add((preyDirection.toVector().mul(4)));
		}
	}
}