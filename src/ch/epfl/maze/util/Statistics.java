package ch.epfl.maze.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.simulation.Simulation;

/**
 * Utility class that allows to compute statistics on a list of results.
 * 
 */

public final class Statistics {

	/* constants for the length of the distribution axis */
	public static final int X_LENGTH = 40;
	public static final int Y_LENGTH = 13;

	/**
	 * Returns the sum of all the numbers in results.
	 * 
	 * @param results
	 *            List of numbers
	 * @return The total of the list
	 */

	public static int total(List<Integer> results) {
		int total = 0;
		for (Integer result : results) {
			if (result == Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
			total += result;
		}
		return total;
	}

	/**
	 * Returns the mean of the numbers in results.
	 * <p>
	 * mean(<b>X</b>) = total(<b>X</b>) / N
	 * 
	 * @param results
	 *            List of numbers
	 * @return The mean of the results
	 */

	public static int mean(List<Integer> results) {
		int total = total(results);
		if (total == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return total / results.size();
	}

	/**
	 * Returns the variance of the numbers in results.
	 * <p>
	 * var(<b>X</b>) = (<b>X</b> - mean(<b>X</b>)) / N
	 * 
	 * @param results
	 *            List of numbers
	 * @return The variance of the results
	 */

	public static double var(List<Integer> results) {
		double mean = mean(results);
		if (mean == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		double var = 0;
		for (Integer result : results) {
			var += (result - mean) * (result - mean);
		}
		return var / results.size();
	}

	/**
	 * Returns the standard deviation of the numbers in results.
	 * <p>
	 * std(<b>X</b>) = sqrt(var(<b>X</b>))
	 * 
	 * @param results
	 *            List of numbers
	 * @return The variance of the results
	 */

	public static double std(List<Integer> results) {
		return Math.sqrt(var(results));
	}

	/**
	 * Computes distribution for each animal in simulation
	 * 
	 * @param simulation
	 *            Simulation to make statistics on
	 * @param numberOfSimulations
	 *            The number of simulations
	 */

	public static Map<String, List<Integer>> computeStatistics(
			Simulation simulation, int numberOfSimulations) {
		// maps animals' names with their overall results (which are linked-list)
		Map<String, List<Integer>> results = new TreeMap<String, List<Integer>>();

		for (Animal a : simulation.getWorld().getAnimals()) {
			results.put(a.getClass().getSimpleName(), new LinkedList<Integer>());
		}

		// simulates world a lot of times
		for (int i = 0; i < numberOfSimulations; i++) {

			// simulates world until the end
			simulation.restart();
			while (!simulation.isOver()) {
				simulation.move(null);
			}

			// retrieves arrival times and appends them to the results
			Map<Integer, List<Animal>> arrivalTimes = simulation.getArrivalTimes();
			for (Map.Entry<Integer, List<Animal>> entry : arrivalTimes.entrySet()) {
				for (Animal a : entry.getValue()) {
					String animalName = a.getClass().getSimpleName();
					List<Integer> list = results.get(animalName);
					list.add(entry.getKey());
				}
			}
		}

		return results;
	}

	/**
	 * Prints the distribution of all the results.
	 * 
	 * @param results
	 *            List of numbers
	 */

	public static void printDistribution(List<Integer> results) {

		int min = results.get(0);
		int max = results.get(results.size() - 1);
		int length = (max - min) / X_LENGTH;

		// counts number of steps inside a range
		int lowerBound = Integer.MIN_VALUE;
		int upperBound = min + length;
		int index = 0;
		List<Integer> boxPlot = new ArrayList<>();
		for (int i = 0; i < X_LENGTH; i++) {
			int counter = 0;

			while (index < results.size()
					&& (results.get(index) > lowerBound && results.get(index) <= upperBound)) {
				counter++;
				index++;
			}
			boxPlot.add(counter);
			lowerBound = upperBound;
			upperBound += length;
		}

		// draws plot on string
		String[] printPlot = new String[Y_LENGTH];
		for (int i = 0; i < Y_LENGTH; i++) {
			printPlot[i] = "| ";
		}

		int maxCount = Collections.max(boxPlot);
		for (Integer count : boxPlot) {
			for (int i = 0; i < Y_LENGTH; i++) {
				if (count > (i * maxCount) / Y_LENGTH) {
					printPlot[i] += "#";
				} else {
					printPlot[i] += " ";
				}
			}
		}

		// prints plot
		System.out.println("\n^");
		for (int i = Y_LENGTH - 1; i > 0; i--) {
			System.out.println(printPlot[i]);
		}
		System.out.print("--");
		for (int i = 0; i < X_LENGTH; i++) {
			System.out.print("-");
		}
		System.out.println(">");
	}
}
