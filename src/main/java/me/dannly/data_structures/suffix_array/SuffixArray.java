package me.dannly.data_structures.suffix_array;

import java.util.Arrays;

public class SuffixArray {

    private final int[] lcp;
    private final String[] suffixArray;

    public SuffixArray(String input) {
        final int length = input.length();
        suffixArray = new String[length];
        lcp = new int[length];
        lcp[0] = 0;
        for (int i = 0; i < length; i++) {
            suffixArray[i] = input.substring(i);
        }
        Arrays.sort(suffixArray);
        for (int i = 1; i < suffixArray.length; i++) {
            final int last = i - 1;
            lcp[i] = commonCharacters(suffixArray[i], suffixArray[last]);
        }
    }

    public int uniqueSuffixes() {
        return ((suffixArray.length * (suffixArray.length + 1)) >> 1) - Arrays.stream(lcp).sum();
    }

    private int commonCharacters(String first, String second) {
        int common = 0;
        while (common < Math.min(first.length(), second.length()) && first.charAt(common) == second.charAt(common))
            common++;
        return common;
    }
}
