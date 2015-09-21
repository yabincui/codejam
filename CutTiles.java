import java.util.*;
import java.io.*;

public class CutTiles {
  static class Square {
    long edge;
    int count;

    Square(long edge, int count) {
      this.edge = edge;
      this.count = count;
    }
  }

  private void fillRange(long width, long height, ArrayList<Square> squares, int start) {
    for (int i = start; i < squares.size(); ++i) {
      long edge = squares.get(i).edge;
      if (width < edge || height < edge) {
        continue;
      }
      long count = ((width / edge) * (height / edge));
      System.out.printf("width = %d, height = %d, edge = %d, count = %d\n", width, height,
                        edge, count);
      if (count > squares.get(i).count) {
        long moreCount = count - squares.get(i).count;
        fillRange(edge * moreCount, edge, squares, i + 1);
        squares.get(i).count = 0;
      } else {
        squares.get(i).count -= count;
      }

      long remWidth = width % edge;
      long remHeight = height % edge;
      System.out.printf("remWidth = %d, remHeight = %d\n", remWidth, remHeight);
      if (remWidth > 0) {
        fillRange(remWidth, height, squares, i + 1);
      }
      if (remHeight > 0) {
        fillRange(width - remWidth, remHeight, squares, i + 1);
      }
      if (squares.get(i).count == 0) {
        squares.remove(i);
      }
      
      break;
    }
  }

  public int howManyTiles(long tileEdge, ArrayList<Square> squares) {
    int count = 0;
    while (!squares.isEmpty()) {
      fillRange(tileEdge, tileEdge, squares, 0);
      count++;
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    String input = "cuttiles_in_large.txt";
    String output = "cuttiles_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      int N = scanner.nextInt();
      long tileEdge = scanner.nextLong();
      int[] s = new int[32];
      for (int i = 0; i < N; ++i) {
        int k = scanner.nextInt();
        s[k]++;
      }
      ArrayList<Square> squares = new ArrayList<Square>();
      long edge = 1;
      for (int i = 0; i < s.length; ++i, edge *= 2) {
        if (s[i] == 0) {
          continue;
        }
        Square square = new Square(edge, s[i]);
        squares.add(0, square);
      }
      CutTiles cutTiles = new CutTiles();
      int count = cutTiles.howManyTiles(tileEdge, squares);
      writer.printf("Case #%d: %d\n", test, count);
    }
    writer.close();
  }
}
