package processor;

public class ThreadStarter implements Runnable {
	Processing p;
	String orderFile;
	
	public ThreadStarter(Processing p, String orderFile) {
		this.p = p;
		this.orderFile = orderFile;
	}
	
	public void run() {
		p.rOrder(orderFile);
	}
	
}
