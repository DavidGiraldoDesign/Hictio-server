package server; //Packege's name in min

import processing.core.*; //Import processing


public class MainServer extends PApplet { // Main have to extend from Processing
	private Logic l; // Instance of Logic


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("server.MainServer"); // Important!

	}

	@Override
	public void settings() {
		super.settings();
		size(200, 200);
	}

	@Override
	public void setup() {
		super.setup();
		l = new Logic(this);
		frameRate(60);
	
	}

	@Override
	public void draw() {
		// super.draw();
		l.execute();

	}
	
	@Override
		public void keyPressed() {
			l.keyPressed();
		}

	@Override
	public void exit() {
		l.shotdown();
		super.exit();
	}

}
