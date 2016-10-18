package ch.epfl.maze.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.physical.pacman.Blinky;
import ch.epfl.maze.physical.pacman.PacMan;
import ch.epfl.maze.util.Vector2D;

/**
 * Test case for {@code Daedalus} implementation.
 * 
 */

public class DaedalusTest extends TestCase {

	private final static int[][] LABYRINTH = {
		{ 1, 1, 1, 1, 1 },
		{ 1, 0, 0, 0, 1 },
		{ 1, 1, 1, 1, 1 }
	};

	/**
	 * Test case for several methods in {@code Daedalus}.
	 */

	@Test
	public void testGeneral() {
		Daedalus daedalus = new Daedalus(LABYRINTH);

		// initial maze should be solved
		assertTrue("Initial maze should be solved", daedalus.isSolved());
		assertTrue("Initial maze should NOT have animals in it",
				daedalus.getAnimals().size() == 0);

		// adds dummy predator and prey
		Predator dummyPred = new Blinky(new Vector2D(3, 1));
		Prey dummyPrey = new PacMan(new Vector2D(3, 1));
		daedalus.addPredator(dummyPred);
		daedalus.addPrey(dummyPrey);

		assertTrue("Daedalus Predators should contain one Predator",
				daedalus.getPredators().size() == 1);
		assertTrue("Daedalus Preys should contain one Prey",
				daedalus.getPreys().size() == 1);
		assertTrue("Daedalus Animals should contain one Predator and one Prey",
				daedalus.getAnimals().size() == 2);

		// retrieves dummy predator and prey from Daedalus
		Predator retrievedPred = daedalus.getPredators().get(0);
		Prey retrievedPrey = daedalus.getPreys().get(0);

		assertTrue("Daedalus should contain the added Blinky",
				daedalus.hasPredator(dummyPred));
		assertTrue("Daedalus should contain the added PacMan",
				daedalus.hasPrey(dummyPrey));
		assertTrue("Predator inside the Daedalus should be the same as Predator added",
				retrievedPred == dummyPred);
		assertTrue("Prey inside the Daedalus should be the same as Prey added",
				retrievedPrey == dummyPrey);
		assertFalse("Daedalus with one Prey should NOT be solved",
				daedalus.isSolved());

		// removes dummy predator
		daedalus.removePredator(dummyPred);
		daedalus.removePrey(dummyPrey);

		assertFalse("Daedalus should NOT contain Blinky anymore",
				daedalus.hasPredator(dummyPred));
		assertFalse("Daedalus should NOT contain PacMan anymore",
				daedalus.hasPrey(dummyPrey));
		assertTrue("Daedalus should NOT have anymore Predator in it",
				daedalus.getPredators().size() == 0);
		assertTrue("Daedalus should NOT have anymore Prey in it",
				daedalus.getPreys().size() == 0);
		assertTrue("Daedalus should NOT have anymore Animal in it",
				daedalus.getAnimals().size() == 0);
		assertTrue("Daedalus without any animal should be solved", daedalus.isSolved());
	}

	/**
	 * Test case for {@code reset()}.
	 */

	@Test
	public void testReset() {
		Daedalus daedalus = new Daedalus(LABYRINTH);

		// adds dummy predator and prey
		Predator dummyPred = new Blinky(new Vector2D(3, 1));
		Prey dummyPrey = new PacMan(new Vector2D(3, 1));
		daedalus.addPredator(dummyPred);
		daedalus.addPrey(dummyPrey);

		// removes dummy animal
		daedalus.removePredator(dummyPred);
		daedalus.removePrey(dummyPrey);

		// checks reset method
		daedalus.reset();

		assertFalse("Daedalus should NOT be solved anymore",
				daedalus.isSolved());
		assertTrue("Daedalus should contain Blinky again",
				daedalus.getPredators().size() == 1);
		assertTrue("Daedalus should contain PacMan again",
				daedalus.getPreys().size() == 1);
		assertTrue("Daedalus should contain Blinky and PacMan again",
				daedalus.getAnimals().size() == 2);

		// checks that predator in maze is not null
		Animal retrievedPred = daedalus.getAnimals().get(0);
		Animal retrievedPrey = daedalus.getAnimals().get(1);

		assertTrue("Animals in Daedalus should be not null", retrievedPred != null);
		assertTrue("Animals in Daedalus should be not null", retrievedPrey != null);
	}
}
