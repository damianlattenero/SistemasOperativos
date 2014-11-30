import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class IO_Manager {

	ConcurrentHashMap<String, IO_Device> devices = new ConcurrentHashMap<String, IO_Device>();
	ConcurrentHashMap<String, Integer> disponibleDevices;
	List<PCB> processWithIO;

	public IO_Manager(ConcurrentHashMap<String, IO_Device> devices) {
		super();
		this.devices = devices;
		this.disponibleDevices = this.makeDisponibleVector();
		this.processWithIO = new ArrayList<>();
	}

	private ConcurrentHashMap<String, Integer> makeDisponibleVector() {
		ConcurrentHashMap<String, Integer> result = new ConcurrentHashMap<String, Integer>();
		for (IO_Device d : this.getIO_Device().values()) {
			result.put(d.getType(), d.quantityOfFreeDevices());
		}

		return result;
	}

	public void addProcessWithIO(PCB pcb) {
		this.getProcessWithIO().add(pcb);
	}

	public void removeProcessWithIO(PCB pcb) {
		this.getProcessWithIO().remove(pcb);
	}

	public Enumeration<String> getTypesOfIODevices() {
		return this.getDisponibleDevices().keys();
	}

	public boolean haveDevices(ConcurrentHashMap<String, Integer> listOfNeededDevices){
		boolean haveDevices = true;
		for (String elem : listOfNeededDevices.keySet()){
			haveDevices = (listOfNeededDevices.get(elem)) <= (this.getIO_Device().get(elem).quantityOfFreeDevices());
		}
		
		return haveDevices;
	}
	
	public boolean noDeadLock(String deviceName, PCB pcb){
		ConcurrentHashMap<String, Integer> trabajo = copiarMap(this.getDisponibleDevices());
		ConcurrentHashMap<String, Integer> processInfo = new ConcurrentHashMap<String, Integer>();
		//complete
		return false;
	}

	public void takeADevice(String deviceName){
		int newNum = this.getDisponibleDevices().get(deviceName) - 1;
		this.getDisponibleDevices().put(deviceName, newNum);
	}
	
	public void freeADevice(String deviceName){
		int newNum = this.getDisponibleDevices().get(deviceName) + 1;
		this.getDisponibleDevices().put(deviceName, newNum);
	}
	
	public void takePCB(PCB pcb){
		((IO_Instruction) pcb.getFetchedInstr()).runIO(pcb, this);
	}
	
	public void alloc(PCB pcb, String deviceName) {
		System.out.println("Proceso " + pcb.getId() + " intenta alloc " + deviceName);
		if(this.getDisponibleDevices().get(deviceName) > 0 && this.noDeadLock(deviceName, pcb)){
			System.out.println("Proceso " + pcb.getId() + " alloc " + deviceName);
			this.takeADevice(deviceName);
			pcb.addDevice(deviceName);
		}else{
			pcb.currentInst -= 1;
		}
		
		pcb.setReady();
		
		if(pcb.haveNextInstr()){
			pcb.nextInstr();
		}
	}
	
	public void unalloc(PCB pcb,String deviceName){
		System.out.println("Proceso " + pcb.getId() + " unalloc " + deviceName);
		
		pcb.freeDevice(deviceName);
		freeADevice(deviceName);
		pcb.setReady();
		if(pcb.haveNextInstr()){
			pcb.nextInstr();
		}
	}
	
	public boolean canUseDevice(PCB pcb,IO_Device ioDevice){
		return ioDevice.isShared() || pcb.canUseDevice(ioDevice.getType());
	}
	
	public void useDevice(PCB pcb, String deviceName) {
		IO_Device ioDevice = this.getIO_Device().get(deviceName);
		
		if((ioDevice != null) && this.canUseDevice(pcb, ioDevice)){
			ioDevice.giveAFreeDevice(pcb);
		}else{
			pcb.finalize("tratar de chorear el recurso " + deviceName);
		}
	}
	
	
	
	private ConcurrentHashMap<String, Integer> copiarMap(ConcurrentHashMap<String, Integer> map) {
		ConcurrentHashMap<String, Integer> copy = new ConcurrentHashMap<String, Integer>();
		copy.putAll(map);
		return copy;
	}

	public ConcurrentHashMap<String, Integer> getDisponibleDevices() {
		return disponibleDevices;
	}

	public List<PCB> getProcessWithIO() {
		return processWithIO;
	}

	public ConcurrentHashMap<String, IO_Device> getIO_Device() {
		return devices;
	}

	

	

}
