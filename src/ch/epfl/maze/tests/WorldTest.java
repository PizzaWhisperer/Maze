package ch.epfl.maze.tests;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.World;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Test case for {@code World} implementation.
 * 
 */

public class WorldTest extends TestCase {

	/* sample labyrinth */
	private static final int[][] LABYRINTH_SAMPLE = {
		{1, 1, 1, 1, 1, 3, 1},
		{1, 0, 0, 0, 0, 0, 1},
		{1, 2, 1, 1, 1, 1, 1}
	};

	/* constants labyrinth for testing getChoices(Vector2D) */
	private static final int[][] LABYRINTH_STUCK = {
		{1, 1, 1},
		{1, 0, 1},
		{1, 1, 1}
	};
	private static final int[][] LABYRINTH_CORRIDOR = {
		{1, 1, 1, 1, 1, 1},
		{1, 0, 1, 0, 0, 1},
		{1, 0, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1}
	};
	private static final int[][] LABYRINTH_DOGHNUT = {
		{1, 1, 1, 1, 1},
		{1, 0, 0, 0, 1},
		{1, 0, 1, 0, 1},
		{1, 0, 0, 0, 1},
		{1, 1, 1, 1, 1}
	};
	private static final int[][] LABYRINTH_SQUARE = {
		{1, 1, 1, 1, 1},
		{1, 0, 0, 0, 1},
		{1, 0, 0, 0, 1},
		{1, 0, 0, 0, 1},
		{1, 1, 1, 1, 1}
	};

	/**
	 * Test case for {@code getTile(int x, int y)}.
	 */

	@Test
	public void testGetTile() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks if positions are reversed
		assertTrue("You reversed the coordinates in your method !",
				world.getTile(5, 1) != World.NOTHING);

