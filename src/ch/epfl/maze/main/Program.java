package ch.epfl.maze.main;

import ch.epfl.maze.graphics.Display;
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
import ch.epfl.maze.physical.zoo.SpaceInvader;
import ch.epfl.maze.simulation.DaedalusSimulation;
import ch.epfl.maze.simulation.MazeSimulation;
import ch.epfl.maze.simulation.Simulation;
import ch.epfl.maze.util.LabyrinthGenerator;
import ch.epfl.maze.util.Vector2D;

/**
 * Mini-project main program that will run the simulations on a {@code Display}.
 * 
 */

public class Program {

	/**
	 * Runs one of the two available simulations
	 * 
	 * @see #getMazeSimulation()
	 * @see #getDaedalusSimulation()
	 */

	public static void main(String[] args) {
		Simulation simulation;

		simulation = getMazeSimulation();
		//simulation = getDaedalusSimulation();

		Display display = new Display(simulation);
		display.run();
	}

	/**
	 * Creates a {@code MazeSimulation} with every animal implementations.
	 * 
	 * @return A {@code MazeSimulation} to display
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
		m.addAnimal(new Bear(m.getStart()));

		// adds a Panda
		m.addAnimal(new Panda(m.getStart()));
		
		//adds a SpaceInvader
		m.addAnimal(new SpaceInvader(m.getStart()));

		return simulation;
	}

	/**
	 * Creates a {@code DaedalusSimulation} with every ghost implementation and
	 * 3 Pac-Mans.
	 * 
	 * @return A {@code DaedalusSimulation} to display
	 */

	public static Simulation getDaedalusSimulation() {
		int[][] labyrinth = LabyrinthGenerator.getPacMan();
		Daedalus d = new Daedalus(labyrinth);
		Simulation simulation = new DaedalusSimulation(d);

		// adds Pac-Mans
		d.addPrey(new PacMan(new Vector2D(9, 15)));
		d.addPrey(new PacMan(new Vector2D(10, 15)));
		d.addPrey(new PacMan(new Vector2D(8, 15)));

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
}
