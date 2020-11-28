package cse.buet.b2;

import java.util.*;

public class AC3 {
    public static final int MAINTAINING_ARC_CONSTANCY = 0;
    public static final int FORWARD_CHECKING = 1;
    public static final int SIMPLE_BACKTRACK = 2;
    private static int algorithm = MAINTAINING_ARC_CONSTANCY;

    public static boolean reduce(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain, Pair<Integer, Integer> next) {
        if(algorithm == SIMPLE_BACKTRACK) return simpleBacktrack(backupdomain);
        if(algorithm == MAINTAINING_ARC_CONSTANCY) return ac3(backupdomain,next);
        if(algorithm == FORWARD_CHECKING) return fc(backupdomain,next);
        return false;
    }

    private static boolean ac3(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain, Pair<Integer, Integer> next) {
        Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> neighbours = null;
        if(next == null)neighbours = generateAllConstraints(backupdomain);
        else neighbours = getNeighbours(backupdomain, next);

        while (!neighbours.isEmpty()) {
            Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> ppii = neighbours.poll();
            if (backupdomain.get(ppii.getSecond()).size() == 1) {
                boolean modified = false;
                for (int value : backupdomain.get(ppii.getSecond())) {
                    modified |= backupdomain.get(ppii.getFirst()).remove(value);
                }
                if (backupdomain.get(ppii.getFirst()).size() == 0) return false;
                if (modified) {
                    insertNeighbours(neighbours, backupdomain, ppii);
                }
            }
        }
        return true;
    }

    private static void insertNeighbours(Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> neighbours, Map<Pair<Integer, Integer>, Set<Integer>> backupdomain, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> ppii) {
        Pair<Integer, Integer> next = ppii.getFirst();
        int row = next.getFirst();
        int col = next.getSecond();

        int rc = ppii.getSecond().getFirst();
        int cc = ppii.getSecond().getSecond();

        int size = (int) Math.sqrt(backupdomain.size());

        for (int i = 0; i < size; i++) {
            if (i != col) {
                Pair<Integer, Integer> temp = new Pair<>(row, i);
                if (backupdomain.get(temp).size() > 1 && (!(i == cc && row == rc))) {
                    neighbours.add(new Pair<>(temp, next));
                }
            }
            if (i != row) {
                Pair<Integer, Integer> temp = new Pair<>(i, col);
                if (backupdomain.get(temp).size() > 1 && (!(col == cc && i == rc))) {
                    neighbours.add(new Pair<>(temp, next));
                }
            }
        }

    }


    private static Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getNeighbours(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain, Pair<Integer, Integer> next) {
        Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> neighbours = new LinkedList<>();
        int row = next.getFirst();
        int col = next.getSecond();
        int size = (int) Math.sqrt(backupdomain.size());

        for (int i = 0; i < size; i++) {
            if (i != col) {
                Pair<Integer, Integer> temp = new Pair<>(row, i);
                if (backupdomain.get(temp).size() > 1) {
                    neighbours.add(new Pair<>(temp, next));
                }
            }
            if (i != row) {
                Pair<Integer, Integer> temp = new Pair<>(i, col);
                if (backupdomain.get(temp).size() > 1) {
                    neighbours.add(new Pair<>(temp, next));
                }
            }
        }
        return neighbours;
    }

    private static Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> generateAllConstraints(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain){
        Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> allArcs = new LinkedList<>();
        int size = (int) Math.sqrt(backupdomain.size());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<>(i, j);
                for (int k = 0; k < size; k++) {
                    if (k != j) allArcs.add(new Pair<>(key, new Pair<>(i, k)));
                }
                for (int k = 0; k < size; k++) {
                    if (k != i) allArcs.add(new Pair<>(key, new Pair<>(k, j)));
                }
            }
        }
        return allArcs;
    }

    private static boolean fc(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain, Pair<Integer, Integer> next) {
        Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> neighbours = getNeighbours(backupdomain,next);
        if(backupdomain.get(next).size()!=1) return false;
        int val = backupdomain.get(next).iterator().next();
        while (!neighbours.isEmpty()) {
            Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> ppii = neighbours.poll();
            Set<Integer> temp = backupdomain.get(ppii.getFirst());
            if (temp.size() == 1 && temp.contains(val)) {
                return false;
            } else temp.remove(val);
        }
        return validate(backupdomain);
    }

    private static boolean validate(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        int size = (int) Math.sqrt(dom.size());
        Integer [][] a = new Integer[size][size];
        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() == 1) {
                a[entry.getKey().getFirst()][entry.getKey().getSecond()] = entry.getValue().iterator().next();
            }else {
                a[entry.getKey().getFirst()][entry.getKey().getSecond()] = 0;
            }
        }

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if(a[i][j]!=0) {
                    for (int m = i; m < a.length; m++) {
                        if (m != i && a[m][j] != 0) if (a[m][j].equals(a[i][j])) return false;
                    }
                    for (int m = j; m < a[0].length; m++) {
                        if (m != j && a[i][m] != 0) if (a[i][m].equals(a[i][j])) return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean simpleBacktrack(Map<Pair<Integer, Integer>, Set<Integer>> backupdomain) {
        return validate(backupdomain);
    }

    public static void setAlgorithm(int algorithm) {
        AC3.algorithm = algorithm;
    }
}
