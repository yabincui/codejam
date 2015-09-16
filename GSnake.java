import java.util.*;
import java.io.*;

public class GSnake {

  public int getEndLength(int R, int C, int[] time, char[] action) {
    HashSet<Long> snakeSet = new HashSet<Long>();
    Queue<Long> snakeBody = new ArrayDeque<Long>();
    HashSet<Long> usedFoodSet = new HashSet<Long>();
    long initPos = 1 * (C + 1) + 1;
    snakeBody.add(initPos);
    snakeSet.add(initPos);
    long head = initPos;
    final int LEFT = 0;
    final int UP = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    int direction = RIGHT;

    int t = 0;
    int curTimeIndex = 0;
    while (true) {
      t++;
      //System.out.printf("t = %d\n", t);
      // Move.
      long r = head / (C + 1);
      long c = head % (C + 1);
      //System.out.printf("prev (%d, %d), ", r, c);
      if (direction == LEFT) {
        c--;
        if (c <= 0) {
          c = C;
        }
      } else if (direction == UP) {
        r--;
        if (r <= 0) {
          r = R;
        }
      } else if (direction == RIGHT) {
        c++;
        if (c > C) {
          c = 1;
        }
      } else if (direction == DOWN) {
        r++;
        if (r > R) {
          r = 1;
        }
      }
      //System.out.printf("dir %d, new (%d, %d)\n", direction, r, c);
      head = r * (C + 1) + c;
      // Have food to eat.
      if ((r + c) % 2 == 1 && usedFoodSet.contains(head) == false) {
        //System.out.printf("eat (%d, %d)\n", r, c);
        usedFoodSet.add(head);
      } else {
        long tail = snakeBody.poll();
        snakeSet.remove(tail);
      }
      // Does the head hit something?
      if (snakeSet.contains(head) == true) {
        return snakeBody.size() + 1;
      }

      snakeBody.add(head);
      snakeSet.add(head);
      //System.out.printf("snakeBody.size() = %d, snakeSet.size() = %d\n", snakeBody.size(),
      //    snakeSet.size());

      final String[] direction_str = new String[]{
        "LEFT", "UP", "RIGHT", "DOWN"};

      // Change direction.
      if (curTimeIndex < time.length && time[curTimeIndex] == t) {
        char act = action[curTimeIndex];
        int old_direction = direction;
        if (act == 'L') {
          direction = (direction + 3) % 4;
        } else {
          direction = (direction + 1) % 4;
        }
        //System.out.printf("change dir from  %s to %s\n", direction_str[old_direction],
        //    direction_str[direction]);
        curTimeIndex++;
      }
      if (curTimeIndex == time.length &&
        (t - time[time.length - 1]) >  (Math.max(R, C) + snakeBody.size())) {
        // No way to continue.
        return snakeBody.size();
      }
    }
  }

/*
  public int getEndLength2(int R, int C, int[] time, int[] action) {
    int[][] map = new int[R + 1][C + 1];
    for (int i = 1; i <= R; ++i) {
      for (int j = 1; j <= C; ++j) {
        map[i][j] = ((i + j) % 2 == 1 ? 1 : 0);
      }
    }
    long headR = 1;
    long headC = 1;
    final int LEFT = 0;
    final int UP = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    int direction = RIGHT;
    Queue<Long> queue = new ArrayDeque<Long>();
    queue.add(headR * 1000 + headC);
    int curTimeIndex = 0;
    for (int t = 1; t < 10000; ++t) {
      if (direction == LEFT) {
        headC--;
        if (headC < 1) {
          headC = C;
        }
      } else if (direction == UP) {
        headR--;
        if (headR < 1) {
          headR = R;
        }
      } else if (direction == RIGHT) {
        headC++;
        if (headC > C) {
          headC = 1;
        }
      } else if (direction == DOWN) {
        headR++;
        if (headR > R) {
          headR = 1;
        }
      }
      if (map[headR][headC] == 1) {
        map[headR][headC] = 0;
      } else {
        long tail = queue.poll();
        long tailR = tail / 1000;
        long tailC = tail % 1000;
        map[tailR][tailC] = 0;
      }
      if (map[headR][headC] == 2) {
        return queue.size() + 1;
      }
      map[headR][headC] = 2;
      queue.add(headR * 1000 + headC);

      // Change direction.
      if (curTimeIndex < time.length && time[currentTimeIndex] == t) {
        if (action[curTimeIndex] == 'L') {
          direction = (direction + 3) % 4;
        } else {
          direction = (direction + 1) % 4;
        }
        curTimeIndex++;
      }
    }
    return queue.size();
  }
*/

  public static void main(String[] args) throws Exception {
    String input = "gsnake_in_large.txt";
    String output = "gsnake_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    System.out.printf("testCount = %d\n", testCount);
    for (int test = 1; test <= testCount; ++test) {
      int S = scanner.nextInt();
      int R = scanner.nextInt();
      int C = scanner.nextInt();
      int[] time = new int[S];
      char[] action = new char[S];
      for (int i = 0; i < S; ++i) {
        time[i] = scanner.nextInt();
        action[i] = scanner.next().charAt(0);
      }

      GSnake snake = new GSnake();
      int result = snake.getEndLength(R, C, time, action);
      //int result2 = snake.getEndLength2(R, C, time, action);

      writer.printf("Case #%d: %d\n", test, result);
    }
    writer.close();
  }
}
