import java.util.concurrent.ConcurrentLinkedQueue;


public class ReadyState extends ProcessState {

	public ReadyState(ConcurrentLinkedQueue<PCB> readyQueue) {
		super();
		this.list = readyQueue;
	}

	public ReadyState() {
		super();
	}

	public void nextState(PCB pcb){
		super.nextState(pcb);
        pcb.setRunning();
	}
	
	
	
}
