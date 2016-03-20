import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class Job {
	
	int processingTime;
	JobType type;
	HashSet<Machine> capableMachines;
	List<Machine> allMachines;
	
	/**
	 * returns a job of a certain type (A, B, or C), with a defined processing time,
	 * and a set of machines capable of processing it.
	 * @param machines
	 */
	public Job(List<Machine> machines){
		this.allMachines = machines;
		assignJobType();
		assignProcessingTime();
		assignCapableMachines(machines);
	}
	
	private void assignJobType() {
		
		Random rand = new Random();
		
		// Probability of belonging to classes A, B and C are: 1/2, 1/3 and 1/6 respectively.
		if (rand.nextInt(2)==0) {// 1/2 probability
			type = JobType.A;
		} else if (rand.nextInt(3)==0) {// 1/3 probability
			type = JobType.B;
		} else {// 1/6 probability
			type = JobType.C;
		}
	}
	
	private void assignProcessingTime(){
	
		if (this.type == JobType.A){
			
			// range = [11,20]
		} else if (this.type == JobType.B) {
			// range = [11,20]			
		} else { // Type C
			// range = [1,99]
		}
	}
	
	private void assignCapableMachines(List<Machine> allMachines){
		
		if (this.type == JobType.B) {
			
			// while we have less than 4 capable machines, randomly add machines to subset.
			while (capableMachines.size() < 4){
				
				int randomIndex = //TODO;
				capableMachines.add(allMachines.get(randomIndex));
			}
		} else {// types A or C
			double probability;
			
			if (this.type == JobType.A){
				probability = 0.5;
			} else { // Type C
				probability = 1/3;
			}
			
			addMachines(probability, allMachines);
		}
	}
	
	private void addMachines(double probability, List<Machine> allMachines){
		
		// go over all machines and add them at given probability 
		for (Machine machine : allMachines) {
			//TODO	
		}
	}
}
