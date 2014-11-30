import java.util.concurrent.PriorityBlockingQueue;


public class RoundRobinWithPriorityPolicy extends RoundRobinPolicy {
	
	PriorityBlockingQueue<PCB> queue;
	Pair<PCB,Integer> lastAndTimes;
	Integer maxTimes;
	
	public RoundRobinWithPriorityPolicy(double quantum,
			PriorityBlockingQueue queue, Pair<Object,Number> lastAndTimes, Number maxTimes) {
		super(quantum);
		this.queue = new PriorityBlockingQueue();
		this.lastAndTimes = new Pair<PCB,Integer>(null, null);
		this.maxTimes = 2;
	}
	
	public Integer getMaxTimes() {
		return maxTimes;
	}
	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}
	
	public boolean isInTheLimitOfAgeing(){
		return this.lastAndTimes.getRight() >= this.maxTimes;
	}
	
	public boolean nextIsTheLastRetrieved(){
		return this.queue.element().equals(this.lastAndTimes.getLeft());
	}
	
	public boolean shouldAgeing(){
		return this.nextIsTheLastRetrieved() && this.isInTheLimitOfAgeing();
	}

	public PCB nextProcess(){
		PCB p = this.queue.element();
		if(this.lastAndTimes.getRight() == null){
			this.lastAndTimes = new Pair<PCB, Integer>(p, 1);
		}else{
			if(this.shouldAgeing()){
				p.decreasePriority(1);
	            p.setReady();
	            p = this.queue.element();
	            this.lastAndTimes = new Pair<PCB, Integer>(p, 1);
			}else{
				if(this.nextIsTheLastRetrieved()){
					this.lastAndTimes = new Pair<PCB, Integer>(p, this.lastAndTimes.getRight() + 1);
				}else{
					this.lastAndTimes = new Pair<PCB, Integer>(p, this.lastAndTimes.getRight());
				}
			}
		}
		
		return p;
	}
	

}
