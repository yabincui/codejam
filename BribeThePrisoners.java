import java.io.*;
import java.util.*;

public class BribeThePrisoners {

  int minBribe(int P, int Q, int[] release) {
    // bribe[i][j] is the cost to release prisons in release[i..j], in the situation that
    // prison in releae[i-1] and release[j+1] are both released already.
    int[][] bribe = new int[Q][Q];
    for (int i = 0; i < Q; ++i) {
      int left = (i == 0) ? (release[i] - 1) : (release[i] - release[i-1] - 1);
      int right = (i == Q - 1) ? (P - release[i]) : (release[i+1] - 1 - release[i]);
      bribe[i][i] = left + right;
    }
    for (int step = 2; step <= Q; ++step) {
      for (int i = 0; i <= Q - step; ++i) {
        int j = i + step - 1;
        // select one prison between release[i..j] to release.
        int minCost = Integer.MAX_VALUE;
        for (int k = i; k <= j; ++k) {
          int left = (i == 0) ? (release[k] - 1) : (release[k] - release[i-1] - 1);
          int right = (j == Q - 1) ? (P - release[k]) : (release[j+1] - 1 - release[k]);
          int cost = left + right;
          if (k > i) {
            cost += bribe[i][k-1];
          }
          if (k < j) {
            cost += bribe[k+1][j];
          }
          minCost = Math.min(minCost, cost);
        }
        bribe[i][j] = minCost;
      }
    }
    return bribe[0][Q-1];
  }
  
  public static void main(String[] args) throws IOException {
    String input = "bribe_the_prisoners_in.txt";
    String output = "bribe_the_prisoners_out.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    BribeThePrisoners bribe = new BribeThePrisoners();

    for (int test = 1; test <= testCount; ++test) {
      int P = scanner.nextInt();
      int Q = scanner.nextInt();
      int[] release = new int[Q];
      for (int i = 0; i < Q; ++i) {
        release[i] = scanner.nextInt();
      }
      int result = bribe.minBribe(P, Q, release);
      writer.printf("Case #%d: %d\n", test, result);
    }
    writer.close();
  }
}
