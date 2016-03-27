package jatdg;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.InputEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jate.map.TileMap;
import jate.map.TileNestedHashMap;
import jate.map.TokenMap;
import jate.tile.FileTile;
import jate.tile.Tile;

public class App {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		new App().run();
	}

	public void run() {
		JFrame frame = new JFrame();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();

        frame.setTitle("SpaceShoot");
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);//we run at a regular redraw, so ignore repaint requests outside of that
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        device.setFullScreenWindow(frame); //will also make visible
        
        if (frame.getSize().width < 1024 || frame.getSize().height < 768) {
        	throw new RuntimeException("Must be at least 1024x768 resolution");
        }

        frame.createBufferStrategy(2);

		TileMap tileMap = createTileMap();
		TokenMap tokenMap = createTokenMap();

		ConcurrentLinkedQueue<InputEvent> eventQueue = new ConcurrentLinkedQueue<>();
		QueueingEventHandler eventHandler = new QueueingEventHandler(eventQueue);
		frame.addMouseListener(eventHandler);
		frame.addMouseMotionListener(eventHandler);
		frame.addMouseWheelListener(eventHandler);
		frame.addKeyListener(eventHandler);
		
		Thread threadRenderer = new Thread(new RenderRunnable(tileMap, tokenMap, frame));
		Thread threadSim = new Thread(new SimRunnable(tileMap, tokenMap, eventQueue));
				
		frame.setVisible(true);		
		threadRenderer.start();
		threadSim.start();
	}

	private TokenMap createTokenMap() {
		// TODO Auto-generated method stub
		return null;
	}

	private TileMap createTileMap() {
		int sizeX = 1024;
		int sizeY = 1024;
		//create some tiles
		Tile emptyTile = new FileTile("tile_001667", false);
		Tile solidTile = new FileTile("tile_002009", true);
		Tile doorTile = new FileTile("tile_001896", true);
		TileMap tileMap = new TileNestedHashMap(sizeX,sizeY , 32,32);
		//make it all open
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tileMap.set(x, y, emptyTile);
			}
		}
		//make the edges solid
		for (int x = 0; x < sizeX; x++) {
			tileMap.set(x,0, solidTile);
			tileMap.set(x,sizeY-1, solidTile);
		}
		for (int y = 0; y < sizeY; y++) {
			tileMap.set(0,y, solidTile);
			tileMap.set(sizeX-1,y, solidTile);
		}
		return tileMap;
	}
}
