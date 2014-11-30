import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Loader extends PCB {

	
	


	


	public Loader(List<Instruction> instrs, int id, int currentInst,
			StateHandler stateHandler, int priority, IO_Manager iOManager,
			LTS lts, ConcurrentHashMap<String, Integer> neededDevices,
			List<Instruction> list, int pid, Program program) {
		super(instrs, id, currentInst, stateHandler, priority, iOManager, lts,
				neededDevices, list);
		
		LoadInstr in = new LoadInstr(0.5, lts.mmu_policy, program, pid);
		this.getInstrs().add(in);
	}



}
