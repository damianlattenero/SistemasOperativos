import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MainSimulation {
	
	public void simulacionCon(List<CPU> cpus,List<SchedulingPolicy> politicasScheduling,List<Program> programas, 
			MMU mmu,PhysicalMemory memory, ConcurrentHashMap<String, IO_Device> devices){
		OperatingSystem os = new OperatingSystem(politicasScheduling, cpus, mmu,  devices);
		
		System.out.println("Start System"); 
		os.startSystem();
		
		for(Program p : programas){
			os.start(p);
		}
		
		while (!os.lts.jobQueue.isEmpty()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		os.shutDown();
		
		System.out.println("System is ShutDown");
		
		System.out.println("Estado de la memoria: ");
		System.out.println(memory.getCells());
		System.out.println(("Disco Rigido: ")+ mmu.secondaryStore.keys());
		
	}
	
	public void simulacionSegmentacion_10Programas_PrinterYScanner_TodasLasPoliticasScheduling(){
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(new OPWrite(50, new Instruction()));
		instructions.add(new OPWrite(25, new Instruction()));
		instructions.add(new OPMov(25, 50));
		List<String> vars = new ArrayList<String>();
		vars.add("A");
		vars.add("B");
		Program program1 = new Program(instructions, vars, 2);

		List<Program> programas = new ArrayList<Program>();
		
		programas.add(program1);
		
		RoundRobinPolicy rr = new RoundRobinPolicy(0.5);
		RoundRobinWithPriorityPolicy rrwp = new RoundRobinWithPriorityPolicy(10);
		FCFSPolicy fcfs = new FCFSPolicy();
		
		List<SchedulingPolicy> policies = new ArrayList<SchedulingPolicy>();
		policies.add(rr);
		policies.add(rrwp);
		policies.add(fcfs);
		
		PhysicalMemory memory = new PhysicalMemory(64);
		
		MMUPolicy mmu_policy = new MMUPolicy_Continua(memory);
		
		MMU mmu = new MMU(memory,mmu_policy);
		
		Log log = new Log();
		
		CPU cpu1 = new CPU(log, "cpu 1", 0.1f, mmu);
		CPU cpu2 = new CPU(log, "cpu 2", 0.1f, mmu);
		CPU cpu3 = new CPU(log, "cpu 3", 0.1f, mmu);
		
		List<CPU> cpus = new ArrayList<CPU>();
		cpus.add(cpu1);
		cpus.add(cpu2);
		cpus.add(cpu3);
		
		IO_Device printer = new IO_Device("printer");
		IO_Device scanner = new IO_Device("scanner");
		
		
		ConcurrentHashMap<String, IO_Device> devices = new ConcurrentHashMap<String, IO_Device>();
		devices.put("printer", printer);
		devices.put("scanner", scanner);
		
		this.simulacionCon(cpus, policies, programas, mmu, memory, devices);
	}

	public static void main(String[] args) {
		MainSimulation main = new MainSimulation();
		main.simulacionSegmentacion_10Programas_PrinterYScanner_TodasLasPoliticasScheduling();
	}

}
