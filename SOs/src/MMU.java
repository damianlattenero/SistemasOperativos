import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MMU {
	
	
	MMUPolicy policy;
	PhysicalMemory memory;
	ConcurrentHashMap<Integer, List<Instruction>> secondaryStore; 
	
	public MMU(PhysicalMemory memory, MMUPolicy policy) {
		super();
		this.memory = memory;
		this.policy = policy;
	}
	
	public MMU(PhysicalMemory memory) {
		super();
		this.memory = memory;
		this.policy = new MMUPolicy_Continua(memory);
	}

	public synchronized PhysicalMemory getMemory() {
		return memory;
	}

	public synchronized MMUPolicy getPolicy() {
		return policy;
	}

	public synchronized void write(int ref, Instruction value, int adress){
		this.getPolicy().write(ref,value, adress);
	}

	public synchronized Instruction read(Integer pid, int instr) {
		return this.getPolicy().read(pid, instr);
	}

}
