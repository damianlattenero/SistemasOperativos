import java.util.ArrayList;
import java.util.List;

public class IO_Instruction extends Instruction {

	private String deviceName;
	List<Object> params;
	Double quantum = null;
	
	public IO_Instruction(Double quantum, String deviceName, List<Object> params) {
		super(quantum);
		this.deviceName = deviceName;
		this.params = params;
        if(quantum == null) {
        	this.quantum = Math.random()+1;
        }
        else {
        	this.quantum = quantum;
        }
	}

	public IO_Instruction(Double quantum, String deviceName) {
		this(quantum, deviceName, new ArrayList<Object>());
	}

	@Override
	public String toString() {
		return " IO: " + this.deviceName;
	}

	public boolean isIO() {
		return true;
	}

	public void runIO(PCB pcb, IO_Manager iO_Manager) {
		iO_Manager.useDevice(pcb, this.getDeviceName());
	}

	public void executeIO(String freeDevice) {
		System.out.println("Executing IO on " + this.getDeviceName());
		try {
			Thread.sleep(this.getQuantum().longValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDeviceName() {
		return deviceName;
	}

	@Override
	public void run(Integer pid, MMU mmu) {
		// TODO Auto-generated method stub
		
	}

	



}
