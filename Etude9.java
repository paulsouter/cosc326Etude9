
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author paul
 */
public class Etude9 {

    public static final int TOTAL_CARPET_STATES = 51;
    public static ArrayList<int[][]> pieces;
    public static ArrayList<int[][][]> states;
    public static int statenum = 0;
    public static boolean foundCarpet = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int width = 0;
        int length = 0;
        ArrayList<uniqueCarpet> finishedCarpets = new ArrayList<uniqueCarpet>();
        while (scanner.hasNext()) {
            try {
                width = scanner.nextInt();
                length = scanner.nextInt();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.exit(0);
            }
            createPieces();
            //   createStates();
            if (width * length % 4 != 0) {
                System.out.println("not divisable by 4 ");
            }
            int piece = 0;
            int result = 0;
            int[][] carpet = new int[width][length];
            int[][] pieceLoc = new int[(width * length) / 4][2];

            while (piece < TOTAL_CARPET_STATES) {
                System.out.println("piece " + piece);
                for (int r = 0; r < carpet.length; r++) {
                    for (int c = 0; c < carpet[r].length; c++) {
                        carpet[r][c] = 0;
                    }
                }
                if (tryPiece(carpet, piece, pieceLoc, 0) != 0) {
                    System.out.println("Have " + finishedCarpets.size() + " carpets");
                    System.out.println("Found a complete carpet");
                    for (int[] row : carpet) {
                        for (int col : row) {
                            System.out.print(col + " ");
                        }
                        System.out.println();
                    }

                    uniqueCarpet current = new uniqueCarpet(carpet);
                    Boolean found = false;
                    for (uniqueCarpet c : finishedCarpets) {
                        if (Arrays.deepEquals(carpet, c.carpet)) {
                            System.out.println("but it was a duplicate... ");
                            found = true;
                            break;
                        }
                    }
                    //if(finishedCarpets.contains(current)){

                    if (!found) {
                        System.out.println("and it was unique!");
                        finishedCarpets.add(current);

                        result++;
                    }
                } else {
                    System.out.println("carpet filled = false");
                }
                System.out.println("\n");
                piece++;
            }
            for (int[] row : carpet) {
                for (int col : row) {
                    System.out.print(col + " ");
                }
                System.out.println();
            }
            System.out.println("\nResult was " + result + " unique carpets");
        }
    }

    //Checks to see if current spot has free spots next to it
    public static boolean checkFreeSpots(int[][] carpet, int row, int col) {
        int i = 0;
        if (row + 1 < carpet.length) {
            if (carpet[row + 1][col] != 0) {
                i++;
            }
        } else {
            i++;
        }
        if (row > 0) {
            if (carpet[row - 1][col] != 0) {
                i++;
            }
        } else {
            i++;
        }
        if (col + 1 < carpet[row].length) {
            if (carpet[row][col + 1] != 0) {
                i++;
            }
        } else {
            i++;
        }
        if (col > 0) {
            if (carpet[row][col - 1] != 0) {
                i++;
            }
        } else {
            i++;
        }
        if (i == 4) {
            return false;
        }

        return true;
    }

    //tries to fill a carpet 
    public static int tryPiece(int[][] carpet, int number, int[][] pieceLoc, int startingPiece) {
        if (startingPiece == 1)//break conditions
        {
            return 0;
        }

        int row = 0;

        // int[][] pieceLoc = new int[(carpet.length * carpet[0].length)][2];
        int lastPieceAdded = startingPiece;
        int i = number;
        //loops though the array trying to put peices in starting at 0,0 and removes peices when it can't add any more
        while (row < carpet.length) {
            int col = 0;
            while (col < carpet[0].length) {
                if (carpet[row][col] == 0) {
                    if (!checkFreeSpots(carpet, row, col)) {
                        i = 1000;
                    }
                    //goes through all the pieces to add
                    while (i < TOTAL_CARPET_STATES && carpet[row][col] == 0) {
                        System.out.println("col : " + col + " row " + row + " i " + i + "\n");
                        if (shape1(carpet, pieces.get(i), col, row, i + 1)) {
                            pieceLoc[lastPieceAdded][0] = row;
                            pieceLoc[lastPieceAdded][1] = col;
                            lastPieceAdded++;
                        } else if (startingPiece == 0 && lastPieceAdded == 0) {
                            return 0;
                        }
                        System.out.println("col : " + col + " row " + row + " i " + i + "\n");
                        for (int[] r : carpet) {
                            for (int c : r) {
                                System.out.print(c + " ");
                            }
                            System.out.println();
                        }
                        i++;
                    }
                }
                //if a peice wasn't added then it remove the last peice and sets row and col to that location
                if (carpet[row][col] == 0 && startingPiece <= lastPieceAdded) {
                    System.out.println("remove ");
                    if (lastPieceAdded == 0) {
                        return -1;
                    }
                    row = pieceLoc[lastPieceAdded - 1][0];
                    col = pieceLoc[lastPieceAdded - 1][1];
                    i = carpet[row][col];
                    System.out.println("i " + i);
                    System.out.println("piece " + ((carpet[pieceLoc[lastPieceAdded - 1][0]][pieceLoc[lastPieceAdded - 1][1]] - 1) + 1) + " row " + row + " col " + col);
                    remove(carpet, pieces.get(carpet[pieceLoc[lastPieceAdded - 1][0]][pieceLoc[lastPieceAdded - 1][1]] - 1), pieceLoc[lastPieceAdded - 1][0], pieceLoc[lastPieceAdded - 1][1]);
                    lastPieceAdded--;
                    // return false;

                } else {
                    i = 0;
                    col++;
                }
            }
            row++;
        }

        if (!foundCarpet) {
            System.out.println("starting Piece before " + startingPiece);
            if (startingPiece == 0) {
                startingPiece = lastPieceAdded - 2;
            } else {
                startingPiece--;
            }
            if (startingPiece == 0) {
                return 1;
            }
            foundCarpet = true;
        }

//        for (int[] r : pieceLoc) {
//            for (int c : r) {
//                System.out.print(c + " ");
//            }
//            System.out.println();
        //  }
        System.out.println("last piece added " + lastPieceAdded);
        System.out.println("starting Piece " + startingPiece);
        int piece = carpet[pieceLoc[startingPiece][0]][pieceLoc[startingPiece][1]];
        for (int x = startingPiece; x < lastPieceAdded; x++) {
//            System.out.println("peice x 0 :" +  pieceLoc[x][0]);
//            System.out.println("peice x 1 :" +  pieceLoc[x][1]);
            remove(carpet, pieces.get(carpet[pieceLoc[x][0]][pieceLoc[x][1]] - 1), pieceLoc[x][0], pieceLoc[x][1]);
        }
        for (int[] r : carpet) {
            for (int c : r) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
        return 1 + tryPiece(carpet, piece, pieceLoc, startingPiece);
    }

    //addd that piece to the carpet with the number in the carpet the same as the number of the shape
    //x and y are the position where the piece is to go
    public static boolean shape1(int[][] carpet, int[][] piece, int x, int y, int shape) {

        int[] cols = {piece[0][0] + x, piece[1][0] + x, piece[2][0] + x, piece[3][0] + x};
        int[] rows = {piece[0][1] + y, piece[1][1] + y, piece[2][1] + y, piece[3][1] + y};

//        for(int i =0; i < 4; i++){
//               System.out.println("row " + rows[i] + " col " + cols[i]);
//        }
        //checks if the location is in the bounds and if something is in there.
        int position = 0;
        while (position < 4) {
            if (rows[position] < carpet.length && rows[position] >= 0
                    && cols[position] < carpet[rows[position]].length
                    && cols[position] >= 0
                    && carpet[rows[position]][cols[position]] == 0) {
                position++;
            } else {
                break;
            }
        }
        if (position < 4) {
            //    System.out.println("Insertion failed");
            return false;
        } else {
            for (int i = 0; i < 4; i++) {
                carpet[rows[i]][cols[i]] = shape;

            }
            //  System.out.println("Inserted");
            return true;
        }
    }

    //removes that shape from the carpet
    public static void remove(int[][] carpet, int[][] piece, int x, int y) {
        int[] rows = {piece[0][1] + x, piece[1][1] + x, piece[2][1] + x, piece[3][1] + x};
        int[] cols = {piece[0][0] + y, piece[1][0] + y, piece[2][0] + y, piece[3][0] + y};
        //    System.out.println("pi");
        int position = 0;
        while (position < 4) {
            if (rows[position] < carpet.length && rows[position] >= 0
                    && cols[position] < carpet[rows[position]].length
                    && cols[position] >= 0) {
                position++;
            } else {
                // System.out.println("remove failed");
                break;
            }
        }
        if (position < 4) {
            //     System.out.println("remove failed");
        } else {
            for (int i = 0; i < 4; i++) {
                carpet[rows[i]][cols[i]] = 0;
                //      System.out.println("row " + rows[i] + " col " + cols[i]);
            }
//                  for (int[] r : carpet) {
//                            for (int c : r) {
//                                System.out.print(c + " ");
//                            }
//                            System.out.println();
//                        }
            //     System.out.println("removed shape");
        }

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
        int[][] myInt = new int[old.length][];
        for (int i = 0; i < old.length; i++) {
            myInt[i] = old[i].clone();
        }
        return myInt;
    }

    public static void createPieces() {
        pieces = new ArrayList<int[][]>();
        //(x,y)

        //negative steps
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 1}, {1, 2}});//1
        pieces.add(new int[][]{{0, 0}, {-1, 0}, {-1, 1}, {-2, 1}});//2

        // steps
        pieces.add(new int[][]{{0, 0}, {0, 1}, {-1, 1}, {-1, 2}});//3
        pieces.add(new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 1}});//4

        //T
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 1}});//5
        pieces.add(new int[][]{{0, 0}, {-1, 0}, {-2, 0}, {-1, 1}});//6
        pieces.add(new int[][]{{0, 0}, {0, -1}, {0, -2}, {-1, -1}});//7
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {1, -1}});//8

        //negative L
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, 1}});//9
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 2}});//10
        pieces.add(new int[][]{{0, 0}, {-1, 0}, {-2, 0}, {-2, -1}});//11
        pieces.add(new int[][]{{0, 0}, {0, -1}, {0, -2}, {1, -2}});//12

        // L
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, -1}});//13
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {-1, 2}});//14
        pieces.add(new int[][]{{0, 0}, {-1, 0}, {-2, 0}, {-2, 1}});//15
        pieces.add(new int[][]{{0, 0}, {0, -1}, {0, -2}, {-1, -2}});//16

        //square
        pieces.add(new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});//17

        //line
        pieces.add(new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}});//18
        pieces.add(new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}});//19

        //negative steps translate
        pieces.add(translate(copy(pieces.get(0)), new int[]{-1, -2}));//20
        pieces.add(translate(copy(pieces.get(1)), new int[]{2, -1}));//21

        //steps translate corner
        pieces.add(translate(copy(pieces.get(2)), new int[]{1, -2}));//22
        pieces.add(translate(copy(pieces.get(3)), new int[]{-2, -1}));//23

        //negative steps translate corner
        pieces.add(translate(copy(pieces.get(0)), new int[]{-1, -1}));//20
        pieces.add(translate(copy(pieces.get(1)), new int[]{1, -1}));//21

        //steps translate
        pieces.add(translate(copy(pieces.get(2)), new int[]{1, -1}));//22
        pieces.add(translate(copy(pieces.get(3)), new int[]{-1, -1}));//23

        //T translate
        pieces.add(translate(copy(pieces.get(4)), new int[]{0, -2}));//24
        pieces.add(translate(copy(pieces.get(4)), new int[]{-1, -1}));//25

        pieces.add(translate(copy(pieces.get(5)), new int[]{2, 0}));//26
        pieces.add(translate(copy(pieces.get(5)), new int[]{1, -1}));//27

        pieces.add(translate(copy(pieces.get(6)), new int[]{0, 2}));//28
        pieces.add(translate(copy(pieces.get(6)), new int[]{1, 1}));//29

        pieces.add(translate(copy(pieces.get(7)), new int[]{-2, 0}));//30
        pieces.add(translate(copy(pieces.get(7)), new int[]{-1, 1}));//31

        //negative L translate
        pieces.add(translate(copy(pieces.get(8)), new int[]{-2, -1}));//32
        pieces.add(translate(copy(pieces.get(9)), new int[]{-1, -2}));//33
        pieces.add(translate(copy(pieces.get(10)), new int[]{2, 1}));//34
        pieces.add(translate(copy(pieces.get(11)), new int[]{-1, 2}));//35

        //negative L corner
        pieces.add(translate(copy(pieces.get(8)), new int[]{-2, 0}));//32
        pieces.add(translate(copy(pieces.get(9)), new int[]{0, -2}));//33
        pieces.add(translate(copy(pieces.get(10)), new int[]{2, 0}));//34
        pieces.add(translate(copy(pieces.get(11)), new int[]{0, 2}));//35

        //L translate
        pieces.add(translate(copy(pieces.get(12)), new int[]{-2, 1}));//36
        pieces.add(translate(copy(pieces.get(13)), new int[]{-1, -2}));//37
        pieces.add(translate(copy(pieces.get(14)), new int[]{2, -1}));//38
        pieces.add(translate(copy(pieces.get(15)), new int[]{1, 2}));//39

        //L corner
        pieces.add(translate(copy(pieces.get(12)), new int[]{-2, 0}));//36
        pieces.add(translate(copy(pieces.get(13)), new int[]{0, -2}));//37
        pieces.add(translate(copy(pieces.get(14)), new int[]{2, 0}));//38
        pieces.add(translate(copy(pieces.get(15)), new int[]{0, 2}));//39
    }

    public static void createStates() {

    }

    public static class uniqueCarpet {

        public static int id;
        private static int[][] carpet;

        public uniqueCarpet(int[][] carpet) {
            this.carpet = carpet;
            this.id = id++;
        }

        public static int[][] getCarpet() {
            return carpet;
        }
        /*	//overide??
    	public static Boolean equals(uniqueCarpet b){
    	
    		for (int[] row : carpet) {
                for (int col : row) {
                    System.out.print(col + " ");
                    if(col != b.carpet[row][col]){
                    	return false; 
                    }
                }
                System.out.println();
            }
    		return true;
    	}*/

    }
}
