package me.dannly.data_structures.suffix_array.longest_common_suffix;

import me.dannly.data_structures.suffix_array.SuffixArray;

import java.util.*;

public class LongestCommonSuffixArray {

    private static final char[] separators = {'&', '$', '#', '%', '|'};

    private List<Integer> separatorPositions;

    private final Map<String, Integer> groups = new HashMap<>();
    private final int k;
    private int[] lcp;
    private String[] suffixArray;

    public LongestCommonSuffixArray(int k, String... inputs) {
        this.k = k;
        buildArrays(inputs);
    }

    private void buildArrays(String... input) {
        final String t = buildFullString(input);
        assignGroups(t);
        suffixArray = trimSeparators(suffixArray);
        Arrays.sort(suffixArray);
        lcp = new int[suffixArray.length];
        buildLcp(lcp, suffixArray);
    }

    private void assignGroups(String fullString) {
        for (int i = 0; i < fullString.length(); i++) {
            final String substring = fullString.substring(i);
            suffixArray[i] = substring;
            if (!groups.containsKey(substring))
                for (int separatorPosition : separatorPositions)
                    if (i >= separatorPosition) {
                        groups.put(substring, separatorPosition);
                    }
        }
        separatorPositions = null;
    }

    private String buildFullString(String... input) {
        separatorPositions = new ArrayList<>();
        final StringBuilder tBuilder = new StringBuilder();
        separatorPositions.add(0);
        for (int i = 0; i < input.length; i++) {
            tBuilder.append(input[i]);
            separatorPositions.add(tBuilder.length());
            tBuilder.append(separators[i]);
        }
        final String t = tBuilder.toString();
        final int length = t.length();
        suffixArray = new String[length];
        return t;
    }

    private String[] trimSeparators(String[] arr) {
        return Arrays.stream(arr).filter(s -> {
            for (char separator : separators) {
                if (s.charAt(0) == separator) {
                    return false;
                }
            }
            return true;
        }).toArray(String[]::new);
    }

    private void buildLcp(int[] lcp, String[] suffixes) {
        for (int j = 1; j < lcp.length; j++) {
            final int last = j - 1;
            lcp[j] = SuffixArray.commonCharactersCount(suffixes[j], suffixes[last]);
        }
    }

    public String[] longestCommonSuffixes() {
        final LinkedList<Integer> currentGroups = new LinkedList<>();
        int start = 0, end = 0;
        int currentSize = 0;
        final List<String> lcs = new ArrayList<>();
        while (end < lcp.length) {
            final int group = groups.get(suffixArray[end]);
            if (currentGroups.peekLast() == null || currentGroups.peekLast() != group)
                currentGroups.offer(group);
            if (currentGroups.size() < k) {
                end++;
                continue;
            }
            final int commonCount = lcp[++start + 1];
            currentGroups.poll();
            if (start + 1 >= lcp.length)
                break;
            final String first = suffixArray[start], second = suffixArray[start + 1];
            if (commonCount < currentSize)
                continue;
            final String commonCharacters = commonCharacters(first, second);
            if (commonCharacters.isEmpty())
                continue;
            if (commonCharacters.length() > currentSize) {
                currentSize = commonCharacters.length();
                lcs.clear();
            }
            lcs.add(commonCharacters);
        }
        return lcs.toArray(new String[0]);
    }

    private String commonCharacters(String first, String second) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Math.min(first.length(), second.length()); i++) {
            if (first.charAt(i) != second.charAt(i))
                break;
            builder.append(first.charAt(i));
        }
        return builder.toString();
    }
}
