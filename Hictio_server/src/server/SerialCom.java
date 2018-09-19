package server;

import java.io.InputStream;
import java.util.Observable;

import com.fazecast.jSerialComm.SerialPort;

public class SerialCom extends Observable implements Runnable {
	private char soundTrack;
	private SerialPort comPort;
	private InputStream in;

	public SerialCom() {
		// TODO Auto-generated constructor stub
		this.comPort = SerialPort.getCommPorts()[1];
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			//SerialPort comPort = SerialPort.getCommPorts()[1];
			comPort.openPort();
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
			this.in = comPort.getInputStream();

			try {
				while (true) {
					while (comPort.bytesAvailable() == 0)
						Thread.sleep(100);
					soundTrack = (char) in.read();
					System.out.println(soundTrack);
				
					switch (soundTrack) {
					case '0':
						setChanged();
						notifyObservers(new String("head"));
						clearChanged();
						in.close();
						break;
					case '1':
						setChanged();
						notifyObservers(new String("middle"));
						clearChanged();
						in.close();
						break;
					case '2':
						setChanged();
						notifyObservers(new String("tail"));
						clearChanged();
						in.close();
						break;

					default:
						break;
					}
			
					//in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			comPort.closePort();
		}

	}

}
