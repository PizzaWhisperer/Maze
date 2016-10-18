package ch.epfl.maze.physical;

import java.util.ArrayList;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

public interface Cibleur {
	public default Direction bestWayTo(Vector2D caseCible, Direction[] choices, Direction prev, Vector2D maPosition) {

		ArrayList<Direction> ways = new ArrayList<>();
		for (Direction choice : choices) {
			ways.add(choice);
		}
		ways.remove(prev.reverse());
		Vector2D[] nextPosition = new Vector2D[ways.size()]; 
		int indice = 0;
		for (int i = 0; i < ways.size(); i++) {
			nextPosition[i] = maPosition.add(ways.get(i).toVector());
		}
		for (int i = 0; i < nextPosition.length; ++i) {
			if ((nextPosition[indice].sub(caseCible)).dist() >= (nextPosition[i].sub(caseCible)).dist()) {
				indice = i;
			}
		}
		return (nextPosition[indice].sub(maPosition)).toDirection();
	}
}
