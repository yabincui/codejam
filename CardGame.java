import java.io.*;
import java.util.*;

public class CardGame {
  public int findMinLeft(int[] s, int K) {
    int n = s.length;
    // dp[i][j] means the maximum numbers can be removed between s[i..j].
    int[][] dp = new int[n][n];
    for (int i = 0; i + 2 < n; ++i) {
      if (s[i+2] - s[i+1] == K && s[i+1] - s[i] == K) {
        dp[i][i+2] = 3;
      }
    }
    for (int len = 4; len <= n; ++len) {
      for (int i = 0; i <= n - len; ++i) {
        int j = i + len - 1;
        for (int k = i; k < j; ++k) {
          dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 1][j]);
        }
        if (dp[i+2][j-1] == len - 3 && s[i] + K == s[i+1] && s[i+1] + K == s[j]) {
          dp[i][j] = len;
        }
        if (dp[i+1][j-2] == len - 3 && s[i] + K == s[j-1] && s[j-1] + K == s[j]) {
          dp[i][j] = len;
        }
        for (int k = i + 2; k < j - 1; ++k) {
          if (dp[i+1][k-1] == (k-1-i-1+1) && dp[k+1][j-1] == (j-1-k-1+1) &&
                s[i] + K == s[k] && s[k] + K == s[j]) {
            dp[i][j] = len;
          }
        }
      }
    }
    return n - dp[0][n-1];
  }

  public static void main(String[] args) throws IOException {
    String input = "cardgame_in_large.txt";
    String output = "cardgame_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      int N = scanner.nextInt();
      int K = scanner.nextInt();
      int[] s = new int[N];
      for (int i = 0; i < N; ++i) {
        s[i] = scanner.nextInt();
      }
      CardGame game = new CardGame();
      int minLeft = game.findMinLeft(s, K);
      writer.printf("Case #%d: %d\n", test, minLeft);
    }
    writer.close();
  }
}
