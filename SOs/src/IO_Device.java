import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class IO_Device {
	
	String type;
	boolean isUp = false;
	ConcurrentHashMap<Integer, String> givedDevices = new ConcurrentHashMap<Integer, String>();
	ConcurrentLinkedQueue<String> listOfDevices = new ConcurrentLinkedQueue<String>();
	ConcurrentLinkedQueue<String> freeDevices = new ConcurrentLinkedQueue<String>();
	boolean shared = false;
	
	public IO_Device(String type) {
		super();
		this.type = type;
	}
	
	public IO_Device(String type, boolean shared) {
		super();
		this.type = type;
		this.shared = shared;
	}

	public boolean equals(IO_Device device) {
		return this.type == device.type;
	}
	
	public boolean isShared() {
		return shared;
	}
	
	public Integer quantityOfDevices() {
		return this.getDevices().size();
	}
	
	public Integer quantityOfFreeDevices() {
		return this.getFreeDevices().size();
	}
	
	public void setUp(){
		this.setState(true);
	}

	public void shutDown(){
		this.setState(false);
	}	
	
	public void addDevice(String device){
		this.getDevices().add(device);
		this.getFreeDevices().add(device);
	}
	
	public boolean haveFreeDevices(){
		return !this.getFreeDevices().isEmpty();
	}
	
	public String takeADevice(){
		String freeDevice = this.getFreeDevices().poll();
		
		if(this.isShared()){
			getFreeDevices().add(freeDevice);
		}
		
		return freeDevice;
	}
	
	public void registerATakenFreeDevice(int pcbId, String device){
		getGivedDevices().put(pcbId, device);
	}
	
	public void giveAFreeDevice(final PCB pcb){
		final String freeDevice = this.takeADevice();
		this.registerATakenFreeDevice(pcb.getId(), freeDevice);
		pcb.nextState(); 
		Thread t = new Thread() {
			@Override
			public void run() {
				executeIO(freeDevice, pcb);
			}
		};
		t.start();
		if(this.isShared()) {
			this.getFreeDevices().add(freeDevice);
		}
	}
	
	public void executeIO(String freeDevice, PCB pcb){
		pcb.useDevice(freeDevice);
		this.getGivedDevices().remove(pcb.getId());
		pcb.nextState();
		if(!this.isShared()){
			this.getFreeDevices().add(freeDevice);
		}
	}
	
	public void usedDevice(int pcbId, String device){
		getGivedDevices().remove(pcbId);
        getFreeDevices().add(device);
	}

	private void setState(boolean b) {
		this.isUp = b;
	}

	private ConcurrentLinkedQueue<String> getFreeDevices() {
		return freeDevices;
	}

	private ConcurrentLinkedQueue<String> getDevices() {
		return listOfDevices;
	}

	private  ConcurrentHashMap<Integer, String> getGivedDevices() {
		return givedDevices;
	}	
	
	public String getType() {
		return type;
	}
}
