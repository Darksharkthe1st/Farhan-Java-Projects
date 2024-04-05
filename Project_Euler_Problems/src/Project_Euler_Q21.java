
public class Project_Euler_Q21 {
	public static void main(String[] args) {
		
	}
	static int[] factorFinder(int number) {
		int[] factors = new int[number];
		for (int i = 0; i < number; i++) {
			factors[i] = -1;
		}
		int currentFactor = 1;
		int factorCount = 0;
		int numSqrt = (int)Math.sqrt(number);
		while (number != 1 && currentFactor < numSqrt) {
			currentFactor ++;
			if (number % currentFactor == 0) {
				factors[factorCount] = currentFactor;
				factorCount ++;
				number = number / currentFactor;
				numSqrt = (int)Math.sqrt(number);
				currentFactor = 1;
			}
		}
		if (number != 1) {
			factors[factorCount] = number;
		}
		return factors;
	}
}
