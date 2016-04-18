import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class daniel {	
	public static final int M = 12500;
	public static final int a = 80;

	public static void main(String []args) {			
		RandomNumberGenerator.generateParedoSpread();			

		int[] observed = new int[Main.M];
		for(int i = 0; i < 62500; i++){
			observed[RandomNumberGenerator.getParedoRandomNumber() - 1]++;
		}
		//System.out.println(Arrays.toString(observed));

		double[] expectedDistributions = RandomNumberGenerator.generateProbabilityArray();
		double[] expected = new double[Main.M];
		
		for (int i = Main.M - 1; i > 0; i--){
			expected[i] = (expectedDistributions[i] - expectedDistributions[i - 1]) * 62500;
		}
		
		expected[0] = expected[1];
		System.out.println(Arrays.toString(expected));
		int[][] array = arrayBuilder(expected);
		print2d(array, 12);

		int[] newArray = anotherNewFunction(array, observed);
		
		System.out.println(Arrays.toString(newArray));
//		chiSquare(expected, observed);
		int[] perfectBins = new int[12];
		int num = 62500 / 12;
		for (int i = 0; i< 12; i++) {
			perfectBins[i] = num;
		}
		
		System.out.println(chiSquareTest(perfectBins, newArray, 12));

	}
	

	private static int[] anotherNewFunction(int[][] bins, int[] array) {
		int[] outputArray = new int[12];
		int sum = 0;
		int startIndex;
		int endIndex;

		for (int i = 0; i < 12; i++) {
			sum = 0;
			for (startIndex = bins[i][0], endIndex = bins[i][1]; startIndex <= endIndex && startIndex < array.length; startIndex++){
				
				if (startIndex == endIndex || startIndex == endIndex - 1){
					System.out.println();
				}

				
				sum += array[startIndex];
				
				
			}

			outputArray[i] = sum;
		}

		return outputArray;
	}

	
	//System.out.println(Arrays.toString(newFunction(expected)));
	private static void print2d(int[][] array, int lastColumn){
		for (int i = 0; i < array[0].length ; i++){
			for (int j = 0; j < lastColumn; j++){
				System.out.print(array[j][i]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
	}
	private static int[][] arrayBuilder(double[] array) {
		int[][] bins = new int[Main.M][2];


		int binSize = (int)array[0];
		int binsIndex = 0;
		int sum = 0;

		// go over array and sum numbers until adding any more would surpass binSize
		for (int i = 0; i < array.length; i++, binsIndex++){
			
			if (binsIndex == 11){
				
				System.out.println();
			}
			sum = 0;
			bins[binsIndex][0] = i; 

			// sum array cells until we reach required size
			while(array[i] + sum < binSize && i < array.length) {
				sum += array[i];
				i++;
				if  (i == array.length){
					break;
				}
			}

			bins[binsIndex][1] = i;
		}

		return bins;

	}
	/*
		public static double[] chiTest(int[] expected, int[] observed){
			double[][] bins = new double[500][3];
			int limit = expected[0] * 2; //size will be sum of top 2 ppl
			int binCount = 0;
			for(int i = 0; i < Main.M; i++){
				if(bins[binCount] + expected[i] <= limit){
					bins[binCount][0] = i; 
				} else{
					binCount++;
					bins[binCount] = i;
				}
			}
			return bins;

			double currChi = 0; 
			for(int i = 0; i < k; i++){
				currChi += bins[i];
			}
			return currChi;		 	
		}*/
	public static int getDepth(int a){
		return (int) Math.ceil(Math.log(5 / Main.M) / Math.log((100 - a) / 100));
	}
	public static void writeToTXT(int[] randomValues) {
		try{
			String weightsText = "";

			File file = new File("pareto-expected.txt");

			// if file does not exist, create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(weightsText + Arrays.toString(randomValues));
			bw.close();

			//output onto txt file done
		}
		catch(IOException e) {
			e.printStackTrace();

		}
	}
	public static double getIntersect(int a1, int a2, int b1, int b2){
		//returns the probability that a random # will be in both a and b, 
		RandomNumberGenerator.generateParedoSpread();
		int count = 0;
		for (int i = 0; i < 62500; i++){
			int x = RandomNumberGenerator.getParedoRandomNumber();
			int y = RandomNumberGenerator.getParedoRandomNumber();
			if (x >= a1 && x <= a2 && y >=b1 && y <= b2){
				count++;
			}
		}
		return (double) count / 62500;
	}
	
	public static double chiSquareTest(int[] expectedBins, int[] observedBins, int numberOfBins){
		double output;
		double sum = 0;
		
		// compare all k bins in arrays 
		for (int i = 0; i < numberOfBins; i++){
			sum += Math.pow((observedBins[i] - expectedBins[i]), 2) / expectedBins[i];
		}
		
		return sum;
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
//			Random rand = new Random();

//			double uniRandom = rand.nextDouble();
			percentile = Arrays.binarySearch(array, uniRandom);

			if (percentile < 0) {
				percentile = -1 * percentile - 1;
			}

			return percentile  + 1;
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

		private static double[] generateProbabilityArray(){

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
				array[topIndex] = money / Main.M;			
			}
			return array;
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
			//				double output = (double)(z >> 30);

			return output;
		}
	}
}












