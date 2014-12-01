import java.util.List;
public interface MMUPolicy {
	
	public boolean asignar(List<Instruction> particion, int pid);
	
	public void liberar(int pid);
	
	public void write(int ref, Instruction value, int dest);
	
	public Instruction read(int ref, int i);
	
	
	public List<Instruction> load(int pid, Program program);
	
}
