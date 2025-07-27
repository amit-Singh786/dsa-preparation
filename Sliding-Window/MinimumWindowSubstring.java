
/**
 * Problem Statement:
 * Given two strings s and t, find the minimum window in s 
 * that contains all the characters of t (including duplicates).
 * If there’s no such window, return an empty string "".
 */

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    public static String minWindow(String s, String t) {
        if (s.length() < t.length())
            return "";

        // Step 1: Count frequency of characters in t
        Map<Character, Integer> tFreq = new HashMap<>();
        for (char c : t.toCharArray()) {
            tFreq.put(c, tFreq.getOrDefault(c, 0) + 1);
        }

        int required = tFreq.size(); // number of unique chars in t
        int formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<>();

        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            windowCounts.put(c, windowCounts.getOrDefault(c, 0) + 1);

            // Check if current char count matches the required
            if (tFreq.containsKey(c) && windowCounts.get(c).intValue() == tFreq.get(c).intValue()) {
                formed++;
            }

            // Try to shrink the window from left
            while (left <= right && formed == required) {
                char ch = s.charAt(left);

                // Save the smallest window
                if ((right - left + 1) < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                // Shrink from left
                windowCounts.put(ch, windowCounts.get(ch) - 1);
                if (tFreq.containsKey(ch) && windowCounts.get(ch).intValue() < tFreq.get(ch).intValue()) {
                    formed--;
                }

                left++;
            }

            right++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println("Minimum window substring: " + minWindow(s, t)); // Output: "BANC"
    }
}