package ch.epfl.maze.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import ch.epfl.maze.physical.World;
import ch.epfl.maze.simulation.Simulation;

/**
 * Handles the display of a {@code Simulation} on a window.
 * 
 */

public final class Display implements Runnable {

	/* constants */
	public static final Color BACKGROUND_COLOR = Color.GRAY;
	public static final int SQUARE_SIZE = 42;
	public static final int BUFFERS_NUMBER = 2;
	public static final int MAX_SPEED = 32;
	public static final int DEFAULT_SPEED = 2;
	public static final int MIN_SPEED = 1;
	public static final int ANIMATION_SLEEP = 10;

	/* lock for mutual exclusion between human interactions and display */
	private final Object mLock = new Object();

	/* simulation and animation handlers */
	private final Simulation mSimulation;
	private final Animation mAnimation;
	private volatile float mSpeed;

	/* actual window frame and canvas */
	private JFrame mFrame;
	private JMenuBar mMenuBar;
	private Canvas mCanvas;

	/* drawing buffers */
	private BufferStrategy mStrategy;
	private Map<Integer, BufferedImage> mTiles;

	/* control variables */
	private boolean mRunning;
	private boolean mPaused;
	private boolean mShowGrid;
	private boolean mDebug;
	private boolean mFinished;

	/**
	 * Constructs a {@code Display} that will display a simulation.
	 * 
	 * @param simulation
	 *            A {@code Simulation} to display
	 */

