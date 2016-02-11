import java.io.*;
import java.util.*;

public class Millionaire {

  double getProb(int M, double P, int X) {
    double[] dp = new double[2];
    dp[0] = 0.0;
    dp[1] = 1.0;
    for (int i = 1; i <= M; ++i) {
      double[] nextDp = new double[(dp.length - 1) * 2 + 1];
      for (int cur = 0; cur < nextDp.length; ++cur) {
        double bestProb = 0.0;
        for (int bet = 0; bet <= cur; ++bet) {
          double prob = P * dp[Math.min((cur + bet) / 2, dp.length - 1)] + (1 - P) * dp[(cur - bet) / 2];
          bestProb = Math.max(bestProb, prob);
        }
        nextDp[cur] = bestProb;
      }
      dp = nextDp;
    }
    double onePiece = 1000000;
    for (int i = 1; i <= M; ++i) {
      onePiece /= 2;
    }
    int map = (int)(X / onePiece);
    return dp[map];
  }
  
  public static void main(String[] args) throws IOException {
    String input = "millionaire_in.txt";
    String output = "millionaire_out.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    Millionaire million = new Millionaire();
    for (int test = 1; test <= testCount; ++test) {
      int M = scanner.nextInt();
      double P = scanner.nextDouble();
      int X = scanner.nextInt();
      double result = million.getProb(M, P, X);
      writer.printf("Case #%d: %f\n", test, result);
    }
    writer.close();
  }
}
