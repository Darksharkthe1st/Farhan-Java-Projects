import java.util.Scanner;

public class Project_Euler_Q15 {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Starting in the top left corner of a 2 x 2 grid,"
				+ " and only being able to move to the right and down, there are exactly \r\n"
				+ "6 routes to the bottom right corner.\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "How many such routes are there through a \r\n"
				+ "20 x 20 grid?");
		//First, we'll try brute forcing it:
		for (int i = 2; i < 20; i ++)
			System.out.println("Paths in " + i + " x " + i + " " + countPaths(i));
		//System.out.println("Paths in 20 x 20: " + countPaths(20));
	}
	public static long countPaths(int size) {
		return simulatePaths(0, 0, size, 0);
	}
	public static long simulatePaths(int x, int y, int size, long count) {
		//If we've reached the end, we return
		if (x == size && y == size) {
			return count + 1;
		}
		if (x == size) {
			count = simulatePaths(x, y + 1, size, count);
		}
		else if (y == size) {
			count = simulatePaths(x + 1, y, size, count);
		}
		else {
			count = simulatePaths(x, y + 1, size, count);
			count = simulatePaths(x + 1, y, size, count);
		}
		return count;
	}
}
