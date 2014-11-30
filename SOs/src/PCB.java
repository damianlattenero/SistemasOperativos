
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class PCB {
	
	private List<Instruction> instrs;
	private int id;
	int currentInst;
	private StateHandler stateHandler;
	private ProcessState state;
	private int priority; 
	private IO_Manager iOManager;
	private LTS lts;
	private ConcurrentHashMap<String, Integer> neededDevices;
	private ConcurrentHashMap<String, Integer> actualDevices;
	private List<Instruction> fetchedInstr;
	int[] registers = {0,0,0,0,0,0,0,0,0};

	public PCB(List<Instruction> instrs, int id, int currentInst,
			StateHandler stateHandler, int priority,
			IO_Manager iOManager, LTS lts, ConcurrentHashMap<String, Integer> neededDevices, List<Instruction> list) {
		super();
		this.instrs = instrs;
		this.id = id;
		this.currentInst = currentInst;
		this.stateHandler = stateHandler;
		this.state = stateHandler.get_firstState(this);
		this.priority = priority;
		this.iOManager = iOManager;
		this.lts = lts;
		this.neededDevices = neededDevices;
		this.fetchedInstr = list;
	}
	
	




	public PCB(int id, int currentInst, StateHandler stateHandler,
			ProcessState state, int priority, IO_Manager iOManager, LTS lts,
			ConcurrentHashMap<String, Integer> neededDevices,
			ConcurrentHashMap<String, Integer> actualDevices,
			List<Instruction> fetchedInstr, int[] registers) {
		super();
		this.instrs = new ArrayList<Instruction>();
		this.id = id;
		this.currentInst = currentInst;
		this.stateHandler = stateHandler;
		this.state = state;
		this.priority = priority;
		this.iOManager = iOManager;
		this.lts = lts;
		this.neededDevices = neededDevices;
		this.actualDevices = actualDevices;
		this.fetchedInstr = fetchedInstr;
		this.registers = registers;
	}
	
	public LTS getLts() {
		return lts;
	}
	
	public Instruction getFetchedInstr(){
		if (this.fetchedInstr.isEmpty()){
			return this.getCurrentInstruction();
		}else{
			return this.fetchedInstr.get(this.getCurrentInst());			
		}
		
	}
	
	public void setFecthedInstr(List<Instruction> value){
		this.fetchedInstr = value;
	}
	
	public int[] getRegisters() {
		return registers;
	}
	
	public void setRegisters(int[] registers) {
		this.registers = registers;
	}

	public boolean haveNextInstruct() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Pid " + this.getId();
	}
	
	public boolean equals(PCB process) {
		return this.id == process.id;
	}
	
	public boolean greaterOrEqual(PCB process){
		return this.priority >= process.priority;
	}
	
	public boolean greater(PCB process){
		return this.priority > process.priority;
	}
	
	public boolean lessOrEqual(PCB process){
		return this.priority <= process.priority;
	}
	
	public boolean menor(PCB process){
		return this.priority < process.priority;
	}
	
	public int getId() {
		return id;
	}

//////////////Ejecucion
	
	
	
	public int getCurrentInst() {
		return currentInst;
	}
	
	public List<Instruction> getInstrs() {
		return instrs;
	}
	
	public Instruction getCurrentInstruction(){
		return this.getInstrs().get(this.getCurrentInst());
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void decreasePriority(int n){
		this.setPriority(this.getPriority() + n);
	}
	
	public void increasePriority(int n){
		this.setPriority(this.getPriority() - n);
	}

	public Instruction nextInstr(){
		Instruction instr = this.getCurrentInstruction();
		this.currentInst += 1;		
		return instr;
	}
	
	public boolean haveNextInstr(){
		return this.currentInst != this.instrs.size();
	}
	
///////////////////////  States
	
	public ProcessState getState() {
		return state;
	}
	
	public void setState(ProcessState state){
		this.stateHandler.changeQueues(this,this.state, state);
		this.state = state;
	}
	
	public void nextState(){
		this.state.nextState(this);
	}
	
	public void setNew(){
		this.setState(this.stateHandler.getNewState());
	}
	
	public void setReady(){
        this.setState(this.stateHandler.getReadyState());
	}
	
	public void setRunning(){
        this.setState(this.stateHandler.getRunningState());
	}
	
	public void setWaitingIO(){
		this.setState(this.stateHandler.getWaitingIOState());
	}
	
	public void setRunningIO(){
		this.setState(this.stateHandler.getRunningIOState());
	}
	
	public void setFinished(){
		System.out.println("Proceso terminado " + this.getId());
        terminated();
	}

	private void terminated() {
		this.getLts().processTerminated(this);
        this.setState(this.stateHandler.getFinishedState());
	}
	
	public void finalize(String causa){
		System.out.println("Proceso " + this.getId() + " finalizado abruptamente por " + causa);
		terminated();
	}
	
	public boolean isWaitingIO(){
		return this.getState() == this.stateHandler.getWaitingIOState();
	}
	
	////////////////// IO
	
	public IO_Manager getiOManager() {
		return iOManager;
	}
	
	public  ConcurrentHashMap<String, Integer> getActualNumOfDevices() {
		return actualDevices;
	}
	
	public ConcurrentMap<String, Integer> getNeededDevices() {
		return neededDevices;
	}
	
	public  Integer neededNumOfDevices(String deviceName){
		return this.getNeededDevices().get(deviceName);
	}
	
	public Integer actualNumOfDevices(String deviceName){
		return (Integer) this.getActualNumOfDevices().get(deviceName);
	}
	
	public void freeDevice(String deviceName){
		Integer newNum = ((Integer) this.getActualNumOfDevices().get(deviceName)) - 1;
		this.getActualNumOfDevices().put(deviceName, newNum);

        Integer newNeedNum = ((Integer) this.getNeededDevices().get(deviceName)) + 1;
        this.getNeededDevices().put(deviceName, newNeedNum);
	}
	
	
	public void addDevice(String deviceName) {
		Integer newNum = ((Integer) this.getActualNumOfDevices().get(deviceName)) + 1;
		this.getActualNumOfDevices().put(deviceName, newNum);

        Integer newNeedNum = ((Integer) this.getNeededDevices().get(deviceName)) - 1;
        this.getNeededDevices().put(deviceName, newNeedNum);
	}
	
	public boolean canUseDevice(String deviceName){
		Integer num = this.getActualNumOfDevices().get(deviceName);
		return !((num == null) && (num > 0));
	}
	
	public void useDevice(String freeDevice){
		((IO_Instruction) this.getFetchedInstr()).executeIO(freeDevice);
        this.currentInst += 1;
	}

	

}
