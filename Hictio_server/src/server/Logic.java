package server;

import java.util.Observable;
import java.util.Observer;

import processing.core.*;

public class Logic implements Observer {

	/**
	 * Notas de martes 18 de 2018 - 9pm Se agregó processing Serial para recibir
	 * 0,1y2 del Arduino. cada numero manda al cliente conetado, cabeza, cuerpo,
	 * cola respectivamente.
	 **/
	/**
	 * Notas de martes 25 de 2018 - 4:50 pm Se agregó comunicacion con Makey Makey,
	 * para leer 9 caracteres.
	 **/
	private char[][] fishKeys = { { 'w', 'a', 's' }, { 'd', 'f', 'g' } };
	private PApplet p;

	public Logic(PApplet p) {
		this.p = p;
		Server.getInstance(this, 5000);
	}

	public void execute() {

		p.background(255);

	}

	@Override
	public void update(Observable o, Object arg) {

	}

	public void keyPressed() {

		for (int i = 0; i < fishKeys.length; i++) {
			for (int j = 0; j < fishKeys[i].length; j++) {
				if (p.key == fishKeys[i][j]) {

					System.out.println("Fish: " + i + " Key touched: " + fishKeys[i][j]);

					Server.getInstance(this, 5000).verifyFish(i, j);

				}
			}
		}

	}

	public void shotdown() {
		System.out.println("ShotDown");
		Server.getInstance(this, 5000).closeServer();
	}

}
