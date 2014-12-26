/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.threadsafe;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prakbans
 */
public class ThreadSafeQueue<Key> {
    private Key[] elems;
    private int currentSize;
    private int first = 0;
    private int last = 0;
    
    ThreadSafeQueue(int capacity) {
        if(capacity<=0) throw new IllegalArgumentException("Size should be positive!");
        elems = (Key[]) new Object[capacity];   
    }
    
    private synchronized boolean isEmpty() {
        return this.currentSize == 0;
    }
    
    private synchronized int size() {
        return this.currentSize;
    }
    
    public synchronized void add(Key key) {
        if(elems.length == currentSize) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSafeQueue.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(String.format("InterruptedException occured: name {0}", Thread.currentThread().getName()), ex);
            }
        }
        
        elems[last++] = key;
        if(last == elems.length) last = 0;
        currentSize++;
        
        notifyAll();
          
    }
    
    public synchronized Key remove() {
        if(elems.length == 0) {
             try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSafeQueue.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(String.format("InterruptedException occured: name {0}", Thread.currentThread().getName()), ex);
            }
        }
        
        Key k = elems[first];
        elems[first] = null;
        currentSize--;
        first++;
        if(first == elems.length) first = 0;
        
        notifyAll();
        
        return k;
    }
}
