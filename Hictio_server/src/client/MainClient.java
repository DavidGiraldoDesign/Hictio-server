package client;

import processing.core.*;

public class MainClient extends PApplet {

	private Logic l;
	
	public static void main(String[] args) {
		PApplet.main("client.MainClient");
		
	}
	
	@Override
	public void settings() {
		//super.settings();
		size(250, 100);
	}

	@Override
	public void setup() {
		//super.setup();
		l = new Logic(this);
	}

	@Override
	public void draw() {
		//super.draw();
		l.execute();
	}

	@Override
	public void mousePressed() {
		//super.mousePressed();
		l.mouse();
	}
	
	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub
		//super.keyPressed();
		l.key();
		
	}
	
}
