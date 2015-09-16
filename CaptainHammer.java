import java.util.*;
import java.io.*;

public class CaptainHammer {
  public double calculateAngle(int V, int D) {
    double d = D * 9.8 / (V * V);
    if (d > 1.0) {
      d = 1.0;
    }
    double result = Math.asin(d) / 2.0;
    result = Math.toDegrees(result);
    return result;
  }
  
  public static void main(String[] args) throws Exception {
    String input = "captain_hammer_in_small.txt";
    String output = "captain_hammer_out_small.txt";
    //String input = "captain_hammer_in.txt";
    //String output = "captain_hammer_out.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int i = 1; i <= testCount; ++i) {
      int V = scanner.nextInt();
      int D = scanner.nextInt();
      CaptainHammer hammer = new CaptainHammer();
      double result = hammer.calculateAngle(V, D);
      writer.printf("Case #%d: %.7f\n", i, result);
    }
    writer.close();
  }
}
