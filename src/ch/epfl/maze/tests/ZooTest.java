package ch.epfl.maze.tests;

import org.junit.Test;

import ch.epfl.maze.graphics.Display;
import ch.epfl.maze.physical.Maze;
import ch.epfl.maze.physical.zoo.Bear;
import ch.epfl.maze.physical.zoo.Hamster;
import ch.epfl.maze.physical.zoo.Monkey;
import ch.epfl.maze.physical.zoo.Mouse;
import ch.epfl.maze.physical.zoo.Panda;
import ch.epfl.maze.physical.zoo.SpaceInvader;
import ch.epfl.maze.simulation.MazeSimulation;
import ch.epfl.maze.simulation.Simulation;
import ch.epfl.maze.util.LabyrinthGenerator;
import junit.framework.TestCase;

/**
 * Test cases for animals implementation.
 * 
 */

public class ZooTest extends TestCase {

	/**
	 * Tests the behaviour of the Mouse A.I.
	 * <p>
	 * The Mouse should go forward and never retrace its steps.
	 */

	@Test
	public void testMouse() {
		int[][] labyrinth = LabyrinthGenerator.getDebugMouse();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Mouse(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Hamster A.I.
	 * <p>
	 * The Hamster should never revisit a dead-end it has already visited.
	 */

	@Test
	public void testHamster() {
		int[][] labyrinth = LabyrinthGenerator.getDebugHamster();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Hamster(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Monkey A.I.
	 * <p>
	 * The Monkey should go directly to the exit without taking any dead-end.
	 */

	@Test
	public void testMonkey() {
		int[][] labyrinth = LabyrinthGenerator.getDebugMonkey();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Monkey(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Bear A.I.
	 * <p>
	 * The Bear must follow the entire wall in front of him until the exit. If
	 * it loops infinitely in this maze, it means that it does not properly
	 * count the turns it makes.
	 */

	@Test
	public void testBear1() {
		int[][] labyrinth = LabyrinthGenerator.getDebugBear1();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Bear(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Bear A.I.
	 * <p>
	 * If the Bear loops infinitely in this maze, it means that it does not
	 * properly count the turns it makes.
	 */

	@Test
	public void testBear2() {
		int[][] labyrinth = LabyrinthGenerator.getDebugBear2();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Bear(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Panda A.I.
	 * <p>
	 * When the Panda comes back to the intersection, then it must leave the
	 * position marked once and go back into the loop.
	 */

	@Test
	public void testPanda1() {
		int[][] labyrinth = LabyrinthGenerator.getDebugPanda1();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Panda(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Panda A.I.
	 * <p>
	 * The Panda must mark the intersection twice only the very last time it
	 * walks on it.
	 */

	@Test
	public void testPanda2() {
		int[][] labyrinth = LabyrinthGenerator.getDebugPanda2();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Panda(m.getStart()));

		Display display = new Display(simulation);
		display.setDebug(true);
		display.run();
	}

	/**
	 * Tests the behaviour of the Bear A.I versus the Monkey A.I.
	 * <p>
	 * The Bear should leave the maze while the Monkey should loop infinitely.
	 */

	@Test
	public void testBearVsMonkey() {
		int[][] labyrinth = LabyrinthGenerator.getBearVsMonkey();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Bear(m.getStart()));
		m.addAnimal(new Monkey(m.getStart()));

		Display display = new Display(simulation);
		display.run();
	}

	/**
	 * Tests the behaviour of the Panda A.I versus the Hamster A.I.
	 * <p>
	 * The Panda should leave the maze earlier than the Hamster with a higher
	 * probability than the Hamster.
	 */

	@Test
	public void testPandaVsHamster() {
		int[][] labyrinth = LabyrinthGenerator.getPandaVsHamster();

		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		m.addAnimal(new Panda(m.getStart()));
		m.addAnimal(new Hamster(m.getStart()));

		Display display = new Display(simulation);
		display.run();
	}
}
