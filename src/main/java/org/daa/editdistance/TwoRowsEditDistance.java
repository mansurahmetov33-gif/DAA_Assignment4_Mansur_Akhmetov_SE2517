
package org.daa.editdistance;

import org.daa.metrics.Metrics;

public class TwoRowsEditDistance implements EditDistance {

    private final Metrics metrics;

    public TwoRowsEditDistance(Metrics metrics) {
        this.metrics = metrics;
    }


    @Override
    public int distance(String a, String b) {

        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= n; i++) {

            curr[0] = i;

            for (int j = 1; j <= m; j++) {

                metrics.comparisons++;
                metrics.dpUpdates++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {

                    int delete = prev[j];
                    int insert = curr[j - 1];
                    int replace = prev[j - 1];

                    curr[j] = 1 + Math.min(delete,
                            Math.min(insert, replace));
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m];
    }
}
