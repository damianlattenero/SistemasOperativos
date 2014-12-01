import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Instruction {

	Double quantum;
	List<String> params;

	public Instruction(Double quantum) {
		super();
		this.quantum = quantum;
		params = new ArrayList<String>();
	}
	
	public Instruction() {
		super();
		this.quantum = (double) (Math.random() * 10);
		params = new ArrayList<String>();
	}
	
	public List<String> getParams() {
		return params;
	}
	
	

	
	@Override
	public String toString() {
		return "Instr(Quantum " + this.quantum + ")";
	}

	

	public Double getQuantum() {
		return quantum;
	}
	
	public boolean isIo() {
		return false;
	}	

	public void run(Integer pid, MMU mmu){
		//do nothing
	}

	public void asignarDirsLogicasAsigContinua(List<String> vars) {
		for (int i = 0; i < this.getParams().size(); i++) {
			this.params.set(i, vars.get(i));
		}

	}
	
//	public void asignarDirsLogicasSegmentacion(List<Instruction> vars){
//		for (int i=0; i < this.getParams().size(); i++){
//			this.params.set(i, vars.indexOf(this.params.get(i)));
//			                   //(1,vars.index(self.params[i]))
//		}
//	}
//
//	public void asignarDirsLogicasPaginacion(Map mapVars){
//		for (int i=0; i < this.getParams().size(); i++){
//			this.params.set(i, (Integer) mapVars.get(this.params.get(i)));
//		}
//	}

}
