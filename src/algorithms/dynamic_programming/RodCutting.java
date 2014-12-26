/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * optimal rod cutting solution.
 *
 * @author prakbans
 */
public class RodCutting {

    // #rods + prices/length unit

    int rod_count;
    double prices[];
    double cut_cost;

    public RodCutting(int rod_count, double prices[], int cut_cost) {
        if (prices.length - 1 != rod_count) {
            throw new IllegalStateException("The prices for rods mismatches the actual rod count.");
        }
        this.rod_count = rod_count;
        this.prices = prices;
        this.cut_cost = cut_cost;

        // zero length rod should cost nothing, 
        // I mean this is sort of ridiculous statement but gonna live with this for now.
        prices[0] = 0;
    }

    // find optimal soluion in terms of revenue
    //  the cost for cutting have some cost
    List<Integer> sequence = new ArrayList<Integer>();
    double revenue[] = new double[rod_count];

    public Map<Iterable<Integer>, Double> getOptimalCutSeq(int rod_length) {
        Map<Iterable<Integer>, Double> map = new HashMap<Iterable<Integer>, Double>();
        map.put(sequence, cutRod(rod_length));
        return map;
    }

    private double cutRod(int rod_length) {
        if (rod_length <= 0) {
            return 0;
        }
        double q = -1;
        for (int i = 1; i <= rod_length; i++) {
            double cost = prices[i] + cutRod(rod_length - i);
            if (q < cost) {
                sequence.add(i);
                q = cost;
            }
        }
        return q;
    }
}
