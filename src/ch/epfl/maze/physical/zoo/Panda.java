package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import java.util.Random;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Panda A.I. that implements Tremeaux's Algorithm.
 * 
 */
public class Panda extends Animal {
	private ArrayList<Vector2D> markedOnce = new ArrayList<>();
	private ArrayList<Vector2D> markedTwice = new ArrayList<>();
	private Random random = new Random();

	/**
	 * Constructs a panda with a starting position.
	 * 
	 * @param position
	 *            Starting position of the panda in the labyrinth
	 */

	public Panda(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to <i>Tremeaux's Algorithm</i>: when the panda moves, it
	 * will mark the ground at most two times (with two different colors). It
	 * will prefer taking the least marked paths. Special cases have to be
	 * handled, especially when the panda is at an intersection.
	 * 
	 * @throws Exception
	 */
	/*
	 * the method is a bit long, but here is the plan : A. the panda marks the
	 * position or not B. the panda decides where it goes
	 */
	@Override

	public Direction move(Direction[] choices) {

		// those lists are list of direction to positions that are either marked
		// or not that are possible to choose (i.e. we put out walls etc.)
		LinkedList<Direction> notMarked = new LinkedList<>();
		LinkedList<Direction> marked1 = new LinkedList<>();
		LinkedList<Direction> marked2 = new LinkedList<>();
		// creation of the lists above
		for (Direction choice : choices) {
			if (notMarked(getPosition().add(choice.toVector()))) {
				notMarked.add(choice);
			}
			if (isMarked(getPosition().add(choice.toVector()))) {
				marked1.add(choice);
			}
			if (isMarkedTwice(getPosition().add(choice.toVector()))) {
				marked2.add(choice);
			}
		}

		// A.MARQUAGE :

		// 0. Si toutes les cases sont deja marquees deux fois, on ne verifie
		// pas : aucune importance, la fonction marquage ne
		// peut marquer plus de deux fois

		// 1. cas de l'intersection

		// le panda ne marque l'intersection que si c'est la derniere fois qu'il
		// y passe.
		// longueur = 3 ou 4 ET on est a une intersection deja marquee
		// On la marque seulement si la condition du troisieme if est remplie.

		if ((choices.length == 3 || choices.length == 4)) {
			// si l'intersection a deja ete visitee une fois on la marque
			// seulement a la condition suivante
			if (isMarked(getPosition())) {
				if (choices.length - marked2.size() == 1) {
					marquage(getPosition());
				}

			}
			// si l'intersection n'est pas marquee, on la marque tout simplement
			else {
				marquage(getPosition());
			}

		}

		// 2. cas de l'impasse
		// si le panda est dans une impasse, il marque directement la case deux
		// fois.
		if (choices.length == 1) {
			marquage(getPosition());
			marquage(getPosition());
		}

		// 3. cas du couloir ou du coin

		// dans un couloir ou un coin, le panda marque la position sans question
		if (choices.length == 2) {
			marquage(getPosition());
		}

		// 4. s'il n'y a aucun choix, le panda ne marque rien.

		// B.RETOUR DE LA DECISION

		int i = 0;

		// cas speciaux
		// si le panda est entoure de cases marquees une seule fois, il retourne
		// en arriere.

		if ((marked1.size() == 4 && choices.length == 4) || (marked1.size() == 3 && choices.length == 3)) {

			// il retourne en arriere seulement si il n'y a pas de cases
			// marquees deux fois

			if (marked2.isEmpty()) {
				setPrevious(getPrevious().reverse());
				return getPrevious();

			}

			// il choisit sinon un chemin parmi ceux marques une fois. Sauf
			// celui qui le fait revenir en arriere

			else {
				removePreviousReverse(marked1, getPrevious().reverse(), choices);

				if (choices.length == marked2.size()) {
					removePreviousReverse(marked2, getPrevious().reverse(), choices);
					i = random.nextInt(marked2.size());
					setPrevious(marked2.get(i));
					return getPrevious();
				}

				else {
					do {
						i = random.nextInt(marked1.size());
						setPrevious(marked1.get(i));
					} while (marked2.contains(getPrevious()));

					return getPrevious();
				}
			}

		}
		// on fonctionne par gradation pour le reste des cas, et le panda ne
		// retourne pas en arriere
		// 1. s'il y a des cases non marquees, il choisit au hasard parmis
		// celles-ci. Il ne retournera de toute façon pas
		// arriere, le chemin etant marque.

		if (!notMarked.isEmpty()) {
			i = random.nextInt(notMarked.size());
			setPrevious(notMarked.get(i));
			return notMarked.get(i);
		}

		// 2. s'il n'y a pas de cases non marquees le panda choisit parmis
		// celles marquees une fois seulement
		else if (!marked1.isEmpty() && marked2.size() != choices.length) {
			removePreviousReverse(marked1, getPrevious().reverse(), choices);

			do {
				i = random.nextInt(marked1.size());
				setPrevious(marked1.get(i));
			} while (marked2.contains(getPrevious()));

			return getPrevious();
		}

		// 3. s'il n'y a que des cases marquees deux fois, il choisit parmis
		// celles-la
		else if (!marked2.isEmpty()) {

			removePreviousReverse(marked2, getPrevious().reverse(), choices);

			i = random.nextInt(marked2.size());
			setPrevious(marked2.get(i));
			return marked2.get(i);
		}
		// le panda ne peut pas arriver sur un cas non-gere par les condition
		// au-dessus.
		else {
			return Direction.NONE;
		}
	}

	/*
	 * ajoute une "position" a une liste de marquage. on gere les cas speciaux
	 * de marquage plus haut.
	 */

	private void marquage(Vector2D position) {
		// note : la fonction marquage ne peut marquer plus de deux fois
		if (isMarked(position)) {
			markedTwice.add(position);
		} else if (notMarked(position)) {
			markedOnce.add(position);
		}

	}

	/*
	 * Supprime l'element "previous.reverse" de la "liste" si les choix
	 * possibles ne sont pas une impasse
	 */
	private void removePreviousReverse(List<Direction> liste, Direction previousReverse, Direction[] choices) {
		int j = liste.indexOf(previousReverse);
		if (j != -1 && choices.length != 1) {
			liste.remove(j);
		}
	}

	/*
	 * Savoir si la position est marquee une fois ou non
	 */
	private boolean isMarked(Vector2D position) {
		if (markedOnce.contains(position)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Savoir si la position est marquee deux fois ou non
	 */
	private boolean isMarkedTwice(Vector2D position) {
		if (markedTwice.contains(position)) {
			return true;
		}

		else {
			return false;
		}

	}

	/*
	 * Savoir si la position n'est marquee ou non
	 */
	private boolean notMarked(Vector2D position) {
		if (!markedOnce.contains(position) && !markedTwice.contains(position))
			return true;
		else
			return false;
	}

	@Override
	public Animal copy() {
		return new Panda(getPosition());
	}
}