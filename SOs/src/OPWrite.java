
public class OPWrite extends OPMemory {
	

	Instruction value;
	

	public OPWrite(Double quantum, int source, int dest, Instruction value) {
		super(quantum, source, dest);
		this.value = value;
	}

	@Override
	public String toString() {
		
		return "OPWrite " + this.value + this.params;
	}
	
	
	public void run(int pid, MMU mmu) {
		 mmu.write(pid, this.value, this.getDest());
	}

}
