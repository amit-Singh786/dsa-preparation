# Sliding Window

## 1. [Maximum Sum Subarray of Size K](./MaxSumSubarrayOfSizeK.java)

### 🔶 Problem Statement:

Given an array of integers and a number `k`, find the **maximum sum** of a **subarray of size k**.

---

### 🧠 Intuition (Easy Explanation):

Imagine you have a sliding window of size `k` that moves from left to right across the array. At each step, you calculate the sum of the numbers in the window.

But instead of recalculating the sum from scratch every time (which takes extra time), we:

- Add the next number coming into the window
- Subtract the number going out of the window

This way, you always keep track of the current window sum and compare it to the maximum you've seen.

---

### 🪟 Sliding Window Approach (Step-by-Step):

1. Calculate the sum of the **first `k` elements** – this is your starting window.
2. Slide the window one element at a time:

   - Add the new element coming into the window (right side).
   - Subtract the element that's sliding out of the window (left side).

3. At each step, update the **maximum sum** if the current window sum is greater.
4. Return the **maximum sum** at the end.

---

### ✅ Example:

```java
Input: arr = [2, 1, 5, 1, 3, 2], k = 3

First window sum = 2 + 1 + 5 = 8
Next window = 1 + 5 + 1 = 7
Next window = 5 + 1 + 3 = 9  ← max so far
Next window = 1 + 3 + 2 = 6

Maximum sum = 9
```

---

### 💻 Java Code:

```java
public class MaxSumSubarrayOfSizeK {
    public static int maxSum(int[] arr, int k) {
        int maxSum = 0;
        int windowSum = 0;

        // First window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        maxSum = windowSum;

        // Slide the window
        for (int i = k; i < arr.length; i++) {
            windowSum += arr[i] - arr[i - k]; // Add next, remove previous
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;
        System.out.println("Maximum sum of subarray of size " + k + " is: " + maxSum(arr, k));
    }
}
```

---

### 🕒 Time & Space Complexity:

- **Time**: O(n), because we traverse the array only once.
- **Space**: O(1), no extra space used apart from a few variables.

---

## 2. [Longest Substring Without Repeating Characters](./LongestSubstringWithoutRepeatingChar.java)

### 🔶 Problem Statement:

Given a string `s`, find the **length** of the **longest substring** without repeating characters.

---

### 🧠 Intuition (Easy Explanation):

Imagine you’re reading a string from **left to right**, and you're keeping track of a **window** (substring) that has **no duplicate characters**.

As soon as you find a **repeating character**, shrink the window **from the left** until there are **no more duplicates**.

---

### 🪟 Sliding Window with HashMap (or Set):

1. Use a **Set** to store characters in the current window.
2. Start with two pointers: `left` and `right`, both at the beginning.
3. Move `right` to expand the window.
4. If a **duplicate character** is found:

   - Remove characters from the **left side** until the duplicate is removed.

5. Keep track of the **maximum length** at each step.

---

### ✅ Example:

```java
Input: "abcabcbb"

Step-by-step:
"a" → ok
"ab" → ok
"abc" → ok
"abca" → 'a' repeats → remove 'a' from left → now "bca"
"bcab" → 'b' repeats → remove 'b' from left → now "cab"

Longest length = 3 ("abc", "bca", "cab")
```

---

### 💻 Java Code:

```java
import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeating {
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // If character already in set, shrink window from left
            while (set.contains(currentChar)) {
                set.remove(s.charAt(left));
                left++;
            }

            // Add current character and update maxLength
            set.add(currentChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        String input = "abcabcbb";
        System.out.println("Length of Longest Substring Without Repeating Characters: " + lengthOfLongestSubstring(input));
    }
}
```

---

### 🕒 Time & Space Complexity:

- **Time**: O(n) – each character is visited at most twice (once by `right`, once by `left`)
- **Space**: O(k) – `k` is the size of the character set (at most 26 or 128 depending on char set)

---

## 3. [Longest Repeating Character Replacement](./LongestRepeatingCharacterReplacement.java)

### 🔶 Problem Statement:

You're given a string `s` and an integer `k`.

You can **replace at most `k` characters** in the string, and your goal is to find the **length of the longest substring** that can be made to contain **only one repeating character** after **at most `k` replacements**.

---

### 🧠 Easy Intuition (Sliding Window + Frequency):

Think of it this way:

> We want to find the longest window (substring) where we can replace **at most `k` characters** to make **all characters the same**.

✅ So we:

1. Use a sliding window (left & right pointers).
2. Count how many times the **most frequent character** appears in the current window.
3. If the number of characters to replace is:

   ```
   (window size - most frequent count) <= k → window is valid
   ```

