package ch.epfl.maze.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import ch.epfl.maze.util.Action;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Graphic component of an animal that will be drawn by an {@link Animation}.
 * 
 */

public final class GraphicComponent {

	/* constants */
	public static final int MAXIMUM_FRAMES = 4;
	public static final int SQUARE_SIZE = Display.SQUARE_SIZE;

	/* drawing variables */
	private final Vector2D mPosition;
	private final BufferedImage mImage;
	private Action mAction;
	private boolean mRotate;

	/**
	 * Constructs a graphic component with the image of the animal, the position
	 * at which the component needs to be drawn, and the corresponding action
	 * that it needs to perform.
	 * 
	 * @param image
	 *            Image of animal
	 * @param position
	 *            Position at which the image will be drawn
	 * @param action
	 *            Action that the component needs to perform
	 */

	public GraphicComponent(BufferedImage image, Vector2D position, Action action) {
		// sanity checks
		if (image == null) {
			throw new IllegalArgumentException("BufferedImage cannot be null.");
		}
		if (position == null) {
			throw new IllegalArgumentException("Position cannot be null.");
		}
		if (action == null) {
			action = new Action(Direction.NONE, false);
		}

		// default values
		mImage = image;
		mPosition = position;
		mRotate = true;
		mAction = action;
	}

	/**
	 * Notifies the component that it will die between two squares.
	 */

	public void willDieMoving() {
		mAction = new Action(mAction.getDirection(), mAction.isSuccessful(), true);
	}

	/**
	 * Asks the graphic component to paint itself on graphic environment, by
	 * performing a ratio of its action.
	 * 
	 * @param ratio
	 *            Ratio of the action to be performed
	 * @param g
	 *            Graphic environment
	 * @param targetWindow
	 *            Window on which the graphic is being drawn
	 */

	public void paint(float ratio, Graphics2D g, ImageObserver targetWindow) {
		if (mAction.getDirection() == Direction.NONE) {
			renderStuck(g, targetWindow);
		} else {
			if (ratio > 0.5) {
				if (!mAction.diesBetweenSquares() && !mAction.isSuccessful()) {
					renderMove(1 - ratio, g, targetWindow, true);
				} else if (!mAction.diesBetweenSquares()) {
					renderMove(ratio, g, targetWindow, false);
				}
			} else {
				renderMove(ratio, g, targetWindow, false);
			}
		}
	}

	/**
	 * Draws the moving component on graphics environment and target window.
	 * <p>
	 * The function draws the animal at {@code (position + ratio*heading)}.
	 * 
	 * @param ratio
	 *            Ratio of action performed by animation
	 * @param g
	 *            Graphic environment
	 * @param targetWindow
	 *            Frame display
	 * @param buzz
	 *            Buzzes the animal, used when he has just hit a wall
	 */

	private void renderMove(float ratio, Graphics2D g, ImageObserver targetWindow, boolean buzz) {
		// transforms direction into vector
		Vector2D heading = mAction.getDirection().toVector().mul(SQUARE_SIZE);
		Vector2D normalized = heading.normalize();

		// loads the correct frame
		BufferedImage img = cropImage(ratio, mAction.getDirection());

		AffineTransform reset = new AffineTransform();

		// applies translation
		double newX = (mPosition.getX() + ratio * heading.getX());
		double newY = (mPosition.getY() + ratio * heading.getY());
		reset.translate(newX, newY);

		// applies rotation
		double rotation = 0;
		if (buzz) {
			rotation = -(Math.PI / 6.0) * Math.sin((60 * ratio) / Math.PI);
		}
		if (mRotate) {
			rotation += Math.atan2(normalized.getY(), normalized.getX()) - Math.PI / 2;
		}
		reset.rotate(rotation, SQUARE_SIZE / 2, SQUARE_SIZE / 2);

		// transforms and draws image
		g.setTransform(reset);
		g.drawImage(img, 0, 0, targetWindow);

		// inverts transformations
		reset.rotate(-rotation, SQUARE_SIZE / 2, SQUARE_SIZE / 2);
		reset.translate(-newX, -newY);
		g.setTransform(reset);
	}

	/**
	 * Draws the still component on graphics environment and target window.
	 * <p>
	 * Draws a question mark if {@code mAction} is not successful
	 * 
	 * @param g
	 *            Graphic environment
	 * @param targetWindow
	 *            Frame display
	 */

	private void renderStuck(Graphics2D g, ImageObserver targetWindow) {
		// loads default frame of image with default direction
		BufferedImage img = cropImage(-1, Direction.NONE);

		AffineTransform reset = new AffineTransform();

		// applies translation
		double newX = mPosition.getX();
		double newY = mPosition.getY();
		reset.translate(newX, newY);

		// transforms and draws image
		g.setTransform(reset);
		g.drawImage(img, 0, 0, targetWindow);

		// draws interrogation mark
		if (!mAction.isSuccessful()) {
			ImageIcon icon = new ImageIcon("img/unknown.png");
			g.drawImage(icon.getImage(), SQUARE_SIZE - icon.getIconWidth() - 2, 2, targetWindow);
		}

		// inverts translation
		reset.translate(-newX, -newY);
		g.setTransform(reset);
	}

	/**
	 * Transforms the image according to frame and movement.
	 * 
	 * @param ratio
	 *            The ratio of the action to perform
	 * @param dir
	 *            The direction towards which the component faces
	 * @return The correct frame that faces towards the direction specified
	 */

	private BufferedImage cropImage(float ratio, Direction dir) {
		int width = mImage.getWidth();
		int height = mImage.getHeight();
		int frames = width / SQUARE_SIZE;
		int moves = height / SQUARE_SIZE;

		// sanity checks
		if (width % SQUARE_SIZE != 0 || height % SQUARE_SIZE != 0) {
			throw new UnsupportedOperationException(
					"Image size is not a multiple of " + SQUARE_SIZE + " pixels, but " + width + "x" + height);
		}
		if (moves > Direction.values().length) {
			throw new UnsupportedOperationException(
					"Image height has more than " + Direction.values().length + " moves (" + height + ")");
		}
		if (frames > MAXIMUM_FRAMES) {
			throw new UnsupportedOperationException(
					"Image width has more than " + MAXIMUM_FRAMES + " frames (" + frames + ")");
		}

		// selects frame
		int frame = ((int) (ratio * 2 * frames)) % frames;
		if (frame >= frames) {
			frame = 0;
		} else if (ratio < 0 || ratio > 1) { // handles bad ratio
			frame = (int) (frames / 2);
		}

		// selects move
		int move = dir.intValue();
		mRotate = false;
		if (move >= moves) {
			mRotate = true;
			move = 0;
		}

		// selects subimage according to frame and move
		BufferedImage img = mImage.getSubimage(frame * SQUARE_SIZE, move * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

		return img;
	}
}
