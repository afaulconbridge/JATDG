package jatdg;

import java.awt.event.InputEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jate.map.TileMap;
import jate.map.TokenMap;

public class SimRunnable implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TileMap tileMap;
	private TokenMap tokenMap;
	private ConcurrentLinkedQueue<InputEvent> eventQueue;

	public SimRunnable(TileMap tileMap, TokenMap tokenMap, ConcurrentLinkedQueue<InputEvent> queue) {
		super();
		this.tileMap = tileMap;
		this.tokenMap = tokenMap;
		this.eventQueue = queue;
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
		//handle events
		InputEvent event = null;
		while ((event = eventQueue.poll()) != null) {
			log.info(event.toString());
		}
		//step simulation
	}

}