4. If it's more than `k`, shrink the window from the left.

📌 Why keep track of the most frequent character?
Because we will **replace everything else** in the window to match this frequent character.

---

### ✅ Example:

```java
Input: s = "AABABBA", k = 1

Window "AABA" → 'A' appears 3 times → length = 4, only 1 change needed → valid
Window "ABAB" → max frequency = 2 ('A' or 'B'), length = 4 → need 2 changes → not valid if k=1 → shrink window

Answer = 4
```

---

### 💻 Java Code:

```java
public class LongestRepeatingCharacterReplacement {
    public static int characterReplacement(String s, int k) {
        int[] freq = new int[26]; // For A-Z
        int maxFreq = 0;          // Max freq of a single char in current window
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
```

---

### 🕒 Time & Space Complexity:

- **Time**: O(n), where `n` is the length of the string. Each character is visited once.
- **Space**: O(1), because the frequency array is fixed size (26).

---

## 4. [Minimum Window Substring](./MinimumWindowSubstring.java)

### 🔶 Problem Statement:

Given two strings `s` and `t`, find the **minimum window in `s`** that contains **all the characters of `t` (including duplicates)**.

If there’s no such window, return an empty string `""`.

---

### 🧠 Intuition (Simple Explanation):

We want to find the **smallest window** in `s` that contains **all characters from `t`** (in any order, but with exact counts).

✅ So we:

1. Use a **hashmap** to count how many characters we need from `t`.
2. Use a **sliding window** (two pointers: `left` and `right`) to move through `s`.
3. Expand `right` to include characters until we have a valid window (i.e., it has all the required characters).
4. Then, **shrink from the left** to find the smallest such window.

---

### 🪟 Step-by-Step Idea:

1. **Count chars of `t`** → using a hashmap (e.g., `'A': 1, 'B': 1, 'C': 1`)
2. **Start sliding window** → move right pointer to include characters
3. Keep track of how many **required characters have been matched**
4. When all characters are matched:

   - Try to **shrink from the left** to get the minimum window

5. Update the **minimum window length and position**
6. Repeat until end of string `s`

---

### ✅ Example:

```java
Input: s = "ADOBECODEBANC", t = "ABC"

- Start expanding window → "ADOBEC" (has A, B, C)
- Try to shrink → "DOBEC" → not valid, "ODEBANC" → valid
- Minimum window = "BANC"
```

---

### 💻 Java Code:

```java
import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

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
```

---

### 🕒 Time & Space Complexity:

- **Time**: O(s.length + t.length)
  Efficient since each character is processed at most twice.
- **Space**: O(t.length) for frequency maps.

---

## 5. [Sliding Window Maximum](./SlidingWindowMaximum.java)

### 🔶 Problem Statement:

Given an integer array `nums` and a window size `k`, return an array of the **maximum values** in every **sliding window** of size `k`.

---

### 🧠 Easy Intuition:

You're sliding a window of size `k` over the array, and at each step, you want to know:

> “What is the **maximum element** inside this window?”

If you do this **naively**, you’d scan every `k` elements for every window — that’s **O(n \* k)** → **too slow**.

---

### ✅ Optimal Approach: **Using Deque (Double-Ended Queue)**

We use a **deque** to store **indices** of elements in the window, not the values themselves.

🪄 The key idea:

- Keep elements in the deque in **decreasing order**.
- The **front** of the deque always contains the index of the **maximum** element in the current window.

---

### 🎯 How it Works:

1. **Remove indices from the back** of the deque **while current element is greater** — because they can’t be the max anymore.
2. **Add current element’s index** to the back.
3. **Remove the index from front** if it’s **out of the current window**.
4. **Add the value at the front index** to the result when the first full window is formed.

---

### 🔄 Example:

```java
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3

Window = [1,3,-1] → max = 3
Window = [3,-1,-3] → max = 3
Window = [-1,-3,5] → max = 5
Window = [-3,5,3] → max = 5
Window = [5,3,6] → max = 6
Window = [3,6,7] → max = 7

Output = [3,3,5,5,6,7]
```

---

### 💻 Java Code:

```java
import java.util.*;

public class SlidingWindowMaximum {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove indices outside the current window
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Remove smaller values from the end
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Add result from front when window is full
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        int[] output = maxSlidingWindow(nums, k);
        System.out.println("Sliding Window Maximums: " + Arrays.toString(output));
    }
}
```

---

### 🕒 Time & Space Complexity:

- **Time**: O(n), because each element is pushed and popped at most once.
- **Space**: O(k), for the deque storing indices.

---
