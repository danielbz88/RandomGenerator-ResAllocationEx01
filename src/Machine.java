import java.util.PriorityQueue;

public class Machine {
	private PriorityQueue<Job> jobsQueue;
	private int load;
	
	public Machine(){
		
		this.load = 0;
		jobsQueue = new PriorityQueue<Job>();
	}
	
	public int getLoad(){
		return this.load;
	}
		
	public void addJob(Job nextJob){
		jobsQueue.add(nextJob);
		this.load = this.load + nextJob.processingTime;
	}
	
	// Returns null if Queue is empty.
	public Job getJob(){
		
//		TODO: Implement
		return jobsQueue.poll();
	}
}
