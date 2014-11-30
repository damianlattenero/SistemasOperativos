import java.util.List;
public interface MMUPolicy {
	
	public void asignar(List<Instruction> particion, int pid);
	
	public void liberar(int pid);
	
	public void write(int ref, Instruction value, int adress);
	
	public Instruction read(int ref, int adress);
	
	public void swapIn(int pid);
	
	public void swapOut(int pid);
	
	public List<Instruction> load(int pid, Program program);
	
}
