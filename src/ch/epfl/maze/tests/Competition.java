package ch.epfl.maze.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.maze.physical.Animal;
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

/**
 * Competition of the {@code SpaceInvader} against the other animals.
 * <p>
 * The rules are the following :
 * <ul>
 * <li>The {@code SpaceInvader} will be confronted to every animal at the same
 * time in one simulation.</li>
 * <li>The result will be the outcomes of 1,000 rounds.</li>
 * <li>If the {@code SpaceInvader} exits the maze earlier than one animal, it
 * will earn 1 point in the rivalry against it. If it exits later, then it will
 * lose 1 point in this rivalry. In case of draw, it will earn no points.</li>
 * <li>After the 1,000 simulations, the {@code SpaceInvader} will be declared
 * winner against one animal if it obtained a positive score in the rivalry
 * against it.</li>
 * </ul>
 *
 */

public class Competition {

	static final String COMPETITION_MAZE_FILE = "labyrinth.txt";
	static final int NUMBER_OF_ROUNDS = 1000;
	static HashMap<String, Integer> rivalries;

	/**
	 * Launches the competition between the {@code SpaceInvader} and the other
	 * animals.
	 */

	@BeforeClass
	public static void setUpClass() {
		int[][] labyrinth = LabyrinthGenerator.readFromFile(COMPETITION_MAZE_FILE);
		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		// adds a Mouse
		m.addAnimal(new Mouse(m.getStart()));

		// adds a Monkey
		m.addAnimal(new Monkey(m.getStart()));

		// adds a Hamster
		m.addAnimal(new Hamster(m.getStart()));

		// adds a Bear (if coded)
		// m.addAnimal(new Bear(m.getStart()));

		// adds a Panda
		m.addAnimal(new Panda(m.getStart()));

		// adds a Space Invader
		m.addAnimal(new SpaceInvader(m.getStart()));

		rivalries = new HashMap<String, Integer>();
		rivalries.put("Mouse", 0);
		rivalries.put("Hamster", 0);
		rivalries.put("Monkey", 0);
		// rivalries.put("Bear", 0); // if coded
		rivalries.put("Panda", 0);

		System.out.print("Launching competition, please wait... ");
		for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
			simulation.restart();
			while (!simulation.isOver()) {
				simulation.move(null);
			}

			int result = MazeSimulation.COUNTER_LIMIT;
			Map<Integer, List<Animal>> arrivalTimes = simulation.getArrivalTimes();
			for (Map.Entry<Integer, List<Animal>> entry : arrivalTimes.entrySet()) {
				for (Animal a : entry.getValue()) {
					if (a.getClass() == SpaceInvader.class) {
						result = entry.getKey();
					}
				}
			}

			for (Map.Entry<Integer, List<Animal>> entry : arrivalTimes.entrySet()) {
				for (Animal a : entry.getValue()) {
					String animalName = a.getClass().getSimpleName();
					if (!"SpaceInvader".equals(animalName)) {
						int score = rivalries.get(animalName);
						int adversary = entry.getKey();
						if (adversary < result) {
							rivalries.put(animalName, --score);
						} else if (adversary > result) {
							rivalries.put(animalName, ++score);
						}
					}
				}
			}
		}
		System.out.println("done !");
	}

	/**
	 * Determines if the {@code SpaceInvader} has beaten the {@code Mouse}.
	 */

	@Test
	public void testVSMouse() {
		assertTrue("The SpaceInvader has not beaten the Mouse", rivalries.get("Mouse") > 0);
	}

	/**
	 * Determines if the {@code SpaceInvader} has beaten the {@code Hamster}.
	 */

	@Test
	public void testVSHamster() {
		assertTrue("The SpaceInvader has not beaten the Hamster", rivalries.get("Hamster") > 0);
	}

	/**
	 * Determines if the {@code SpaceInvader} has beaten the {@code Monkey}.
	 */

	@Test
	public void testVSMonkey() {
		assertTrue("The SpaceInvader has not beaten the Monkey", rivalries.get("Monkey") > 0);
	}

	/**
	 * Determines if the {@code SpaceInvader} has beaten the {@code Bear}.
	 */

	@Test
	public void testVSBear() {
		assertTrue("The SpaceInvader has not beaten the Bear", rivalries.get("Bear") > 0);
	}

	/**
	 * Determines if the {@code SpaceInvader} has beaten the {@code Panda}.
	 */

	@Test
	public void testVSPanda() {
		assertTrue("The SpaceInvader has not beaten the Panda", rivalries.get("Panda") > 0);
	}
}
