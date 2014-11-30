import java.util.List;


public class LoadInstr extends Instruction {
	
	MMUPolicy mmu_policy;
	Program program;
	int pid;

    public LoadInstr(Double quantum, MMUPolicy mmu_policy, Program program,
			int pid) {
		super(quantum);
		this.mmu_policy = mmu_policy;
		this.program = program;
		this.pid = pid;
	}
    
	public int getPid(){
    	return this.pid;
    }
	
	public Program getProgram() {
		return program;
	}

	public MMUPolicy getMMUPolicy() {
		return mmu_policy;
	}
	
	@Override
	public String toString() {
		return "Load program";
	}
	
    public void run(){
    	this.getMMUPolicy().load(this.getPid(), this.getProgram());
    }
    
    public void asignarDirsLogicasAsigContinua(List vars){
//        for(LoadInstr i : this.params){
//        	this.params.set(i, vars.get(i));
//        }
    }

	@Override
	public void run(Integer pid, MMU mmu) {
		// TODO Auto-generated method stub
		
	}


	

    
    

}
