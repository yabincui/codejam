import java.io.*;
import java.util.*;

public class Super2048 {
  static final int LEFT = 0;
  static final int RIGHT = 1;
  static final int UP = 2;
  static final int DOWN = 3;

  public void move(int[][] board, int direction) {
    if (direction == LEFT) {
      for (int i = 0; i < board.length; ++i) {
        int lastCombinePos = -1;
        for (int j = 1; j < board[0].length; ++j) {
          if (board[i][j] == 0) {
            continue;
          }
          int k = j - 1;
          while (k >= 0 && board[i][k] == 0) {
            board[i][k] = board[i][k + 1];
            board[i][k + 1] = 0;
            k--;
          }
          if (k >= 0 && board[i][k] == board[i][k + 1] && lastCombinePos != k) {
            board[i][k + 1] = 0;
            board[i][k] *= 2;
            lastCombinePos = k;
          }
        }
      }
    } else if (direction == RIGHT) {
      for (int i = 0; i < board.length; ++i) {
        int lastCombinePos = -1;
        for (int j = board[0].length - 2; j >= 0; --j) {
          if (board[i][j] == 0) {
            continue;
          }
          int k = j + 1;
          while (k < board[0].length && board[i][k] == 0) {
            board[i][k] = board[i][k - 1];
            board[i][k - 1] = 0;
            k++;
          }
          if (k < board[0].length && board[i][k] == board[i][k - 1] && lastCombinePos != k) {
            board[i][k - 1] = 0;
            board[i][k] *= 2;
            lastCombinePos = k;
          }
        }
      }
    } else if (direction == UP) {
      for (int j = 0; j < board[0].length; ++j) {
        int lastCombinePos = -1;
        for (int i = 1; i < board.length; ++i) {
          if (board[i][j] == 0) {
            continue;
          }
          int k = i - 1;
          while (k >= 0 && board[k][j] == 0) {
            board[k][j] = board[k + 1][j];
            board[k + 1][j] = 0;
            k--;
          }
          if (k >= 0 && board[k][j] == board[k + 1][j] && lastCombinePos != k) {
            board[k + 1][j] = 0;
            board[k][j] *= 2;
            lastCombinePos = k;
          }
        }
      }
    } else if (direction == DOWN) {
      for (int j = 0; j < board[0].length; ++j) {
        int lastCombinePos = -1;
        for (int i = board.length - 2; i >= 0; --i) {
          if (board[i][j] == 0) {
            continue;
          }
          int k = i + 1;
          while (k < board.length && board[k][j] == 0) {
            board[k][j] = board[k - 1][j];
            board[k - 1][j] = 0;
            k++;
          }
          if (k < board.length && board[k][j] == board[k - 1][j] && lastCombinePos != k) {
            board[k - 1][j] = 0;
            board[k][j] *= 2;
            lastCombinePos = k;
          }
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String input = "super2048_in_large.txt";
    String output = "super2048_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      int n = scanner.nextInt();
      String dir = scanner.next();
      int direction = LEFT;
      if (dir.equals("left")) {
        direction = LEFT;
      } else if (dir.equals("right")) {
        direction = RIGHT;
      } else if (dir.equals("up")) {
        direction = UP;
      } else if (dir.equals("down")) {
        direction = DOWN;
      }
      System.out.printf("direction = %d\n", direction);
      int[][] board = new int[n][n];
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          board[i][j] = scanner.nextInt();
        }
      }
      Super2048 super2048 = new Super2048();
      super2048.move(board, direction);
      writer.printf("Case #%d:\n", test);
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          writer.printf("%d%c", board[i][j], (j == n - 1) ? '\n' : ' ');
        }
      }
    }
    writer.close();
  }
}
