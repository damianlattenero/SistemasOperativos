import java.util.List;
public interface MMUPolicy {
	
	public boolean asignar(List<Instruction> particion, int pid);
	
	public void liberar(int pid);
	
	public void write(int ref, String value, int dest);
	
	public Instruction read(int ref, int i);
	
	public void swapIn(int pid);
	
	public void swapOut(int pid);
	
	public List<Instruction> load(int pid, Program program);
	
}
