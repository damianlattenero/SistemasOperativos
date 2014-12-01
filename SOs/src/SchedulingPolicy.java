import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SchedulingPolicy {
	
	private ConcurrentLinkedQueue<PCB> queue = new ConcurrentLinkedQueue<PCB>();
	
	public ConcurrentLinkedQueue<PCB> getQueue(){
		return queue;
	}
	
	public PCB nextProcess(){
		return this.getQueue().element();
	}
	
	public boolean interruptCondition(PCB process, CPU cpu){
		if (process.haveNextInstruct()){
			cpu.setPC(process.getId(), process.getInstrs().get(process.getCurrentInst()));
			return !(cpu.fetch().isIo());
		}
		
		return false;
	}
	
	public void executePolicy(PCB process, CPU cpu){
		cpu.setPC(process.getId(), process.nextInstr());
        cpu.execute();
	}
	
	public void execute(PCB process, CPU cpu){
        System.out.println("Executing Process With ID " + process.getId());
        
        cpu.setRegisters(process.getRegisters());
        
        cpu.setPC(process.getId(), process.getCurrentInstruction());
        
        while(this.interruptCondition(process, cpu)){
        	this.executePolicy(process, cpu);
        }
            
        process.setRegisters(cpu.getRegisters());
	}

}
