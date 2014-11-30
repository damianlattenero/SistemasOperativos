import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MMUPolicy_Continua implements MMUPolicy {
	
	PhysicalMemory memory;
	ConcurrentHashMap<Integer, List<Instruction>> secondaryStore;
	ConcurrentHashMap<Integer, List<Instruction>> particionesAsignadas;
	ConcurrentHashMap<Integer, List<Instruction>> particionesLibres;
	
	
	public MMUPolicy_Continua(PhysicalMemory memory) {
		super();
		this.memory = memory;
		this.secondaryStore = new ConcurrentHashMap<Integer, List<Instruction>>();
		this.particionesAsignadas = new ConcurrentHashMap<Integer, List<Instruction>>();
		this.particionesLibres = new ConcurrentHashMap<Integer, List<Instruction>>();
	}


	public ConcurrentHashMap<Integer, List<Instruction>> getParticionesAsignadas() {
		return particionesAsignadas;
	}



	public ConcurrentHashMap getParticionesLibres() {
		return particionesLibres;
	}
	
	
	public ConcurrentHashMap<Integer,List<Instruction>> getSecondaryStore() {
		return secondaryStore;
	}
	
	
	public PhysicalMemory getMemory() {
		return memory;
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
        
        particion.addAll(this.replicar(tamVars));
        particion.addAll(compiledInstrs);
        particion.addAll(this.replicar(tamData));
        
        //[0]*tamVars + compiledInstrs + [0]*tamData;
        
        this.asignar(particion, pid);
        
        List<Instruction> instrsForPCB = new ArrayList<Instruction>();
        
        for(int i=compiledInstrs.size();i<0;i--){
        	Instruction io_Instruction = new IO_Instruction(i+tamVars, null);
			instrsForPCB.add(io_Instruction);
        }
            
        return instrsForPCB;
	}
	
	private Collection<Instruction> replicar( double tamVars) {
		List<Instruction> ret = new ArrayList<Instruction>();
		for(double i=tamVars; i<0; i=i-1){
			ret.add(new Instruction(0d));
		}
		
		return ret;
	}
	
	public boolean asignar(List<Instruction> particion, int pid) {
		this.getSecondaryStore().put(pid, particion);
		return true;
	}
	
	public void liberar(int pid) {
		List<Instruction> hueco = this.getParticionesAsignadas().get(pid);
                
        if(hueco != null){
        	this.getParticionesAsignadas().remove(pid);
        	this.agregarEspacioLibre(hueco.get(0), hueco.get(1));
        	
        	
        }
        this.getSecondaryStore().remove(pid) ;

	}
	
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
	public void write(int ref, Instruction value, int adress) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void swapOut(int pid) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void swapIn(int pid) {
		// TODO Auto-generated method stub

	}
	
	public Object espacioLibre(){
		return null;
	}
	
	public int conseguirEspacio(){
		return 0;
	}
	
	public int desalocarProcesoConLastDir(){
		return 0;
	}
	
	private void agregarEspacioLibre(Instruction io_Instruction,
			Instruction io_Instruction2) {
	}
	
	public void compactar(){
		
	}


	public void trasladarCeldas(int start, int limit, int offset){
		
	}

	

	
	

	
	
	

	

	
	

	

}
