import java.util.*;
import java.io.*;

public class BadHorse {

  public boolean canPartition(String[] pairA, String[] pairB) {
    HashMap<String, Integer> stringMap = new HashMap<String, Integer>();
    int indexCount = 0;
    int[] indexA = new int[pairA.length];
    int[] indexB = new int[pairB.length];

    for (int i = 0; i < pairA.length; ++i) {
      Integer indexOne = stringMap.get(pairA[i]);
      if (indexOne == null) {
        indexOne = indexCount++;
        stringMap.put(pairA[i], indexOne);
      }
      indexA[i] = indexOne;
      Integer indexTwo = stringMap.get(pairB[i]);
      if (indexTwo == null) {
        indexTwo = indexCount++;
        stringMap.put(pairB[i], indexTwo);
      }
      indexB[i] = indexTwo;
    }
    ArrayList<ArrayList<Integer>> neighbor = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < indexCount; ++i) {
      neighbor.add(new ArrayList<Integer>());
    }
    for (int i = 0; i < pairA.length; ++i) {
      int j = indexA[i];
      int k = indexB[i];
      neighbor.get(j).add(k);
      neighbor.get(k).add(j);
    }

    boolean[] visited = new boolean[indexCount];
    boolean[] color = new boolean[indexCount];
    for (int i = 0; i < visited.length; ++i) {
      if (visited[i]) {
        continue;
      }
      Queue<Integer> queue = new ArrayDeque<Integer>();
      queue.add(i);
      boolean neededColor = false;
      while (!queue.isEmpty()) {
        int curLevelCount = queue.size();
        while (curLevelCount-- != 0) {
          Integer pos = queue.poll();
          if (!visited[pos]) {
            visited[pos] = true;
            color[pos] = neededColor;
            for (int j = 0; j < neighbor.get(pos).size(); ++j) {
              queue.add(neighbor.get(pos).get(j));
            }
          } else {
            if (neededColor != color[pos]) {
              return false;
            }
          }
        }
        neededColor = !neededColor;
      }
    }
    return true;
  }
  
  public static void main(String[] args) throws Exception {
    String input = "badhorse_in_small2.txt";
    String output = "badhorse_out_small2.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int i = 1; i <= testCount; ++i) {
      int M = scanner.nextInt();
      System.out.printf("testCount = %d, M = %d\n", testCount, M);
      String[] pairA = new String[M];
      String[] pairB = new String[M];
      for (int j = 0; j < M; ++j) {
        pairA[j] = scanner.next();
        pairB[j] = scanner.next();
      }
      System.out.printf("testCount = %d\n", testCount);
      System.out.printf("Case #%d: M = %d\n", i, M);
      for (int j = 0; j < M; ++j) {
        System.out.printf("pairA[%d] = %s\n", j, pairA[j]);
        System.out.printf("pairB[%d] = %s\n", j, pairB[j]);
      }
      BadHorse badHorse = new BadHorse();
      boolean result = badHorse.canPartition(pairA, pairB);
      writer.printf("Case #%d: %s\n", i, result ? "Yes" : "No");
    }
    writer.close();
  }
}
