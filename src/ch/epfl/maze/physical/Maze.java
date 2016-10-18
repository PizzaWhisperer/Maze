package ch.epfl.maze.physical;

import java.util.List;
import java.util.LinkedList;

/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */

public final class Maze extends World {

	/**
	 * Constructs a Maze with a labyrinth structure.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */
	private List<Animal> animaux = new LinkedList<Animal>();
	private List<Animal> animauxCopy = new LinkedList<Animal>();

	public Maze(int[][] labyrinth){
		super(labyrinth);
	}

	@Override
	public boolean isSolved() {
		return (animaux.size() == 0);
	}

	@Override
	public List<Animal> getAnimals() {
		return new LinkedList<Animal>(animaux);
	}

	/**
	 * Determines if the maze contains an animal.
	 * 
	 * @param a
	 *            The animal in question
	 * @return <b>true</b> if the animal belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasAnimal(Animal a) {
		return animaux.contains(a);
	}

	/**
	 * Adds an animal to the maze.
	 * 
	 * @param a
	 *            The animal to add
	 */

	public void addAnimal(Animal a) {
		a.setPosition(getStart());
		animaux.add(a);
		animauxCopy.add(a.copy());
	}

	/**
	 * Removes an animal from the maze.
	 * 
	 * @param a
	 *            The animal to remove
	 */

	public void removeAnimal(Animal a) {
		animaux.remove(a);
	}

	@Override
	public void reset() {
		animaux.clear();
		animaux.addAll(animauxCopy);
		for (Animal a : animaux) {
			a.setPosition(getStart());
		}
	}
}
