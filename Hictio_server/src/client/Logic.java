package client;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logic implements Observer {
	private PApplet app;
	//private Client c = Client.getInstance(this);

	public Logic(PApplet app) {
		this.app = app;
		//Client.getInstance(this);
	}

	public void execute() {
		app.background(0);

		for (int i = 0; i < 4; i++) {

			app.fill(255);
			app.ellipse(50 + (i * 50), 50, 20, 20);
		}

	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof String) {
			if (((String) arg).contains("server_outline")) {
				//System.err.println("La conexion con el Servidor fallo, por favor intente nuevamente");
			}
		}
	}

	public void mouse() {
		for (int i = 0; i < 4; i++) {
			if (PApplet.dist(app.mouseX, app.mouseY, 50 + (i * 50), 50) < 10) {
				app.fill(255, 0, 0);
				app.ellipse(50 + (i * 50), 50, 20, 20);
				switch (i) {
				case 0:
					Client.getInstance(this).startSocket();
					break;
				case 1:
					Client.getInstance(this).forceDisconnection();
					break;
				case 2:
					Client.getInstance(this).sendString("fish");
					break;
				case 3:

				}
			}
		}
	}

}
