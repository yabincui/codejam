import java.util.*;
import java.io.*;

public class Moist {
  public int pickCount(String[] strs) {
    String max = strs[0];
    int count = 0;
    for (int i = 1; i < strs.length; ++i) {
      if (strs[i].compareTo(max) > 0) {
        max = strs[i];
      } else {
        count++;
      }
    }
    return count;
  }
  
  public static void main(String[] args) throws Exception {
    //String input = "moist_in.txt";
    //String output = "moist_out.txt";
    String input = "moist_in_small2.txt";
    String output = "moist_out_small2.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int i = 1; i <= testCount; ++i) {
      int N = scanner.nextInt();
      scanner.nextLine();
      System.out.printf("N = %d\n", N);
      String[] strs = new String[N];
      for (int j = 0; j < N; ++j) {
        strs[j] = scanner.nextLine();
        System.out.printf("strs[%d] = %s\n", j, strs[j]);
      }
      Moist moist = new Moist();
      int result = moist.pickCount(strs);
      writer.printf("Case #%d: %d\n", i, result);
    }
    writer.close();
  }
}
