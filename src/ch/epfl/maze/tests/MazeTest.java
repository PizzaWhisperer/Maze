package ch.epfl.maze.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Maze;
import ch.epfl.maze.physical.zoo.Mouse;
import ch.epfl.maze.util.Vector2D;

/**
 * Test case for {@code Maze} implementation.
 * 
 */

public class MazeTest extends TestCase {

	private final static int[][] LABYRINTH = {
		{ 1, 1, 1, 3, 1 },
		{ 1, 0, 0, 0, 1 },
		{ 1, 2, 1, 1, 1 }
	};

	/**
	 * Test case for several methods in {@code Maze}.
	 */

	@Test
	public void testGeneral() {
		Maze maze = new Maze(LABYRINTH);

		// initial maze should be solved
		assertTrue("Initial maze should be solved", maze.isSolved());
		assertTrue("Initial maze should NOT have animals in it",
				maze.getAnimals().size() == 0);

		// adds dummy animal
		Animal dummy = new Mouse(new Vector2D(3, 0));
		maze.addAnimal(dummy);

		assertTrue("Maze Animals should contain one Animal",
				maze.getAnimals().size() == 1);

		// retrieves dummy anima from Maze
		Animal retrieved = maze.getAnimals().get(0);

		assertTrue("Maze should contain the Mouse, even at the exit",
				maze.hasAnimal(dummy));
		assertTrue("Mouse inside the Maze should be the same as Mouse added",
				retrieved == dummy);
		assertFalse("Maze with one Mouse even at the exit should NOT be solved",
				maze.isSolved());

		// removes dummy animal
		maze.removeAnimal(dummy);

		assertFalse("Maze should NOT contain the Mouse anymore",
				maze.hasAnimal(dummy));
		assertTrue("Maze should NOT have anymore Animal in it",
				maze.getAnimals().size() == 0);
		assertTrue("Maze without any animal should be solved", maze.isSolved());
	}

	/**
	 * Test case for {@code reset()}.
	 */

	@Test
	public void testReset() {
		Maze maze = new Maze(LABYRINTH);

		// adds dummy animal
		Animal dummy = new Mouse(new Vector2D(2, 2));
		maze.addAnimal(dummy);

		// removes dummy animal
		maze.removeAnimal(dummy);

		// checks reset method
		maze.reset();
		System.out.println(maze.getAnimals().size());

		assertFalse("Maze should NOT be solved anymore", maze.isSolved());
		assertTrue("Maze should contain the Mouse again", maze.getAnimals().size() == 1);

		// checks that animal in maze is not null
		Animal retrieved = maze.getAnimals().get(0);

		assertTrue("Animal in Maze should be a Mouse", retrieved != null);
	}
}
