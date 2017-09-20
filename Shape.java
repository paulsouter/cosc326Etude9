
/**
 *
 * @author willy
 */
public class Shape {

    //  negative stairs 
    public static void shape1(int[][] carpet, int[][] piece, int x, int y) {

        int[] rows = {piece[0][0]+x, piece[1][0]+x, piece[2][0]+x, piece[3][0]+x};
        int[] cols = {piece[0][1]+y, piece[1][1]+y, piece[2][1]+y, piece[3][1]+y};

        //checks if the location is in the bounds and if something is in there.
        int position = 0;
        while (position < 4) {
            if (rows[position] < carpet.length
                    && cols[position] < carpet[rows[position]].length
                    && carpet[rows[position]][cols[position]] == 0) {
                position++;
            } else {
                break;
            }
        }
        if (position < 4) {
            System.out.println("Insertion failed");
        } else {
            for (int i = 0; i < 4; i++) {
                carpet[rows[i]][cols[i]] = 1;
            }
            System.out.println("Inserted");
        }
    }
    //private boolean boundCheck(int[][] tempCarpet, x, y) {

    //}
}
