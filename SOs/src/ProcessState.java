import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ProcessState {
	
	protected Queue<PCB> list = new ConcurrentLinkedQueue<PCB>();
	

	public void nextState(PCB pbc) {
		// pass
	}
	
	public Queue<PCB> getQueue() {
		return list;
	}

	public void deleteProcess(PCB pcb) {
		this.list.remove(pcb);
	}

	public void addProcess(PCB pcb) {
		this.list.add(pcb);
	}
	
	public boolean includesProcess(PCB pcb){
		return this.list.contains(pcb);
	}
	
	

}
