
public class RunningIOState extends ProcessState {
	public void nextState(PCB pcb){
		super.nextState(pcb);
		
		if(pcb.haveNextInstr()){
			 pcb.setReady();
		}else{
			pcb.setFinished();
		}
	}
	
}
