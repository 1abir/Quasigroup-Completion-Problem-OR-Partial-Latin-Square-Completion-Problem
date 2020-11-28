package cse.buet.b2;

import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DataScanner dataScanner = new DataScanner("data/d-10-01.txt.txt");
        HashMap<Pair<Integer, Integer>, Set<Integer>>
                domain = dataScanner.read();
        AC3.setAlgorithm(AC3.MAINTAINING_ARC_CONSTANCY);
        AC3.reduce(domain,null);

//        VarriableOrder.setAlgorithm(VarriableOrder.SMALLEST_DOMAIN_FIRST);
//        AC3.setAlgorithm(AC3.MAINTAINING_ARC_CONSTANCY);
        VarriableOrder.setAlgorithm(dataScanner.readVarriableOrderHeuristic());
        AC3.setAlgorithm(dataScanner.readBacktrackOrderHeuristic());

        Backtrack backtrack  = new Backtrack(domain);
        if(backtrack.solve()){
            Integer [][] array = backtrack.getArray();
            Utils.print2D(backtrack.getArray());
            if(Utils.alldifferentchecker(array)) {
                System.out.println("Passed Checker");
                System.out.println("Number of Nodes : "+backtrack.getNodes());
                System.out.println("Number of Backtracks : "+backtrack.getFails());
            }
            else System.out.println("Unknown Error Occurred");
        }else {
            System.out.println("Couldn't solve");
        }
    }
}
