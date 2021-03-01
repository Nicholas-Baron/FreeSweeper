package nick.sweeper.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import nick.sweeper.ai.AILogic;
import radar.sweeper.draw.MousePath;

public class Input implements MouseListener, MouseMotionListener, KeyListener {

	private static int		mX, mY;

	private static boolean	mouseIn	= false;

	public static boolean isMouseIn( ) {

		return mouseIn;
	}

	public static int mouseX( ) {

		return mX;
	}

	public static int mouseY( ) {

		return mY;
	}

	private Grid g;
	
	private MousePath mousePath;
	
	public Input() {
	}

	public void setGrid(Grid g) {
		this.g = g;
	}
	
	public void setMousePath(MousePath mp) {
		this.mousePath = mp;
	}
	
	@Override
	public void keyPressed(final KeyEvent e) {

		if (e.getKeyCode( ) == KeyEvent.VK_A) {
			if(!MineSweeper.getAI().isAlive()) {
				MineSweeper.getAI().start();
			}else {
				AILogic.halt();
			}
		}
	}

	@Override
	public void keyReleased(final KeyEvent arg0) {

	}

	@Override
	public void keyTyped(final KeyEvent e) {

	}

	@Override
	public void mouseClicked(final MouseEvent e) {

//		g.onClick(e.getX( ), e.getY( ), e.getButton( ) != MouseEvent.BUTTON1);

	}

	@Override
	public void mouseDragged(final MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(final MouseEvent arg0) {

		mouseIn = true;
	}

	@Override
	public void mouseExited(final MouseEvent arg0) {

		mouseIn = false;
	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		mX = e.getX( );
		mY = e.getY( );
		
		if(mousePath != null) {
			mousePath.hoveredLocation(mX, mY);
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {

		if(e.getButton() == MouseEvent.BUTTON1) {
			g.onClick(e.getX( ), e.getY( ), false);
			return;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			g.onClick(e.getX( ), e.getY( ), true);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent arg0) {

	}

}
