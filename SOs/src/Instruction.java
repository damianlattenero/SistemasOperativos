import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Instruction {

	Double quantum;
	List<Instruction> params;

	public Instruction(Double quantum) {
		super();
		if (quantum == null) {
			this.quantum = (double) (Math.random() * 10);
		} else {
			this.quantum = quantum;
		}
		params = new ArrayList<Instruction>();
	}
	public List<Instruction> getParams() {
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

	public void asignarDirsLogicasAsigContinua(List<Integer> list) {
//		for (int i = 0; i < this.getParams().size(); i++) {
//			this.params.set(i, vars.indexOf(this.params.get(i)));
//		}

	}
	
	public void asignarDirsLogicasSegmentacion(List<Instruction> vars){
//		for (int i=0; i < this.getParams().size(); i++){
//			this.params.set(i, vars.indexOf(this.params.get(i)));
//			                   //(1,vars.index(self.params[i]))
//		}
	}

	public void asignarDirsLogicasPaginacion(Map mapVars){
//		for (int i=0; i < this.getParams().size(); i++){
//			this.params.set(i, (Integer) mapVars.get(this.params.get(i)));
//		}
	}

}
