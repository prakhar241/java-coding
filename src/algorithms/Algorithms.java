/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.trees.BinarySearchTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author prakbans
 */
public class Algorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        BinarySearchTree bst = new BinarySearchTree();
        List<Double> keys = new ArrayList<Double>(Arrays.asList(5.0, 51.0, 5.0, 4.0, 7.0, 8.0, 9.0));
        
        for (double key: (Iterable<Double>)keys)
            bst.put(key);
        
        
        System.out.println(bst.exists(52.0));
        System.out.println(bst.exists(51.0));
        
        System.out.println(bst.minimum());
        System.out.println(bst.floor(6.0));
        System.out.println(bst.floor(5.0));
        System.out.println(bst.floor(4.0));
        
        System.out.println(bst.ceiling(4.2));
        System.out.println(bst.ceiling(8.7));
        System.out.println(bst.ceiling(5.0));
        System.out.println(bst.ceiling(4.0));
        System.out.println(bst.ceiling(11.0));
        System.out.println(bst.ceiling(161.0));
        
    }
}
