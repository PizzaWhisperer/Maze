package ch.epfl.maze.simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.epfl.maze.graphics.Animation;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Maze;
import ch.epfl.maze.physical.World;
import ch.epfl.maze.util.Action;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Simulation of a maze solver. Handles the next move of each animal, as well as
 * the animation by notifying the changes to it. The simulation finishes when
 * every animal has found the exit.
 * 
 */

public final class MazeSimulation implements Simulation {

	/* limit to the step counter, over which the animals are considered lost */
	public static final int COUNTER_LIMIT = 10000;

	/* simulation components */
	private Maze mMaze;
	private Map<Integer, List<Animal>> mArrivalTimes;
	private int mStepCounter;

	/**
	 * Constructs a simulation with a {@code Maze} to simulate.
	 * 
	 * @param maze
	 *            The maze to simulate
	 */

	public MazeSimulation(Maze maze) {
		mMaze = maze;
		mArrivalTimes = new TreeMap<Integer, List<Animal>>();
		mStepCounter = 0;
	}

	@Override
	public void move(Animation listener) {
		if (isOver()) {
			return;
		}

		// increments counter
		mStepCounter++;

		// if counter exceeded limit, it considers animals lost
		if (mStepCounter > COUNTER_LIMIT) {
			List<Animal> animals = mMaze.getAnimals();
			List<Animal> lostAnimals = new LinkedList<Animal>();
			for (Animal animal : animals) {
				mMaze.removeAnimal(animal);
				lostAnimals.add(animal);
			}

			mArrivalTimes.put(Integer.MAX_VALUE, lostAnimals); // infinite
			return;
		}

		// asks animals to move
		moveAnimals(listener);

		// notifies animation that all the changes are done
		if (listener != null) {
			listener.doneUpdating();
		}
	}

	@Override
	public boolean isOver() {
		return mMaze.isSolved();
	}

	@Override
	public World getWorld() {
		return mMaze;
	}

	@Override
	public int getSteps() {
		return mStepCounter;
	}

	public Map<Integer, List<Animal>> getArrivalTimes() {
		return new TreeMap<Integer, List<Animal>>(mArrivalTimes);
	}

	public String getRecordTable() {
		String recordTable = "";
		int position = 1;
		for (Map.Entry<Integer, List<Animal>> entry : mArrivalTimes.entrySet()) {
			// only returns the 10 first
			if (position > 10) {
				return recordTable;
			}

			for (Animal animal : entry.getValue()) {
				if (entry.getKey() == Integer.MAX_VALUE) {
					recordTable += "-- ";
					recordTable += animal.getClass().getSimpleName();
					recordTable += " - never finished\n";
				} else {
					recordTable += position + ". ";
					recordTable += animal.getClass().getSimpleName();
					recordTable += " - " + entry.getKey() + " steps\n";
				}
			}
			position += entry.getValue().size();
		}

		return recordTable;
	}

	@Override
	public void restart() {
		mMaze.reset();
		mArrivalTimes.clear();
		mStepCounter = 0;
	}

	@Override
	public void stop() {
		List<Animal> forgottenAnimals = new LinkedList<Animal>();
		for (Animal animal : mMaze.getAnimals()) {
			forgottenAnimals.add(animal);
			mMaze.removeAnimal(animal);
		}
		mArrivalTimes.put(Integer.MAX_VALUE, forgottenAnimals);
	}

	/**
	 * Moves the animals in the maze.
	 * 
	 * @param listener
	 *            The listener to which the function will notify the changes
	 *            (can be null)
	 */

	private void moveAnimals(Animation listener) {
		List<Animal> animals = mMaze.getAnimals();
		for (int i = 0; i < animals.size(); i++) {
			Animal animal = animals.get(i);
			Vector2D position = animal.getPosition();
			Direction[] choices = mMaze.getChoices(position);

			// tries to make animal move
			Direction choice;
			try {
				choice = animal.move(choices);
				if (!animal.getPosition().equals(position)) {
					System.err.println("Error : Animal position changed while choosing direction.");
					System.err.println("\tDid you call setPosition(Vector2D) or update(Direction) ?\n");
					animal.setPosition(position);
					choice = null;
				}
			} catch (Exception E) {
				System.err.print("Exception occurred while moving animals: ");
				E.printStackTrace();
				choice = null;
			}

			// if animal could move
			if (choice != null) {
				Vector2D futurePosition = animal.getPosition();
				futurePosition = futurePosition.addDirectionTo(choice);

				int x = futurePosition.getX();
				int y = futurePosition.getY();

				
				if (mMaze.isFree(x, y)) {
					// asks animation to draw the action of the animal
					if (listener != null) {
						Action action = new Action(choice, true);
						listener.update(animal, i, action);
					}

					// if at the end of the maze
					if (mMaze.getTile(x, y) == World.EXIT) {
						mMaze.removeAnimal(animal);

						// records arrival time
						if (mArrivalTimes.get(mStepCounter) == null) {
							mArrivalTimes.put(mStepCounter, new LinkedList<Animal>());
						}
						mArrivalTimes.get(mStepCounter).add(animal);
					} else {
						animal.update(choice);
					}
				} else if (listener != null) {
					// asks animation to draw an interrupted movement
					Action action = new Action(choice, false);
					listener.update(animal, i, action);
				}

			} else if (listener != null) {
				// asks animation to draw a confused animal
				Action action = new Action(Direction.NONE, false);
				listener.update(animal, i, action);
			}
		}
	}
}
