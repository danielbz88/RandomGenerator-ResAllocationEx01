import java.util.PriorityQueue;

public class Machine {
	private PriorityQueue<Job> jobsQueue;
	private int load;
	
	public Machine(){
		
		//TODO: implement
		this.load = 0;
		jobsQueue = new PriorityQueue<Job>();
	}
	
	public int getLoad(){
		return this.load;
	}
		
	public void addJob(Job nextJob){
		
//		TODO: Implement
	}
	
	// Returns null if Queue is empty.
	public Job getJob(){
		
//		TODO: Implement
		return jobsQueue.poll();
	}
}
