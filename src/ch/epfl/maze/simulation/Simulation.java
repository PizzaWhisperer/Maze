package ch.epfl.maze.simulation;

import java.util.List;
import java.util.Map;

import ch.epfl.maze.graphics.Animation;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.World;

/**
 * The {@code Simulation} interface defines a set of rules that must be
 * fulfilled in order to be displayed.
 * 
 */

public interface Simulation {

	/**
	 * Asks the {@code Simulation} to compute the next move and, if specified,
	 * notifies the changes to the listener.
	 * 
	 * @param listener
	 *            The listener to which the function will notify the changes
	 *            (can be null)
	 */

	public void move(Animation listener);

	/**
	 * Determines if the simulation is over.
	 * 
	 * @return <b>true</b> if no more moves can be made, <b>false</b> otherwise
	 */

	public boolean isOver();

	/**
	 * Retrieves the current state of the simulated world.
	 * 
	 * @return The {@code World} that is being simulated
	 */

	public World getWorld();

	/**
	 * Retrieves the step counter of the {@code Simulation}.
	 * 
	 * @return The current step counter
	 */

	public int getSteps();

	/**
	 * Retrieves the mapping of the steps done by the animals that have finished
	 * the simulation.
	 * 
	 * @return Map of steps done by animals which have accomplished the
	 *         simulation
	 */

	public Map<Integer, List<Animal>> getArrivalTimes();

	/**
	 * Retrieves the record table of the animals that have finished the
	 * simulation.
	 * 
	 * @return A {@code String} containing the top 10 animals which have
	 *         accomplished the simulation
	 */

	public String getRecordTable();

	/**
	 * Restarts the simulation from the beginning.
	 */

	public void restart();

	/**
	 * Stops abruptly the simulation.
	 */

	public void stop();
}
