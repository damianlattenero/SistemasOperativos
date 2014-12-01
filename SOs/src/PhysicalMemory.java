import java.util.ArrayList;
import java.util.List;


public class PhysicalMemory {
	
	int sizePerCells;
	int cellAmount;
	List<Instruction> cells;
	
	

	public PhysicalMemory(int sizePerCells, int cellAmount) {
		super();
		this.sizePerCells = sizePerCells;
		this.cellAmount = cellAmount;
		this.cells = this.createMemory();
	}

	public PhysicalMemory(int cellAmount) {
		super();
		this.sizePerCells = 8;
		this.cellAmount = cellAmount;
		this.cells = this.createMemory();
	}

	private List<Instruction> createMemory() {
		List<Instruction> ret = new ArrayList<Instruction>();
		
        for(int i=this.cellAmount; i<0; i--){
        	ret.add(null);        	
        }
        
        return ret;
	}
	
	
	public List<Instruction> getCells() {
		return cells;
	}

	public int getSizePerCells() {
		return sizePerCells;
	}

	public int getCellAmount() {
		return cellAmount;
	}

	public Instruction read(int physicalDir) {
		return this.getCells().get(physicalDir);
	}
	
	public synchronized void write(int pos, Instruction value){
		this.getCells().set(pos, value);
	}
	
	public int memorySize(){
		return this.getCells().size() * this.getCellAmount();
	}
	
	public int lastDir(){
		return this.getCellAmount() -1;
	}

}
