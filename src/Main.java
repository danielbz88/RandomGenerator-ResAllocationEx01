import java.io.IOException;



public class Main {
	public static final int M = 100;
	public static final int a = 80;
	
	public static void main(String []args) {
		for(int i = 0; i < 15; i++){
			System.out.println(RandomNumberGenerator.getRandomNumber());
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class RandomNumberGenerator {
		private static long z = System.currentTimeMillis();
		private static double[] array = new double[Main.M];
		
		public static double getRandomNumber(){
			double output = generateUniformNumber();
			return output;
		}
		
		public static double getParetoRandomNumber() {
			double ratio = (double)Main.a / 100;
			
			
		}
		
		
		// Preprocessing to fill in array of size M with the 
		private static void generateParetoSpread(){
			double ratio = (double)Main.a / 100;
			double doubleM = (double) Main.M;
			int index = Main.M - 1;
			
			double leftoverProb = 1.0;

			int groupSize = (int)Math.ceil(M * ratio);
			double prob = doubleM * (1.0 - ratio) / groupSize;
			
			for (int i = 0; i < groupSize; i++, index--) {
				array[index] = prob;
			}
			
			leftoverProb = leftoverProb - prob;
			
			// need to go from tail to head, filling in the probabilities
			// can it be calculated ahead of time what the path would be to each index?
			// if so, I'll just use a^currentDepth * (1.0-a) until I reach the final group (at the most left).
			
			
			
		}
		
		private static double generateUniformNumber() {
			
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
