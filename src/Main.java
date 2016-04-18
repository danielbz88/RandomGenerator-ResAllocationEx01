import java.util.Arrays;

public class Main {
	public static final int M = 12500;
	public static final int a = 80;

	public static void main(String []args) {

		RandomNumberGenerator.generateParedoSpread();
		System.out.println(RandomNumberGenerator.getParedoArray());

		//		for (int i = 0; i < 20; i++){
		//			System.out.println("Percentile = " + RandomNumberGenerator.getParedoRandomNumber() + "%");
		//			System.out.println(RandomNumberGenerator.getUniformRandomNumber(11, 20));
		//		}

		//		Simulator.runSimulation();

		System.out.println(Arrays.toString(bafkbn(RandomNumberGenerator.array)));
	}

	public static class RandomNumberGenerator {
		private static long z = System.currentTimeMillis();
		private static double[] array = new double[Main.M];

		public static double getUniformRandomNumber(){
			double output = generateUniformRandomNumber();
			return output;
		}

		public static int getParedoRandomNumber(){
			int percentile;
			double uniRandom = generateUniformRandomNumber();
			percentile = Arrays.binarySearch(array, uniRandom);

			if (percentile < 0) {
				percentile = -1 * percentile - 1;
			}

			return ((percentile * 100) / Main.M) + 1;
		}

		public static String getParedoArray(){
			return Arrays.toString(array);
		}

		private static void generateParedoSpread(){

			double ratioA = (double)Main.a / 100;
			double ratioB = (double)(100 - Main.a)/ 100;
			double money = (double) Main.M;
			double people = array.length;
			double depth = Math.ceil(Math.log(5.0/people) / Math.log(ratioB));
			double indMoney;
			int topIndex = array.length - 1;

			for (int i = 0; i < depth; i++){

				//update difference
				indMoney = Math.floor(money * ratioB) / Math.ceil(people * ratioA);
				people = Math.floor(people * ratioB);

				for (; topIndex >= people; topIndex--, money -= indMoney) {
					array[topIndex] = money / Main.M;
				}

				indMoney = Math.floor((money) * ratioB) / Math.ceil(people * ratioA);				
			}

			indMoney = money / people;

			// remaining cells get an equal share of the remainder
			for(; topIndex >= 0; topIndex--, money -= indMoney) {
				array[topIndex] = money / Main.M;			}
		}

		private static double generateUniformRandomNumber() {

			long m = 2147483648L;
			long c = 0x3039;
			long a = 0x41C64E6D;
			long  mask = 0x7FFF0000;

			// select bits 30 .. 16 (mask = 2147418112)
			z = (a*z + c) & (m - 1);
			z = z & mask;

			double output = (double)z / Math.pow(2, 31);
			//			double output = (double)(z >> 30);

			return output;
		}

		public static int getUniformRandomNumber(int low, int high) {
			double rand = getUniformRandomNumber();
			double output = rand * (high - low) + low;

			return (int) Math.round(output);
		}
	}


	private static int[][] newFunction(double[] array) {
		//		double[] array = new double[Main.M];
		int[][] bins = new int[Main.M][2];


		int binSize = (int)array[0];
		int binsIndex = 0;
		int sum = 0;

		// go over array and sum numbers until adding any more would surpass binSize
		for (int i = 0; i < array.length; i++, binsIndex++){
			sum = 0;
			bins[binsIndex][0] = i; 

			// sum array cells until we reach required size
			while((int)array[i] + sum <= binSize && i < array.length) {
				sum += (int)array[i];
				i++;
			}

			bins[binsIndex][1] = i;
		}

		return bins;

	}

	private static int[] anotherNewFunction(int[] array,int[][] bins) {

		int[] outputArray = new int[bins.length];
		int sum = 0;
		int startIndex;
		int endIndex;
		
		
		for (int i = 0; i < bins.length; i++) {
			sum = 0;
			
			for (startIndex = bins[i][0], endIndex = bins[i][1]; startIndex <= endIndex; startIndex++){
				sum += array[startIndex];
			}
			
			outputArray[i] = sum;
		}

		return outputArray;
	}
}













