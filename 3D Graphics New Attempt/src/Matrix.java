import java.util.Arrays;


//Self made matrix class built to handle matrix multiplication for 3d applications
public class Matrix {
	
	//Dimensions
	int m, n;
	
	//The matrix itself (2D array)
	double[][] arraytrix;
	public Matrix(double[][] arraytrix) {
		super();
		this.arraytrix = arraytrix;
		m = arraytrix.length;
		n = arraytrix[0].length;
	}
	
	//Multiplies "this" by another matrix and stores the result in "this"
	public void multiply(Matrix other) {
		if (n != other.m) return;
		double[][] outputArr = new double[m][other.n];
		int sum;
		
		//For each row in original
		for (int a = 0; a < m; a++) {
			
			//For each col in other
			for (int col = 0; col < other.n; col++) {
				sum = 0;
				//Add up product of matching values from row of me and col of other
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
	
	
	//Non-mutating version of non-static multiply
	public static Matrix multiply(Matrix one, Matrix two) {
		if (one == null || two == null) return null;
		if (one.n != two.m) return null;
		double[][] outputArr = new double[one.m][two.n];
		int sum;
		//For each row in original
		for (int a = 0; a < one.m; a++) {
			//For each col in other
			for (int c = 0; c < two.n; c++) {
				sum = 0;
				//Add up product of matching values from row of one and col of two
				for (int r = 0; r < two.m; r++) {
					sum += one.arraytrix[a][r] * two.arraytrix[r][c];
				}
				outputArr[a][c] = sum;
			}
		}
		return new Matrix(outputArr);
	}
	
	//Multiplies multiple matrices
	public static Matrix multiply(Matrix[] matrices) {
		if (matrices.length == 0) return null;
		Matrix result = matrices[0];
		for (int i = 1; i < matrices.length; i++) {
			result = multiply(result, matrices[i]);
		}
		return result;
	}
	
	//Makes an identity matrix
	public static Matrix identity(int size) {
		double[][] arrayx = new double[size][size];
		for (int i = 0; i < size; i++) {
			arrayx[i][i] = 1;
		}
		return new Matrix(arrayx);
	}
	
	//Checks if a matrix is the identity matrix
	public boolean isIdentity() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					if (Math.abs(arraytrix[i][j] - 1) > 0.01) return false;
				} else {
					if (Math.abs(arraytrix[i][j]) > 0.01) return false;
				}
			}
		}
		return true;
	}
	
	//Just prints all the individual rows using arrays.tostring
	public String toString() {
		String output = "";
		for (double[] arr : arraytrix)
			output += Arrays.toString(arr) + "\n";
		return output;
	}
}