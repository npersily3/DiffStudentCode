import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Plagiarism Checker
 * A tool for finding the longest shared substring between two documents.
 *
 * @author Zach Blick
 * @author Noah Persily
 */
public class PlagiarismChecker {

    /**
     * This method finds the longest sequence of characters that appear in both texts in the same order,
     * although not necessarily contiguously.
     * @param doc1 the first document
     * @param doc2 the second
     * @return The length of the longest shared substring.
     *
     */

    public static int longestSharedSubstring(String doc1, String doc2) {
        String[] allLCS = findAllLCS(doc1, doc2);
        System.out.println("All Longest Common Subsequences:");
        for (String lcs : allLCS) {
            System.out.println(lcs);
        }
        return allLCS[0].length();

    }
    /*
       Here is the method that I am submitting everything else is ai made
       run this about return lssLength(doc1,doc2);

     */
    private static int lssLength(String doc1, String doc2) {
        // Tabulation grid
        int d1length = doc1.length();
        int d2length = doc2.length();
        int[][] grid = new int[d1length][d2length];

        // Heuristic to populate first row and column
        char first = doc2.charAt(0);

        for (int i = 0; i < grid.length; i++) {
            // Travel down a row until a match is achieved with the letter in the first column
            if(first == doc1.charAt(i)) {
                // Then fill the rest of the grid with rows
                while (i < grid.length) {
                    grid[i][0] = 1;
                    i++;
                }
                break;
            }
        }
        // Same as above but flip rows and columns
        first = doc1.charAt(0);
        for (int i = 0; i < grid[0].length; i++) {
            if(first == doc2.charAt(i)) {
                while (i < grid[0].length) {
                    grid[0][i] = 1;
                    i++;
                }
                break;
            }
        }

        // Row major traversal through the grid
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                // If the letters match set the current index to diagonal index + 1
                if(doc1.charAt(i) == doc2.charAt(j)) {
                    grid[i][j] = grid[i-1][j-1] + 1;
                }
                else {
                    // Set the current space to the greatest between the up and  left index
                    grid[i][j] = Math.max(grid[i-1][j], grid[i][j-1]);
                }
            }
        }
        // Return last space in grid which corresponds with both full words
        return grid[d1length - 1][d2length - 1];
    }

    private static Map<String, Set<String>> memo;

    public static String[] findAllLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        // Step 1: Build the DP table for LCS lengths
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Initialize memoization map
        memo = new HashMap<>();

        // Step 3: Backtrack with memoization to get all LCS
        Set<String> lcsSet = backtrackAllLCS(dp, str1, str2, m, n);

        // Convert set to array for return
        return lcsSet.toArray(new String[0]);
    }

    private static Set<String> backtrackAllLCS(int[][] dp, String str1, String str2, int i, int j) {
        // Create a unique key for the current subproblem
        String key = i + "," + j;
        if (memo.containsKey(key)) {
            return memo.get(key); // Return cached result
        }

        Set<String> result = new HashSet<>();

        // Base case: if either string is fully processed
        if (i == 0 || j == 0) {
            result.add("");
            memo.put(key, result);
            return result;
        }

        // Case 1: Characters match
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
            Set<String> subResult = backtrackAllLCS(dp, str1, str2, i - 1, j - 1);
            for (String sub : subResult) {
                result.add(sub + str1.charAt(i - 1));
            }
        }
        // Case 2: Characters don’t match
        else {
            // Explore upward path if it contributes to the LCS
            if (dp[i - 1][j] >= dp[i][j - 1]) {
                Set<String> up = backtrackAllLCS(dp, str1, str2, i - 1, j);
                result.addAll(up);
            }
            // Explore leftward path if it contributes to the LCS
            if (dp[i][j - 1] >= dp[i - 1][j]) {
                Set<String> left = backtrackAllLCS(dp, str1, str2, i, j - 1);
                result.addAll(left);
            }
        }

        memo.put(key, result);
        return result;
    }
}
