package cse.buet.b2;

import java.util.Arrays;

public class Utils {
    public static void print2D(Integer[][] mat) {
//        if(mat == null) return;
        // Loop through all rows
        for (Integer[] row : mat)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
    public static boolean alldifferentchecker(Integer [][] a){
//        if(a == null) return false;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                for (int m = i; m < a.length; m++) {
                    if(m!=i) if(a[m][j].equals(a[i][j])) return false;
                }
                for (int m = j; m < a[0].length; m++) {
                    if(m!=j) if(a[i][m].equals(a[i][j])) return false;
                }
            }
        }
        return true;
    }
}
