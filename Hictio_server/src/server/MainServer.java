package server; //Packege's name in min

import processing.core.*; //Import processing

public class MainServer extends PApplet { // Main have to extend from Processing
	private Logic l; // Instance of Logic

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("server.MainServer"); //Important!

	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		super.settings();
		size(800, 800);
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		super.setup();
		l = new Logic(this);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		l.execute();
	}

}
