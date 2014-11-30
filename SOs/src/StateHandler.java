import java.util.concurrent.ConcurrentLinkedQueue;


public class StateHandler {
	
	NewState newState; 
	ReadyState readyState; 
	RunningState runningState ;
	WaitingIOState waitingIOState;
	RunningIOState runningIOState ;
	FinishedState finishedState;
	
	public StateHandler(ConcurrentLinkedQueue<PCB> readyQueue) {
		super();
		this.newState = new NewState();
		this.readyState = new ReadyState(readyQueue);
		this.runningState = new RunningState();
		this.waitingIOState = new WaitingIOState();
		this.runningIOState = new RunningIOState();
		this.finishedState = new FinishedState();
	}
	
	

	public ProcessState get_firstState(PCB pcb){
		this.newState.addProcess(pcb);
        return this.getNewState();
	}

	public void changeQueues(PCB pcb, ProcessState iniState, ProcessState endState) {
		iniState.deleteProcess(pcb);
        endState.addProcess(pcb);
	}

	public NewState getNewState() {
		return newState;
	}

	public ReadyState getReadyState() {
		return readyState;
	}

	public RunningState getRunningState() {
		return runningState;
	}

	public WaitingIOState getWaitingIOState() {
		return waitingIOState;
	}

	public RunningIOState getRunningIOState() {
		return runningIOState;
	}

	public FinishedState getFinishedState() {
		return finishedState;
	}


	


}
