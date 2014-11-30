
public class RunningState extends ProcessState {
	public void nextState(PCB pcb){
		super.nextState(pcb);
		
		if(!pcb.haveNextInstr()){
			 pcb.setFinished();;
		}else{
			if(!pcb.getFetchedInstr().isIo()){
				pcb.setReady();
			}else{
				pcb.setWaitingIO();
			}
		}
	}
}
