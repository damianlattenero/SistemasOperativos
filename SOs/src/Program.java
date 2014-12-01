import java.util.ArrayList;
import java.util.List;


public class Program {
	
	private List<Instruction> instructions;
	private List<String> vars;
	private Integer tamData;
	
	public Program(Integer tamData) {
		super();
		this.tamData = tamData;
		this.vars = new ArrayList<String>();
		this.instructions = new ArrayList<Instruction>();
	}
	
	

	public Program(List<Instruction> instructions, List<String> vars, Integer tamData) {
		super();
		this.instructions = instructions;
		this.vars = vars;
		this.tamData = tamData;
	}



	public List<Instruction> getInstructions() {
		return instructions;
	}

	public List<String> getVariables() {
		return vars;
	}

	public Integer getTamData() {
		return tamData;
	}
	
	public Program copy(){
		List<Instruction> ins = new ArrayList<Instruction>(this.instructions);
		List<String> vars = new ArrayList<String>(this.vars);
		
		return new Program(ins, vars, tamData);
	}
	
	
}
