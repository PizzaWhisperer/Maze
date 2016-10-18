package ch.epfl.maze.physical;

import java.util.LinkedList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {

	private List<Prey> preys;
	private List<Predator> predators;
	private List<Prey> preyCopy;
	private List<Predator> predatorCopy;

	/**
	 * Constructs a Daedalus with a labyrinth structure
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 * @throws Exception
	 */

	public Daedalus(int[][] labyrinth) {
		super(labyrinth);
		preys = new LinkedList<Prey>();
		predators = new LinkedList<Predator>();
		preyCopy = new LinkedList<Prey>();
		predatorCopy = new LinkedList<Predator>();
	}

	@Override
	public boolean isSolved() {
		return (preys.size() == 0);
	}

	/**
	 * Adds a predator to the daedalus.
	 * 
	 * @param p
	 *            The predator to add
	 */

	public void addPredator(Predator p) {
		predators.add(p);
		predatorCopy.add(p);
	}

	/**
	 * Adds a prey to the daedalus.
	 * 
	 * @param p
	 *            The prey to add
	 */

	public void addPrey(Prey p) {
		preys.add(p);
		preyCopy.add(p);
	}

	/**
	 * Removes a predator from the daedalus.
	 * 
	 * @param p
	 *            The predator to remove
	 */

	public void removePredator(Predator p) {
		predators.remove(p);
	}

	/**
	 * Removes a prey from the daedalus.
	 * 
	 * @param p
	 *            The prey to remove
	 */

	public void removePrey(Prey p) {
		preys.remove(p);
	}

	@Override
	public List<Animal> getAnimals() {
		List<Animal> animaux = new LinkedList<Animal>();
		animaux.addAll(preys);
		animaux.addAll(predators);
		return animaux;
	}

	/**
	 * Returns a copy of the list of all current predators in the daedalus.
	 * 
	 * @return A list of all predators in the daedalus
	 */

	public List<Predator> getPredators() {
		return new LinkedList<Predator>(predators);
	}

	/**
	 * Returns a copy of the list of all current preys in the daedalus.
	 * 
	 * @return A list of all preys in the daedalus
	 */

	public List<Prey> getPreys() {
		return new LinkedList<Prey>(preys);
	}

	/**
	 * Determines if the daedalus contains a predator.
	 * 
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPredator(Predator p) {
		return predators.contains(p);
	}

	/**
	 * Determines if the daedalus contains a prey.
	 * 
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPrey(Prey p) {
		return preys.contains(p);
	}

	@Override
	public void reset() {
		preys.clear();
		preys.addAll(preyCopy);
		for(Prey prey : preys)
			prey.setPosition(prey.getHomePlace());
		predators.clear();
		predators.addAll(predatorCopy);
		for(Predator predator : predators)
			predator.setPosition(predator.getHomePlace());
	}
}