	public Display(Simulation simulation) {
		// sanity check
		if (simulation == null) {
			throw new IllegalArgumentException("Simulation must be defined.");
		}
		if (simulation.getWorld() == null) {
			throw new IllegalArgumentException("World in Simulation must be defined.");
		}

		// initiates instances
		mSimulation = simulation;
		mAnimation = new Animation(simulation.getWorld().getAnimals());
		mSpeed = DEFAULT_SPEED;

		// default control variables
		mRunning = true;
		mPaused = false;
		mShowGrid = false;
		mDebug = false;
		mFinished = false;

		// creates menu
		createMenu();

		// creates canvas
		createCanvas();

		// creates window
		createWindow();

		// sets canvas and menu on frame
		mFrame.setJMenuBar(mMenuBar);
		mFrame.add(mCanvas);
		mFrame.pack();
		mFrame.setLocationRelativeTo(null);

		// creates buffer strategy
		mCanvas.createBufferStrategy(BUFFERS_NUMBER);
		mStrategy = mCanvas.getBufferStrategy();

		// loads images of tiles
		mTiles = new HashMap<Integer, BufferedImage>();
		try {
			mTiles.put(World.FREE, ImageIO.read(new File("img/tiles/free.png")));
			mTiles.put(World.WALL, ImageIO.read(new File("img/tiles/wall.png")));
			mTiles.put(World.START, ImageIO.read(new File("img/tiles/start.png")));
			mTiles.put(World.EXIT, ImageIO.read(new File("img/tiles/exit.png")));
			mTiles.put(World.NOTHING, ImageIO.read(new File("img/tiles/nothing.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		mFrame.setVisible(true);
		mainLoop();
		mFrame.dispose();
	}

	/**
	 * Sets the debug control.
	 * 
	 * @param debug
	 *            The new debug value
	 */

	public void setDebug(boolean debug) {
		mDebug = debug;
		mShowGrid = debug;
		createMenu();
		mFrame.setJMenuBar(mMenuBar);
	}

	/**
	 * Creates frame window.
	 */

	private void createWindow() {
		// actual window
		mFrame = new JFrame("Maze solver simulation");

		// redefines closing operation
		mFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				synchronized (mLock) {
					mRunning = false;
				}
			}
		});

		// sets up various options to make it look good
		mFrame.setIgnoreRepaint(false);
		mFrame.setBackground(BACKGROUND_COLOR);
		mFrame.setFocusable(false);
		mFrame.setFocusTraversalKeysEnabled(false);
		mFrame.setResizable(false);
		System.setProperty("sun.awt.noerasebackground", "true");
	}

	/**
	 * Creates canvas.
	 */

	private void createCanvas() {
		// actual canvas
		mCanvas = new Canvas();

		// sets canvas size
		int height = SQUARE_SIZE * mSimulation.getWorld().getHeight();
		int width = SQUARE_SIZE * mSimulation.getWorld().getWidth();
		mCanvas.setSize(width, height);

		// sets up options to make it look good
		mCanvas.setIgnoreRepaint(true);
		mCanvas.setBackground(Color.BLACK);
		mCanvas.setFocusable(true);
		mCanvas.setFocusTraversalKeysEnabled(false);
	}

	/**
	 * Creates menu.
	 */

	private void createMenu() {
		// creates menu bar
		mMenuBar = new JMenuBar();

		// creates "Maze" menu
		JMenu menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.setToolTipText("Contains main manipulation options.");
		mMenuBar.add(menu);

		// creates menu items
		// "Stop"
		JMenuItem stopItem = new JMenuItem("Stop", KeyEvent.VK_S);
		stopItem.setToolTipText("Stops simulation in its current state.");
		stopItem.setAccelerator(KeyStroke.getKeyStroke("control W"));
		stopItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					mSimulation.stop();
					mAnimation.reset(null);
					mPaused = false;
				}
			}
		});
		menu.add(stopItem);

		// "Restart"
		JMenuItem restart = new JMenuItem("Restart", KeyEvent.VK_R);
		restart.setToolTipText("Restarts the simulation from the beginning.");
		restart.setAccelerator(KeyStroke.getKeyStroke("control R"));
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					mSimulation.restart();
					mAnimation.reset(mSimulation.getWorld().getAnimals());
					mPaused = false;
					mFinished = false;
				}
			}
		});
		menu.add(restart);

		// ==========================================================
		menu.addSeparator();

		// "Pause"
		final JMenuItem pauseItem = new JMenuItem(mDebug ? "Next step" : 
			(mPaused ? "Resume" : "Pause"), KeyEvent.VK_P);
		pauseItem.setAccelerator(KeyStroke.getKeyStroke("SPACE"));
		pauseItem.setToolTipText("Pauses simulation.");
		pauseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					if (mPaused) {
						mPaused = false;
						if (!mDebug) {
							pauseItem.setText("Pause");
						}
					} else {
						mPaused = true;
						if (!mDebug) {
							pauseItem.setText("Resume");
						}
					}
				};
			}
		});
		menu.add(pauseItem);

		// "Debug mode"
		JCheckBoxMenuItem debugItem = new JCheckBoxMenuItem("Debug mode");
		debugItem.setState(mDebug);
		debugItem.setMnemonic(KeyEvent.VK_M);
		debugItem.setAccelerator(KeyStroke.getKeyStroke("alt G"));
		debugItem.setToolTipText("Enters in debug mode, allowing to move animals step by step.");
		menu.add(debugItem);

		// ==========================================================
		menu.addSeparator();

		// "Accelerate"
		final JMenuItem accelerateItem = new JMenuItem("Accelerate",
				KeyEvent.VK_A);
		// "Decelerate"
		final JMenuItem decelerateItem = new JMenuItem("Decelerate",
				KeyEvent.VK_D);

		accelerateItem.setToolTipText("Increases simulation speed up to a maximum.");
		decelerateItem.setToolTipText("Decreases simulation speed down to a minimum.");

		accelerateItem.setAccelerator(KeyStroke.getKeyStroke('+'));
		decelerateItem.setAccelerator(KeyStroke.getKeyStroke('-'));

		accelerateItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					decelerateItem.setEnabled(true);
					if (mSpeed < MAX_SPEED) {
						mSpeed *= 2;
					}
					if (mSpeed == MAX_SPEED) {
						accelerateItem.setEnabled(false);
					}
				}
			}
		});
		decelerateItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					accelerateItem.setEnabled(true);
					if (mSpeed > MIN_SPEED) {
						mSpeed /= 2;
					}
					if (mSpeed == MIN_SPEED) {
						decelerateItem.setEnabled(false);
					}
				}
			}
		});

		menu.add(accelerateItem);
		menu.add(decelerateItem);

		// ==========================================================
		menu.addSeparator();

		// "Show grid"
		final JCheckBoxMenuItem gridItem = new JCheckBoxMenuItem("Show grid");
		gridItem.setState(mShowGrid);
		gridItem.setMnemonic(KeyEvent.VK_G);
		gridItem.setToolTipText("Criss-crosses the maze tiles.");
		gridItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					mShowGrid = !mShowGrid;
					gridItem.setState(mShowGrid);
				}
			}
		});
		debugItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					if (!mDebug) {
						mDebug = true;
						mShowGrid = true;
						pauseItem.setText("Next Step");

					} else {
						mShowGrid = false;
						mPaused = false;
						mDebug = false;
						pauseItem.setText("Pause");
					}
					gridItem.setState(mShowGrid);
				}
			}
		});
		menu.add(gridItem);

		// "Exit"
		JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_E);
		exitItem.setToolTipText("Exits program.");
		exitItem.setAccelerator(KeyStroke.getKeyStroke("ESCAPE"));
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (mLock) {
					mRunning = false;
				}
			}
		});
		menu.add(exitItem);
	}

	/**
	 * Draws the labyrinth being simulated.
	 * 
	 * @param g
	 *            The graphics on which the labyrinth will be drawn
	 */

	private void drawLabyrinth(Graphics2D g) {
		World world = mSimulation.getWorld();

		BufferedImage tile;
		for (int y = 0; y < world.getHeight(); y++) {
			for (int x = 0; x < world.getWidth(); x++) {
				// retrieves corresponding image
				tile = mTiles.get(world.getTile(x, y));
				if (tile == null) {
					tile = mTiles.get(World.WALL);
				}

				int width = x * SQUARE_SIZE;
				int height = y * SQUARE_SIZE;

				g.drawImage(tile, width, height, mFrame);

				if (mShowGrid) {
					g.setColor(BACKGROUND_COLOR);
					g.drawRect(width, height, SQUARE_SIZE, SQUARE_SIZE);
				}
			}
		}
	}

	/**
	 * Draws the actual animation of the display.
	 * 
	 * @param dt
	 *            The elapsed time between two frames
	 * @param g
	 *            The graphics on which the animation will be drawn
	 * @param width
	 *            Width of graphics
	 * @param height
	 *            Height of graphics
	 */

	private void drawAnimation(float dt, Graphics2D g, int width, int height) {
		// clears background
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);

		// paints maze
		drawLabyrinth(g);

		synchronized (mLock) {
			// paints next animation frame
			mAnimation.paint(dt, g, mFrame);

			if (mAnimation.isDone()) {
				// determines if maze is solved
				if (!mFinished && mSimulation.isOver()) {
					mFinished = true;
					final String recordTable = mSimulation.getRecordTable();

					// message dialog to invoke later, to prevent Display from
					// crashing when a key is being held
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, "Simulation is complete.\n\n" + recordTable);
						}
					});
					mPaused = true;
				} else {
					if (mDebug) {
						mPaused = true;
					}
					nextMove();
				}
			}
		}
	}

	/**
	 * Controls the graphics environment for the animation of the display.
	 * 
	 * @param dt
	 *            The elapsed time between two frames
	 */

	private void animate(float dt) {
		Graphics2D g = null;
		try {
			// retrieves Graphics2D from strategy
			g = (Graphics2D) mStrategy.getDrawGraphics();

			// sets options
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			drawAnimation(dt, g, mCanvas.getWidth(), mCanvas.getHeight());
		} finally {
			if (g != null) {
				// to call at the end
				g.dispose();
			}
		}

		// displays the buffer
		mStrategy.show();
	}

	/**
	 * Runs the animation main loop.
	 */

	private void mainLoop() {
		long before = System.nanoTime();
		while (mRunning) {
			if (mPaused || mFinished) {
				animate(0);
				before = System.nanoTime();
			} else {
				long now = System.nanoTime();
				float dt = (now - before) * 0.000000001f * mSpeed;
				if (dt < 0.001f) {
					dt = 0.001f;
				}

				animate(dt);

				before = now;
			}

			try {
				Thread.sleep(ANIMATION_SLEEP);
			} catch (InterruptedException e) {
				// do nothing
			}
		}

		// paints a last frame
		animate(0);
	}

	/**
	 * Computes the {@code Simulation}'s next move.
	 */

	private void nextMove() {
		synchronized (mLock) {
			if (mRunning) {
				mSimulation.move(mAnimation);
			}
		}
	}
}
