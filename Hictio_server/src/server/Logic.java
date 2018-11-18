package server;

import java.util.Observable;
import java.util.Observer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
//import processing.core.*;

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
	/**
	 * Notas de sábado 6 de octubre de 2018, se revincula la comuniacion serial con
	 * Arduino. Esperanod recibir un ID unico (UID) de una tarjeta NFC. La clase
	 * ClientAttention tendrá una variable UID vinuclada, proviniente del cliente
	 * que se conecte.
	 */

	// =========================================== Using Minim
	private MainServer p;
	private Minim minim;
	private AudioPlayer beep;
	// ================================================= Using a PCD
	private boolean allowTouchPCD_A = false;
	private int timerToAllowTouch = 0;
	private String PCDClient = "";

	private char[][] fishKeys = { { 'w', 'a', 's' }, { 'd', 'f', 'g' } };
	private String[] fishNames = { "oscar", "piranha", "ghost" };

	public Logic(MainServer p) {
		this.p = p;
		this.minim = new Minim(p);
		beep = minim.loadFile("music/Beep_Short.mp3");
		Server.getInstance(this, 5000);
	}

	int seconds(int s) {
		return 60 * s;
	}

	public void execute() {
		//System.out.println(timerToAllowTouch+ " >= "+seconds(10));
		p.background(255);
		if (allowTouchPCD_A == true) {
			timerToAllowTouch++;
			if (timerToAllowTouch >= seconds(10)) {
				allowTouchPCD_A = false;
				PCDClient = "";
				System.out.println("allowTouchPCD_A: " + allowTouchPCD_A);
				System.out.println("PCDClient: " + PCDClient);
				
			}
		}

	}

	// Play beep
	private void play(AudioPlayer a) {
		a.play();
		if (a.position() >= 800) {
			a.rewind();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String msn = ((String) arg);
			if (msn.contains("haptic")) {

				this.play(beep);

			} else if (msn.contains("PC")) {
				timerToAllowTouch = 0;

				try {
					if (allowTouchPCD_A == false) {
						allowTouchPCD_A = Boolean.parseBoolean(msn.split("-")[1]);
						PCDClient = msn.split("-")[2];
						Server.getInstance(this, 5000).sendMoldelsAboutToTouch(PCDClient);
						System.out.println("allowTouchPCD_A: " + allowTouchPCD_A);
						System.out.println("PCDClient: " + PCDClient);

					} else {

					}

				} catch (ArrayIndexOutOfBoundsException e) {
					this.allowTouchPCD_A = false;
				}
			}
		}

	}

	public void keyPressed() {

		Server.getInstance(this, 5000).sendFakeBeacon(p.key);
		if (allowTouchPCD_A == true) {
			for (int i = 0; i < fishKeys.length; i++) {
				for (int j = 0; j < fishKeys[i].length; j++) {
					if (p.key == fishKeys[i][j]) {
						timerToAllowTouch = 0;
						System.out.println("Fish: " + fishNames[i] + " Key touched: " + fishKeys[i][j]);
						// Server.getInstance(this, 5000).verifyFish(i, j);
						Server.getInstance(this, 5000).sendModelPartTouching(PCDClient, fishNames[i], fishKeys[i][j]);
					}
				}
			}
		}

	}

	public void shotdown() {
		System.out.println("ShotDown");
		Server.getInstance(this, 5000).closeServer();
	}

}
