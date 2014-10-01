/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.trees;

/**
 * The {@code BinarySearchTree} implementation.
 * A BST is a binary tree in symmetric order.
 * A binary tree is either: empty or has two disjoint binary trees (left or right).
 * Symmetric order implies: each node has key and every node's key is larger than all keys in left subtree and
 * smaller than all keys in right subtree.
 * @author prakbans
 */
public class BinarySearchTree<Key extends Comparable<Key>> {
    private Node root;

    private class Node {
        Key item;
        Node left;
        Node right;
        int count = 1;

        private Node(Key item) {
            this.item = item;
        }
    }
    
    private Node makeNode(Key item) {
        return new Node(item);                
    }

    public void put(Key item) {
        root = put(root, item);
    }

    private Node put(Node node, Key item) {
        if(node == null)
            return makeNode(item);
        int decision = item.compareTo(node.item);        
        if (decision > 0) 
            node.right = put(node.right, item);
        else if (decision < 0)
            node.left = put(node.left, item);
        else 
            node.count++;
        return node;
    }
    
    public boolean exists(Key item) {
        return exists(root, item);
    }
    
    private boolean exists(Node root, Key item) {
        if(root != null) {
            int decision = item.compareTo(root.item);
            if(decision < 0)
                return exists(root.left, item);
            else if (decision > 0)
                return exists(root.right, item);
            else
                return true;
        }
        return false;
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
