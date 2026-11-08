
package org.daa;

import org.daa.editdistance.*;
import org.daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private final EditDistance[] algorithms = {
            new RecursiveEditDistance(new Metrics()),
            new TableEditDistance(new Metrics()),
            new TwoRowsEditDistance(new Metrics())
    };

    @Test
    void referenceValues() {

        for (EditDistance algorithm : algorithms) {

            assertEquals(3, algorithm.distance("kitten", "sitting"));
            assertEquals(2, algorithm.distance("flaw", "lawn"));
            assertEquals(5, algorithm.distance("intention", "execution"));
            assertEquals(3, algorithm.distance("", "abc"));
            assertEquals(0, algorithm.distance("abc", "abc"));
        }
    }

    @Test
    void emptyStrings() {

        for (EditDistance algorithm : algorithms) {

            assertEquals(0, algorithm.distance("", ""));
            assertEquals(3, algorithm.distance("abc", ""));
            assertEquals(3, algorithm.distance("", "abc"));
        }
    }

    @Test
    void randomStringsAgree() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            String a = randomString(random, 5);
            String b = randomString(random, 5);

            int expected = algorithms[0].distance(a, b);

            assertEquals(expected, algorithms[1].distance(a, b));
            assertEquals(expected, algorithms[2].distance(a, b));
        }
    }

    private String randomString(Random random, int maxLength) {

        int length = random.nextInt(maxLength + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(3));
            sb.append(c);
        }

        return sb.toString();
    }
}
