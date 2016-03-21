import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class Job implements Comparable<Job>{
	
	int processingTime;
	JobType type;
	HashSet<Machine> capableMachines;
	List<Machine> allMachines;
	
	/**
	 * Creates a single job of a certain type (A, B, or C), with a defined processing time,
	 * and a set of machines capable of processing it.
	 * @param machines
	 */
	public Job(List<Machine> machines){
		this.allMachines = machines;
		this.capableMachines = new HashSet<Machine>();
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
		} else {// remaining 1/6 probability
			type = JobType.C;
		}
	}
	
	private void assignProcessingTime(){
	
		if (this.type == JobType.A){
			// range = [11,20]
			this.processingTime = Main.RandomNumberGenerator.getUniformRandomNumber(11, 20);
		} else if (this.type == JobType.B) {
			// range = [1,10]			
			this.processingTime = Main.RandomNumberGenerator.getUniformRandomNumber(1, 10);
		} else { // Type C
			// range = [1,99]
			this.processingTime = Main.RandomNumberGenerator.getUniformRandomNumber(1, 99);
		}
	}
	
	private void assignCapableMachines(List<Machine> allMachines){
		
		if (this.type == JobType.B) {
			
			// while we have less than 4 capable machines, randomly add machines to subset.
			while (capableMachines.size() < 4){
				
				int randomIndex = Main.RandomNumberGenerator.getUniformRandomNumber(0, 7);
				capableMachines.add(allMachines.get(randomIndex));
			}
		} else {// types A or C
			double probability;
			
			if (this.type == JobType.A){
				probability = 0.5;
			} else { // Type C
				probability = (double) 1/3;
			}
			
			addMachines(probability, allMachines);
		}
	}
	
	private void addMachines(double probability, List<Machine> allMachines){
		
		// go over all machines and add them at given probability 
		while (this.capableMachines.size() == 0){
			for (Machine machine : allMachines) {
				if (Main.RandomNumberGenerator.getUniformRandomNumber() < probability) {
					this.capableMachines.add(machine);
				}
			}	
		}
	}

	@Override
	public int compareTo(Job job) {
		return Integer.compare(this.capableMachines.size(), job.capableMachines.size());
	}

	public int processingCompareTo(Job job01) {
		return Integer.compare(this.processingTime, job01.processingTime);
	}
}



















