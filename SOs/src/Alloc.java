
public class Alloc extends IO_Instruction {

	public Alloc(Double quantum, String deviceName) {
		super(quantum, deviceName);
	}

	@Override
	public String toString() {
		return "alloc";
	}

	public void runIO(PCB pcb, IO_Manager iO_Manager){
		iO_Manager.alloc(pcb, this.getDeviceName());
	}

}
