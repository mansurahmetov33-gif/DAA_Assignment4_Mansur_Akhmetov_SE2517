
package org.daa;

import org.daa.editdistance.*;
import org.daa.search.*;
import org.daa.metrics.Metrics;

public class Main {

    public static void main(String[] args) {

        Metrics metrics = new Metrics();

        EditDistance recursive = new RecursiveEditDistance(metrics);
        EditDistance table = new TableEditDistance(metrics);
        EditDistance twoRows = new TwoRowsEditDistance(metrics);

        String a = "kitten";
        String b = "sitting";

        metrics.reset();
        System.out.println("Recursive: " + recursive.distance(a, b));
        System.out.println("Recursive calls: " + metrics.recursiveCalls);

        metrics.reset();
        System.out.println("Table: " + table.distance(a, b));
        System.out.println("DP updates: " + metrics.dpUpdates);

        metrics.reset();
        System.out.println("Two Rows: " + twoRows.distance(a, b));
        System.out.println("DP updates: " + metrics.dpUpdates);

        StringSearch naive = new NaiveSearch(metrics);
        StringSearch kmp = new KMPSearch(metrics);

        String text = "ABABABA";
        String pattern = "ABA";

        metrics.reset();
        System.out.println("Naive: " + naive.search(text, pattern));
        System.out.println("Comparisons: " + metrics.comparisons);

        metrics.reset();
        System.out.println("KMP: " + kmp.search(text, pattern));
        System.out.println("Search comparisons: " + metrics.comparisons);
        System.out.println("LPS comparisons: " + metrics.lpsComparisons);
    }
}
