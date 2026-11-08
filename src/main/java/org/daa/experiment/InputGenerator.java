
package org.daa.experiment;

import java.util.Random;

public class InputGenerator {

    private final Random random;

    public InputGenerator(long seed) {
        this.random = new Random(seed);
    }

    public String randomString(int length) {

        StringBuilder sb = new StringBuilder(length);
        String alphabet = "ABCD";

        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(
                    random.nextInt(alphabet.length())
            ));
        }

        return sb.toString();
    }

    public String repeatedString(char c, int length) {
        return String.valueOf(c).repeat(length);
    }

    public String worstPattern(int length) {
        return "A".repeat(length - 1) + "B";
    }
}
