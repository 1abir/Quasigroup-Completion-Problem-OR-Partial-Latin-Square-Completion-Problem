package cse.buet.b2;

import java.util.*;

public class Backtrack {
    private Map<Pair<Integer, Integer>, Set<Integer>> initialDomain;
    private Integer[][] array;
    private int nodes;
    private int fails;

    private Backtrack(){}
    public Backtrack(Map<Pair<Integer, Integer>, Set<Integer>> initialDomain) {
        this.initialDomain = initialDomain;
    }
    public boolean solve(){
        nodes = 0;
        fails = 0;
        return backtrack(initialDomain);
    }

    private boolean backtrack(Map<Pair<Integer, Integer>, Set<Integer>> dom) {

        Pair<Integer, Integer> next = VarriableOrder.varriableOrder(dom);

        if (next == null) {
            nodes++;
            array = addSolution(dom);
            return true;
        }

        for (Integer value : dom.get(next)) {
            nodes++;
            Map<Pair<Integer, Integer>, Set<Integer>> backupdomain = copyDomain(dom);
            backupdomain.get(next).clear();
            backupdomain.get(next).add(value);
            if (AC3.reduce(backupdomain, next)) {
                boolean ret = backtrack(backupdomain);
                if(ret) return  true;
            }else {
                fails++;
            }
        }

        return false;
    }

    public static HashMap<Pair<Integer, Integer>, Set<Integer>> copyDomain(Map<Pair<Integer, Integer>, Set<Integer>> domain){
        HashMap<Pair<Integer, Integer>, Set<Integer>> newDomain = new HashMap<>();
        for (Pair<Integer, Integer> key:
                domain.keySet()) {
            newDomain.put(key,new HashSet<>(domain.get(key)));
        }
        return newDomain;
    }

    private Integer[][] addSolution(Map<Pair<Integer, Integer>, Set<Integer>> dom) {
        int size = (int) Math.sqrt(dom.size());
        Integer [][] array = new Integer[size][size];

        for (Map.Entry<Pair<Integer, Integer>, Set<Integer>> entry :
                dom.entrySet()) {
            if (entry.getValue().size() == 1) {
                array[entry.getKey().getFirst()][entry.getKey().getSecond()] = entry.getValue().iterator().next();
            }else {
                System.out.println(entry.getKey()+" : "+entry.getValue());
                return null;
            }
        }
        return array;
    }

    public Integer[][] getArray() {
        return array;
    }

    public int getNodes() {
        return nodes;
    }

    public int getFails() {
        return fails;
    }
}
