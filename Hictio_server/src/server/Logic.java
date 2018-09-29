package server;

import java.util.Observable;
import java.util.Observer;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;


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
	/**
	 * Notas de sábado 29 de 2018 - 4:47 pm, se agregó Audio en el servidor para
	 * localizarlo dentro de una habitación. Usando Minim
	 */
	private char[][] fishKeys = { { 'w', 'a', 's' }, { 'd', 'f', 'g' } };
	// private PApplet p;
	private MainServer p;

	// =========================================== Using Minim

	private Minim minim;
	private AudioPlayer beep;

	// public Logic(PApplet p) {
	public Logic(MainServer p) {
		this.p = p;
		this.minim = new Minim(p);
		beep = minim.loadFile("music/Beep_Short.mp3");
		Server.getInstance(this, 5000);
	}

	public void execute() {

		p.background(255);

	}

	//Play beep
	private void play(AudioPlayer a) {
			
		System.out.println("Posistion before= "+a.position()+" Length(): "+a.length());
			a.play();
	
		if (a.position() >= 800) {
			//a.pause();
			a.rewind();
		}
			System.out.println("Posistion after= "+a.position()+" Length(): "+a.length());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String string = (String) arg;
			if (string.contains("haptic")) {
				if (beep.isPlaying() == false) {
					beep.play();
					beep.rewind();
					
				}
			}

		}
	}

	public void keyPressed() {

		Server.getInstance().sendFakeBeacon(p.key);
		this.play(beep);
		for (int i = 0; i < fishKeys.length; i++) {
			for (int j = 0; j < fishKeys[i].length; j++) {
				if (p.key == fishKeys[i][j]) {
					System.out.println("Fish: " + i + " Touching key: " + fishKeys[i][j]);
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
