
public class OPMov extends OPMemory {
	

	
	

	public OPMov(Double quantum, int source, int dest) {
		super(quantum, source, dest);
	}

	@Override
	public String toString() {
		
		return "OPMov " + this.params;
	}
	
	public int getSource(){
		return this.source;
				
	}
	
	public int getDest(){
		return this.dest;
				
	}
	
	
	public void run(int pid, MMU mmu) {
        Instruction sourceValue = mmu.read(pid, this.getSource());       
        mmu.write(pid, sourceValue, this.getDest());
	}

}
