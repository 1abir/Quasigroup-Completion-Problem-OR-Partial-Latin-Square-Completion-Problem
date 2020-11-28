package cse.buet.b2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataScanner {
    private String fileName;

    private DataScanner(){}

    public DataScanner(String fileName) {
        this.fileName = fileName;
    }
    public HashMap<Pair<Integer, Integer>, Set<Integer>> read(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        int size = Integer.parseInt(scanner.nextLine().split("=")[1].split(";")[0]);
        scanner.nextLine();
        scanner.nextLine();
        scanner.useDelimiter("\\|");
        HashMap<Pair<Integer, Integer>, Set<Integer>> domain = new HashMap<>(size * size);
        for (int i = 0; i < size; i++) {
            String[] row = scanner.next().split(",");
            for (int j = 0; j < size; j++) {
                int temp = Integer.parseInt(row[j].trim());
                Set<Integer> dom;
                if (temp == 0) dom = IntStream.rangeClosed(1, size)
                        .boxed().collect(Collectors.toSet());
                else {
                    dom = new HashSet<>();
                    dom.add(temp);
                }
                Pair<Integer, Integer> key = new Pair<>(i, j);
                domain.put(key, dom);

            }
        }
        scanner.close();
        return domain;
    }
    public int readVarriableOrderHeuristic(){
        Scanner scanner = new Scanner(System.in);
        int i = 9;
        while (i<0 || i>7) {
            System.out.println("Enter Varriable order Huristic");
            System.out.println("SMALLEST_DOMAIN_FIRST = 0\n" +
                    "BRELAZ = 1\n" +
                    "RANDOM = 3\n" +
                    "MAX_STATIC_DEGREE = 4\n" +
                    "MAX_DYNAMIC_DEGREE = 5\n" +
                    "DOM_DEG = 6\n" +
                    "DOM_D_DEG = 7\n");
            try {
                i = scanner.nextInt();
            } catch (Exception e) {
//                i = VarriableOrder.SMALLEST_DOMAIN_FIRST;
//                break;
                i = 9;
            }
        }
        return i;
    }

    public int readBacktrackOrderHeuristic(){
        Scanner scanner = new Scanner(System.in);
        int i = 3;
        while (i<0 || i>2) {
            System.out.println("Enter Backtracking Huristic");
            System.out.println("MAINTAINING_ARC_CONSTANCY = 0\n" +
                    "FORWARD_CHECKING = 1\n" +
                    "SIMPLE_BACKTRACK = 2\n");
            try {
                i = scanner.nextInt();
            } catch (Exception e) {
                i = 9;
            }
        }
        return i;
    }
}
