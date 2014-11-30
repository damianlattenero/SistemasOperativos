import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MMU {
	
	

	PhysicalMemory memory;
	ConcurrentHashMap secondaryStore;
	ConcurrentHashMap<Integer, List<Instruction>> particionesAsignadas;
	
	public ConcurrentHashMap<Integer, List<Instruction>> getParticionesAsignadas() {
		return particionesAsignadas;
	}

	public ConcurrentHashMap getParticionesLibres() {
		return particionesLibres;
	}

	ConcurrentHashMap particionesLibres;
	MMUPolicy policy;
	
	public MMU(PhysicalMemory memory, ConcurrentHashMap secondaryStore,
			MMUPolicy policy) {
		super();
		this.memory = memory;
		this.secondaryStore = secondaryStore;
		this.policy = policy;
	}

	public synchronized void write(int ref, Instruction value, int adress){
		this.getPolicy().write(ref,value, adress);
	}

	public synchronized Instruction read(Integer pid, int instr) {
		return this.getPolicy().read(pid, instr);
	}


	public synchronized PhysicalMemory getMemory() {
		return memory;
	}

	public synchronized MMUPolicy getPolicy() {
		return policy;
	}
	
	public ConcurrentHashMap getSecondaryStore() {
		return secondaryStore;
	}

	public void swapOut(int pid, Object data) {
		// TODO Auto-generated method stub
		
	}



	
}
