import java.io.*;
import java.util.*;
import java.math.*;

public class ParenthesesOrder {
  /*
  BigInteger[] ways = null;
  ParenthesesOrder() {
    ways = new BigInteger[101];
    ways[0] = BigInteger.ONE;
    for (int i = 1; i <= ways.length - 1; ++i) {
      ways[i] = ways[i-1].add(ways[i-1]);
    }
  }

  public String getKthString(int n, long k) {
    StringBuilder builder = new StringBuilder();
    BigInteger K = BigInteger.valueOf(k);
    if (K.compareTo(ways[n]) > 0) {
      return null;
    }
    K = K.subtract(BigInteger.ONE);
    int singleLeftCount = 0;
    while (K != BigInteger.ZERO) {
      if (K.compareTo(ways[n - 1]) >= 0) {
        while (singleLeftCount > 0) {
          builder.append(")");
          singleLeftCount--;
        }
        builder.append("()");
        K = K.subtract(ways[n-1]);
        n--;
      } else {
        builder.append("(");
        singleLeftCount++;
        n--;
      }
    }
    while (n > 0) {
      builder.append("(");
      n--;
    }
    while (builder.length() < n * 2) {
      builder.append(")");
    }
    return builder.toString();
  }
  */
 
  public String getKthString(int n, long k) {
    // ways[i][j] is how many ways to arrange i '(' and j ')'. i <= j, and if i < j,
    // there should have j - i extra '(' before. The rule is ways[i][j] = 0; i > j.
    BigInteger[][] ways = new BigInteger[n+1][n+1];
    for (int i = 0; i <= n; ++i) {
      for (int j = 0; j <= n; ++j) {
        ways[i][j] = BigInteger.ZERO;
      }
    }
    for (int i = 0; i <= n; ++i) {
      ways[0][i] = BigInteger.ONE;
    }
    for (int i = 1; i <= n; ++i) {
      for (int j = i; j <= n; ++j) {
        ways[i][j] = ways[i-1][j].add(ways[i][j-1]);
      }
    }
    StringBuilder builder = new StringBuilder();
    if (ways[n][n].compareTo(BigInteger.valueOf(k)) < 0) {
      return null;
    }
    int leftParenCount = n;
    int rightParenCount = n;
    BigInteger lastK = BigInteger.valueOf(k);
    while (leftParenCount != 0 || rightParenCount != 0) {
      if (leftParenCount != 0 && ways[leftParenCount - 1][rightParenCount].compareTo(lastK) >= 0) {
        builder.append('(');
        leftParenCount--;
      } else if (rightParenCount != 0) {
        builder.append(')');
        if (leftParenCount != 0) {
          lastK = lastK.subtract(ways[leftParenCount - 1][rightParenCount]);
        }
        rightParenCount--;
      }
    }
    return builder.toString();
  }

  public static void main(String[] args) throws IOException {
    String input = "parentheses_order_in_large.txt";
    String output = "parentheses_order_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    ParenthesesOrder order = new ParenthesesOrder();

    for (int test = 1; test <= testCount; ++test) {
      int n = scanner.nextInt();
      long k = scanner.nextLong();
      String s = order.getKthString(n, k);
      if (s == null) {
        s = "Doesn't Exist!";
      }
      writer.printf("Case #%d: %s\n", test, s);
    }
    writer.close();
  }
}
