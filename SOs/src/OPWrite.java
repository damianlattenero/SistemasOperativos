
public class OPWrite extends OPMemory {
	

	String value;
	int dest;


	

	public OPWrite(int dest, String string) {
		super(0.5);
		this.dest = dest;
		this.value = string;
	}

	@Override
	public String toString() {
		
		return "OPWrite " + this.value + this.params;
	}
	
	
	
	public String getValue() {
		return value;
	}

	public int getDest() {
		return dest;
	}

	public void run(int pid, MMU mmu) {
		 mmu.write(pid, this.value, this.getDest());
	}

}
