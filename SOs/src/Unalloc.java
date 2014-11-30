
public class Unalloc extends IO_Instruction {

	public Unalloc(Double quantum, String deviceName) {
		super(quantum, deviceName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "unalloc";
	}
	
	public void runIO(PCB pcb, IO_Manager iO_Manager) {
		iO_Manager.unalloc(pcb, this.getDeviceName());
	}

}
