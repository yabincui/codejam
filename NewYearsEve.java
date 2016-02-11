import java.io.*;
import java.util.*;
import java.math.*;

public class NewYearsEve {
  public double countQuantity(double total, int L, int N) {
    int[] levelStart = new int[L + 2];
    levelStart[1] = 1;
    for (int i = 2; i <= L + 1; ++i) {
      levelStart[i] = levelStart[i - 1] + i - 1;
    }
    BigDecimal[] layer = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.valueOf(total)};
    for (int i = 2; i <= L; ++i) {
      int prevCount = levelStart[i] - 1;
      int count = levelStart[i] + i - 1;
      BigDecimal[] nextLayer = new BigDecimal[count + 1];
      int curRow = 1;
      for (int j = 1; j <= prevCount; ++j) {
        if (j >= levelStart[curRow + 1]) {
          curRow++;
        }
        //System.out.printf("i = %d, j = %d\n", i, j);
        if (layer[j] == null) {
          continue;
        }
        BigDecimal rem = layer[j].subtract(BigDecimal.valueOf(250.0));
        if (rem.compareTo(BigDecimal.ZERO) <= 0) {
          continue;
        }
        rem = rem.divide(BigDecimal.valueOf(3.0), MathContext.DECIMAL128);
        if (nextLayer[j] == null) {
          nextLayer[j] = BigDecimal.ZERO;
        }
        nextLayer[j] = nextLayer[j].add(rem);
        //System.out.printf("nextLayer[%d]\n", j);
        int bottomLeft = j - levelStart[curRow] + levelStart[curRow + 1];
        if (nextLayer[bottomLeft] == null) {
          nextLayer[bottomLeft] = BigDecimal.ZERO;
        }
        if (nextLayer[bottomLeft+1] == null) {
          nextLayer[bottomLeft+1] = BigDecimal.ZERO;
        }
        nextLayer[bottomLeft] = nextLayer[bottomLeft].add(rem);
        nextLayer[bottomLeft+1] = nextLayer[bottomLeft+1].add(rem);
      }
      //System.out.printf("level %d, layer[1] = %f\n", i, nextLayer[1]);
      layer = nextLayer;
    }
    BigDecimal value = layer[N];
    if (value == null) {
      value = BigDecimal.ZERO;
    }
    double result = value.doubleValue();
    if (result > 250) {
      result = 250;
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    String input = "newyearseve_in_large.txt";
    String output = "newyearseve_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      int B = scanner.nextInt();
      int L = scanner.nextInt();
      int N = scanner.nextInt();
      NewYearsEve eve = new NewYearsEve();
      double quantity = eve.countQuantity(B * 750.0, L, N);
      writer.printf("Case #%d: %.7f\n", test, quantity);
    }
    writer.close();
  }
}
