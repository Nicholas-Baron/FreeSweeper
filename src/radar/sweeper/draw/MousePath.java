package radar.sweeper.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nick.sweeper.main.Grid;

/**
 * @author Radar
 * A class to plot the path of your cursor as you play minesweeper
 */
public class MousePath {
	private BufferedImage img;
	private String outFile;
	
	/**
	 * Function to tell the canvas that the mouse has hovered a point
	 * @param x The x position of the cursor
	 * @param y The y position of the cursor
	 */
	public void hoveredLocation(int x, int y) {
		if(x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
			return;
		}
		img.setRGB(x, y, Color.WHITE.getRGB());
	}
	
	/**
	 * Creates a new mouse path tracker
	 * @param grid The grid for the minesweeper game
	 */
	public MousePath(Grid grid, String outFile) {
		img = new BufferedImage(grid.renderSize().width, grid.renderSize().height, BufferedImage.TYPE_INT_RGB);
		this.outFile = outFile;
	}
	
	/**
	 * Function to save the mouse path into an image file
	 * @param filename The name of the file to save the output to without extension (png is used)
	 */
	public void saveImg() {
		File file = new File(outFile + ".png");
		
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			System.out.println("Unable to save image");
		}
		System.out.println("Saved image as "+outFile+".png");
	}
}
