import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulator {
	private static final int m = 8;
	private static final int n = 200;

	private static List<Machine> machines;
	private static List<Job> jobs;
	
	static int maxLoadSumArb = 0;
	static int minLoadSumArb = 0;
	static int maxLoadSumSToL = 0;
	static int minLoadSumSToL = 0;
	static int maxLoadSumLToS = 0;
	static int minLoadSumLToS = 0;
	static int sumOfAllProcTimes = 0;

	public static void runSimulation(){

		String[][] outputTable = createMatrixTable();
		outputTable[0][0] = "\t";
		outputTable[0][1] = "\t";
		outputTable[1][0] = "";
		
		//run simulation 10 times
		for (int i = 0; i < 10; i++) {
			// Initiate Jobs and Machines (machines first)
			initiateMachines();
			initiateJobs();
			
			// collect sums for averages
			int procTimeSum = 0;
			
			//get total processing time
			for (Job job : jobs) {
				procTimeSum += job.processingTime;
			}
			
			Simulator.sumOfAllProcTimes += procTimeSum;
			
			fillInMatrix(outputTable, i + 1, procTimeSum);
		}
		
//		System.out.println(Arrays.deepToString(outputTable));
		FormattedTablePrint.printArray(outputTable);
		System.out.println();
	}

	private static void assignJobs(){
		
		clearAllMachineQueues();
		
		int minLoad = Integer.MAX_VALUE;
		Machine currMachine = null;

		// for each job, find capable machine with minimal load and add job to it's queue
		for (Job job : jobs) {
			minLoad = Integer.MAX_VALUE;
			currMachine = null;
			
			// go over capable machines and find machine with minimal load
			for (Machine machine : job.capableMachines) {
				if (machine.getLoad() < minLoad){
					
					minLoad = machine.getLoad();
					currMachine = machine;					
				}
			}

			// add job to the machine we've found (with minimal load)
			currMachine.addJob(job);
		}

		System.out.println();
	}

	private static void clearAllMachineQueues() {
		
		for (Machine machine : Simulator.machines) {
			machine.clearQueue();
		}
		
		System.out.println();
		
	}

	private static void initiateMachines(){	
		machines = new ArrayList<Machine>();

		for (int i = 0; i < Simulator.m; i++){
			machines.add(new Machine());
		}		
	}

	private static void initiateJobs(){
		jobs = new ArrayList<Job>();

		for (int i = 0; i < Simulator.n; i++){
			jobs.add(new Job(machines));
		}		
	}

//	 Sort jobs according to number of capable machines. Small to large.
	private static void sortJobsSmallToLarge(){
		Collections.sort(jobs, 
				(job01, job02) -> job01.compareTo(job02));
	}
	
//	private static void sortJobsSmallToLarge() {
//		jobs.sort(c);
//	}

	// Sort jobs according to process length. Large to small.
	private static void sortJobsLargeToSmall(){
		Collections.sort(jobs, 
				(job01, job02) -> job02.processingCompareTo(job01));
	}

	private static String[][] createMatrixTable() {
		String[][] table = new String[13][8];

		// Set Headers

		// First column
		for (int i = 2; i < 12; i++) {
			table[i][0] = Integer.toString(i - 1);
		}

		table[12][0] = "Avg";

		// First row
		table[0][2] = "Arb order";
		table[0][3] = "Arb order";
		table[0][4] = "     Sorted StoL";
		table[0][5] = "Sorted StoL";
		table[0][6] = "Sorted LtoS";
		table[0][7] = "Sorted LtoS";

		// Second row
		table[1][1] = "Total PT"; 
		table[1][2] = "Max Load";
		table[1][3] = "Max L / Min L";
		table[1][4] = "Max Load";
		table[1][5] = "   Max L / Min L";
		table[1][6] = "\tMax Load";
		table[1][7] = "\tMax L / Min L";
		
		return table;
	}

	private static int getTotalProcTime() {
		
		int totalProcTime = 0;
		
		// go over all macines, and find min load
		for (Machine machine : Simulator.machines){
			totalProcTime += machine.getLoad();
		}
		
		return totalProcTime;
	}

	private static int getMinLoad() {
		
		int minLoad = Integer.MAX_VALUE;
		
		// go over all machines, and find min load
		for (Machine machine : Simulator.machines){
			if (machine.getLoad() < minLoad) {
				minLoad = machine.getLoad();
			}
		}
		
		return minLoad;
	}

	private static int getMaxLoad() {
		
		int maxLoad = -1;
		
		// go over all machines, and find max load
		for (Machine machine : Simulator.machines){
			if (machine.getLoad() > maxLoad) {
				maxLoad = machine.getLoad();
			}
		}
		
		return maxLoad;
	}
	
	private static void fillInMatrixArbitrary(String[][] outputTable, int run) {

		// Assign jobs to machines
		assignJobs();
		
		DecimalFormat df2 = new DecimalFormat(".###");


		// Update row 'run + 1', in columns 2 and 3
		int maxLoad = getMaxLoad();
		int minLoad = getMinLoad();		
		
		outputTable[run + 1][2] = Integer.toString(maxLoad);
		outputTable[run + 1][3] = df2.format(((double)maxLoad) / minLoad);
		Simulator.maxLoadSumArb += maxLoad;
		Simulator.minLoadSumArb += minLoad;
	}

	private static void fillInMatrixSortedSToL(String[][] outputTable, int run) {

		// Update row 'run + 1', in columns 4 and 5

		// Assign jobs to machines
		assignJobs();
		
		DecimalFormat df2 = new DecimalFormat(".###");

		int maxLoad = getMaxLoad();
		int minLoad = getMinLoad();		

		outputTable[run + 1][4] = Integer.toString(maxLoad);
		outputTable[run + 1][5] = df2.format(((double)maxLoad) / minLoad);
		Simulator.maxLoadSumSToL += maxLoad;
		Simulator.minLoadSumSToL += minLoad;
	}
	
	private static void fillInMatrixSortedLToS(String[][] outputTable, int run) {

		// Update row 'run + 1', in columns 6 and 7
		// Assign jobs to machines
		assignJobs();
		
		DecimalFormat df2 = new DecimalFormat(".###");

		int maxLoad = getMaxLoad();
		int minLoad = getMinLoad();		

		outputTable[run + 1][6] = Integer.toString(maxLoad);
		outputTable[run + 1][7] = df2.format(((double)maxLoad) / minLoad);
		Simulator.maxLoadSumLToS += maxLoad;
		Simulator.minLoadSumLToS += minLoad;
	}

	private static void fillInMatrix(String[][] table, int run, int procTimeSum){
		
		table[run + 1][1] = Integer.toString(procTimeSum);
		
		// Fill Matrix/Table with data (arbitrary)
		fillInMatrixArbitrary(table, run);

		System.out.println("Arbitrary");
		
		printJobMachines();
		printJobLength();
		
		System.out.println("Sorted small machine set to large");
		
		// Sort Small to Large
		sortJobsSmallToLarge();

		printJobMachines();
		printJobLength();
		
		// Fill Matrix/Table with data (Small to Large)
		fillInMatrixSortedSToL(table, run);

		// Sort Large to Small
		sortJobsLargeToSmall();

		System.out.println("Sorted long time to short");
		
		printJobMachines();
		printJobLength();

		
		// Fill Matrix/Table with data (Large to Small)
		fillInMatrixSortedLToS(table, run);
		
		
		DecimalFormat df2 = new DecimalFormat(".###");

		
		// Fill in all averages (last row)
		table[12][1] = Integer.toString(Simulator.sumOfAllProcTimes / 10);
		table[12][2] = Integer.toString(Simulator.maxLoadSumArb / 10);
		table[12][3] = df2.format(((double)Simulator.maxLoadSumArb) / Simulator.minLoadSumArb);
		table[12][4] = Integer.toString(Simulator.maxLoadSumSToL / 10);
		table[12][5] = df2.format(((double)Simulator.maxLoadSumSToL) / Simulator.minLoadSumSToL);
		table[12][6] = Integer.toString(Simulator.maxLoadSumLToS / 10);
		table[12][7] = df2.format(((double)Simulator.maxLoadSumLToS) / Simulator.minLoadSumLToS);
				
	}
	
	
	private static void printJobMachines(){
		
		System.out.println("Job Machines");
		
		for (Job job : Simulator.jobs) {
			System.out.print(Integer.toString(job.capableMachines.size()) + ", ");
		}
		System.out.println();
		System.out.println("============================================================");
	}
	private static void printJobLength(){
		
		System.out.println("Job Lengths");
		
		for (Job job : Simulator.jobs) {
			System.out.print(Integer.toString(job.processingTime) + ", ");
		}
		System.out.println();
		System.out.println("============================================================");
	}
}















