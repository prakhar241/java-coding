/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.dynamic_programming.RodCutting;
import algorithms.sort.Sort;
import algorithms.trees.BinarySearchTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import topcoder.srm.srm1_div1.Lottery;
import topcoder.srm.srm1_div1.PenLift;

/**
 *
 * @author prakbans
 */
public class Algorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PenLift penLift = new PenLift();
        String[] inputs = new String[]{"-2 6 -2 1",  "2 6 2 1",  "6 -2 1 -2",  "6 2 1 2",
 "-2 5 -2 -1", "2 5 2 -1", "5 -2 -1 -2", "5 2 -1 2",
 "-2 1 -2 -5", "2 1 2 -5", "1 -2 -5 -2", "1 2 -5 2",
 "-2 -1 -2 -6","2 -1 2 -6","-1 -2 -6 -2","-1 2 -6 2"};
        int times = 5;
        System.out.println(penLift.numTimes(inputs, times));
        
        
        
        Lottery lottery = new Lottery();
        String[] winwin = lottery.sortByOdds(new String[]{
"PICK ANY TWO: 10 2 F F"
,"PICK TWO IN ORDER: 10 2 T F"
,"PICK TWO DIFFERENT: 10 2 F T"
,"PICK TWO LIMITED: 10 2 T T"});

        for (String win : winwin) {
            System.out.println(win);
        }

        BinaryCode srm_1_300 = new BinaryCode();
        String[] outs = srm_1_300.decode("12221112222221112221111111112221111");
        System.out.println(outs[0]);
        System.out.println(outs[1]);

        // TODO code application logic here  
        BinarySearchTree bst = new BinarySearchTree();
        List<Double> keys = new ArrayList<Double>(Arrays.asList(5.0, 51.0, 5.0, 7.0, 8.0, 9.0));

        Integer a[] = {2, 6, 1, 7, 9, 0, -7};
        System.out.println(isSorted(a));
        new Sort().mergeSort(a);
        System.out.println(isSorted(a));
        for (Integer b : a) {
            System.out.print(b + " ");
        }

        for (double key : (Iterable<Double>) keys) {
            bst.put(key);
        }

        System.out.println(bst.exists(52.0));
        System.out.println(bst.exists(51.0));

        System.out.println(bst.minimum());
        System.out.println(bst.floor(6.0));
        System.out.println(bst.floor(5.0));
        // System.out.println(bst.floor(4.0));

        System.out.println(bst.ceiling(4.2));
        System.out.println(bst.ceiling(8.7));
        System.out.println(bst.ceiling(5.0));
        System.out.println(bst.ceiling(4.0));
        System.out.println(bst.ceiling(11.0));
        // System.out.println(bst.ceiling(161.0)); NPE

        System.out.println(bst.hasRootToLeafPathWithSum(6.0));
        System.out.println(bst.hasRootToLeafPathWithSum(5.0));
        System.out.println(bst.hasRootToLeafPathWithSum(9.0));
        System.out.println(bst.hasRootToLeafPathWithSum(80.0));
        System.out.println(bst.hasRootToLeafPathWithSum(60.0));
        System.out.println(bst.hasRootToLeafPathWithSum(75.0));

        double prices[] = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        RodCutting rc = new RodCutting(10, prices, 0);
        int problemLength = 9;
        Map<Iterable<Integer>, Double> sol = rc.getOptimalCutSeq(problemLength);

        for (Map.Entry e : sol.entrySet()) {
            List<Integer> seq = (List<Integer>) e.getKey();
            for (int s : seq) {
                System.out.print(s + "-");
            }
            System.out.println((double) e.getValue());
        }
    }

    private static boolean isSorted(Comparable[] keys) {
        for (int i = 1; i < keys.length; i++) {
            if (keys[i - 1].compareTo(keys[i]) >= 0) {
                return false;
            }
        }
        return true;
    }
}
