import java.util.*;
import java.io.*;

public class GoogolString {

  public int getKthBit(long K) {
    long len = 1;
    int n = 1;
    while (len < K) {
      n++;
      len = len * 2 + 1;
    }
    boolean reverse = false;
    while (len > 1) {
      if (K == len / 2 + 1) {
        break;
      }
      if (K > len / 2 + 1) {
        K = len - K + 1;
        reverse = !reverse;
      }
      len /= 2;
    }
    return reverse ? 1 : 0;
  }

  public static void main(String[] args) throws Exception {
    String input = "googol_string_in_large.txt";
    String output = "googol_string_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int i = 1; i <= testCount; ++i) {
      long K = scanner.nextLong();
      System.out.printf("testCount = %d, K = %d\n", testCount, K);
      GoogolString string = new GoogolString();
      int result = string.getKthBit(K);
      writer.printf("Case #%d: %d\n", i, result);
    }
    writer.close();
  }
}
