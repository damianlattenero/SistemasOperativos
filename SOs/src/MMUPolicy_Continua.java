import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MMUPolicy_Continua extends MMU implements MMUPolicy {

	public MMUPolicy_Continua(PhysicalMemory memory,
			ConcurrentHashMap secondaryStore, MMUPolicy policy) {
		super(memory, secondaryStore, policy);
	}
	
	@Override
	public List<Instruction> load(int pid, Program program) {
        double tamVars = program.getVariables().size();
        double tamData = program.getTamData();
        
        List<Instruction> compiledInstrs = new ArrayList<Instruction>(program.getInstructions()); 
        
        for (Instruction instr : compiledInstrs){
        	instr.asignarDirsLogicasAsigContinua(program.getVariables());
        }
            
        List<Instruction> particion = new ArrayList<Instruction>(); 
        
        particion.addAll(this.replicar(0, tamVars));
        particion.addAll(compiledInstrs);
        particion.addAll(this.replicar(0, tamData));
        
        //[0]*tamVars + compiledInstrs + [0]*tamData;
        
        this.asignar(particion, pid);
        
        List<Instruction> instrsForPCB = new ArrayList<Instruction>();
        
        for(int i=compiledInstrs.size();i<0;i--){
        	Instruction io_Instruction = new IO_Instruction(i+tamVars, null);
			instrsForPCB.add(io_Instruction);
        }
            
        return instrsForPCB;
	}

	private Collection<Instruction> replicar(double i, double tamVars) {
		return null;
	}

	@Override
	public void asignar(List<Instruction> particion, int pid) {
		this.getSecondaryStore().put(pid, particion);
	}

	@Override
	public void liberar(int pid) {
		List<Instruction> hueco = this.getParticionesAsignadas().get(pid);
                
        if(hueco != null){
        	this.getParticionesAsignadas().remove(pid);
        	this.agregarEspacioLibre(hueco.get(0), hueco.get(1));
        	
        	
        }
        this.getSecondaryStore().remove(pid) ;

	}

	private void agregarEspacioLibre(Instruction io_Instruction,
			Instruction io_Instruction2) {
	}

	

	@Override
	public Instruction read(int pid, int adress) {
        List<Instruction> particion = this.getParticionesAsignadas().get(pid);
                
        if (particion != null){
        	this.swapIn(pid);
        }
            
        particion = this.getParticionesAsignadas().get(pid);
        int physicalDir = (int) (particion.get(0).getQuantum() + adress);
        double limit = particion.get(1).getQuantum();
        
        if (limit >= physicalDir){
        	return this.getMemory().read(physicalDir);
        }else{
        	return null;
        }
	}

	@Override
	public void swapIn(int pid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swapOut(int pid) {
		// TODO Auto-generated method stub

	}

	

	

}
