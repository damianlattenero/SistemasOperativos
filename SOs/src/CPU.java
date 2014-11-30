
public class CPU extends Thread{
	
	private Log log;
	private int id;
	private boolean isIdle;
	private float delay;
	private MMU mmu;
	private int[] registers;
	private Pair<Integer, Instruction> pc;
	int adress;
	
	public CPU(Log log, int id, float delay, MMU mmu, int destino) {
		super();
		this.log = log;
		this.id = id;
		this.isIdle = true;
		this.delay = 0.5f;
		this.mmu = mmu;
		this.registers = new int[]{0,0,0,0,0,0,0,0};
		this.pc = new Pair<Integer, Instruction>(null, null);
		this.adress = destino;
	}
	
	public MMU getMmu() {
		return mmu;
	}

	public int[] getRegisters() {
		return registers;
	}

	public void setRegisters(int[] registers) {
		this.registers = registers;
	}
	
	public void setPC(int id2, Instruction instruction) {
		this.pc = new Pair<Integer, Instruction>(id2, instruction);
	}
	
    public Pair<Integer, Instruction> getPc() {
		return pc;
	}	

	public Integer getPID(){
		return pc.left;
	}

	private Instruction getFetchInfo() {
		return pc.right;
	}
	
	public void execute(){
        this.isIdle = false;
                
        Instruction instr = this.fetch();
        
        this.log.addEvent(instr, this);
        
        instr.run(this.getPID(), this.getMmu());
        
        sleep();
        
        this.isIdle = true;
	}
	
	public boolean isIdle() {
		return isIdle;
	}
	
	public Instruction fetch() {
		
		return this.getMmu().read(this.getPID(), this.adress);
	}
	
	private void sleep() {
		try {
			Thread.sleep((long) this.delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
