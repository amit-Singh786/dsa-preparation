
/**
 * Problem Statement:
 * Given an array of integers and a number k, find the maximum sum of a subarray
 * of size k.
 */

public class MaxSumSubarrayOfSizeK {
    public static int maxSumSubarrayOfSizeK(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int maxSum = 0;
        int currentSum = 0;

        // Calculate the sum of the first 'k' elements
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        maxSum = currentSum;

        // Slide the window over the rest of the array
        for (int i = k; i < arr.length; i++) {
            currentSum += arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = { 2, 1, 5, 1, 3, 2 };
        int k = 3;
        System.out.println(maxSumSubarrayOfSizeK(arr, k)); // Output: 9
    }
}