package ch.epfl.maze.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Test case for {@code Animal} implementation.
 * 
 */

public class AnimalTest extends TestCase {

	/**
	 * Test case for {@code getPosition()}.
	 */

	@Test
	public void testGetPosition() {
		Animal animal = new MockAnimal(new Vector2D(2, 1));

		// checks getPosition()
		assertEquals(new Vector2D(2, 1), animal.getPosition());
	}

	/**
	 * Test case for {@code setPosition(Vector2D position)}.
	 */

	@Test
	public void testSetPosition() {
		Animal animal = new MockAnimal(new Vector2D(2, 1));

		// checks setPosition(Vector2D position)
		animal.setPosition(new Vector2D(3, 5));
		assertEquals(new Vector2D(3, 5), animal.getPosition());
	}

	/**
	 * Test case for {@code update(Direction dir)}.
	 */

	@Test
	public void testUpdate() {
		Animal animal = new MockAnimal(new Vector2D(2, 1));

		// checks update(Direction dir) with NONE
		animal.update(Direction.NONE);
		assertEquals(new Vector2D(2, 1), animal.getPosition());

		// checks update(Direction dir) with DOWN
		animal.update(Direction.DOWN);
		assertEquals(new Vector2D(2, 2), animal.getPosition());

		// checks update(Direction dir) with UP
		animal.update(Direction.UP);
		assertEquals(new Vector2D(2, 1), animal.getPosition());

		// checks update(Direction dir) with RIGHT
		animal.update(Direction.RIGHT);
		assertEquals(new Vector2D(3, 1), animal.getPosition());

		// checks update(Direction dir) with LEFT
		animal.update(Direction.LEFT);
		assertEquals(new Vector2D(2, 1), animal.getPosition());
	}

	/**
	 * Mock class that makes {@code Animal} concrete.
	 * 
	 */

	private class MockAnimal extends Animal {

		/**
		 * Creates a concrete instance of the {@code Animal} class.
		 * 
		 * @param labyrinth
		 *            Actual maze
		 */

		public MockAnimal(Vector2D position) {
			super(position);
		}

		@Override
		public Direction move(Direction[] choices) {
			return null;
		}

		@Override
		public Animal copy() {
			return null;
		}
	}
}
