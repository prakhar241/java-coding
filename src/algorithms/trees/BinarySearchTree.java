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
 * @param <Key> the generic type
 */
public class BinarySearchTree<Key extends Comparable<Key>> implements BinaryTree<Key> {
    private Node root;

    private class Node {
        Key key;
        Node left;
        Node right;
        int keyCount = 1;
        int familyCount = 1;

        private Node(Key key) {
            this.key = key;
        }
    }
    
    private Node makeNode(Key key) {
        return new Node(key);                
    }
    
    @Override
    public void put(Key key) {
        root = put(root, key);
    }

    private Node put(Node node, Key key) {
        if(node == null)
            return makeNode(key);
        int decision = key.compareTo(node.key);        
        if (decision > 0) 
            node.right = put(node.right, key);
        else if (decision < 0)
            node.left = put(node.left, key);
        else 
            node.keyCount++;
        return node;
    }
    
    @Override
    public boolean exists(Key key) {
        return exists(root, key);
    }
    
    private boolean exists(Node root, Key key) {
        if(root != null) {
            int decision = key.compareTo(root.key);
            if(decision < 0)
                return exists(root.left, key);
            else if (decision > 0)
                return exists(root.right, key);
            else
                return true;
        }
        return false;
    }
    
    @Override
    public Key minimum() {
        // FIXME: NPE
        return leftMost(root);
    }

    private Key leftMost(Node node) {
        // Can I help it, could cause NPE for the caller
        if(node == null) return null;
        else if(node.left == null) return node.key;
        else return leftMost(node.left);
    }
   
    @Override
    public Key maximum() {
        // FIXME: NPE
        return rightMost(root);
    }
    
    private Key rightMost(Node node) {
        if(node == null) return null;
        else if(node.right == null) return node.key;
        else return rightMost(node.right);
    }

    @Override
    public Key floor(Key key) {
        // FIXME: NPE
        return floor(root, key).key;
    }
    
    private Node floor(Node node, Key key) {
        if (node == null) return null;        
        int cmp = key.compareTo(node.key);
        
        if(cmp < 0) return floor(node.left, key);   
        else if(cmp > 0) {
            Node n = floor(node.right, key);
            if(n==null) return node;
            else return n;
        }
        else return node;        
    }

    @Override
    public Key ceiling(Key key) {
        return ceiling(root, key).key;
    }
    
    private Node ceiling(Node node, Key key) {
        if(node == null) return null;
        int cmp = key.compareTo(node.key);
        
        if(cmp > 0) return ceiling(node.right, key);
        else if(cmp < 0) {
            Node n = ceiling(node.left, key);
            if (n == null) return node;
            else return n;
        }
        else return node;
    }

    @Override
    public int subtreeCount(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> recursiveInorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> recursivePreorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> recursivePostorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> stackBasedNonrecursiveInorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> stackBasedNonrecursivePreorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> stackBasedNonrecursivePostorder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Key> levelOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int rank(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int nodeOccurence(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
