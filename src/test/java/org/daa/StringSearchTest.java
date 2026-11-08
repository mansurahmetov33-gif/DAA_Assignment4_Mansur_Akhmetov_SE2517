
package org.daa;

import org.daa.metrics.Metrics;
import org.daa.search.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    private final StringSearch naive = new NaiveSearch(new Metrics());
    private final StringSearch kmp = new KMPSearch(new Metrics());

    @Test
    void referenceMatches() {

        checkBoth("ABABDABACDABABCABAB", "ABABCABAB",
                List.of(10));

        checkBoth("AAAAA", "AA",
                List.of(0, 1, 2, 3));

        checkBoth("ABABABA", "ABA",
                List.of(0, 2, 4));
    }

    @Test
    void lpsReferenceValues() {

        KMPSearch algorithm = new KMPSearch(new Metrics());

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                algorithm.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                algorithm.lps("ABABCABAB")
        );
    }

    @Test
    void emptyPatternThrows() {

        assertThrows(IllegalArgumentException.class,
                () -> naive.search("abc", ""));

        assertThrows(IllegalArgumentException.class,
                () -> kmp.search("abc", ""));
    }

    @Test
    void patternLongerThanText() {

        assertEquals(List.of(),
                naive.search("ab", "abcd"));

        assertEquals(List.of(),
                kmp.search("ab", "abcd"));
    }

    @Test
    void noMatches() {

        checkBoth("abcdef", "xyz", List.of());
        checkBoth("", "abc", List.of());
    }

    @Test
    void randomSearchesAgree() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            String text = randomString(random, 30);
            String pattern = randomString(random, 5);

            if (pattern.isEmpty()) {
                pattern = "a";
            }

            assertEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
        }
    }

    private void checkBoth(String text, String pattern,
                           List<Integer> expected) {

        assertEquals(expected, naive.search(text, pattern));
        assertEquals(expected, kmp.search(text, pattern));
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
