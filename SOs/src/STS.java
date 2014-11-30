import java.util.concurrent.ConcurrentLinkedQueue;


public class STS extends Thread{
	
	ConcurrentLinkedQueue<PCB> readyQueue;
	SchedulingPolicy policy;
	CPU cpu;
	OperatingSystem os;
	StateHandler stateHandler;
	IO_Manager io_Manager;
	
	

	public STS(SchedulingPolicy policy,CPU cpu, OperatingSystem os, IO_Manager io_Manager) {
		super();
		this.readyQueue = policy.getQueue();
		this.policy = policy;
		this.cpu = cpu;
		this.os = os;
		this.stateHandler = new StateHandler(this.readyQueue);
		this.io_Manager = io_Manager;
	}
	
	

	public ConcurrentLinkedQueue<PCB> getReadyQueue() {
		return readyQueue;
	}



	public CPU getCpu() {
		return cpu;
	}



	public IO_Manager getIo_Manager() {
		return io_Manager;
	}
	
	public void putProcess(PCB pcb) {
		pcb.nextState();
	}
	
	public PCB nextProcess(){
		return this.policy.nextProcess();
	}
	
	public void execute(PCB process){
        process.nextState();
        this.policy.execute(process, this.getCpu());
        process.nextState();
        
        if(process.isWaitingIO()){
        	this.getIo_Manager().takePCB(process);
        }
	}
	
	public boolean haveAnyProcess(){
		return !this.readyQueue.isEmpty();
	}
	
	public int lengthQueue(){
		return this.readyQueue.size();
	}
	
	public boolean processIsWaiting(PCB pcb){
		return this.readyQueue.contains(pcb);
	}

	@Override
	public void run() {
        while(this.os.isUp){
        	if(this.haveAnyProcess()){
        		PCB nextP = this.nextProcess();
   				this.execute(nextP);
        		
        	}else{
        		System.out.println("No process to execute is available");
        		try {
					sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
        	
        }
	}
}
