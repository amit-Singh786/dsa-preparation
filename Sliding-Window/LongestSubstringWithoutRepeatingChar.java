
/**
 * Problem Statement:
 * Given a string s, find the length of the longest substring 
 * without repeating characters.
*/

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingChar {
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int maxLength = 0;
        int left = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // If the character is already in the map, move the left pointer
            if (charIndexMap.containsKey(currentChar)) {
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }

            // Update the character's index in the map
            charIndexMap.put(currentChar, right);

            // Calculate the maximum length of the substring
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        String input = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(input)); // Output: 3
    }
}
