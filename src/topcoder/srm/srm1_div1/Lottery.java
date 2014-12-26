/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topcoder.srm.srm1_div1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author prakbans
 */
public class Lottery {

    public String[] sortByOdds(String[] rules) {
        String out[] = new String[rules.length];
        // To focus: binomial, multiplication, not factorial (since costly even
        // using tail recursion not required), power
        // calculate probability for each rule and arrange in a priority queue
        // To focus: building priority queue and associating the priority queue element with the rules
        // from priority queue - arrange the output
        // To focus: iterator for priority queue
        MinPriorityQueue<BigInteger, String> life_saver = new MinPriorityQueue<BigInteger, String>(rules.length);

        // scan rules - "<NAME>:_<CHOICES>_<BLANKS>_<SORTED>_<UNIQUE>"
        // <names: choices blanks sorted unique>
        for (String rule : rules) {
            String[] tokens = rule_parser(rule);
            // token[0] = name, token[1] = choices(n), token[2] = blanks(k)
            // token[3] = sorted, token[4] = unique
            String rule_name = tokens[0];
            int n = Integer.parseInt(tokens[1], 10);
            int k = Integer.parseInt(tokens[2], 10);
            String sorted = tokens[3];
            String unique = tokens[4];

            BigInteger possibilities;
            // choices blanks sorted unique result // n choices:range::[10,100] // k
            // blanks:range::[1,8] 
            // n k T T C(n, n-k) 
            // n k F T
            // n.(n-1).....(n-k+1) or n!/(n-k)! 
            // n k T F C(n-1+k, n-1) // n k F F
            // pow(n,k)
            if ("T".equals(sorted) && "T".equals(unique)) {
                // C(n, n-k)
                possibilities = binomial(n, n - k);
            } else if ("T".equals(sorted) && "F".equals(unique)) {
                // C(n-1+k, n-1)
                possibilities = binomial(n - 1 + k, n - 1);
            } else if ("F".equals(sorted) && "T".equals(unique)) {
                // perm
                possibilities = permutation(n, k);
            } else { //if(sorted == "F" && unique == "F") {
                // pow(n,k)
                possibilities = power(n, k);
            }

            //double probability = BigInteger.valueOf(1).divide(possibilities).doubleValue();
            life_saver.insert(possibilities, rule_name);
        }

        int i = 0;
        while (life_saver.size() != 0) {
            out[i++] = life_saver.delMin();
        }

        return out;
    }

    // "<NAME>:_<CHOICES>_<BLANKS>_<SORTED>_<UNIQUE>"
    private String[] rule_parser(String rule) {
        String[] tokens = rule.split("\\:");
        String[] phase2 = tokens[1].split("\\ ");
        // algo for inplace merging of two arrays
        String[] out = new String[5];
        out[0] = tokens[0];
        // phase2[0] is empty string
        out[1] = phase2[1];
        out[2] = phase2[2];
        out[3] = phase2[3];
        out[4] = phase2[4];
        return out;
    }

    BigInteger one = BigInteger.valueOf(1);

    private BigInteger permutation(int n, int k) {
        if (k == 0) {
            return one;
        }
        if (k == 1) {
            return BigInteger.valueOf(n);
        }

        int i = 1;
        BigInteger result = BigInteger.valueOf(n);
        while (i < k) {
            result = result.multiply(BigInteger.valueOf(n).subtract(BigInteger.valueOf(i)));
            i++;
        }

        return result;
    }

    private BigInteger power(int n, int k) {
        return power(BigInteger.valueOf(n), k);
    }

    private BigInteger power(BigInteger n, int k) {
        if (k == 0) {
            return one;
        }
        if (k == 1) {
            return n;
        }
        // FIXME: fix the code below - add precision or else ready for exceptions
        if (k < 0) {
            return one.divide(power(n, k));
        }
        if (k % 2 == 1) {
            return n.multiply(power(n.multiply(n), (k - 1) / 2));
        } else {
            return power(n.multiply(n), k / 2);
        }
    }

    // n choices:range::[10,100]
    // k blanks:range::[1,8]
    BigDecimal d1 = BigDecimal.valueOf(1);
    BigDecimal d0 = BigDecimal.valueOf(0);

    private BigInteger binomial(int n, int k) {
        if (k > n - k) {
            k = (n - k);
        }
        return binomial_iter(BigDecimal.valueOf(n), k, d0, d1).toBigInteger();
    }

    // tail-recursive iteration code
    private BigDecimal binomial_iter(BigDecimal n, int k, BigDecimal i, BigDecimal prev) {
        if (i.intValue() >= k) {
            return prev;
        }
        prev = prev.multiply(n.subtract(i).divide(i.add(d1), 8, RoundingMode.HALF_UP));
        return binomial_iter(n, k, i.add(d1), prev);
    }

    // Value is the abstract data-type for the information one is wanting to be associated along with 
    //   node in the priority queue
    private class MinPriorityQueue<Key extends Comparable, Value extends Comparable> {

        private final int capacity;

        // size = elements in priority queue
        private int N = 0;

        // Our Heap - actually array based - complete binary min-heap
        private final Node<Key, Value>[] heap;

        private Node<Key, Value> node; // node in a priority queue

        private class Node<Key, Value> { //<Value> {

            Key probability;
            Value value; // We want value to be rule-name for now 
            //  but could be something else

            private Node() {
                super();
            }

            private Node(Key probability, Value value) {
                this.probability = probability;
                this.value = value;
            }

        }

        // Dynamic array resizing
        // in my case, I know the size of elements beforehand
        // multiway heaps
        // immutability of keys
        private MinPriorityQueue(int capacity) {
            this.capacity = capacity;
            // 1 -based index (0 would not be used)
            this.heap = new Node[capacity + 1]; //(Node<Value>[]) new Object[capacity]; //(Node<Value>[]) new ArrayList<Node>().toArray(new Object[capacity]); //(Node[]) new Object[capacity];
        }

        // operations I need:
        // insertion        
        // delMin
        void insert(Key probability, Value rule) {
            // create node
            Node<Key, Value> newNode = new Node<Key, Value>(probability, rule);

            // heap insertion logic
            // swim - get to its destiny
            this.heap[++N] = newNode;

            swim(N);

        }

        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exchange(k / 2, k);
                k = k / 2;
            }
        }

        private void exchange(int a, int b) {
            Node<Key, Value> temp = this.heap[a];
            this.heap[a] = this.heap[b];
            this.heap[b] = temp;
        }
        
        private boolean alphabeticGreater(Value a, Value b) {
            return a.compareTo(b) > 0;
        }
        
        private boolean greater(int a, int b) {
            if(this.heap[a].probability.compareTo(this.heap[b].probability) > 0) {
                return true;
            } else if (this.heap[a].probability.compareTo(this.heap[b].probability) == 0) {
                if(alphabeticGreater(this.heap[a].value, this.heap[b].value)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        Value delMin() {
            if (N == 0) {
                return null;
            }
            // exchange with last
            // declare the last as not part of heap any more
            // sink the root and lets destiny find its way
            Node<Key, Value> node = this.heap[1];
            exchange(N--, 1);
            sink(1);
            heap[N + 1] = null;
            return node.value;
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                int smaller = (j < N && greater(j, j + 1)) ? j + 1 : j;
                if (!greater(k, smaller)) {
                    break;
                }

                exchange(k, smaller);
                k = smaller;
            }
        }

        int size() {
            return N;
        }
    }
}
