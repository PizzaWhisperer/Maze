package ch.epfl.maze.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Action;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Handles the animation of a {@code Simulation} by extrapolating the positions
 * of animals.
 * 
 */

public final class Animation {

	/** Default number of waiting frames to display when animation is aborting. */
	public static final int DEFAULT_WAITING_FRAMES = 2;

	/** Maps animals identity to graphical components that will be  animated. */
	private Map<Integer, GraphicComponent> mGraphMap;

	/** Buffer of images of animals. Key format: "superclass.class" */
	private Map<String, BufferedImage> mImages;

	/** Drawing ratio variable. */
	private float mRatio;

	/** Control variable. */
	private boolean mDone;

	/** Current number of waiting frames, to prevent screen from flashing. */
	private int mWaitingFrames;

	/**
	 * Constructs an animation handler that will animate animals on a graphic
	 * environment by extrapolating their position.
	 * 
	 * @param animals
	 *            The {@code List} of animals that will be shown on the first
	 *            frame
	 */

	public Animation(List<Animal> animals) {
		mGraphMap = new TreeMap<Integer, GraphicComponent>();
		mImages = new HashMap<String, BufferedImage>();

		// sanity check
		if (animals != null) {
			// puts default action to draw animals and loads corresponding image
			Action none = new Action(Direction.NONE);
			for (int i = 0; i < animals.size(); i++) {
				Animal animal = animals.get(i);
				BufferedImage img = loadImage(animal);
				Vector2D position = animal.getPosition().mul(Display.SQUARE_SIZE);
	
				mGraphMap.put(i, new GraphicComponent(img, position, none));
			}
		}

		// default values
		mDone = true;
		mWaitingFrames = 0;
	}

	/**
	 * Asks the animation to update an animal on the screen with a corresponding
	 * action. The animal is identified by a number, so it can be overwritten in
	 * case of a future update.
	 * 
	 * @param animal
	 *            Animal to update with action
	 * @param id
	 *            Unique identifier for animal
	 * @param action
	 *            Action that animal needs to perform
	 */

	public void update(Animal animal, int id, Action action) {
		// sanity checks
		if (action == null) {
			action = new Action(Direction.NONE, false);
		}
		if (animal != null) {
			// retrieves BufferedImage
			String folder = animal.getClass().getSuperclass().getSimpleName();
			String file = animal.getClass().getSimpleName();
			BufferedImage img = mImages.get(folder + "." + file);
			if (img == null) {
				img = loadImage(animal);
			}
	
			// transforms position
			Vector2D position = animal.getPosition().mul(Display.SQUARE_SIZE);
	
			mGraphMap.put(id, new GraphicComponent(img, position, action));
		}
	}

	/**
	 * Asks the animation to make the animal corresponding to the identifier die
	 * between two squares. This will be done by animating only half of its
	 * action.
	 * 
	 * @param id
	 *            Identifier of animal to kill
	 */

	public void updateDying(int id) {
		GraphicComponent graphComp = mGraphMap.get(id);
		if (graphComp != null) {
			graphComp.willDieMoving();
		}
	}

	/**
	 * Notifies the animation that updates were done, and that it can start
	 * animating from now.
	 */

	public void doneUpdating() {
		mDone = false;
	}

	/**
	 * Paints the dt-step of the animation.
	 * 
	 * @param dt
	 *            The elapsed time between two frames
	 * @param g
	 *            The graphics environment on which the graphic components will
	 *            be painted (assumed non-null)
	 * @param targetWindow
	 *            The window on which the graphic components will be painted
	 *            (assumed non-null)
	 */

	public void paint(float dt, Graphics2D g, ImageObserver targetWindow) {
		mRatio += dt;
		if (mRatio > 1) {
			mRatio = 1;
		}

		// paints every graphic component stored so far
		for (Map.Entry<Integer, GraphicComponent> entry : mGraphMap.entrySet()) {
			GraphicComponent comp = entry.getValue();
			comp.paint(mRatio, g, targetWindow);
		}

		// decides whether the animation is done
		if (mDone || mRatio == 1 || mWaitingFrames == 1) {
			mWaitingFrames = 0;
			mDone = true;
			mGraphMap.clear();
			mRatio = 0;
		}

		// prevents screen from flashing when aborting
		if (mWaitingFrames > 0) {
			mWaitingFrames--;
		}
	}

	/**
	 * Determines whether the animation has finished.
	 * 
	 * @return <b>true</b> if the animation is done, <b>false</b> otherwise
	 */

	public boolean isDone() {
		return mDone;
	}

	/**
	 * Resets the animation with a new {@code List} of animals. If it is set to
	 * {@code null}, it just informs that it needs to abort its current job. A
	 * number of frames will still be painted to prevent the screen from
	 * flashing.
	 */

	public void reset(List<Animal> animals) {
		mGraphMap.clear();
		if (animals != null) {
			// puts default action to draw animals
			Action none = new Action(Direction.NONE);
			for (int i = 0; i < animals.size(); i++) {
				Animal animal = animals.get(i);

				// loads corresponding image only if not already existing
				String folder = animal.getClass().getSuperclass().getSimpleName();
				String file = animal.getClass().getSimpleName();
				BufferedImage img = mImages.get(folder + "." + file);
				if (img == null) {
					img = loadImage(animal);
				}

				// transforms position
				Vector2D position = animal.getPosition().mul(Display.SQUARE_SIZE);

				mGraphMap.put(i, new GraphicComponent(img, position, none));
			}
		}
		mWaitingFrames = DEFAULT_WAITING_FRAMES;
	}

	/**
	 * Buffers and returns the image of an animal. It does not load its image if
	 * it's already been loaded.
	 * 
	 * @param animal
	 *            Animal whose image needs to be loaded or returned
	 * @return The buffered image of the animal
	 */

	private BufferedImage loadImage(Animal animal) {
		// path = "img/superclass/class.png"
		String folder = animal.getClass().getSuperclass().getSimpleName();
		String file = animal.getClass().getSimpleName();
		String path = "img/" + folder + File.separator + file + ".png";

		// adds image to buffer if not already there
		BufferedImage img = mImages.get(folder + "." + file);
		if (img == null) {
			try {
				img = ImageIO.read(new File(path));
				mImages.put(folder + "." + file, img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return img;
	}
}
