package jatdg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jate.map.TileMap;
import jate.map.TokenMap;
import jate.render.TileRenderer;
import jate.render.TokenRenderer;

public class RenderRunnable implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TileMap tileMap;
	private TokenMap tokenMap;
	private JFrame frame;
	
	private int offsetX = 0;
	private int offsetY = 0;

	public RenderRunnable(TileMap tileMap, TokenMap tokenMap, JFrame frame) {
		super();
		this.tileMap = tileMap;
		this.tokenMap = tokenMap;
		this.frame = frame;
	}

	@Override
	public void run() {
		while (true) {
			long timeStepStart = System.nanoTime();
			step(timeStepStart);
			long timeStepEnd = System.nanoTime();
			int intervalMs = (int)((timeStepEnd-timeStepStart) / 1000);
			log.trace("Interval "+intervalMs+"ms");
			/*
			int sleep = 100-intervalMs;
			if (sleep > 0) {
				try {
					Thread.sleep(100-intervalMs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					throw e;
				}
			}
			*/
		}
	}

	private void step(long timeStepStart) {
        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        Graphics g = null;
        try {
        	g = bufferStrategy.getDrawGraphics();
            Graphics2D gg = (Graphics2D) g;

            //now do some background drawing
            gg.setColor(Color.black);
            gg.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            
    		Rectangle r = getVisibleRect();    		
    		if (tileMap != null) {
        		TileRenderer.render(gg, tileMap, r.x, r.y, r.x + r.width, r.y + r.height);
    		}
    		if (tokenMap != null) {
        		TokenRenderer.render(gg, tokenMap, r.x, r.y, r.x + r.width, r.y + r.height);
    		}
            
            //flip to next buffer
            bufferStrategy.show();
        } finally {
	        //ensure cleanup
	        if (g != null) {
	        	g.dispose();
	        	g = null;
	        }
        }
	}

	private Rectangle getVisibleRect() {
		return new Rectangle(offsetX, offsetY, frame.getWidth(), frame.getHeight());
	}

}
