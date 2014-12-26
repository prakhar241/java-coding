/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.sort;

/**
 *
 * @author prakbans
 * @param <Key>
 */
public class Sort<Key extends Comparable<Key>> {
    public void mergeSort(Key[] keys) {        
        sort(keys, (Key[]) new Comparable[keys.length], 0, keys.length - 1);
    }
    
    private void sort(Key[] keys, Key[] aux, int low, int high) {           
        if(high <= low) return;
        int mid = low + (high - low)/2; 
        
        sort(keys, aux, low, mid);
        sort(keys, aux, mid +1, high);
        merge(keys, aux, low, mid, high);
    }
    
    private void merge(Key[] keys, Key[] aux, int low, int mid, int high) {
        for (int k = low; k <= high; k++)
            aux[k] = keys[k];
        
        int i = low, j= mid +1;
        for (int k = low; k <= high; k++) {
            if (i > mid) keys[k] = aux[j++];
            else if (j > high) keys[k] = aux[i++];
            else if (less(keys[i], keys[j])) keys[k] = aux[i++];
            else keys[k] = aux[j++];
        }
    }
    
    private boolean less(Key key1, Key key2) {
        return key1.compareTo(key2) < 0;
    }
}
