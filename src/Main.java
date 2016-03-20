import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
	public static final int M = 100;
	public static final int a = 80;
	
	public static void main(String []args) {
		
		RandomNumberGenerator.generateParedoSpread();
		System.out.println(RandomNumberGenerator.getParedoArray());
		System.out.println("Percentile = " + RandomNumberGenerator.getParedoRandomNumber());

	}
	
	public static class RandomNumberGenerator {
		private static long z = System.currentTimeMillis();
		private static double[] array = new double[Main.M];
		
		public static double getUniformRandomNumber(){
			double output = generateUniformRandomNumber();
			return output;
		}
		
		public static double getParedoRandomNumber(){
			int percentile;
			double uniRandom = generateUniformRandomNumber();
			System.out.println("uniRandom = " + uniRandom);
			percentile = Arrays.binarySearch(array, uniRandom);
			
			if (percentile < 0) {
				percentile = -1 * percentile - 1;
			}
						
			return percentile + 1;
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
	}
}
