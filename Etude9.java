
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author willy
 */
public class Etude9 {

    public static final int TOTAL_CARPET_STATES = 19;
    public static ArrayList<int[][]> pieces;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int width = 0;
        int length = 0;
        while (scanner.hasNext()) {
            try {
                width = scanner.nextInt();
                length = scanner.nextInt();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.exit(0);
            }
            createPieces();
            int[][] carpet = new int[width][length];

            int piece = 0;

            int result = 0;

            //while (piece < TOTAL_CARPET_STATES) {
            result += tryPiece(carpet);
            //    piece++;
            //}
            for (int[] row : carpet) {
                for (int col : row) {
                    System.out.print(col + " ");
                }
                System.out.println();
            }
        }
    }

    public static int tryPiece(int[][] carpet) {

        int col = 0;

        while (col < carpet[0].length) {
            int row = 0;
            while (row < carpet.length && carpet[row][col] == 0) {
                int i = 0;
                while (i < 1) {
                    Shape.shape1(carpet, pieces.get(i), row, col);
                    i++;
                }
                row++;
            }
            col++;
        }
        return 0;
    }

    public static void createPieces() {
        pieces = new ArrayList<int[][]>();
       // pieces.add(new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 1}});
        pieces.add(new int[][]{{1, 0}, {1, 1}, {0, 1}, {0, 2}});
    }
}
