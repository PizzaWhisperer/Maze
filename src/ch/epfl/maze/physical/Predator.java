package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.util.Vector2D;

import java.util.List;
import java.util.Random;
import ch.epfl.maze.physical.Prey;

/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */

abstract public class Predator extends Animal implements Cibleur{

	/* constants relative to the Pac-Man game */
	public static final int SCATTER_DURATION = 14;
	public static final int CHASE_DURATION = 40;
	private Vector2D homePlace;
	private int compteurChase;
	private int compteurScatter;

	/**
	 * Constructs a predator with a specified position.
	 * 
	 * @param position
	 *            Position of the predator in the labyrinth
	 */

	public Predator(Vector2D position) {
		super(position);
		homePlace = position;
		compteurChase = 0;
		compteurScatter = -1;
	}

	protected int getCompteurChase() {
		return compteurChase;
	}

	protected void setCompteurChase(int compteurChase) {
		this.compteurChase = compteurChase;
	}

	protected int getCompteurScatter() {
		return compteurScatter;
	}

	protected void setCompteurScatter(int compteurScatter) {
		this.compteurScatter = compteurScatter;
	}

	protected boolean scatter(int compteurChase, int compteurScatter) {
		if (compteurChase < CHASE_DURATION && compteurChase != -1) {
			setCompteurChase(getCompteurChase() + 1);
			return false;
		} else if (compteurChase == CHASE_DURATION) {
			setCompteurChase(-1);
			setCompteurScatter(1);
			return true;
		} else if (compteurScatter < SCATTER_DURATION && compteurScatter != -1) {
			setCompteurScatter(getCompteurScatter() + 1);
			return true;
		} else if (compteurScatter == SCATTER_DURATION) {
			setCompteurScatter(-1);
			setCompteurChase(1);
			return false;
		} else
			return true;
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not hunting in a
	 * {@code MazeSimulation}.
	 * 
	 */

	@Override
	public final Direction move(Direction[] choices) {
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

	/*
	 * @param list of prey
	 * 
	 * @return a prey at random
	 */
	protected Prey pickAPrey(List<Prey> liste) {
		return liste.get(0);
	}

	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to hunt more
	 * effectively.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)}) @param daedalus The world in
	 *            which the animal moves @return The next direction of the
	 *            animal, chosen in {@code
	 * choices} @throws
	 */

	abstract public Direction move(Direction[] choices, Daedalus daedalus);


	/*
	 * La methode gere tous les cas selon la longueur de choices. Ensuite, elle choisit le meilleur chemin
	 * cad le plus court jusqu'à sa case cible, sans revenir en arriere. 
	 * 
	 * Note : dans le cas ou il n y a pas de choix, l'element de choices est NONE et donc le tableau de longueur 1. 
	 * 
	 * @param choices : choix de directions
	 * @param caseCible : case a cibler
	 * @return a Direction. 
	 */

	protected Direction decision(Direction[] choices, Vector2D caseCible){
		switch(choices.length){
		case 1 : 
			if (choices[0].equals(Direction.NONE)) return Direction.NONE;
			else{
				setPrevious(getPrevious().reverse());
				return getPrevious();
			}
			
		case 2 : 
			if (choices[0] == getPrevious().reverse()) 
				setPrevious(choices[1]);
			else 
				setPrevious(choices[0]);
			return getPrevious();
		case 3 : 
		case 4 : 
			setPrevious(bestWayTo(caseCible, choices, getPrevious(), getPosition()));
			return getPrevious();

		default : return Direction.NONE;
		}
	}
	
	protected Vector2D getHomePlace() {
		return homePlace;
	}
}