package pl.duncol.truckito.utils;

public class AsyncThread implements Runnable {
	
	private Runnable action;
	
	public AsyncThread(Runnable action) {
		this.action = action;
	}

	@Override
	public void run() {
		new Thread(action).start();
	}
}
