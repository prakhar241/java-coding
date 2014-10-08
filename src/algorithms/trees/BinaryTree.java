/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.trees;

/**
 *
 * @author prakbans
 * @param <Key>
 */
public interface BinaryTree<Key extends Comparable<Key>> {
    // Minimum operations
    public void put(Key key);
    public boolean exists(Key key);
    // size = subtreeCount(root)
    public int size();
    public int nodeOccurence(Key key);
    
    // Ordered operations
    public Key minimum();
    public Key maximum();
    public Key floor(Key key);
    public Key ceiling(Key key);
    public int subtreeCount(Key key);
    // rank = #keys < key
    public int rank(Key key);
    
    // Traversals
    public Iterable<Key> recursiveInorder();
    public Iterable<Key> recursivePreorder();
    public Iterable<Key> recursivePostorder();
    public Iterable<Key> stackBasedNonrecursiveInorder();
    public Iterable<Key> stackBasedNonrecursivePreorder();
    public Iterable<Key> stackBasedNonrecursivePostorder();
    // TODO: Threaded tree traversal
    public Iterable<Key> levelOrder();
    
    // To doubly LL
}
