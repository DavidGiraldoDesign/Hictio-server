package server;

public class Element extends Thread {
	private int x, y,fish;

	public Element(int x, int y, int fish) {
		this.x = x;
		this.y = y;
		this.fish=fish;
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
	
	public int getFish() {
		return this.fish;
	}

}
