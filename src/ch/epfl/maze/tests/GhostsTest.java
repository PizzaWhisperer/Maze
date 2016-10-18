package ch.epfl.maze.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.maze.graphics.Display;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.physical.pacman.Blinky;
import ch.epfl.maze.physical.pacman.Clyde;
import ch.epfl.maze.physical.pacman.Inky;
import ch.epfl.maze.physical.pacman.Pinky;
import ch.epfl.maze.simulation.DaedalusSimulation;
import ch.epfl.maze.simulation.Simulation;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.LabyrinthGenerator;
import ch.epfl.maze.util.Vector2D;

/**
 * Test suite for ghosts implementation.
 * 
 */

public class GhostsTest extends TestCase {

	/**
	 * Tests the behavior of Blinky.
	 * <p>
	 * In this case, Blinky should go straight to the PacMan's position.
	 */

	@Test
	public void testBlinky() {
		int[][] labyrinth = LabyrinthGenerator.getDebugBlinky();

		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		d.addPredator(new Blinky(new Vector2D(6, 1)));
		d.addPrey(new PacMan(new Vector2D(1, 5), false));

		Display display = new Display(simulation);
		display.setDebug(false);
		display.run();
	}

	/**
	 * Tests the behavior of Pinky.
	 * <p>
	 * In this case, Pinky should go back and forth.
	 */

	@Test
	public void testPinky() {
		int[][] labyrinth = LabyrinthGenerator.getDebugPinky();

		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		d.addPredator(new Pinky(new Vector2D(1, 1)));
		d.addPrey(new PacMan(new Vector2D(6, 3), true));

		Display display = new Display(simulation);
		display.setDebug(false);
		display.run();
	}

	/**
	 * Tests the behavior of Inky.
	 * <p>
	 * In this case, Inky should target the red tile of the labyrinth.
	 */

	@Test
	public void testInky() {
		int[][] labyrinth = LabyrinthGenerator.getDebugInky();

		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		d.addPredator(new Inky(new Vector2D(9, 9)));
		d.addPredator(new Blinky(new Vector2D(7, 7)));
		d.addPrey(new PacMan(new Vector2D(5, 5), false));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behavior of Clyde.
	 * <p>
	 * In this case, Clyde should go back and forth.
	 */

	@Test
	public void testClyde() {
		int[][] labyrinth = LabyrinthGenerator.getDebugClyde();

		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		d.addPredator(new Clyde(new Vector2D(1, 3)));
		d.addPrey(new PacMan(new Vector2D(8, 3), false));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Mock class to create a dummy PacMan in our testing unit.
	 * 
	 */

	private class PacMan extends Prey {

		private boolean mMoves;

		/**
		 * Constructs a dummy PacMan that can move back and forth.
		 * 
		 * @param position
		 *            Starting position of PacMan in the labyrinth
		 * @param moves
		 *            Determines if the dummy PacMan will move back and forth
		 */

		public PacMan(Vector2D position, boolean moves) {
			super(position);
			mMoves = moves;
		}

		@Override
		public Direction move(Direction[] choices, Daedalus daedalus) {
			if (mMoves) {
				return getPosition().getX() % 2 == 0 ? Direction.RIGHT : Direction.LEFT;
			}
			return Direction.NONE;
		}

		@Override
		public Animal copy() {
			return new PacMan(getPosition(), mMoves);
		}
	}
}
