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
        return lssLength(doc1,doc2);
    }
    private static int lssLength(String doc1, String doc2) {
        int d1length = doc1.length();
        int d2length = doc2.length();
        int[][] grid = new int[d1length][d2length];

        char first = doc2.charAt(0);
        for (int i = 0; i < grid.length; i++) {
            if(first == doc1.charAt(i)) {
                while (i < grid.length) {
                    grid[i][0] = 1;
                    i++;
                }
                break;
            }
        }
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


        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if(doc1.charAt(i) == doc2.charAt(j)) {
                    grid[i][j] = grid[i-1][j-1] + 1;
                }
                else {
                    grid[i][j] = Math.max(grid[i-1][j], grid[i][j-1]);
                }
            }
        }

        return grid[d1length - 1][d2length - 1];
    }
    private static String lssString(String doc1, String doc2) {
        int d1length = doc1.length();
        int d2length = doc2.length();
        String[][] grid = new String[d1length][d2length];

        char first = doc2.charAt(0);
        for (int i = 0; i < grid.length; i++) {
            if(first == doc1.charAt(i)) {
                while (i < grid.length) {
                    grid[i][0] = first + "";
                    i++;
                }
                break;
            }
        }
        first = doc1.charAt(0);
        for (int i = 0; i < grid[0].length; i++) {
            if(first == doc2.charAt(i)) {
                while (i < grid[0].length) {
                    grid[0][i] = first + "";
                    i++;
                }
                break;
            }
        }


        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if(doc1.charAt(i) == doc2.charAt(j)) {
                    grid[i][j] = grid[i-1][j-1] + doc1.charAt(i);
                }
                else {
                    if(grid[i][j - 1].length() > grid[i-1][j].length()) {
                        grid[i][j] = grid[i][j-1];
                    }
                    else {
                        grid[i][j] = grid[i-1][j];
                    }
                }
            }
        }

        return grid[d1length - 1][d2length - 1];
    }
}
