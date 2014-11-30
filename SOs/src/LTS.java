import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LTS {

	public static int id = 0;
	int actualSTS_Index = 0;
	ConcurrentLinkedQueue<PCB> jobQueue = new ConcurrentLinkedQueue<PCB>();
	IO_Manager iOManager;
	MMUPolicy mmu_policy;
	List<STS> stss = new ArrayList<STS>();

	public LTS(IO_Manager iOManager, MMUPolicy mmu_policy, List<STS> stss) {
		super();
		this.iOManager = iOManager;
		this.mmu_policy = mmu_policy;
		this.stss = stss;
		
	}

	public void nextStsIndex() {
		if ((this.actualSTS_Index) >= (this.stss.size() - 1)) {
			this.actualSTS_Index = 0;
		} else {
			this.actualSTS_Index++;
		}
	}

	public STS getActualSts() {
		return this.stss.get(this.actualSTS_Index);
	}

	public ConcurrentHashMap<String, Integer> makeMapForNeededDevices(
			List<Instruction> instrs) {
		ConcurrentHashMap<String, Integer> result = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap<String, Integer> auxMap = new ConcurrentHashMap<String, Integer>();

		for (Instruction instr : instrs) {
			if (instr.toString() == "alloc") {
				String deviceName = ((IO_Instruction) instr).getDeviceName();
				if (result.containsKey(instr)) {
					Integer newNumber = auxMap.get(deviceName) + 1;
					auxMap.put(deviceName, newNumber);
					if (newNumber > result.get(deviceName)) {
						result.put(deviceName, newNumber);
					}
				} else {
					result.put(deviceName, 1);
					auxMap.put(deviceName, 1);
				}
			} else {
				if (instr.toString() == "unalloc") {
					String deviceName = ((IO_Instruction) instr).getDeviceName();
					if (result.containsKey(instr)) {
						Integer newNumber = auxMap.get(deviceName) - 1;
						auxMap.put(deviceName, newNumber);
					}
				}
			}
		}

		return result;
	}

	public PCB start(Program program, int priority) {
		ConcurrentHashMap<String, Integer> neededDevicesMap = this
				.makeMapForNeededDevices(program.getInstructions());
		if (this.enoughResources(neededDevicesMap)) {
			STS actualSts = this.getActualSts();

			Program programCopy = program.copy();
			List<Instruction> instrs = this.mmu_policy.load(LTS.id,
					programCopy);

			PCB newPCB = new PCB(instrs, LTS.id, this.actualSTS_Index,
					actualSts.stateHandler, priority, iOManager, this,
					neededDevicesMap, program.getInstructions());
			if (!neededDevicesMap.isEmpty()) {
				iOManager.addProcessWithIO(newPCB);
			}
			this.jobQueue.add(newPCB);
			actualSts.putProcess(newPCB);

			LTS.id += 1;
			this.nextStsIndex();
			return newPCB;
		}
		return null;
	}

	public void processTerminated(PCB pcb) {
		this.jobQueue.remove(pcb);
		this.mmu_policy.liberar(pcb.getId());
	}

	public void kill(PCB pcb) {
		this.jobQueue.remove(pcb);
		this.mmu_policy.liberar(pcb.getId());
		pcb.setFinished();
	}

	public boolean includes(PCB pcb) {
		return this.jobQueue.contains(pcb);
	}

	public boolean enoughResources(
			ConcurrentHashMap<String, Integer> neededDevicesMap) {
		return this.iOManager.haveDevices(neededDevicesMap);
	}

}
