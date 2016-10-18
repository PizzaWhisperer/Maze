package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * World that is represented by a labyrinth of tiles in which an {@code Animal}
 * can move.
 * 
 */

public abstract class World {

	/* tiles constants */
	public static final int FREE = 0;
	public static final int WALL = 1;
	public static final int START = 2;
	public static final int EXIT = 3;
	public static final int NOTHING = -1;

	private Vector2D startPos;
	private static int h, w;
	private static int[][] labyrinth;

	/**
	 * Constructs a new world with a labyrinth. The labyrinth must be rectangle.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 * @throws Exception
	 */

	public World(int[][] labyrinth) throws IllegalArgumentException {

		// vérification que le labyrinthe contient quelque chose
		if (labyrinth == null) {
			throw new IllegalArgumentException("pas de labyrinthe fourni");
		}
		w = labyrinth[0].length;
		// vérification que toutes les lignes ont la même longueur sinon, on
		// lance une exception
		for (int[] ligne : labyrinth) {
			if (ligne.length != w)
				throw new IllegalArgumentException("Le labyrinthe n'est pas adéquant");
		}

		// Arrivé ici, nous avons bien un labyrinthe consistant et qui est
		// rectangulaire, le labyrinth de
		// world n'est pas vide donc.

		h = labyrinth.length;

		World.labyrinth = new int[h][w];

		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++) {
				World.labyrinth[i][j] = labyrinth[i][j];
				if (labyrinth[i][j] == START)
					startPos = new Vector2D(j, i);
			}
	}

	/**
	 * Determines whether the labyrinth has been solved by every animal.
	 * 
	 * @return <b>true</b> if no more moves can be made, <b>false</b> otherwise
	 */

	abstract public boolean isSolved();

	/**
	 * Resets the world as when it was instantiated.
	 */

	abstract public void reset();

	/**
	 * Returns a copy of the list of all current animals in the world.
	 * 
	 * @return A list of all animals in the world
	 */

	abstract public List<Animal> getAnimals();

	/**
	 * Checks in a safe way the tile number at position (x, y) in the labyrinth.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return The tile number at position (x, y), or the NONE tile if x or y is
	 *         incorrect.
	 */

	public static final int getTile(int x, int y) {
		int t;
		if (x >= w || y >= h || x < 0 || y < 0)
			t = NOTHING;
		else
			t = labyrinth[y][x];
		return t;
	}

	/**
	 * Determines if coordinates are free to walk on.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return <b>true</b> if an animal can walk on tile, <b>false</b> otherwise
	 */

	public final boolean isFree(int x, int y) {
		int pos = getTile(x, y);
		return (pos == EXIT || pos == START || pos == FREE);
	}

	/**
	 * Computes and returns the available choices for a position in the
	 * labyrinth. The result will be typically used by {@code Animal} in
	 * {@link ch.epfl.maze.physical.Animal#move(Direction[]) move(Direction[])}
	 * 
	 * @param position
	 *            A position in the maze
	 * @return An array of all available choices at a position
	 */

	public final Direction[] getChoices(Vector2D position) {
		// le vecteur position est valide
		int hor = position.getX();
		int ver = position.getY();

		ArrayList<Direction> Dir = new ArrayList<>();

		if (isFree(hor + 1, ver))
			Dir.add(Direction.RIGHT);
		if (isFree(hor, ver - 1))
			Dir.add(Direction.UP);
		if (isFree(hor - 1, ver))
			Dir.add(Direction.LEFT);
		if (isFree(hor, ver + 1))
			Dir.add(Direction.DOWN);

		if (Dir.isEmpty())
			Dir.add(Direction.NONE);

		int l = Dir.size();

		return Dir.toArray(new Direction[l]);
	}

	/**
	 * Returns horizontal length of labyrinth.
	 * 
	 * @return The horizontal length of the labyrinth
	 */

	public final int getWidth() {
		return w;
	}

	/**
	 * Returns vertical length of labyrinth.
	 * 
	 * @return The vertical length of the labyrinth
	 */

	public final int getHeight() {
		return h;
	}

	/**
	 * Returns the entrance of the labyrinth at which animals should begin when
	 * added.
	 * 
	 * @return Start position of the labyrinth, null if none.
	 */

	public final Vector2D getStart() {
		// voir le README pour le choix de cette variable locale.
		Vector2D startPos = null;
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				if (labyrinth[i][j] == START) {
					startPos = new Vector2D(j, i);
				}
			}
		}
		return startPos;
	}

	/**
	 * Returns the exit of the labyrinth at which animals should be removed.
	 * 
	 * @return Exit position of the labyrinth, null if none.
	 */

	public final Vector2D getExit() {
		// voir le README pour le choix de cette variable locale.
		Vector2D endPos = null;
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				if (labyrinth[i][j] == EXIT) {
					return new Vector2D(j, i);
				}
			}
		}
		return endPos;
	}
}