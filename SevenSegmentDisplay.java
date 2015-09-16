import java.util.*;
import java.io.*;

public class SevenSegmentDisplay {
  public static void main(String[] args) throws IOException {
    String input = "seven_segment_display_in_large.txt";
    String output = "seven_segment_display_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();

    int[] digits = new int[] {
      0b1111110, // 0
      0b0110000, // 1
      0b1101101, // 2
      0b1111001, // 3
      0b0110011, // 4
      0b1011011, // 5
      0b1011111, // 6
      0b1110000, // 7
      0b1111111, // 8
      0b1111011, // 9
    };
    for (int test = 1; test <= testCount; ++test) {
      int N = scanner.nextInt();
      int[] s = new int[N];
      for (int i = 0; i < N; ++i) {
        String str = scanner.next();
        for (int j = 0; j < 7; ++j) {
          if (str.charAt(j) == '1') {
            s[i] |= (1 << (6 - j));
          }
        }
      }
      boolean[] valid = new boolean[10];
      int[] missingArray = new int[10];
      int[] notMissingArray = new int[10];
      for (int i = 0; i < 10; ++i) {
        int start = i;
        int missing = 0;
        int not_missing = 0;
        valid[i] = true;
        for (int j = 0; j < N && j < 10; ++j) {
          int expected = digits[start];
          int real = s[j];
          if ((real & ~expected) != 0) {
            valid[i] = false;
            break;
          }
          if ((real & missing) != 0) {
            valid[i] = false;
            break;
          }
          if ((real & not_missing) != (expected & not_missing)) {
            valid[i] = false;
            break;
          }
          missing |= expected & ~real;
          not_missing |= real;

          start = (start + 9) % 10;
        }
        if (valid[i] && N >= 10) {
          for (int j = 10; j < N; ++j) {
            if (s[j] != s[j - 10]) {
              valid[i] = false;
              break;
            }
          }
        }
        if (valid[i]) {
          missingArray[i] = missing;
          notMissingArray[i] = not_missing;
        }
      }
      int nextState = -1;
      boolean ok = true;
      for (int i = 0; i < 10; ++i) {
        if (valid[i]) {
          int d = (i + 9 * N) % 10;
          int bits = digits[d];
          int state = 0;
          for (int j = 0; j < 7; ++j) {
            if ((bits & (1 << j)) != 0) {
              if ((missingArray[i] & (1 << j)) != 0) {
              } else if ((notMissingArray[i] & (1 << j)) != 0) {
                state |= (1 << j);
              } else {
                ok = false;
                break;
              }
            }
          }
          if (!ok) {
            break;
          }
          if (nextState != -1 && nextState != state) {
            ok = false;
            break;
          }
          nextState = state;
        }
      }
      if (ok && nextState != -1) {
        int result = nextState;
        StringBuilder builder = new StringBuilder();
        for (int i = 6; i >= 0; --i) {
          builder.append((result & (1 << i)) != 0 ? '1' : '0');
        }
        writer.printf("Case #%d: %s\n", test, builder.toString());
      } else {
        writer.printf("Case #%d: ERROR!\n", test);
      }
    }
    writer.close();
  }
}
