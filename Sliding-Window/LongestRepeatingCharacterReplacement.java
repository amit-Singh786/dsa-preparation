/**
 * Problem Statement:
 * You're given a string s and an integer k.
 * You can replace at most k characters in the string,
 * and your goal is to find the length of the longest
 * substring that can be made to contain only one repeating character
 * after at most k replacements.
 */

public class LongestRepeatingCharacterReplacement {
    public static int characterReplacement(String s, int k) {
        int[] freq = new int[26]; // For A-Z
        int maxFreq = 0; // Max freq of a single char in current window
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);

            // Check if current window is valid
            if ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--; // shrink from left
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        String s = "AABABBA";
        int k = 1;
        System.out.println("Longest substring after " + k + " replacements: " + characterReplacement(s, k));
    }
}