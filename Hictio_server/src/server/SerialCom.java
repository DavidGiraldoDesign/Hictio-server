package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

import com.fazecast.jSerialComm.SerialPort;

public class SerialCom extends Observable implements Runnable {
	private String sensorCardUID = "";
	private SerialPort comPort;
	private InputStream in;

	public SerialCom() {
		this.comPort = SerialPort.getCommPorts()[1];
	}

	@Override
	public void run() {
		while (true) {
			comPort.openPort();
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 50, 0);
			this.in = comPort.getInputStream();

			while (comPort.bytesAvailable() == 0)

			
				try {
					
					Thread.sleep(1);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			if (comPort.bytesAvailable() > 0) {
				for (int i = 0; i < 8; i++) {
					try {
						this.sensorCardUID += ((char) in.read());
				
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				setChanged();
				notifyObservers(new String("PC-true-" + this.sensorCardUID));
				clearChanged();
				this.sensorCardUID = "";
			}

			comPort.closePort();

		}

	}

}
