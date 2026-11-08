
package org.daa.editdistance;

import org.daa.metrics.Metrics;

public class RecursiveEditDistance implements EditDistance {

    private final Metrics metrics;

    public RecursiveEditDistance(Metrics metrics) {
        this.metrics = metrics;
    }


    @Override
    public int distance(String a, String b) {
        return recursive(a, b, a.length(), b.length());
    }

    private int recursive(String a, String b, int i, int j) {

        metrics.recursiveCalls++;

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        metrics.comparisons++;

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return recursive(a, b, i - 1, j - 1);
        }

        int delete = recursive(a, b, i - 1, j);
        int insert = recursive(a, b, i, j - 1);
        int replace = recursive(a, b, i - 1, j - 1);

        return 1 + Math.min(delete, Math.min(insert, replace));
    }
}
