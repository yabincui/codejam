import java.io.*;
import java.util.*;

public class CrazyRows {

  int[] getOneColumns(String[] data) {
    int rows = data.length;
    int[] oneColumns = new int[rows];
    for (int i = 0; i < rows; ++i) {
      int j = rows - 1;
      for (; j >= 0; --j) {
        if (data[i].charAt(j) == '1') {
          break;
        }
      }
      oneColumns[i] = j;
    }
    return oneColumns;
  }

  int getMinSwaps2(String[] data) {
    int rows = data.length;
    int[] oneColumns = getOneColumns(data);
    int[] occupy = new int[rows];
    for (int i = 0; i < rows; ++i) {
      occupy[i] = i;
    }
    int result = 0;
    for (int i = 0; i < rows; ++i) {
      int j = i;
      while (true) {
        int cur = occupy[j];
        if (oneColumns[cur] <= i) {
          break;
        }
        j++;
      }
      result += j - i;
      int tmp = occupy[j];
      for (int t = j - 1; t >= i; --t) {
        occupy[t+1] = occupy[t];
      }
      occupy[i] = tmp;
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    String input = "crazy_rows_in_small.txt";
    String output = "crazy_rows_out_small.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    CrazyRows crazy = new CrazyRows();

    for (int test = 1; test <= testCount; ++test) {
      int n = scanner.nextInt();
      String[] data = new String[n];
      for (int i = 0; i < n; ++i) {
        data[i] = scanner.next();
      }
      int result = crazy.getMinSwaps2(data);
      writer.printf("Case #%d: %d\n", test, result);
    }
    writer.close();
  }
}
