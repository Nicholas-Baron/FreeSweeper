package nick.sweeper.main;

import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import nick.sweeper.ai.AILogic;
import radar.sweeper.draw.MousePath;

public final class MineSweeper extends Canvas implements Runnable {

	private static final long			serialVersionUID	= 1L;

//	public static final short			height				= 26, width = 50, numMines = 250;
	public static short					height				= 15, width = 15, numMines = 20;

	private static JFrame				frame;

	private static Grid					grid;

	private static final Input			input				= new Input();
	
	private MineSweeper	game;

	private Thread thread;

	public static final String			name				= "FreeSweeper v1.3a";
	
	private BufferStrategy				bs;

	public static final boolean			debug				= false;

	private static AILogic				ai;
	
	private MousePath mousePath;

	public static AILogic getAI( ) {
		return ai;
	}

	
	/**
	 * @param args Valid arguments are <width> <height> <numMines> <outputFilename>
	 */
	public static void main(final String[ ] args) {

		MineSweeper game;
		
		if(args.length > 0) {
			if(args.length < 3) {
				System.out.println("Please specify the width and height of the board in the number of spaces and the number of mines to place");
			}
			
			width = (short) Integer.parseInt(args[0]);
			height = (short) Integer.parseInt(args[1]);
			numMines = (short) Integer.parseInt(args[2]);
			
			if(args.length > 3) {
				game = new MineSweeper(args[3]);
			}else {
				game = new MineSweeper();
			}
		}else {
			game = new MineSweeper();
		}


		Thread thread = new Thread(game, "Main Thread");
		
		game.addThread(thread);
		
		frame = new JFrame( );

		frame.setResizable(true);
		frame.setTitle(name);
		frame.setLocationRelativeTo(null);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(grid.renderSize( ));

		game.addMouseListener(input);
		game.addMouseMotionListener(input);

		frame.addKeyListener(input);

		frame.add(game);
		frame.pack( );

		frame.addWindowListener(new WindowAdapter(){  
            public void windowClosing(WindowEvent e) {
            	game.fastStop();
                frame.dispose();
            }
        });
		
		frame.setVisible(true);
		thread.start( );
	}

	public void addThread(Thread thread) {
		this.thread = thread;
	}
	
	public MineSweeper() {
		grid = new Grid(width, height, numMines, this);
		setPreferredSize(grid.renderSize( ));
		
		input.setGrid(grid);
		
		ai = new AILogic(grid);
	}
	
	public MineSweeper(String outputFile) {
		this();

		mousePath = new MousePath(grid, outputFile);

		input.setMousePath(mousePath);
	}

	public int renderHeight( ) {

		return getHeight( );
	}

	public int renderWidth( ) {

		return getWidth( );
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	@Override
	public void run() {
		createBufferStrategy(2);
		bs = getBufferStrategy( );
		
		grid.addGraphics(bs);
	}

	public synchronized void fastStop() {
		AILogic.halt();
		
		if(mousePath != null) {
			mousePath.saveImg();
		}
		
		try {
			ai.join(1000);
			thread.join();
		} catch(Exception e) {
			System.out.println("Error occured while closing");
		}
	}
	
	public synchronized void stop(final boolean lost) {

		AILogic.halt( );
		
		if(mousePath != null) {
			mousePath.saveImg();
		}

		if (lost) {
			System.out.println("Hit a mine!");
		}else {
			System.out.println("Won game!");
		}

		try {
			wait(2000);
			if (debug) {
				wait( );
			}
			ai.join(1000);
			thread.join(2000);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace( );
		}
	}

	public void restart() {
//		frame.remove(game);
		
		game = new MineSweeper();
		
//		frame.add(game);
		frame.pack( );

		frame.setVisible(true);
		
		thread = new Thread(game, "Main Thread");
		thread.start();
	}
}
