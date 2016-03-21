public class FormattedTablePrint {

    public static void printArray(String[][] matrix) {
    	for (int i = 0; i < matrix.length ; i++){
    		for (int j = 0; j < matrix[0].length; j++){
    			System.out.print(matrix[i][j]);
                System.out.print("\t");
                
                if (i == 0 && j > 4) {
                	System.out.print("\t");
                }
                
                if (i > 1 && j >= 3) {
                	System.out.print("\t");
                }
                
                if (i == 1) {
                	System.out.print("   ");
                }
                
                if (i > 1){
                	System.out.print("\t");
                }
    		}
    		System.out.print("\n");
    	}
    }
}