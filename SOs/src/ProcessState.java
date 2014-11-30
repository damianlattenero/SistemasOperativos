import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ProcessState {
	
	protected ConcurrentLinkedQueue<PCB> list = new ConcurrentLinkedQueue<PCB>();
	

	public void nextState(PCB pbc) {
		// pass
	}
	
	public ConcurrentLinkedQueue<PCB> getQueue() {
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