		// checks every position on the sample labyrinth
		assertEquals(World.WALL, world.getTile(0, 0));
		assertEquals(World.FREE, world.getTile(1, 1));
		assertEquals(World.START, world.getTile(1, 2));
		assertEquals(World.EXIT, world.getTile(5, 0));
		assertEquals(World.NOTHING, world.getTile(0, 3));
	}

	/**
	 * Test case for {@code isFree(int x, int y)}.
	 */

	@Test
	public void testIsFree() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks FREE, START and EXIT positions
		assertTrue("FREE tile should be free", world.isFree(3, 1));
		assertTrue("START tile should be free", world.isFree(1, 2));
		assertTrue("EXIT tile should be free", world.isFree(5, 0));

		// checks WALL and NOTHING positions
		assertFalse("WALL tile should NOT be free", world.isFree(0, 0));
		assertFalse("NOTHING tile should NOT be free", world.isFree(0, 3));
	}

	/**
	 * Test case for {@code getWidth()}.
	 */

	@Test
	public void testGetWidth() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks width
		assertEquals(7, world.getWidth());
	}

	/**
	 * Test case for {@code getHeight()}.
	 */

	@Test
	public void testGetHeight() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks height
		assertEquals(3, world.getHeight());
	}

	/**
	 * Test case for {@code getStart()}.
	 */

	@Test
	public void testGetStart() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks starting position
		assertEquals(new Vector2D(1, 2), world.getStart());
	}

	/**
	 * Test case for {@code getExit()}.
	 */

	@Test
	public void testGetExit() {
		World world = new ConcreteWorld(LABYRINTH_SAMPLE);

		// checks exiting position
		assertEquals(new Vector2D(5, 0), world.getExit());
	}

	// ==========================================================

	/**
	 * Test case for {@code getChoices(Vector2D position)}
	 * when there are 0 choices.
	 */

	@Test
	public void testZeroChoice() {
		World world = new ConcreteWorld(LABYRINTH_STUCK);
		Direction[] choices = world.getChoices(new Vector2D(1, 1));

		// checks that the only choice is not to move
		checkChoices(choices, new Direction[] { Direction.NONE });
	}

	/**
	 * Test case for {@code getChoices(Vector2D position)}
	 * when there is 1 choice.
	 */

	@Test
	public void testOneChoice() {
		World world = new ConcreteWorld(LABYRINTH_CORRIDOR);
		Direction[] choices;

		// checks when the only choice is to move down
		choices = world.getChoices(new Vector2D(1, 1));
		checkChoices(choices, new Direction[] { Direction.DOWN });

		// checks when the only choice is to move up
		choices = world.getChoices(new Vector2D(1, 2));
		checkChoices(choices, new Direction[] { Direction.UP });

		// checks when the only choice is to move right
		choices = world.getChoices(new Vector2D(3, 1));
		checkChoices(choices, new Direction[] { Direction.RIGHT });

		// checks when the only choice is to move left
		choices = world.getChoices(new Vector2D(4, 1));
		checkChoices(choices, new Direction[] { Direction.LEFT });
	}

	/**
	 * Test case for {@code getChoices(Vector2D position)}
	 * when there are 2 choices.
	 */

	@Test
	public void testTwoChoices() {
		World world = new ConcreteWorld(LABYRINTH_DOGHNUT);
		Direction[] choices;

		// checks when the available choices are to move down and right
		choices = world.getChoices(new Vector2D(1, 1));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.RIGHT });

		// checks when the available choices are to move down and up
		choices = world.getChoices(new Vector2D(1, 2));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.UP });

		// checks when the available choices are to move up and right
		choices = world.getChoices(new Vector2D(1, 3));
		checkChoices(choices,
				new Direction[] { Direction.UP, Direction.RIGHT });

		// checks when the available choices are to move right and left
		choices = world.getChoices(new Vector2D(2, 1));
		checkChoices(choices,
				new Direction[] { Direction.RIGHT, Direction.LEFT });

		// checks when the available choices are to move down and left
		choices = world.getChoices(new Vector2D(3, 1));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.LEFT });

		// checks when the available choices are to move up and left
		choices = world.getChoices(new Vector2D(3, 3));
		checkChoices(choices,
				new Direction[] { Direction.UP, Direction.LEFT });
	}

	/**
	 * Test case for {@code getChoices(Vector2D position)}
	 * when there are 3 choices.
	 */

	@Test
	public void testThreeChoices() {
		World world = new ConcreteWorld(LABYRINTH_SQUARE);
		Direction[] choices;

		// checks when the available choices are to move right, left, and down
		choices = world.getChoices(new Vector2D(2, 1));
		checkChoices(choices,
				new Direction[] { Direction.RIGHT, Direction.LEFT, Direction.DOWN });

		// checks when the available choices are to move right, left, and up
		choices = world.getChoices(new Vector2D(2, 3));
		checkChoices(choices,
				new Direction[] { Direction.RIGHT, Direction.LEFT, Direction.UP });

		// checks when the available choices are to move down, up, and right
		choices = world.getChoices(new Vector2D(1, 2));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.UP, Direction.RIGHT });

		// checks when the available choices are to move down, up, and left
		choices = world.getChoices(new Vector2D(3, 2));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.UP, Direction.LEFT });
	}

	/**
	 * Test case for {@code getChoices(Vector2D position)}
	 * when there are 4 choices.
	 */

	@Test
	public void testFourChoices() {
		World world = new ConcreteWorld(LABYRINTH_SQUARE);
		Direction[] choices;

		// checks when all the directions are available
		choices = world.getChoices(new Vector2D(2, 2));
		checkChoices(choices,
				new Direction[] { Direction.DOWN, Direction.UP, Direction.RIGHT, Direction.LEFT });
	}

	// ==========================================================

	/**
	 * Checks if there are the same choices in {@code result} as in {@code
	 * expected}
	 * 
	 * @param result
	 *            The choices computed with method {@code getChoices(position)}
	 * @param expected
	 *            The choices expected to be present
	 */

	private void checkChoices(Direction[] result, Direction[] expected) {
		// checks that array was initialized
		assertNotNull(result);

		// checks whether array has correct length and values
		Arrays.sort(result);
		Arrays.sort(expected);
		assertArrayEquals(expected, result);
	}

	/**
	 * Mock class that makes {@code World} concrete.
	 * 
	 */

	private final class ConcreteWorld extends World {

		/**
		 * Creates a concrete instance of the {@code World} class.
		 * 
		 * @param labyrinth
		 *            Actual maze
		 */

		public ConcreteWorld(int[][] labyrinth) {
			super(labyrinth);
		}

		@Override
		public boolean isSolved() {
			return false;
		}

		@Override
		public void reset() {
			// do nothing
		}

		@Override
		public List<Animal> getAnimals() {
			return null;
		}
	}
}
