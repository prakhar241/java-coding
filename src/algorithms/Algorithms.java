/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.trees.BinarySearchTree;

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
        System.out.println("Hello World");
        BinarySearchTree bst = new BinarySearchTree();
        bst.put(5);
        bst.put(51);        
        System.out.println(bst.exists(52));
        System.out.println(bst.exists(51));
                
        bst.inorder();
    }
    
}
