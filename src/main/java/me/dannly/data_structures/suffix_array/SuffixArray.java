package me.dannly.data_structures.suffix_array;

import java.util.Arrays;

public class SuffixArray {

    private final int[] lcp;
    private final String[] suffixArray;

    public SuffixArray(String input) {
        int length = input.length();
        suffixArray = new String[length];
        lcp = new int[length];
        lcp[0] = 0;
        for (int i = 0; i < length; i++)
            suffixArray[i] = input.substring(i);
        Arrays.sort(suffixArray);
        buildLcp(lcp, suffixArray);
    }

    private void buildLcp(int[] lcp, String[] suffixes) {
        for (int j = 1; j < lcp.length; j++) {
            final int last = j - 1;
            lcp[j] = commonCharactersCount(suffixes[j], suffixes[last]);
        }
    }

    public int uniqueSuffixes() {
        return ((suffixArray.length * (suffixArray.length + 1)) >> 1) - Arrays.stream(lcp).sum();
    }

    public static int commonCharactersCount(String first, String second) {
        int common = 0;
        while (common < Math.min(first.length(), second.length()) && first.charAt(common) == second.charAt(common))
            common++;
        return common;
    }
}
