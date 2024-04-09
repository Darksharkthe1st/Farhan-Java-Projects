import java.util.Arrays;


//Self made matrix class built to handle matrix multiplication for 3d applications
public class Matrix {
	
	//Dimensions
	int m, n;
	
	//The matrix itself
	double[][] arraytrix;
	public Matrix(double[][] arraytrix) {
		super();
		this.arraytrix = arraytrix;
		m = arraytrix.length;
		n = arraytrix[0].length;
	}
	
	//This constructor is only for Point3d, which makes its own arraytrix.
	protected Matrix(int m, int n) {
		this.arraytrix = new double[4][1];
		this.m = m;
		this.n = n;
	}
	
	//Multiplies "this" by another matrix and stores the result in "this"
	public void multiply(Matrix other) {
		if (n != other.m) return;
		double[][] outputArr = new double[m][other.n];
		int sum;
		for (int a = 0; a < m; a++) {
			for (int col = 0; col < other.n; col++) {
				sum = 0;
				for (int r = 0; r < n; r++) {
					sum += arraytrix[a][r] * other.arraytrix[r][col];
				}
				outputArr[a][col] = sum;
			}
		}
		this.arraytrix = outputArr;
		this.m = outputArr.length;
		this.n = outputArr[0].length;
	}
	
	public static Matrix multiply(Matrix one, Matrix two) {
		if (one == null || two == null) return null;
		if (one.n != two.m) return null;
		double[][] outputArr = new double[one.m][two.n];
		int sum;
		/*
		 * for (int a = 0; a < one.m; a++) { for (int col = 0; col < two.n; col++) { sum
		 * = 0; for (int r = 0; r < one.n; r++) { sum += one.arraytrix[a][r] *
		 * two.arraytrix[r][col]; } outputArr[a][col] = sum; } }
		 */
		
		for (int a = 0; a < one.m; a++) {
			for (int c = 0; c < two.n; c++) {
				sum = 0;
				for (int r = 0; r < two.m; r++) {
					sum += one.arraytrix[a][r] * two.arraytrix[r][c];
				}
				outputArr[a][c] = sum;
			}
		}
		return new Matrix(outputArr);
	}
	
	public static Matrix multiply(Matrix[] matrices) {
		if (matrices.length == 0) return null;
		Matrix result = matrices[0];
		for (int i = 1; i < matrices.length; i++) {
			result = multiply(result, matrices[i]);
		}
		return result;
	}
	
	public String toString() {
		String output = "";
		for (double[] arr : arraytrix)
			output += Arrays.toString(arr) + "\n";
		return output;
	}
}