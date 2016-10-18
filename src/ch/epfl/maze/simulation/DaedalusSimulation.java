package ch.epfl.maze.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.epfl.maze.graphics.Animation;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.physical.World;
import ch.epfl.maze.util.Action;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Simulation of a predation environment. Handles the next moves of every
 * predator and prey in a Daedalus, as well as the animation by notifying
 * changes to it. The simulation finishes when every prey has been caught.
 * 
 */

public final class DaedalusSimulation implements Simulation {

	/* limit to the step counter, over which the animals are considered lost */
	public static final int COUNTER_LIMIT = 10000;

	/* simulation components */
	private Daedalus mDaedalus;
	private Map<Integer, List<Prey>> mArrivalTimes;
	private int mStepCounter;

	/* collision check variables */
	private Map<Prey, List<Vector2D>> mPreyMoves;
	private Map<Predator, List<Vector2D>> mPredatorMoves;

	/**
	 * Constructs a simulation with a {@code Daedalus} to simulate.
	 * 
	 * @param daedalus
	 *            The daedalus to simulate
	 */

	public DaedalusSimulation(Daedalus daedalus) {
		mDaedalus = daedalus;
		mArrivalTimes = new TreeMap<Integer, List<Prey>>(Collections.reverseOrder());
		mStepCounter = 0;
		mPreyMoves = new HashMap<Prey, List<Vector2D>>();
		mPredatorMoves = new HashMap<Predator, List<Vector2D>>();
	}

	@Override
	public void move(Animation listener) {
		if (isOver()) {
			return;
		}

		// clears moves maps
		mPreyMoves.clear();
		mPredatorMoves.clear();

		// increments counter
		mStepCounter++;

		// if counter exceeded the limit, it considers preys safe
		if (mStepCounter > COUNTER_LIMIT) {
			List<Prey> preys = mDaedalus.getPreys();
			List<Prey> safePreys = new LinkedList<Prey>();
			for (Prey prey : preys) {
				mDaedalus.removePrey(prey);
				safePreys.add(prey);
			}

			mArrivalTimes.put(Integer.MAX_VALUE, safePreys); // infinite
			return;
		}

		// asks predators and preys to move
		movePredators(listener);
		movePreys(listener);

		// checks collisions
		checkCollisions(listener);

		// notifies animation that all the changes are done
		if (listener != null) {
			listener.doneUpdating();
		}
	}

	@Override
	public boolean isOver() {
		return mDaedalus.isSolved();
	}

	@Override
	public World getWorld() {
		return mDaedalus;
	}

	@Override
	public int getSteps() {
		return mStepCounter;
	}

	@Override
	public Map<Integer, List<Animal>> getArrivalTimes() {
		TreeMap<Integer, List<Animal>> arrivalTimes = new TreeMap<Integer, List<Animal>>();
		for (Map.Entry<Integer, List<Prey>> entry : mArrivalTimes.entrySet()) {
			int time = entry.getKey();
			List<Animal> animals = new ArrayList<Animal>(entry.getValue());
			arrivalTimes.put(time, animals);
		}

		return arrivalTimes;
	}

	@Override
	public String getRecordTable() {
		String recordTable = "";
		int position = 1;
		for (Map.Entry<Integer, List<Prey>> entry : mArrivalTimes.entrySet()) {
			// only returns the 10 first
			if (position > 10) {
				return recordTable;
			}

			for (Prey prey : entry.getValue()) {
				if (entry.getKey() == Integer.MIN_VALUE) {
					recordTable += "-- ";
					recordTable += prey.getClass().getSimpleName();
					recordTable += " - never finished\n";
				} else {
					recordTable += position + ". ";
					recordTable += prey.getClass().getSimpleName();
					if (entry.getKey() == Integer.MAX_VALUE) {
						recordTable += " - has survived\n";
					} else {
						recordTable += " - " + entry.getKey() + " steps\n";
					}
				}
			}
			position += entry.getValue().size();
		}

		return recordTable;
	}

	@Override
	public void restart() {
		mDaedalus.reset();
		mArrivalTimes.clear();
		mStepCounter = 0;
	}

	@Override
	public void stop() {
		List<Prey> forgottenPreys = new LinkedList<Prey>();
		for (Prey prey : mDaedalus.getPreys()) {
			forgottenPreys.add(prey);
			mDaedalus.removePrey(prey);
		}
		mArrivalTimes.put(Integer.MIN_VALUE, forgottenPreys);
	}

	/**
	 * Moves the predators in the daedalus.
	 * 
	 * @param listener
	 *            The listener to which the function will notify the changes
	 *            (can be null)
	 */

