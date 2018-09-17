package server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import processing.core.*;

public class Logic implements Observer {

	private PApplet p;
	private LinkedList<Element> elements;

	public Logic(PApplet p) {
		this.p = p;
		this.elements = new LinkedList<>();
		this.elements.add(new Element(p.width / 2, p.height / 2));
		this.elements.add(new Element(p.width / 3, p.height / 2));
		this.elements.add(new Element(p.width / 5, p.height / 2));
		this.elements.getLast().start();
		Server.getInstance(this, 5000);
	}

	public void execute() {
		p.background(255);

		Iterator<Element> iter = elements.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			display(element.getX(), element.getY());
		}
	}

//	public void runUI(PApplet processing) {
//	
//	}

	private void display(int x, int y) {
		p.fill(0);
		p.rect(x, y, 50, 50);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void shotdown() {
		System.out.println("ShotDown");
		Server.getInstance(this, 5000).closeServer();
	}

}
