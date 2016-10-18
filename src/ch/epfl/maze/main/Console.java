package ch.epfl.maze.main;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Maze;
import ch.epfl.maze.physical.pacman.Blinky;
import ch.epfl.maze.physical.pacman.Clyde;
import ch.epfl.maze.physical.pacman.Inky;
import ch.epfl.maze.physical.pacman.PacMan;
import ch.epfl.maze.physical.pacman.Pinky;
import ch.epfl.maze.physical.zoo.Bear;
import ch.epfl.maze.physical.zoo.Hamster;
import ch.epfl.maze.physical.zoo.Monkey;
import ch.epfl.maze.physical.zoo.Mouse;
import ch.epfl.maze.physical.zoo.Panda;
import ch.epfl.maze.simulation.DaedalusSimulation;
import ch.epfl.maze.simulation.MazeSimulation;
import ch.epfl.maze.simulation.Simulation;
import ch.epfl.maze.util.LabyrinthGenerator;
import ch.epfl.maze.util.Statistics;
import ch.epfl.maze.util.Vector2D;

/**
 * Mini-project main program that will run the simulations multiple times and
 * show statistics on the console.
 * 
 */

public class Console {

	/** Number of simulations launched. */
	public static final int NUMBER_OF_SIMULATIONS = 1000;

	public static void main(String[] args) {
		Simulation simulation;

		simulation = getMazeSimulation();
		//simulation = getDaedalusSimulation();

		System.out.print("Launching " + NUMBER_OF_SIMULATIONS + " simulations...");
		Map<String, List<Integer>> results =
				Statistics.computeStatistics(simulation, NUMBER_OF_SIMULATIONS);
		System.out.println(" done !");

		printStats(results);
	}

	/**
	 * Creates a {@code MazeSimulation} suitable for statistics.
	 * <p>
	 * Note that there should be only <b>ONE</b> animal of each kind in the
	 * corresponding {@code Maze}.
	 * 
	 * @return A {@code MazeSimulation} suitable for statistics
	 */

	public static Simulation getMazeSimulation() {
		int[][] labyrinth = LabyrinthGenerator.getMedium();
		Maze m = new Maze(labyrinth);
		Simulation simulation = new MazeSimulation(m);

		// adds a Mouse
		m.addAnimal(new Mouse(m.getStart()));

		// adds a Monkey
		m.addAnimal(new Monkey(m.getStart()));

		// adds a Hamster
		m.addAnimal(new Hamster(m.getStart()));

		// adds a Bear (if this bonus is coded)
		// m.addAnimal(new Bear(m.getStart()));

		// adds a Panda
		m.addAnimal(new Panda(m.getStart()));

		return simulation;
	}

	/**
	 * Creates a {@code DaedalusSimulation} suitable for statistics.
	 * <p>
	 * Note that there should be only <b>ONE</b> animal of each kind in the
	 * corresponding {@code Daedalus}.
	 * 
	 * @return A {@code DaedalusSimulation} suitable for statistics
	 */

	public static Simulation getDaedalusSimulation() {
		int[][] labyrinth = LabyrinthGenerator.getPacMan();
		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		// adds Pac-Man
		d.addPrey(new PacMan(new Vector2D(9, 15)));

		// adds Blinky
		d.addPredator(new Blinky(new Vector2D(17, 1)));

		// adds Pinky
		d.addPredator(new Pinky(new Vector2D(1, 1)));

		// adds Inky
		d.addPredator(new Inky(new Vector2D(17, 17)));

		// adds Clyde
		d.addPredator(new Clyde(new Vector2D(1, 17)));

		return simulation;
	}

	/**
	 * Pretty-prints the statistics computed in the parameters.
	 * 
	 * @param results
	 *            Statistics of arrival times for every animals/preys
	 */

	public static void printStats(Map<String, List<Integer>> results) {
		// computes statistics
		for (Map.Entry<String, List<Integer>> entry : results.entrySet()) {
			String name = entry.getKey();
			List<Integer> list = entry.getValue();
			if (list.isEmpty()) {
				continue;
			}
			Collections.sort(list);

			String max, min, std, mean, median, total;
			// handles infinite values
			if (Statistics.total(list) == Integer.MAX_VALUE) {
				total = "Infinite";
				mean = "Infinite";
				std = "Infinite";
				max = "Infinite";
			} else {
				total = Integer.toString(Statistics.total(list));
				mean = Integer.toString(Statistics.mean(list));
				std = Double.toString(Statistics.std(list));
				max = Integer.toString(list.get(list.size() - 1));
			}
			// min and median are special
			min = (list.get(0) == Integer.MAX_VALUE) ?
					"Infinite" : Integer.toString(list.get(0));
			median = (list.get(list.size() / 2) == Integer.MAX_VALUE) ?
					"Infinite" : Integer.toString(list.get(list.size() / 2));

			System.out.println("\n\n========== " + name + " ==========\n");
			System.out.println(" * total number of steps : " + total);
			System.out.println(" * average steps : " + mean);
			System.out.println(" * median steps : " + median);
			System.out.println(" * standard deviation : " + std);
			System.out.println(" * minimum steps : " + min);
			System.out.println(" * maximum steps : " + max);
			System.out.println("\nDistribution :");
			Statistics.printDistribution(list);
		}
	}
}
