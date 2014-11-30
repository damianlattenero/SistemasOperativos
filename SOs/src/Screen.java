import java.util.concurrent.ConcurrentLinkedQueue;


public class Screen {
	
	ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	
	public boolean showed(String text){
		return this.queue.contains(text);
	}
	
	public void show(String text){
		System.out.println(text);
		this.queue.add(text);
	}

}
