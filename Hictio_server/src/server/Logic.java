package server;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import com.fazecast.jSerialComm.SerialPort;

import processing.core.*;

public class Logic implements Observer {

	/**
	 * Notas de martes 18 de 2019 - 9pm Se agregó processing Serial para recibir
	 * 0,1y2 del Arduino. cada numero manda al cliente conetado, cabeza, cuerpo,
	 * cola respectivamente.
	 **/

	private PApplet p;
	private LinkedList<Element> elements;

	// ==============================================

	// ==============================================

	public Logic(PApplet p) {
		this.p = p;
		this.elements = new LinkedList<>();

		Server.getInstance(this, 5000);

	}


	public void execute() {

		p.background(255);
		if (elements.size() > 0) {

			synchronized (elements) {

				Iterator<Element> iter = elements.iterator();
				while (iter.hasNext()) {
					Element element = (Element) iter.next();
					display(element.getX(), element.getY(), element.getFish());

				}
			}

		}

	}

	private void display(int x, int y, int fish) {
		p.fill(0);
		p.rect(x, y, 50, 50);
		p.fill(255);
		p.textSize(32);
		p.text(fish, x + 25, y + 25);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		if (arg instanceof String) {
			String msn = (String) arg;
			if (msn.contains("fish")) {
				System.out.println("=====" + arg);
				int fish = Integer.parseInt(msn.split("-")[1]);
				synchronized (elements) {
					elements.add(
							new Element(((int) p.random(0, p.width - 50)), ((int) p.random(0, p.height - 50)), fish));
					elements.getLast().start();
					System.out.println("Elementos: " + elements.size());
				}
			}

		}

	}

	public void shotdown() {
		System.out.println("ShotDown");
		Server.getInstance(this, 5000).closeServer();
	}

}
