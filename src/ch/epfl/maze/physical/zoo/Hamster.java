package ch.epfl.maze.physical.zoo;

import java.util.LinkedList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal {
	//On cree un tableau qui va nous servir a stocker les positions des impasses
	private LinkedList<Vector2D> Impasses = new LinkedList<>();

	/**
	 * Constructs a hamster with a starting position.
	 * 
	 * @param position
	 *            Starting position of the hamster in the labyrinth
	 */

	public Hamster(Vector2D position) {
		super(position);
	}

	/**
	 * Moves without retracing directly its steps and by avoiding the dead-ends
	 * it learns during its journey.
	 */

	@Override
	public Direction move(Direction[] choices) {
		Random random = new Random();

		//Si on a qu un seul choix alors c est une impasse
		if (choices.length == 1) {
			Impasses.add(getPosition());
			setPrevious(choices[0]);
		} else {
			//On construit une liste de direction en enlevant les directions menant a une impasse.
			LinkedList<Direction> ok = new LinkedList<>();
			for (Direction choice : choices)
				if (!(Impasses.contains(getPosition().add(choice.toVector()))) )
					ok.add(choice);
			if (ok.size() == 1) {
				//Si la taille du tableau est de 1 c est qu on se trouve dans un couloir menant a une impasse
				Impasses.add(getPosition());
				setPrevious(ok.get(0));
			}
			//Si la taille est > 1 on lance un random pour determiner une position sans revenir en arriere
			if (ok.size() > 1) {
				int r = random.nextInt(ok.size());
				while (ok.get(r).isOpposite(getPrevious())) {
					r = random.nextInt(ok.size());
				}
				setPrevious(ok.get(r));
			}
		}
		return getPrevious();
	}

	@Override
	public Animal copy() {
		return new Hamster(getPosition());
	}
}
