package cse.buet.b2;

import java.util.*;

public class VarriableOrder {

    public static final int SMALLEST_DOMAIN_FIRST = 0;
    public static final int BRELAZ = 1;
    public static final int RANDOM = 3;
    public static final int MAX_STATIC_DEGREE = 4;
    public static final int MAX_DYNAMIC_DEGREE = 5;
    public static final int DOM_DEG = 6;
    public static final int DOM_D_DEG = 7;
    private static int algorithm;

    public static Pair<Integer, Integer> varriableOrder(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        switch (algorithm) {
            case SMALLEST_DOMAIN_FIRST:
            case DOM_DEG:
                return smallestDomainFirst(dom);
            case RANDOM:
            case MAX_STATIC_DEGREE:
                return randomVariableOrder(dom);
            case MAX_DYNAMIC_DEGREE:
                return maxDynamicDegree(dom);
            case BRELAZ:
                return brelaz(dom);
            case DOM_D_DEG:
                return domddeg(dom);
        }
        return smallestDomainFirst(dom);
    }

    private static Pair<Integer, Integer> smallestDomainFirst(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        Pair<Integer, Integer> next = null;
        int minSize = Integer.MAX_VALUE;
        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() > 1 && entry.getValue().size() < minSize) {
                minSize = entry.getValue().size();
                next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
            }
        }
        return next;
    }

    private static Pair<Integer, Integer> randomVariableOrder(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        List<Pair<Integer, Integer>> all = new ArrayList<>();
        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry : dom.entrySet()) {
            if (entry.getValue().size() > 1) all.add(entry.getKey());
        }
        return all.get(new Random().nextInt(all.size()));
    }

    private static Pair<Integer, Integer> maxDynamicDegree(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        Pair<Integer, Integer> next = null;
        int dynamicDegree = Integer.MIN_VALUE;
        int size = (int) Math.sqrt(dom.size());
        Integer[] rowDegree = new Integer[size];
        Integer[] colDegree = new Integer[size];
        Arrays.fill(rowDegree,0);
        Arrays.fill(colDegree,0);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<>(i, j);
                if (dom.get(key).size() > 1) {
                    rowDegree[i]++;
                    colDegree[j]++;
                }
            }
        }

        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() > 1) {
                int i = entry.getKey().getFirst();
                int j = entry.getKey().getSecond();
                int deg = rowDegree[i] + colDegree[j] - 2;
                if (deg > dynamicDegree) {
                    dynamicDegree = deg;
                    next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
                }
            }
        }
        return next;
    }

    private static Pair<Integer, Integer> brelaz(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        Pair<Integer, Integer> next = null;
        int dynamicDegree = Integer.MIN_VALUE;
        int size = (int) Math.sqrt(dom.size());
        Integer[] rowDegree = new Integer[size];
        Integer[] colDegree = new Integer[size];
        Arrays.fill(rowDegree,0);
        Arrays.fill(colDegree,0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<>(i, j);
                if (dom.get(key).size() > 1) {
                    rowDegree[i]++;
                    colDegree[j]++;
                }
            }
        }

        int minSize = Integer.MAX_VALUE;
        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() > 1) {
                if (entry.getValue().size() < minSize) {
                    minSize = entry.getValue().size();
                    next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
                } else if (entry.getValue().size() == minSize) {
                    int i = entry.getKey().getFirst();
                    int j = entry.getKey().getSecond();
                    int deg = rowDegree[i] + colDegree[j] - 2;
                    if (deg > dynamicDegree) {
                        dynamicDegree = deg;
                        next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
                    }
                }
            }
        }
        return next;
    }

    private static Pair<Integer, Integer> domddeg(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        Pair<Integer, Integer> next = null;
        int size = (int) Math.sqrt(dom.size());
        Integer[] rowDegree = new Integer[size];
        Integer[] colDegree = new Integer[size];
        Arrays.fill(rowDegree,0);
        Arrays.fill(colDegree,0);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> key = new Pair<>(i, j);
                if (dom.get(key).size() > 1) {
                    rowDegree[i]++;
                    colDegree[j]++;
                }
            }
        }

        double minRatio = Double.MAX_VALUE;

        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() > 1) {

                size = entry.getValue().size();
                int i = entry.getKey().getFirst();
                int j = entry.getKey().getSecond();
//                double deg = Math.max(rowDegree[i] + colDegree[j] - 2, 1.7976931348623157E-99);
                double deg = rowDegree[i] + colDegree[j] -2;
                if(deg == 0){
                    if(next == null) next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
                }
                else {
                    double ratio = size / deg;

                    if (ratio < minRatio) {
                        minRatio = ratio;
                        next = new Pair<>(entry.getKey().getFirst(), entry.getKey().getSecond());
                    }
                }
            }
        }
        return next;
    }
    public static void setAlgorithm(int algorithm) {
        VarriableOrder.algorithm = algorithm;
    }
}