	private void movePredators(Animation listener) {
		List<Predator> predators = mDaedalus.getPredators();
		for (int i = 0; i < predators.size(); i++) {
			Predator predator = predators.get(i);
			Vector2D position = predator.getPosition();
			Vector2D newPosition = position;
			Direction[] choices = mDaedalus.getChoices(position);

			// tries to make predator move
			Direction choice;
			try {
				choice = predator.move(choices, mDaedalus);
				if (!predator.getPosition().equals(position)) {
					System.err.println("Error : Predator position changed while choosing direction.");
					System.err.println("\tDid you call setPosition(Vector2D) or update(Direction) ?\n");
					predator.setPosition(position);
					choice = null;
				}
			} catch (Exception E) {
				System.err.print("Exception occurred while moving animals: ");
				E.printStackTrace();
				choice = null;
			}

			// if predator could move
			Action action;
			if (choice != null) {
				newPosition = position.addDirectionTo(choice);

				int x = newPosition.getX();
				int y = newPosition.getY();

				if (mDaedalus.isFree(x, y)) {
					action = new Action(choice, true);
				} else {
					newPosition = position;
					action = new Action(choice, false);
					choice = Direction.NONE;
				}

				if (listener != null) {
					// asks animation to draw corresponding action
					listener.update(predator, i, action);
				}

				predator.update(choice);
			} else {
				if (listener != null) {
					// asks animation to draw a confused animal
					action = new Action(Direction.NONE, false);
					listener.update(predator, i, action);
				}
			}

			// records position changes to handle collisions
			List<Vector2D> moves = new ArrayList<Vector2D>();
			moves.add(position);
			moves.add(newPosition);
			mPredatorMoves.put(predator, moves);
		}
	}

	/**
	 * Moves the preys in the daedalus.
	 * 
	 * @param listener
	 *            The listener to which the function will notify the changes
	 *            (can be null)
	 */

	private void movePreys(Animation listener) {
		List<Prey> preys = mDaedalus.getPreys();
		Action action;
		Direction choice;
		for (int i = 0; i < preys.size(); i++) {
			Prey prey = preys.get(i);
			Vector2D position = prey.getPosition();
			Vector2D newPosition = position;
			Direction[] choices = mDaedalus.getChoices(position);

			// tries to make prey move
			try {
				choice = prey.move(choices, mDaedalus);
				if (!prey.getPosition().equals(position)) {
					System.err.println("Error : Prey position changed while choosing direction.");
					System.err.println("\tDid you call setPosition(Vector2D) or update(Direction) ?\n");
					prey.setPosition(position);
					choice = null;
				}
			} catch (Exception E) {
				System.err.print("Exception occurred while moving animals: ");
				E.printStackTrace();
				choice = null;
			}

			// if prey could move
			if (choice != null) {
				newPosition = position.addDirectionTo(choice);

				int x = newPosition.getX();
				int y = newPosition.getY();

				if (mDaedalus.isFree(x, y)) {
					action = new Action(choice, true);
				} else {
					newPosition = position;
					action = new Action(choice, false);
					choice = Direction.NONE;
				}

				if (listener != null) {
					// draws animation
					listener.update(prey, i + mDaedalus.getPredators().size(), action);
				}
				prey.update(choice);
			} else {
				if (listener != null) {
					action = new Action(Direction.NONE, false);
					listener.update(prey, i + mDaedalus.getPredators().size(), action);
				}
			}

			// records position changes to handle collisions
			List<Vector2D> moves = new ArrayList<Vector2D>();
			moves.add(position);
			moves.add(newPosition);
			mPreyMoves.put(prey, moves);
		}
	}

	/**
	 * Checks collisions between predators and preys in {@codeO(n*m)}. A collision
	 * occurs if two animals land on the same tile, or when they run into each
	 * other.
	 * <p>
	 * A special case is handled when animals run into each other. The animation
	 * is notified that an animal dies between two squares.
	 * 
	 * @param listener
	 *            The listener to which the function will notify the changes
	 *            (can be null)
	 */

	private void checkCollisions(Animation listener) {
		List<Predator> predators = mDaedalus.getPredators();
		List<Prey> preys = mDaedalus.getPreys();

		for (int i = 0; i < predators.size(); ++i) {
			Predator a = predators.get(i);
			List<Vector2D> aChanges = mPredatorMoves.get(a);
			for (int j = 0; j < preys.size(); ++j) {
				Prey b = preys.get(j);
				List<Vector2D> bChanges = mPreyMoves.get(b);

				// position changes for animal a
				Vector2D aOld = aChanges.get(0);
				Vector2D aNew = aChanges.get(1);
				// position changes for animal b
				Vector2D bOld = bChanges.get(0);
				Vector2D bNew = bChanges.get(1);

				// if (a.new == b.new) or (a.old == b.new and b.old == a.new)
				boolean diesInBetween = aOld.equals(bNew) && bOld.equals(aNew);
				boolean diesInPlace = aNew.equals(bNew);

				if (diesInPlace || diesInBetween) {
					if (mDaedalus.hasPrey(b)) {
						mDaedalus.removePrey(b);

						// records survival time
						if (mArrivalTimes.get(mStepCounter) == null) {
							mArrivalTimes.put(mStepCounter, new LinkedList<Prey>());
						}
						mArrivalTimes.get(mStepCounter).add(b);

						// asks animation to interrupt movement if it dies
						// moving
						if (listener != null && diesInBetween) {
							listener.updateDying(j + predators.size());
						}
					}
				}
			}
		}
	}
}
