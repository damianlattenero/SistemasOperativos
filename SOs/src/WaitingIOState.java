
public class WaitingIOState extends ProcessState {
	
	public void nextState(PCB pcb) {
		super.nextState(pcb);
		pcb.setRunningIO();
	}

}
