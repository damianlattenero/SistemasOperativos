
public class OPMov extends OPMemory {

	int source;
	int dest;

	
	public OPMov(int source, int dest) {
		super(0.1);
		this.source = source;
		this.dest = dest;
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
        mmu.getPolicy().write(pid, sourceValue, this.getDest());
	}

}
