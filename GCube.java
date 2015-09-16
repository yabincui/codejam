import java.math.*;
import java.util.*;
import java.io.*;

public class GCube {

  public BigDecimal[] getMulArray(int[] s) {
    BigDecimal[] muls = new BigDecimal[s.length];
    muls[0] = BigDecimal.valueOf(s[0]);
    for (int i = 1; i < s.length; ++i) {
      muls[i] = muls[i-1].multiply(BigDecimal.valueOf(s[i]), MathContext.DECIMAL128);
    }
    return muls;
  }

  public double[] getSumArray(int[] s) {
    double[] sums = new double[s.length];
    sums[0] = s[0];
    for (int i = 1; i < s.length; ++i) {
      sums[i] = sums[i-1] + s[i];
    }
    return sums;
  }

  public double getEdge(BigDecimal[] s, double[] sums, int L, int R) {
    int n = R - L + 1;
    BigDecimal mulResult = s[R];
    if (L != 0) {
      mulResult = mulResult.divide(s[L - 1], MathContext.DECIMAL128);
    }

    double sum = sums[R];
    if (L != 0) {
      sum -= sums[L - 1];
    }
    double average = sum / n;

    BigDecimal low = BigDecimal.valueOf(0);
    BigDecimal high = BigDecimal.valueOf(average);
    BigDecimal precision = BigDecimal.valueOf(1e-7);
    BigDecimal two = BigDecimal.valueOf(2);
    while (high.subtract(low).compareTo(precision) > 0) {
      BigDecimal mid = high.add(low, MathContext.DECIMAL128).divide(two, MathContext.DECIMAL128);
      if (mid.pow(n, MathContext.DECIMAL128).compareTo(mulResult) < 0) {
        low = mid;
      } else {
        high = mid;
      }
    }
    return high.doubleValue();
  }

  public static void main(String[] args) throws Exception {
    String input = "gcube_in_large.txt";
    String output = "gcube_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int i = 1; i <= testCount; ++i) {
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      int[] s = new int[N];
      for (int j = 0; j < N; ++j) {
        s[j] = scanner.nextInt();
      }
      int[] L = new int[M];
      int[] R = new int[M];
      for (int j = 0; j < M; ++j) {
        L[j] = scanner.nextInt();
        R[j] = scanner.nextInt();
      }
      writer.printf("Case #%d:\n", i);
      System.out.printf("Case #%d:\n", i);
      GCube cube = new GCube();
      BigDecimal[] muls = cube.getMulArray(s);
      double[] sums = cube.getSumArray(s);
      for (int j = 0; j < M; ++j) {
        System.out.printf("Case #%d: N = %d, L = %d, R = %d\n", i, N, L[j], R[j]);
        double result = cube.getEdge(muls, sums, L[j], R[j]);
        writer.printf("%.9f\n", result);
        System.out.printf("%.9f\n", result);
      }
    }
    writer.close();
  }
}
