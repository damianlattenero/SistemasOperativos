
public class NewState extends ProcessState {

	public void nextState(PCB pcb){
        super.nextState(pcb);
        pcb.setReady();
	}

}
