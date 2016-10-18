package ch.epfl.maze.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generates a set of pre-computed labyrinth structures
 * 
 */

public final class LabyrinthGenerator {

	/**
	 * Returns a precomputed labyrinth of small size.
	 * 
	 * @return A small labyrinth
	 */

	public static int[][] getSmall() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		};

		return labyrinth;
	}

	/**
	 * Returns a precomputed labyrinth of medium size.
	 * 
	 * @return A medium labyrinth
	 */

	public static int[][] getMedium() {
		int[][] labyrinth = {
				{ 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1 },
				{ 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 3 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	public static int[][] getLarge() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
				{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	// ==============================================================

	// ==============================================================
	
	/**
	 * Returns the labyrinth structure of the Pac-Man level.
	 * 
	 * @return The Pac-Man level
	 */
	
	public static int[][] getPacMan() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, -1, -1, -1, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
				{ 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};
	
		return labyrinth;
	}

	// ==============================================================
	
	/**
	 * Returns the labyrinth structure of one of the Ms. Pac-Man levels.
	 * 
	 * @return One of the Ms. Pac-Man levels
	 */
	
	public static int[][] getMsPacMan() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1 },
				{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 },
				{ 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 1, -1, -1, -1, 1, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 },
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};
	
		return labyrinth;
	}

	/**
	 * Returns a hard-coded labyrinth which is multiply connected.
	 * <p>
	 * If the Monkey algorithm is run on this labyrinth, it will never find the
	 * solution.
	 * 
	 * @return A labyrinth multiply connected
	 */

	public static int[][] getMultiplyConnected() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, },
				{ 1, 0, 0, 3, 1, 0, 1, 0, 0, 0, 1, },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, },
				{ 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
				{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, }
		};
		
		return labyrinth;
	}

	// ==============================================================

	/**
	 * Returns a hard-coded maze for debugging the Mouse.
	 * 
	 * @return A maze for debugging the Mouse
	 */

	public static int[][] getDebugMouse() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 2, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 3, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Hamster.
	 * 
	 * @return A maze for debugging the Hamster
	 */

	public static int[][] getDebugHamster() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1 },
				{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
				{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
				{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Monkey.
	 * 
	 * @return A maze for debugging the Monkey
	 */

	public static int[][] getDebugMonkey() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
				{ 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1 },
				{ 1, 1, 0, 1, 1, 1, 3, 1, 0, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Bear.
	 * 
	 * @return A maze for debugging the Bear
	 */

	public static int[][] getDebugBear1() {
		int[][] labyrinth = {
				{ 1, 1, 1, 2, 1, 1, 1, },
				{ 1, 0, 0, 0, 1, 0, 1, },
				{ 1, 0, 0, 0, 0, 0, 1, },
				{ 1, 0, 0, 0, 0, 0, 1, },
				{ 1, 1, 1, 1, 0, 0, 1, },
				{ 3, 0, 0, 0, 0, 0, 1, },
				{ 1, 1, 1, 1, 1, 1, 1, }
		};
		
		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Bear.
	 * 
	 * @return A maze for debugging the Bear
	 */

	public static int[][] getDebugBear2() {
		int[][] labyrinth = {
				{ 1, 2, 1, 1 },
				{ 1, 0, 1, 1 },
				{ 1, 0, 0, 3 },
				{ 1, 0, 1, 1 },
				{ 1, 0, 1, 1 },
				{ 1, 1, 1, 1 }
		};
		
		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Panda.
	 * 
	 * @return A maze for debugging the Panda
	 */

	public static int[][] getDebugPanda1() {
		int[][] labyrinth = {
				{ 1, 1, 1, 2, 1, 1, 1 },
				{ 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging the Panda.
	 * 
	 * @return A maze for debugging the Panda
	 */

	public static int[][] getDebugPanda2() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 2, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	// ==============================================================

	/**
	 * Returns a hard-coded maze that shows the efficiency of the Bear over
	 * the Monkey.
	 * 
	 * @return A maze to run with a Bear and a Monkey
	 */

	public static int[][] getBearVsMonkey() {
		int[][] labyrinth = {
				{ 1, 1, 1, 3, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 2, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }
		};
		
		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze that shows the efficiency of the Panda over
	 * the Hamster.
	 * 
	 * @return A maze to run with a Panda and a Hamster
	 */

	public static int[][] getPandaVsHamster() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	// ==============================================================

	/**
	 * Returns a hard-coded maze for debugging Blinky.
	 * 
	 * @return A maze for debugging Blinky
	 */

	public static int[][] getDebugBlinky() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging Pinky.
	 * 
	 * @return A maze for debugging Pinky
	 */

	public static int[][] getDebugPinky() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging Inky.
	 * 
	 * @return A maze for debugging Inky
	 */

	public static int[][] getDebugInky() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
		};

		return labyrinth;
	}

	/**
	 * Returns a hard-coded maze for debugging Clyde.
	 * 
	 * @return A maze for debugging Clyde
	 */

	public static int[][] getDebugClyde() {
		int[][] labyrinth = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }
		};

		return labyrinth;
	}

	// ==============================================================

	/**
	 * Reads and returns a labyrinth from specified file.
	 * <p>
	 * The file must be present in the root folder of the project.
	 * 
	 * @param filename
	 *            The file location
	 * @return Labyrinth structure parsed from a file
	 */

	public static int[][] readFromFile(String filename) {
		File file = new File(filename);
		int[][] labyrinth = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(file);

			// reads one line after the other
			ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
			while (scanner.hasNext()) {
				String line = scanner.nextLine();

				// looks for a single number in the line using a Regular Expression
				Matcher match = Pattern.compile("-?[0-9]").matcher(line);
				ArrayList<String> list = new ArrayList<String>();
				while (match.find()) {
					list.add(match.group());
				}

				// gets rid of empty lines
				if (!list.isEmpty()) {
					lines.add(list);
				}
			}

			// parses the Strings found into a labyrinth
			labyrinth = new int[lines.size()][];
			for (int i = 0; i < labyrinth.length; i++) {
				ArrayList<String> line = lines.get(i);
				labyrinth[i] = new int[line.size()];
				for (int j = 0; j < line.size(); j++) {
					labyrinth[i][j] = Integer.parseInt(line.get(j));
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return labyrinth;
	}
}
