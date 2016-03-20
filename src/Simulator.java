import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulator {
	static final int m = 8;
	static final int n = 200;
	
	List<Machine> machines;
	List<Job> jobs;
	
	private void assignJobs(){
		int load = Integer.MAX_VALUE;
		Machine currMachine = null;
		
		// for each job, find capable machine with minimal load and add job to it's queue
		for (Job job : jobs) {
			for (Machine machine : job.capableMachines) {
				if (machine.getLoad() < load){
					load = machine.getLoad();
					currMachine = machine;					
				}
			}
			
			// add job to the machine we've found (with minimal load)
			currMachine.addJob(job);
		}
	}
	
	private void initiateMachines(){	
		this.machines = new ArrayList<Machine>();
		
		for (int i = 0; i < Simulator.m; i++){
			machines.add(new Machine());
		}		
	}

	private void initiateJobs(){
		jobs = new ArrayList<Job>();
		
		for (int i = 0; i < Simulator.n; i++){
			jobs.add(new Job(this.machines));
		}		
	}
	
	public void runSimulation(){
		
		// Initiate Jobs and Machines (machines first)
		initiateMachines();
		initiateJobs();
		
		// Sort Jobs (or keep it arbitrary)
//		sortJobsSmallToLarge();
//		sortJobsLargeToSmall();
		
		// Assign jobs to machines
		assignJobs();
		
		// TODO: what now?
		System.out.println();
	}
	
	// Sort jobs according to number of capable machines. Small to large.
	private void sortJobsSmallToLarge(){
		Collections.sort(this.jobs, 
				(job01, job02) -> job01.compareTo(job02));
	}
	
	// Sort jobs according to number of capable machines. Large to small.
	private void sortJobsLargeToSmall(){
		Collections.sort(this.jobs, 
				(job01, job02) -> job02.compareTo(job01));
	}
}















