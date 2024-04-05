import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Problem_1 {
	public static void main(String[] args) {
		System.out.println(Result.findHandSum(5923, "56 27 73 34 99 45 32 17 64 57 18 11", "36 92 22 50 82"));

	}

class Result {

    /*
     * Complete the 'findHandSum' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER originalRows
     *  2. STRING handTiles
     *  3. STRING drawPile
     */

    public static int findHandSum(int originalRows, String handTiles, String drawPile) {
    //originalRows: the starting row numbers. 1 digit per row
    //handTiles: string w/ hand's tiles separated by spaces
    //drawPile:  string w/ draw's tiles separated by spaces
    //Single digit number means one of the tiles has a 0 on it
        int[] rows = formRows(originalRows);
        String[] hand = parseTiles(handTiles);
        String[] draw = parseTiles(drawPile);
        printAll(rows,hand,draw);
        String d[] = new String[1];
        String match;
        int prevRow = -1;
        boolean wasDouble = false;
        
        wholeGame:
        while (true) {
            if (wasDouble) {
                //When we have a double, we check everything in the hand to see if it fits in the double row
                match = "none";
                for (int h = 0; h < hand.length; h++) {
                    match = returnIfMatch(hand, h, rows, prevRow); //prevRow = row of double b/c double was last tile completed
                    if (match != "none") {
                        break;
                    }
                }
                //If no matches were found:
                if (match == "none") {
                    while (draw.length > 0) {
                        hand = addToArr(draw[0], hand);
                        draw = removeFromArr(0, draw);
                        match = returnIfMatch(hand, hand.length - 1, rows, prevRow);
                        if (match != "none") {
                            break;
                        }
                    }
                }
                if (match == "none") {
                    break wholeGame;
                }
            } else {
                match = "none"; //Saves the match
                outer:
                    //Loop through all tiles in hand
                    for (int h = 0; h < hand.length; h++) {
                        int r = (prevRow + 1) % rows.length;
                        //Loop through all rows
                        do {
                            match = returnIfMatch(hand, h, rows, r);
                            System.out.println("Reached!: " + match + "; " + hand[h] + "; " + h + "; " + r);
                            if (match != "none") {
                                System.out.println("Tile: " + hand[h] + "; Row: " + r);
                                removeFromArr(h, hand); //Removes the tile from the hand that was placed
                                 break outer;
                            }
                            r++; r %= rows.length; //we increment rows and set it back to 1 if it goes over
                        } while (r != (prevRow + 1) % rows.length);
                    }
                    
                    //If there are no matches found, we pull from the draw until we find it or run out of tiles in the draw
                    if (match == "none") {
                        outerDraw:
                        while (draw.length > 0) {
                        
                        //Takes a card from the draw
                        String currentTile = draw[0];
                        hand = addToArr(currentTile, hand);
                        removeFromArr(0, draw);
                        
                        //re-run the loop for the new tile
                        int r = prevRow + 1;
                        //Loop through all rows
                        do {
                            match = returnIfMatch(hand, hand.length - 1, rows, r);
                            if (match != "none") break outerDraw;
                            r++; r %= rows.length; //we increment rows and set it back to 1 if it goes over
                        } while (r != prevRow + 1);
                        
                        }
                    }
                    
                    System.out.println("MATCH FINSIHED: " + match);
            }
            
            if (match != "none") {
                    
                    System.out.println("MATCH ACCRUED");
                    //Memorizes if it was a double
                    /* char 0: */ wasDouble = match.charAt(0) == '1';
                    
                    //Takes the used card out of the hand
                    /* char 1: */ hand = removeFromArr(match.charAt(1) - '0', hand);
                    
                    //Sets the row to the new value
                    /* char 2: */ rows[Integer.valueOf(match.substring(3))] = match.charAt(2) - '0';
                    
                    //Sets prevRow to the new row.
                    /* char3+: */ prevRow = Integer.valueOf(match.substring(3));
            } else {
                break wholeGame;
            }
            printAll(rows, hand, draw);
            System.out.println(wasDouble + ", " + prevRow);
        }
        
        printAll(rows, hand, draw);
        System.out.println(wasDouble + ", " + prevRow);
        
        
        int sum = 0;
        for (int i = 0; i < hand.length; i++) {
            sum += hand[i].charAt(0) - '0';
            sum += hand[i].charAt(1) - '0';
        }
        return sum;
    }
    
    public static int left(String tile) {
        return tile.charAt(0) - '0';
    }
    
    public static int right(String tile) {
        return tile.charAt(1) - '0';
    }
    
    public static String returnIfMatch(String[] hand, int h, int[] rows, int r) {
        if (left(hand[h]) == rows[r]) {
            String match = "";
            /* char 0: */ match += ((hand[h].charAt(0) == hand[h].charAt(1)) ? '1' : '0'); //If it was a double
            /* char 0: */ match += h; //Hand index
            /* char 0: */ match += String.valueOf(hand[h].charAt(1)); //Returns the RIGHT side - opp. to the matching side
            /* char 0: */ match += r; //The row index
            return match;
        } 
        if (right(hand[h]) == rows[r]) {
            String match = "";
            /* char 0: */ match += ((hand[h].charAt(0) == hand[h].charAt(1)) ? '1' : '0'); //If it was a double
            /* char 0: */ match += h; //Hand index
            /* char 0: */ match += String.valueOf(hand[h].charAt(0)); //Returns the LEFT side - opp. to the matching side
            /* char 0: */ match += r; //The row index
            return match;
        }
        return "none";
    }
    
    public static void printAll(int[] rows, String[] hand, String[] draw) {
        System.out.println(Arrays.toString(rows));
        System.out.println(Arrays.toString(hand));
        System.out.println(Arrays.toString(draw));
    }
    
    
    public static String[] parseTiles(String s) {
        int spaces = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') spaces++;
        }
        spaces++; String[] tiles = new String[spaces];
        Scanner scanner = new Scanner(s);
        int counter = 0;
        while (scanner.hasNext()) {
            tiles[counter++] = scanner.next();
            if (tiles[counter-1].length() == 1) {
                tiles[counter-1] += "0";
            }
        }
        return tiles;
    }
    
    public static int[] formRows(int rows) {
        String rowString = String.valueOf(rows);
        int[] newRows = new int[rowString.length()];
        for (int i = 0; i < rowString.length(); i++) {
            newRows[i] = (int)(rowString.charAt(i) - '0');
        }
        return newRows;
    }
    
    public static String[] removeFromArr(int index, String[] arr) {
        String[] copy = arr;
        arr = new String[arr.length - 1];
        for (int i = 0; i < index; i++) {
            arr[i] = copy[i];
        }
        for (int i = index + 1; i < copy.length; i++) {
            arr[i-1] = copy[i];
        }
        return arr;
    }
    
    public static String[] addToArr(String value, String[] arr) {
        String[] copy = arr;
        arr = new String[arr.length + 1];
        for (int i = 0; i < copy.length; i++) {
            arr[i] = copy[i];
        }
        arr[arr.length - 1] = value;
        return arr;
        
    }
}
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int originalRows = Integer.parseInt(bufferedReader.readLine().trim());

        String handTiles = bufferedReader.readLine();

        String drawPile = bufferedReader.readLine();

        int result = Result.findHandSum(originalRows, handTiles, drawPile);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();
        
        bufferedReader.close();
        bufferedWriter.close();
    }
}

}
