package ch.epfl.maze.physical.zoo;

import java.util.LinkedList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Cibleur;
import ch.epfl.maze.physical.Maze;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Space Invader A.I. that implements an algorithm of your choice.
 * <p>
 * Note that this class is considered as a <i>bonus</i>, meaning that you do not
 * have to implement it (see statement: section 6, Extensions libres).
 * <p>
 * If you consider implementing it, you will have bonus points on your grade if
 * it can exit a simply-connected maze, plus additional points if it is more
 * efficient than the animals you had to implement.
 * <p>
 * The way we measure efficiency is made by the test case {@code Competition}.
 * 
 * @see ch.epfl.maze.tests.Competition Competition
 * 
 */

public class SpaceInvader extends Animal implements Cibleur {

	Hamster hamster;

	public SpaceInvader(Vector2D position) {
		super(position);
		hamster = new Hamster(position);
	}
	public SpaceInvader(Vector2D position, Hamster hamster){
		super(position);
		this.hamster = hamster;
	}

	/**
	 * Moves according to (... please complete with as many details as you can).
	 */

	@Override
	public Direction move(Direction[] choices) {
		hamster.setPosition(getPosition());
		setPrevious(hamster.move(choices));
		return getPrevious();

	}

	@Override
	public Animal copy() {
		return new SpaceInvader(getPosition(), hamster);
	}
}
