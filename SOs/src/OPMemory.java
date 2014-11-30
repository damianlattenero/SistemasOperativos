
public class OPMemory extends Instruction {
	
	int source;
	int dest;

	public OPMemory(Double quantum) {
		super(quantum);
	}
	
	public OPMemory(Double quantum, int source, int dest) {
		super(quantum);
		this.source = source;
		this.dest = dest;
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

	public int getSource(){
		return this.source;
				
	}
	
	public int getDest(){
		return this.dest;
				
	}
	

}
