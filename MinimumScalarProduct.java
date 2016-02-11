import java.io.*;
import java.util.*;

public class MinimumScalarProduct {

  long getMinProduct(int[] x, int[] y) {
    Arrays.sort(x);
    Arrays.sort(y);
    long result = 0;
    for (int i = 0; i < x.length; ++i) {
      result += (long)x[i] * y[x.length - 1 - i];
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    String input = "minimum_scalar_product_in_small.txt";
    String output = "minimum_scalar_product_out_small.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    MinimumScalarProduct product = new MinimumScalarProduct();

    for (int test = 1; test <= testCount; ++test) {
      int n = scanner.nextInt();
      int[] x = new int[n];
      for (int i = 0; i < n; ++i) {
        x[i] = scanner.nextInt();
      }
      int[] y = new int[n];
      for (int i = 0; i < n; ++i) {
        y[i] = scanner.nextInt();
      }
      long result = product.getMinProduct(x, y);
      writer.printf("Case #%d: %d\n", test, result);
    }
    writer.close();
  }
}
