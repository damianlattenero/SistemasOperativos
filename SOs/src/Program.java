import java.util.ArrayList;
import java.util.List;


public class Program {
	
	private List<Instruction> instructions;
	private List vars;
	private Integer tamData;
	
	public Program(Integer tamData) {
		super();
		this.tamData = tamData;
		this.vars = new ArrayList();
		this.instructions = new ArrayList<Instruction>();
	}
	
	

	public Program(List<Instruction> instructions, List vars, Integer tamData) {
		super();
		this.instructions = instructions;
		this.vars = vars;
		this.tamData = tamData;
	}



	public List<Instruction> getInstructions() {
		return instructions;
	}

	public List<Integer> getVariables() {
		return vars;
	}

	public Integer getTamData() {
		return tamData;
	}
	
	public Program copy(){
		List<Instruction> ins = new ArrayList<Instruction>(this.instructions);
		List vars = new ArrayList(this.vars);
		
		return new Program(ins, vars, tamData);
	}
	
	
}
