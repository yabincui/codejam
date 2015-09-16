import java.util.*;
import java.io.*;

public class GCampus {

  static class Road {
    int n1, n2, d;
    Road(int n1, int n2, int d) {
      this.n1 = n1;
      this.n2 = n2;
      this.d = d;
    }
  }

  public int[] getBadRoads(int[][] map, Road[] roads) {
    int n = map.length;
    int[][] dist = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        dist[i][j] = map[i][j];
      }
    }

    // Flood.
    for (int k = 0; k < n; ++k) {
      for (int i = 0; i < n; ++i) {
        for (int j = i + 1; j < n; ++j) {
          if (i == k || j == k) {
            continue;
          }
          if (dist[i][k] != -1 && dist[k][j] != -1) {
            if (dist[i][j] == -1 || (dist[i][j] > dist[i][k] + dist[k][j])) {
              dist[i][j] = dist[j][i] = dist[i][k] + dist[k][j];
            }
          }
        }
      }
    }

    ArrayList<Integer> badRoads = new ArrayList<Integer>();
    for (int i = 0; i < roads.length; ++i) {
      int n1 = roads[i].n1;
      int n2 = roads[i].n2;
      if (dist[n1][n2] == -1 || dist[n1][n2] > roads[i].d) {
        System.out.printf("impossible\n");
      }
      if (dist[n1][n2] < roads[i].d) {
        badRoads.add(i);
      }
    }
    int[] result = new int[badRoads.size()];
    for (int i = 0; i < badRoads.size(); ++i) {
      result[i] = badRoads.get(i);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    //String input = "gcampus_in.txt";
    //String output = "gcampus_out.txt";
    String input = "gcampus_in_large.txt";
    String output = "gcampus_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int test = 1; test <= testCount; ++test) {
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      int[][] map = new int[N][N];
      for (int i = 0; i < N; ++i) {
        map[i][i] = 0;
        for (int j = i + 1; j < N; ++j) {
          map[i][j] = map[j][i] = -1;
        }
      }
      Road[] roads = new Road[M];
      for (int i = 0; i < M; ++i) {
        int U = scanner.nextInt();
        int V = scanner.nextInt();
        int C = scanner.nextInt();
        if (map[U][V] == -1 || map[U][V] > C) {
          map[U][V] = map[V][U] = C;
        }
        roads[i] = new Road(U, V, C);
      }
      GCampus campus = new GCampus();
      int[] result = campus.getBadRoads(map, roads);

      writer.printf("Case #%d:\n", test);
      System.out.printf("Case #%d:\n", test);
      for (int i = 0; i < result.length; ++i) {
        writer.printf("%d\n", result[i]);
        System.out.printf("%d\n", result[i]);
      }
    }
    writer.close();
  }
}
