package server;

public class Element extends Thread {
	private int x, y;

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {
		System.out.println("Thread running");
		while (true) {
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

}
