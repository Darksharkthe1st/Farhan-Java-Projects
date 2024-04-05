import java.math.BigInteger;
import java.util.Timer;

public class Project_Euler_Q20 {
	public static void main(String[] args) {
		projectEulerQ20();
	}
	
	static void projectEulerQ20() {
		/*	n! means n factorial (duh)
		 * For example, 10! = 3628800
		 * The sum of the digits of 10! = 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27
		 * Find the sum of the digits in the number 100!*/
		//Since zeroes don't count towards the final total, I'm going to work to get rid of all factors of 10 first.
		//To do that, I need to first find all of the prime factors of each number from 1 to 100 though.
//		int[] allFactors = new int[0];
//		int[] currentFactors;
//		for (int i = 2; i <= 100; i++) {
//			print(i + ": ");
//			currentFactors = factorFinder(i);
//			print(countExclusive(currentFactors, -1) + ": ");
//			currentFactors = removeFromArr(currentFactors, -1);
//			
//			print(currentFactors); print("\n");
//			allFactors = combineArr(allFactors, currentFactors);
//		}
//		print("All factors (1): ");
//		print(allFactors);
//		int indexOf2  = allFactors.length - 1, indexOf5 = allFactors.length - 1;
//		boolean factorFound;
//		boolean condition = true;
//		while (true) {
//			factorFound = false;
//			for (int i = indexOf2; i >= 0; i--) { 
//				if (allFactors[i] == 2) {
//					factorFound = true;
//					indexOf2 = i;
//					break;
//				}
//			}
//			if (!factorFound) {
//				break;
//			}
//			
//			factorFound = false;
//			for (int i = indexOf5; i >= 0; i--) { 
//				if (allFactors[i] == 5) {
//					factorFound = true;
//					indexOf5 = i;
//					break;
//				}
//			}
//			if (!factorFound) {
//				break;
//			} else {
//				allFactors[indexOf5] = 1;
//				allFactors[indexOf2] = 1;
//			}
//		}
//		print("\nAll factors (2): ");
//		print(allFactors); print("\n");
//		allFactors = removeFromArr(allFactors, 1);
//		
//		System.out.print("\n\nNew List: ");
//		print(allFactors); System.out.println();
//		BigInteger factorial = BigInteger.valueOf(1);
//		for (int i = 0; i < allFactors.length; i++) {
//			System.out.print(factorial);
//			factorial = factorial.multiply(BigInteger.valueOf(allFactors[i]));
//			System.out.print(allFactors[i] + "\n");
//		}
//		
//		System.out.println("Factorial is: " + factorial);
//		BigInteger digitSum = BigInteger.valueOf(0);
//		while (factorial.compareTo(BigInteger.valueOf(0)) == 1) {
//			digitSum = digitSum.add(factorial.remainder(BigInteger.valueOf(10)));
//			factorial = factorial.divide(BigInteger.valueOf(10));
//		}
//		System.out.println(digitSum.toString());
		
		int factorTo = 40;
		
		System.out.println(factorial1(40).divide(factorial1(20)).divide(factorial1(20)).toString());
		
		System.out.println("NEXT: ");
		//System.out.println(recursiveFactorial(BigInteger.valueOf(1), 1, factorTo).toString());
		
		
	}
	
	static BigInteger factorial1(int toValue) {
		int[] allFactors = factorialList(toValue);
		BigInteger factorial = BigInteger.valueOf(1);
		for (int i = 1; i <= toValue; i++) {
			//System.out.print(factorial);
			factorial = factorial.multiply(BigInteger.valueOf(i));
			//System.out.print(allFactors[i] + "\n");
		}
		return factorial;
	}
	
	static BigInteger recursiveFactorial(BigInteger prev, int current, int goal) {
		prev = prev.multiply(BigInteger.valueOf(current));
		current ++;
		if (current < goal) {
			prev = recursiveFactorial(prev, current, goal);
		}
		//System.out.println(prev);
		return prev;
	}
	
	static int[] factorialList(int factorTo) {
		int[] currentFactors;
		int[] allFactors = new int[0];
		for (int i = 2; i <= factorTo; i++) {
			//print(i + ": ");
			currentFactors = factorFinder(i);
			//print(countExclusive(currentFactors, -1) + ": ");
			currentFactors = removeFromArr(currentFactors, -1);
			
			//print(currentFactors); print("\n");
			allFactors = combineArr(allFactors, currentFactors);
		}
		return allFactors;
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
	
	static <auto> void print(auto a) {
		System.out.print(a);
	}
	static void print(int[] arr) {
		print("[");
		for (int i = 0; i < arr.length - 1; i++) {
			print(arr[i] + ", ");
		}
		print(arr[arr.length - 1] + "]");
	}
	
	static int countExclusive(int[] arr, int toExclude) {
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			count = count + ((arr[i] != toExclude) ? 1 : 0);
		}
		return count;
	}
	static int[] removeFromArr(int[] arr, int toRemove) {
		int[] returnArr = new int[countExclusive(arr, toRemove)];
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != toRemove) {
				returnArr[count] = arr[i];
				count++;
			}
		}
		
		return returnArr;
	}
	
	
	static int[] combineArr(int[] arr1, int[] arr2) { 
		int[] returnArr = new int[arr1.length + arr2.length];
		for (int i = 0; i < arr1.length; i++) {
			returnArr[i] = arr1[i];
		}
		for (int i = 0; i < arr2.length; i++) {
			returnArr[i + arr1.length] = arr2[i];
		}
		return returnArr;
	}
	
	
}
