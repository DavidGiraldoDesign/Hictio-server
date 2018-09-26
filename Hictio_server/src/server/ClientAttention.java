package server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

public class ClientAttention extends Observable implements Runnable {

	// Socket that writes and read, it conects the client to the server
	private Socket socket_atention;
	private boolean online;
	private int id;
	private int fishId;
	private boolean onFish = false;

	public ClientAttention(Socket reference, int id) {
		this.socket_atention = reference;
		this.online = true;
		this.id = id;
		answerClientRequest();
	}

	// ---------------------------------------------------
	public int getFishId() {
		return fishId;
	}

	public void setFishId(int fishId) {
		this.fishId = fishId;
	}

	public boolean isOnFish() {
		return this.onFish;
	}

	public void setOnFish(boolean onFish) {
		this.onFish = onFish;
	}

	private void disconnectFish(String val) {
		if (val.contains("out")) {
			this.fishId = 0;
			this.onFish = false;
			System.out.println("Client: "+this.getId()+ " Out fish!");
		}
	}
	// ---------------------------------------------------

	public int getId() {
		return id;
	}

	public Socket getSocket_atention() {
		return socket_atention;
	}

	@Override
	public void run() {
		while (online) {
			// Blocking operation, ClientAttention is waiting for the client
			receiveString();
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void answerClientRequest() {
		// Input
		DataInputStream input = null;
		try {
			input = new DataInputStream(socket_atention.getInputStream());
			String request = input.readUTF();
			if (request.contains("conect")) {
				sendString("acuario");
				System.out.println("connection_accepted");
			}
		} catch (IOException e) {
			e.printStackTrace();
			disconnect_client(input);
		}
	}

	private void disconnect_client(DataInput input) {
		setChanged();
		System.out.println("Connection lost with:" + id);
		this.online = false;
		try {
			((FilterInputStream) input).close();
			this.socket_atention.close();
			this.socket_atention = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyObservers("off");
		clearChanged();
	}

	private void receiveString() {
		DataInputStream input = null;
		try {
			input = new DataInputStream(socket_atention.getInputStream());
			// int val = Integer.parseInt(input.readUTF());
			String val = input.readUTF();
			disconnectFish(val);
			setChanged();
			notifyObservers(val);
			clearChanged();
			System.out.println("Receive: " + val + " from user " + this.getId());
		} catch (IOException e) {
			disconnect_client(input);
		}
	}

	public void sendString(String message) {
		try {
			DataOutputStream output = new DataOutputStream(socket_atention.getOutputStream());
			output.writeUTF(message);
			// =========================
			output.flush();
			// =========================
			//System.out.println("Message: " + message + " / send to client: " + this.id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
