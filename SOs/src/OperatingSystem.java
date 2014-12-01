import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class OperatingSystem {
	
	List<STS> stss;
	List<SchedulingPolicy> policies;
	List<CPU> cpus;
	IO_Manager iOManager;
	LTS lts;
	boolean isUp;
	MMU mmu_policy;
	
	public OperatingSystem(List<SchedulingPolicy> policies, List<CPU> cpus, MMU mmu_policy, ConcurrentHashMap<String, IO_Device> devices) {
		super();
		this.stss = new ArrayList<STS>();
		this.policies = policies;
		this.cpus = cpus;
		this.iOManager = new IO_Manager(devices);
		lts = new LTS(iOManager, mmu_policy, stss);
		this.isUp = false;
		this.mmu_policy = mmu_policy;
	}


	public IO_Manager getiOManager() {
		return iOManager;
	}
	
	public void assignSTSforCpus() {
        int cantPolicies = this.policies.size();
        SchedulingPolicy policy;    
        for(int i=(this.cpus.size()-1); i>0; i--){
        	if(i < cantPolicies){
        		
        		policy = this.policies.get(i);
        				  
        	}else{
        		
        		policy = this.defaultPolicy();
        	
        	}
        	STS sts = new STS(policy, this.cpus.get(i), this, this.getiOManager());
    		this.stss.add(sts);
        }
                        
	}

	private SchedulingPolicy defaultPolicy() {
		return new RoundRobinPolicy(10);
	}
	
    public void startStss(){
    	
    	for(STS sts : this.stss){
    		
    		sts.start();
    	}
    }
    
    public void startSystem(){
    	this.isUp = true;
		this.assignSTSforCpus();
		this.startStss();
    }
    
    public void start(Program program){
    	this.startWithPriority(program, 10);
    }

	private void startWithPriority(Program program, int priority) {
		this.lts.start(program, priority);
	}
	
	public void shutDown(){
		this.isUp = false;
	}
}
