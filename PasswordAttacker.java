import java.io.*;
import java.util.*;

public class PasswordAttacker {
  final long MOD = 1000000007;

  public int countWays(int m, int n) {
    long[][] C = buildC(n);
    // dp[i][j] means how many ways to form a n length password with m keys.
    long[][] dp = new long[m + 1][n + 1];
    for (int i = 0; i <= n; ++i) {
      dp[1][i] = 1;
    }
    for (int i = 2; i <= m; ++i) {
      for (int j = i; j <= n; ++j) {
        for (int k = 1; k <= (j - i + 1); ++k) {
          long add = (C[j][k] * dp[i-1][j-k]) % MOD;
//          System.out.printf("i = %d, j = %d, k = %d, add = %d\n", i, j, k, add);
          dp[i][j] = (dp[i][j] + add) % MOD;
        }
      }
    }
    return (int)dp[m][n];
  }

  private long[][] buildC(int n) {
    long[][] C = new long[n + 1][n + 1];
    C[0][0] = 1;
    for (int i = 1; i <= n; ++i) {
      C[i][0] = C[i][i] = 1;
      for (int j = 1; j < i; ++j) {
        C[i][j] = (C[i-1][j] + C[i-1][j-1]) % MOD;
      }
    }
    return C;
  }

  public static void main(String[] args) throws IOException {
    String input = "password_attacker_in_large.txt";
    String output = "password_attacker_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      int m = scanner.nextInt();
      int n = scanner.nextInt();
      PasswordAttacker attacker = new PasswordAttacker();
      int ways = attacker.countWays(m, n);
      writer.printf("Case #%d: %d\n", test, ways);
    }
    writer.close();
  }
}
