
package org.daa.experiment;

import org.daa.editdistance.*;
import org.daa.search.*;
import org.daa.metrics.Metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentRunner {

    private static final int[] RECURSIVE_SIZES = {3, 6, 9, 12};
    private static final int[] DP_SIZES = {100, 200, 400, 800};
    private static final int[] SEARCH_SIZES = {1000, 2000, 4000, 8000};

    private static final int RUNS = 5;

    private final InputGenerator generator = new InputGenerator(42);

    public static void main(String[] args) throws IOException {
        new ExperimentRunner().run();
    }

    public void run() throws IOException {

        Path directory = Path.of("results");
        Files.createDirectories(directory);

        Path csv = directory.resolve("results.csv");

        List<String> lines = new ArrayList<>();

        lines.add("algorithm,input,n,pattern_length,run,time_ms,"
                + "comparisons,recursive_calls,dp_updates,lps_comparisons");

        runEditDistance(lines);
        runStringSearch(lines);

        Files.write(csv, lines);

        System.out.println("Experiment finished!");
        System.out.println("Results saved to: " + csv.toAbsolutePath());
    }

    private void runEditDistance(List<String> lines) {

        for (int n : RECURSIVE_SIZES) {
            runEditCase("Recursive", n, lines);
        }

        for (int n : DP_SIZES) {
            runEditCase("Table", n, lines);
            runEditCase("TwoRows", n, lines);
        }
    }

    private void runEditCase(String name, int n, List<String> lines) {

        for (String inputType : List.of("random", "worst")) {

            String a;
            String b;

            if (inputType.equals("random")) {
                a = generator.randomString(n);
                b = generator.randomString(n);
            } else {
                a = generator.repeatedString('A', n);
                b = generator.repeatedString('B', n);
            }

            Metrics metrics = new Metrics();

            EditDistance algorithm = switch (name) {
                case "Recursive" -> new RecursiveEditDistance(metrics);
                case "Table" -> new TableEditDistance(metrics);
                case "TwoRows" -> new TwoRowsEditDistance(metrics);
                default -> throw new IllegalArgumentException(name);
            };

            // Warm-up before measuring.
            // Recursive inputs are intentionally small.
            algorithm.distance(a, b);

            List<Double> times = new ArrayList<>();

            for (int run = 1; run <= RUNS; run++) {

                metrics.reset();

                long start = System.nanoTime();
                algorithm.distance(a, b);
                long end = System.nanoTime();

                double timeMs = (end - start) / 1_000_000.0;
                times.add(timeMs);

                lines.add(name + "," + inputType + "," + n
                        + ",0," + run + "," + timeMs + ","
                        + metrics.comparisons + ","
                        + metrics.recursiveCalls + ","
                        + metrics.dpUpdates + ","
                        + metrics.lpsComparisons);
            }

            Collections.sort(times);

            System.out.printf("%s | %s | n=%d | median=%.4f ms%n",
                    name, inputType, n, times.get(RUNS / 2));
        }
    }

    private void runStringSearch(List<String> lines) {

        for (int n : SEARCH_SIZES) {

            int m = n / 10;

            for (String inputType : List.of("random", "worst")) {

                String text;
                String pattern;

                if (inputType.equals("random")) {
                    text = generator.randomString(n);
                    pattern = generator.randomString(m);
                } else {
                    text = generator.repeatedString('A', n);
                    pattern = generator.worstPattern(m);
                }

                runSearchCase("Naive", text, pattern, inputType, lines);
                runSearchCase("KMP", text, pattern, inputType, lines);
            }
        }
    }

    private void runSearchCase(String name, String text,
                               String pattern, String inputType,
                               List<String> lines) {

        Metrics metrics = new Metrics();

        StringSearch algorithm = name.equals("Naive")
                ? new NaiveSearch(metrics)
                : new KMPSearch(metrics);

        algorithm.search(text, pattern);

        List<Double> times = new ArrayList<>();

        for (int run = 1; run <= RUNS; run++) {

            metrics.reset();

            long start = System.nanoTime();
            algorithm.search(text, pattern);
            long end = System.nanoTime();

            double timeMs = (end - start) / 1_000_000.0;
            times.add(timeMs);

            lines.add(name + "," + inputType + ","
                    + text.length() + "," + pattern.length()
                    + "," + run + "," + timeMs + ","
                    + metrics.comparisons + ","
                    + metrics.recursiveCalls + ","
                    + metrics.dpUpdates + ","
                    + metrics.lpsComparisons);
        }

        Collections.sort(times);

        System.out.printf("%s | %s | n=%d | median=%.4f ms%n",
                name, inputType, text.length(), times.get(RUNS / 2));
    }
}
