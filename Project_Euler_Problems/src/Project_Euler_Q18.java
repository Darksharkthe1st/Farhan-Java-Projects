
public class Project_Euler_Q18 {
	
	static int[] values;
	static byte[] splitChecked;
	static int triangleSize = 15;
	public static void main(String[] args) {
		
		int cellCount = 0;
		for (int i = 1; i <= triangleSize; i++) cellCount += i;
		
		values = new int[cellCount];
		splitChecked = new byte[cellCount];
		java.util.Scanner s = new java.util.Scanner(""
				+ "              75\n"
				+ "             95 64\n"
				+ "            17 47 82\n"
				+ "           18 35 87 10\n"
				+ "          20 04 82 47 65\n"
				+ "         19 01 23 75 03 34\n"
				+ "        88 02 77 73 07 63 67\n"
				+ "       99 65 04 28 06 16 70 92\n"
				+ "      41 41 26 56 83 40 80 70 33\n"
				+ "     41 48 72 33 47 32 37 16 94 29\n"
				+ "    53 71 44 65 25 43 91 52 97 51 14\n"
				+ "   70 11 33 28 77 73 17 78 39 68 17 57\n"
				+ "  91 71 52 38 17 14 91 43 58 50 27 29 48\n"
				+ " 63 66 04 68 89 53 67 30 73 16 69 87 40 31\n"
				+ "04 62 98 27 23 09 70 98 73 93 38 53 60 04 23\n");
		System.out.print(""
				+ "              75\n"
				+ "             95 64\n"
				+ "            17 47 82\n"
				+ "           18 35 87 10\n"
				+ "          20 04 82 47 65\n"
				+ "         19 01 23 75 03 34\n"
				+ "        88 02 77 73 07 63 67\n"
				+ "       99 65 04 28 06 16 70 92\n"
				+ "      41 41 26 56 83 40 80 70 33\n"
				+ "     41 48 72 33 47 32 37 16 94 29\n"
				+ "    53 71 44 65 25 43 91 52 97 51 14\n"
				+ "   70 11 33 28 77 73 17 78 39 68 17 57\n"
				+ "  91 71 52 38 17 14 91 43 58 50 27 29 48\n"
				+ " 63 66 04 68 89 53 67 30 73 16 69 87 40 31\n"
				+ "04 62 98 27 23 09 70 98 73 93 38 53 60 04 23\n");
		
		
		int index = 0;
		while (s.hasNext()) {
			values[index] = s.nextInt();
			splitChecked[index] = 0;
			index++;
		}
		System.out.println(index);
		readTriangle();
		
	}
	
	static void readTriangle() {
		//print(splitChecked); System.out.println();
		int maxSum = 0;
		int temp;
		int range = (int)Math.pow(2, triangleSize);
		for (int i = 0; i < range; i++) {
			temp = addNext(0, 1, values.length);
			if (temp > maxSum) maxSum = temp;
		}
		System.out.println("The max sum is: " + maxSum);
	}
	
	static int addNext(int index, int row, int size) {
		//System.out.print("{" + index + ", " + values[index] + ", " + row + "}; ");
		
		if (index >= size - row - 1) {
			return values[index];
		}
		
		if (splitChecked[index] != 1 && (splitChecked[index + row + 1] == 2 || index + row + 1 >= size - (row + 1) - 1)) {
			splitChecked[index] = 1;
			return (values[index] + addNext(index + row + 1, row + 1, size));
		}
		
		if (splitChecked[index] == 0) {
			return (values[index] + addNext(index + row + 1, row + 1, size));
		} else {
			splitChecked[index] = 2;
			return (values[index] + addNext(index + row, row + 1, size));
		}
	}
	
	static <auto> void print(auto a) {
		System.out.print(a);
	}
	static void print(int[] arr) {
		print("[");
		for (int i = 0; i < arr.length - 1; i++) {
			print(arr[i] + ", ");
		}
		String s;
		print(arr[arr.length - 1] + "]");
	}
	
	static void print(byte[] arr) {
		print("[");
		for (int i = 0; i < arr.length - 1; i++) {
			print(arr[i] + ", ");
		}
		print(arr[arr.length - 1] + "]");
	}
}
