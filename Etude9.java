
import java.util.ArrayList;
import java.util.Scanner;

public class Etude9 {

    private static final int TOTAL_PIECES = 19;
    private static ArrayList<int[][]> pieces;
    private static int width;
    private static int length;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        createPieces();
        Scanner scan = new Scanner(System.in);
         width = scan.nextInt();
        length = scan.nextInt();

        if (width * length % 4 != 0) {
            System.out.println("Unfillable");
            System.out.println(0);
            return;
        }


        boolean[][] carpet = new boolean[width][length];

        long startTime = System.currentTimeMillis();
        long counter = addPieces(carpet, 0, 0);
        long endTime = System.currentTimeMillis();

        System.out.println("Counter : " + counter);
        float duration = (endTime - startTime);
        duration = duration / 1000; //to seconds?
        System.out.println("Time:" + duration + " seconds");
    }

    private static boolean[][] add(boolean[][] carpet, int[][] p, int x, int y) {

        //create a new array
        boolean[][] newArray = new boolean[width][length];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < length; col++) {
                newArray[row][col] = carpet[row][col];
            }
        }
        //see if the piece can fit
        int row;
        int col;
        for (int[] piece : p) {
            row = x + piece[0];
            col = y + piece[1];
            if (carpet[row][col]) {
                return null;
            } else {
                newArray[row][col] = true;
            }
        }
        return newArray;
    }

    private static long addPieces(boolean[][] carpet, int row, int col) {

        if (col == length) {
            if (row== width - 1) {
                if (carpet[row][col- 1] == true) {
                    //Base case
                    //System.out.println("Valid" + String.valueOf(lastPiece));
                    return 1;
                }
                //invalid
                return 0;
            }
            row= row + 1;
            col = 0;
        }

        long result = 0;
        if (!carpet[row][col]) {
            for (int[][] p : pieces) {
                if (checkFreeSpots(carpet, row, col)) {
                    if (checkBounds(p, row, col)) {
                        boolean[][] newCarpet = add(carpet, p, row, col);
                        if (newCarpet != null) {
                            result += addPieces(newCarpet, row, col + 1);
                        }
                    }
                }
            }
        } else {
            if (carpet[row][col]) {
                return addPieces(carpet, row, col + 1);
            }
        }
        return result;
    }

    private static boolean checkBounds(int[][] p, int x, int y) {
        for (int[] piece : p) {
            int row = x + piece[0];
            int col = y + piece[1];

            if (row >= width || col >= length|| row < 0 || col < 0) {
                return false;
            }

        }
        return true;
    }


    //Checks to see if current spot has free spots next to it
    public static boolean checkFreeSpots(boolean[][] carpet, int row, int col) {
        int i = 0;
        int a =0;
        int b =0;
        int c =0;
        int d =0;
        if (row + 1 < width) {
            if (carpet[row + 1][col]) {
                a++;
            }
        } else {
            a++;
        }
        if (row > 0) {
            if (carpet[row - 1][col]) {
                b++;
            }
        } else {
            b++;
        }
        if (col + 1 < length) {
            if (carpet[row][col + 1]) {
                c++;
            }
        } else {
            c++;
        }
        if (col > 0) {
            if (carpet[row][col - 1]) {
                d++;
            }
        } else {
            d++;
        }
        i = a+ b+ c+d;
        if (i == 4) {
            return false;
        }
        return true;
    }

  
    public static int[][] translate(int[][] piece, int[] distance) {
        for (int i = 0; i < 4; i++) {
            piece[i][0] += distance[0];
            piece[i][1] += distance[1];
        }
        return piece;
    }

    //copies a 2d arrray
    public static int[][] copy(int[][] old) {
        int[][] array = new int[old.length][];
        for (int i = 0; i < old.length; i++) {
            array[i] = old[i].clone();
        }
        return array;
    }

   private static void createPieces() {
        pieces = new ArrayList<int[][]>();

        // reverse steps
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, -1}, {2, -1}});//1
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 1}, {1, 2}});//2

        // steps
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 1}});//3
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, -1}, {0, 1}});//4

        // T
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, -1}, {2, 0}});//5
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 1}, {0, 2}});//6
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 0}});//7
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, -1}, {1, 1}});//8

        // L
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 2}});//9 L
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, -1}});//10
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, 1}, {1, 2}});//11
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {0, 1}});//12

        // Reverse L
        pieces.add(new int[][]{{0, 0}, {1, 0}, {0, 1}, {0, 2}});//13
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, 1}});//14
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, -1}, {1, -2}});//15
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 1}, {2, 1}});//16

        // Line
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}});//18
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}});//19

        // Square
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});//17
    }
}