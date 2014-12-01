
public class OPMemory extends Instruction {
	
	
	public OPMemory(Double quantum) {
		super(quantum);
	}
	
	public OPMemory(Double quantum, int source, int dest) {
		super(quantum);
	}

	@Override
	public String toString() {
		return "OP Memory";
	}
	
	public boolean isOpMemory(){
		return true;
	}
	
	public void run(Integer pid, MMU mmu) {
		//do nothing
		
	}

	

}
