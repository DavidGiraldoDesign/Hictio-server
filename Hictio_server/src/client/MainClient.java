package client;

import processing.core.*;

public class MainClient extends PApplet {

	private Logic l;
	
	public static void main(String[] args) {
		PApplet.main("client.MainClient");
		
	}
	
	@Override
	public void settings() {
		size(250, 100);
	}

	@Override
	public void setup() {
		l = new Logic(this);
	}

	@Override
	public void draw() {
		l.execute();
	}

	@Override
	public void mousePressed() {
		l.mouse();
	}
	
}
