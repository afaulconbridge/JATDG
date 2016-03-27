package jatdg;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.event.MouseInputListener;

public class QueueingEventHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	
	private final ConcurrentLinkedQueue<InputEvent> queue;

	public QueueingEventHandler(ConcurrentLinkedQueue<InputEvent> queue) {
		this.queue = queue;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		queue.add(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		queue.add(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		queue.add(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		queue.add(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		queue.add(e);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		queue.add(e);	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		queue.add(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		queue.add(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		queue.add(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		queue.add(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		queue.add(e);
	}

}
