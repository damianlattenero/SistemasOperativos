
public class RoundRobinPolicy extends SchedulingPolicy {
	
	double quantum;
	double quantumLeft;

	

	public RoundRobinPolicy(double quantum) {
		super();
		this.quantum = quantum;
	}

	public double getQuantum() {
		return quantum;
	}

	public void setQuantum(double quantum) {
		this.quantum = quantum;
	}
	
	public boolean interruptCondition(PCB process, CPU cpu){
		if(super.interruptCondition(process, cpu)){
			cpu.setPC(process.getId(), process.getInstrs().get(process.getCurrentInst()));
			return (this.quantumLeft > cpu.fetch().quantum) && (!cpu.fetch().isIo());
		}else{			
			return false;
		}
	}
	
	public void executePolicy(PCB process, CPU cpu){
        cpu.setPC(process.getId(), process.nextInstr());
        cpu.execute();
        this.quantumLeft -= cpu.fetch().getQuantum();
	}
	
	public void execute(PCB process, CPU cpu){
        this.quantumLeft = this.getQuantum();
                
        System.out.println("Executing Process With ID " + process.getId());
        
        cpu.setRegisters(process.getRegisters());
        
        cpu.setPC(process.getId(), process.getCurrentInstruction());
        
        while( this.interruptCondition(process, cpu)){
        	this.executePolicy(process, cpu);
        }
            
        process.setRegisters(cpu.getRegisters());
	}
	

}
