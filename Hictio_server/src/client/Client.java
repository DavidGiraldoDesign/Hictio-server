package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Client extends Observable implements Runnable {

	private static Client client = null;
	private Socket socket = null;
	private static boolean online;
	private int port;

	private Client(Observer observer) {
		// System.out.println("constructor");
		addObserver(observer);
		this.port = 5000;
		this.startSocket();
	}

	public void startSocket() {
		if (this.socket == null) {
			try {
				System.out.println("Starting to socket");
				// Use "127.0.0.1" to connect to the local host - The pc itself
				this.socket = new Socket(InetAddress.getByName("127.0.0.1"), this.port);
				// Use the IP that shows the Server console to connect.
				connectionRequest();
				online = true;
				// ===================================
				new Thread(client).start();
				// ===================================
				System.out.println("Connection state: " + online);
			} catch (IOException e) {
				setChanged();
				notifyObservers("server_outline");
				System.err.println("Connection state: " + online);
				clearChanged();
			}
		} else {
			System.out.println("You are aready connected");
		}

	}

	public static Client getInstance(Observer observer) {
		// System.out.println("Enter to getInstance");
		// System.err.println("Online: "+online + " Instance: " + client);
		if (client == null && online == false) {
			client = new Client(observer);
			// new Thread(client).start();
			// System.err.println("Online: "+online + " Instance: " + client);
		}
		return client;
	}

	@Override
	public void run() {
		while (online) {
			this.receiveString();
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void receiveString() {
		try {
			DataInputStream input = new DataInputStream(this.socket.getInputStream());
			String message = input.readUTF();
			System.out.println(message);
			if (message.contains("x")) {
				this.forceDisconnection();
			}
		} catch (IOException e) {
			online = false;
			setChanged();
			notifyObservers("server_outline");
			clearChanged();

		}
	}

	public void sendString(String message) {
		if (this.socket != null) {
			try {
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				output.writeUTF(message);
				output.flush();
				System.out.println("Send: " + message);
			} catch (IOException e) {
				e.printStackTrace();
				online = false;
				this.forceDisconnection();
				// =========================> little change
			}
		}

	}

	private void connectionRequest() {
		System.out.println("Send_connection_request");
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// int envio = (int) (Math.random() * 255);
			//output.writeUTF("conect");
			output.writeUTF("afg987as");
			output.flush();
			System.out.println("Connection_request_sended");
		} catch (IOException e) {
			e.printStackTrace();
			online = false;
			System.out.println("I die");
		}
	}

	public void forceDisconnection() {
		if (this.socket != null) {
			try {
				this.socket.close();
				this.socket = null;
				online = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("You are aready disconnected");
		}

	}

}
