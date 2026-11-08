
package org.daa.search;

import org.daa.metrics.Metrics;
import java.util.ArrayList;
import java.util.List;

public class KMPSearch implements StringSearch {

    private final Metrics metrics;

    public KMPSearch(Metrics metrics) {
        this.metrics = metrics;
    }

    public int[] lps(String pattern) {

        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {

            metrics.lpsComparisons++;

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {

                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    @Override
    public List<Integer> search(String text, String pattern) {

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] lps = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            metrics.comparisons++;

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == m) {
                    matches.add(i - j);
                    j = lps[j - 1];
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }
}
