import java.util.ArrayList;
import java.util.List;

public class Simulator {
	List<Machine> machines;
	List<Job> jobs;
	
	private void classifyMachines(){
//		TODO: Implement	
	}
	
	private void classifyJobs(){
//		TODO: Implement	
	}
	
	private void assignJobs(){
//		TODO: Implement	
	}
	
	private void initiateMachines(){	
		this.machines = new ArrayList<Machine>();
		
		for (int i = 0; i < Main.M; i++){
			machines.add(new Machine());
		}
		
//		TODO: Implement	
	}

	private void initiateJobs(){
		jobs = new ArrayList<Job>();
		
//		TODO: Implement	
	}
	
	public void runSimulation(){
		
		// Initiate Jobs and Machines
		initiateJobs();
		initiateMachines();
		
		//TODO: Sort Jobs
		
		classifyMachines();
		classifyJobs();
		assignJobs();
		
//		TODO: Implement
	}
}
