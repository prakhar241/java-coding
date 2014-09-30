/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.trees;

/**
 *
 * @author prakbans
 */
public class BinarySearchTree<Key extends Comparable<Key>> {

    private Node root;

    private class Node {
        Key item;
        Node left;
        Node right;

        Node(Key item) {
            this.item = item;
        }
    }
    
    private Node makeNode(Key item) {
        return new Node(item);                
    }

    public void put(Key item) {
        put(root, item);
    }

    private Node put(Node node, Key item) {
        if(node == null)
            return makeNode(item);
        int decision = item.compareTo(node.item);        
        if (decision > 1) 
            node.right = put(node.right, item);
        else
            node.left = put(node.left, item);
        return node;
    }
    
    public void inorder() {
        inorder(root);
    }
    
    private void inorder(Node root) {
        if(root != null) {
            inorder(root.left);
            System.out.println(root.item);
            inorder(root.right);
        }
    }
}
