package radar.sweeper.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nick.sweeper.main.Grid;

public class MousePath {
	private BufferedImage img;
	
	public void hoveredLocation(int x, int y) {
		if(x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
			return;
		}
		img.setRGB(x, y, Color.WHITE.getRGB());
	}
	
	public MousePath(Grid grid) {
		img = new BufferedImage(grid.renderSize().width, grid.renderSize().height, BufferedImage.TYPE_INT_RGB);
		System.out.println(grid.renderSize());
	}
	
	public void saveImg(String filename) {
		File file = new File(filename + ".png");
		
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			System.out.println("Unable to save image");
		}
		System.out.println("Saved image as "+filename+".png");
	}
}
