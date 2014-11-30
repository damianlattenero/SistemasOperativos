import java.util.Queue;


public class ReadyState extends ProcessState {

	public ReadyState(Queue<PCB> readyQueue) {
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
