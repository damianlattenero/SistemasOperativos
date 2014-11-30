
public class SFJ_NonExpropiativePolicy {
	
	public void execute(PCB process, CPU cpu){
		System.out.println("Executing Process With ID " + process.getId());
		
		while(process.haveNextInstr()){
			process.nextInstr();
			cpu.execute();
		}
	}

}
